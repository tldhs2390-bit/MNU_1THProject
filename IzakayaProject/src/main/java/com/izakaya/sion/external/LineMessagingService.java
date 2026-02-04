package com.izakaya.sion.external;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LineMessagingService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.line.me")
            .build();

    @Value("${line.messaging.channel-access-token}")
    private String channelAccessToken;

    public void pushText(String toUserId, String text) {
        Map<String, Object> body = Map.of(
                "to", toUserId,
                "messages", new Object[]{
                        Map.of("type", "text", "text", text)
                }
        );

        webClient.post()
                .uri("/v2/bot/message/push")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + channelAccessToken)
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}