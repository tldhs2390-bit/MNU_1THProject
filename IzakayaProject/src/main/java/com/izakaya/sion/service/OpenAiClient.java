package com.izakaya.sion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class OpenAiClient {

    private final WebClient webClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public OpenAiClient(@Value("${openai.api.key:}") String apiKey) {

        // ✅ apiKey 없으면 Authorization 헤더가 빈 값이 되는데,
        // 이 경우 호출 시 401 날 수 있으니 실제 운영에선 체크/예외처리 권장
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * prompt + (옵션) 이미지(dataURL 또는 base64)로 요청하고
     * 모델이 생성한 "JSON 문자열"만 반환합니다.
     */
    public String callForJson(String prompt, String imageBase64OrDataUrl) {

        // ✅ Responses API 입력(텍스트 + 옵션 이미지)
        Map<String, Object> inputText = Map.of(
                "type", "input_text",
                "text", prompt
        );

        // image는 dataURL("data:image/png;base64,...") 형태면 그대로 사용 가능
        // 만약 순수 base64만 넘어온다면 dataURL로 감싸주는 처리도 함께 넣음
        Map<String, Object> inputImage = null;
        if (imageBase64OrDataUrl != null && !imageBase64OrDataUrl.isBlank()) {
            String url = imageBase64OrDataUrl.trim();
            if (!url.startsWith("data:image/")) {
                // 순수 base64로 왔다고 가정하고 png로 감쌈(필요 시 jpeg로 변경)
                url = "data:image/png;base64," + url;
            }
            inputImage = Map.of(
                    "type", "input_image",
                    "image_url", url
            );
        }

        List<Map<String, Object>> content =
                (inputImage == null) ? List.of(inputText) : List.of(inputText, inputImage);

        Map<String, Object> userMessage = Map.of(
                "role", "user",
                "content", content
        );

        // ✅ body 구성
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("model", "gpt-4.1-mini");
        body.put("input", List.of(userMessage));

        // ✅ JSON만 출력하도록 강하게 요구 (가장 단순/호환 좋은 방식: prompt에서 JSON-only 강제)
        // (Structured Outputs를 쓰는 방식도 가능하지만, 문법이 바뀌면 깨질 수 있어 여기선 안정형으로 처리)
        body.put("max_output_tokens", 300);

        try {
            String raw = webClient.post()
                    .uri("/responses")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();

            return extractOutputText(raw);

        } catch (WebClientResponseException.TooManyRequests e) {
            // 429
            throw new RuntimeException("AI 요청이 많아 잠시 후 다시 시도해 주세요. (429)", e);

        } catch (WebClientResponseException e) {
            // 401/403/500 등
            String errBody = safeString(e.getResponseBodyAsByteArray());
            throw new RuntimeException("AI 호출 실패: " + e.getStatusCode() + " / " + errBody, e);

        } catch (Exception e) {
            throw new RuntimeException("AI 호출 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", e);
        }
    }

    /**
     * Responses API 전체 JSON(raw)에서
     * 모델이 만든 output_text만 뽑아서 반환합니다.
     */
    private String extractOutputText(String rawJson) {
        if (rawJson == null || rawJson.isBlank()) return "";

        try {
            JsonNode root = mapper.readTree(rawJson);

            // ✅ 표준적으로 output 배열 안의 content 배열에 output_text가 들어있음
            JsonNode output = root.path("output");
            if (output.isArray()) {
                for (JsonNode outItem : output) {
                    JsonNode content = outItem.path("content");
                    if (content.isArray()) {
                        for (JsonNode c : content) {
                            if ("output_text".equals(c.path("type").asText())) {
                                String text = c.path("text").asText("");
                                return (text == null) ? "" : text.trim();
                            }
                        }
                    }
                }
            }

            // ✅ 혹시 구조가 다르면 fallback: 전체 문자열에서 JSON 객체만 잘라오기
            return extractFirstJsonObject(rawJson);

        } catch (Exception ignore) {
            return extractFirstJsonObject(rawJson);
        }
    }

    /**
     * 응답이 JSON-only가 아니더라도, 첫 번째 {...} 구간을 잡아내는 안전장치
     */
    private String extractFirstJsonObject(String s) {
        if (s == null) return "";
        int start = s.indexOf('{');
        int end = s.lastIndexOf('}');
        if (start >= 0 && end > start) {
            return s.substring(start, end + 1).trim();
        }
        return "";
    }

    private String safeString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return "";
        return new String(bytes, StandardCharsets.UTF_8);
    }
}