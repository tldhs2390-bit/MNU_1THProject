package com.izakaya.sion.service;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.izakaya.sion.domain.MenuAiRequest;

import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class MenuAiService {

    private final OpenAiClient openAiClient;

    // ✅ AI가 선택 가능한 카테고리 (최종 고정)
    private static final Set<String> ALLOWED_CATEGORIES =
            Set.of("SINGLE", "MAIN", "FRIED_PAN", "SNACK");

    // ✅ 스낵 가격 기준
    private static final int SNACK_PRICE_LIMIT = 600;

    public Map<String, String> generate(MenuAiRequest req) {

        String prompt = """
                あなたは居酒屋メニュー作成の専門家です。

                【厳守ルール】
                ・カテゴリは以下の中から必ず1つだけ選択してください
                ・新しいカテゴリを作ってはいけません

                【カテゴリ候補】
                %s

                【入力情報】
                メニュー名: %s
                価格: %s
                原産地: %s
                アレルギー: %s
                メニュー特徴: %s

                【説明文条件】
                ・80〜140文字
                ・定型表現は禁止
                ・調理法、食感、香り、提供シーンを含める
                ・実際の居酒屋メニューとして自然な日本語

                【出力形式(JSONのみ)】
                {
                  "category": "SINGLE",
                  "description": "説明文"
                }
                """.formatted(
                String.join(", ", ALLOWED_CATEGORIES),
                n(req.getName()),
                n(req.getPrice()),
                n(req.getOrigin()),
                n(req.getAllergy()),
                n(req.getFeatures())
        );

        String image =
                StringUtils.hasText(req.getImageBase64())
                        ? req.getImageBase64().trim()
                        : null;

        String aiJson = openAiClient.callForJson(prompt, image);

        Map<String, String> result = parse(aiJson);

        // ✅ 완전 안전 fallback
        if (result.isEmpty()) {
            return Map.of(
                    "category", "SINGLE",
                    "description",
                    "香ばしく仕上げた一品。食感のアクセントと風味のバランスが良く、お酒のお供にも食事の一品にもおすすめです。"
            );
        }

        // ✅ 규칙 기반 최종 보정
        String fixedCategory = overrideCategory(
                req.getName(),
                req.getPrice(),
                result.get("category")
        );

        return Map.of(
                "category", fixedCategory,
                "description", result.get("description")
        );
    }

    private Map<String, String> parse(String json) {
        try {
            if (!StringUtils.hasText(json)) return Map.of();

            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map =
                    mapper.readValue(json, new TypeReference<>() {});

            String category = n(map.get("category"));
            String desc = n(map.get("description"));

            if (!ALLOWED_CATEGORIES.contains(category)) {
                category = "SINGLE";
            }

            if (desc.isEmpty()) return Map.of();

            return Map.of(
                    "category", category,
                    "description", desc
            );

        } catch (Exception e) {
            return Map.of();
        }
    }

    /**
     * ✅ 카테고리 최종 보정 규칙
     */
    private String overrideCategory(String name, String priceText, String aiCategory) {

        String safeName = StringUtils.hasText(name) ? name.trim() : "";
        String lower = safeName.toLowerCase();

        /* ===============================
           1️⃣ 꼬치류 → 무조건 SINGLE
        =============================== */
        if (!safeName.isEmpty()) {
            if (containsAny(lower,
                    "skewer", "kushi", "yakitori")
                || containsAny(safeName,
                    "串", "꼬치", "야키토리", "焼き鳥", "焼鳥")) {
                return "SINGLE";
            }
        }

        /* ===============================
           2️⃣ 지지미/チヂミ → FRIED_PAN
        =============================== */
        if (!safeName.isEmpty()) {
            if (safeName.endsWith("チヂミ")
                    || safeName.endsWith("チジミ")
                    || safeName.endsWith("지지미")
                    || safeName.endsWith("지짐이")) {
                return "FRIED_PAN";
            }
        }

        /* ===============================
           3️⃣ 샐러드 → SNACK
        =============================== */
        if (!safeName.isEmpty()) {
            if (safeName.endsWith("サラダ")
                    || lower.endsWith("salad")
                    || safeName.endsWith("샐러드")) {
                return "SNACK";
            }
        }

        /* ===============================
           4️⃣ 치킨/닭/난바/가라아게 → FRIED_PAN
        =============================== */
        if (!safeName.isEmpty()) {
            if (containsAny(lower,
                    "chicken", "fried", "karaage", "nanban", "namba")
                || containsAny(safeName,
                    "チキン", "鶏", "とり", "唐揚げ", "から揚げ",
                    "フライ", "南蛮", "タルタル")) {
                return "FRIED_PAN";
            }
        }

        /* ===============================
           5️⃣ 면 / 덮밥 / 소바 → MAIN
           ✅ [추가] そば / 蕎麦 / 소바
        =============================== */
        if (!safeName.isEmpty()) {
            if (safeName.endsWith("麺")
                    || safeName.endsWith("丼")
                    || safeName.endsWith("どん")
                    || safeName.endsWith("ドン")
                    || containsAny(lower, "soba")
                    || containsAny(safeName, "そば", "蕎麦", "소바")) {
                return "MAIN";
            }
        }

        /* ===============================
           6️⃣ 가격 기준 → SNACK
        =============================== */
        Integer price = extractPrice(priceText);
        if (price != null && price <= SNACK_PRICE_LIMIT) {
            return "SNACK";
        }

        /* ===============================
           7️⃣ AI 결과 유지
        =============================== */
        return ALLOWED_CATEGORIES.contains(aiCategory) ? aiCategory : "SINGLE";
    }

    private boolean containsAny(String text, String... keywords) {
        if (!StringUtils.hasText(text)) return false;
        for (String k : keywords) {
            if (StringUtils.hasText(k) && text.contains(k)) return true;
        }
        return false;
    }

    /**
     * "¥600（税込）" / "600" / "600円" 등에서 숫자만 추출
     */
    private Integer extractPrice(String priceText) {
        if (!StringUtils.hasText(priceText)) return null;

        String digits = priceText.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) return null;

        try {
            return Integer.parseInt(digits);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String n(String s) {
        return s == null ? "" : s.trim();
    }
}