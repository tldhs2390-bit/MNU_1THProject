package com.izakaya.sion.service;

import com.izakaya.sion.entity.ReservationEntity;
import com.izakaya.sion.entity.StoreEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class LineMessageService {

    @Value("${line.messaging.channel-access-token}")
    private String channelAccessToken;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String PUSH_URL =
            "https://api.line.me/v2/bot/message/push";

    /* =========================
       기존 텍스트 메시지
    ========================= */
    public void pushText(String toLineUserId, String text) {

        Map<String, Object> body = new HashMap<>();
        body.put("to", toLineUserId);

        Map<String, Object> msg = new HashMap<>();
        msg.put("type", "text");
        msg.put("text", text);

        body.put("messages", List.of(msg));

        send(body);
    }

    /* =========================
       예약 확정 Flex Message
    ========================= */
    public void pushReservationConfirmed(
            String lineUid,
            ReservationEntity resv,
            StoreEntity store
    ) {
        Map<String, Object> body =
                buildReservationConfirmedFlex(lineUid, resv, store);
        send(body);
    }

    /* =========================
       예약 취소 Flex Message ⭐ 추가
    ========================= */
    public void pushReservationCancelled(
            String lineUid,
            ReservationEntity resv,
            StoreEntity store
    ) {
        Map<String, Object> body =
                buildReservationCancelledFlex(lineUid, resv, store);
        send(body);
    }

    /* =========================
       실제 HTTP 전송
    ========================= */
    private void send(Map<String, Object> body) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(channelAccessToken);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        restTemplate.postForEntity(PUSH_URL, entity, String.class);
    }

    /* =========================
       예약 확정 Flex JSON
    ========================= */
    private Map<String, Object> buildReservationConfirmedFlex(
            String lineUid,
            ReservationEntity resv,
            StoreEntity store
    ) {

        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");

        return Map.of(
            "to", lineUid,
            "messages", List.of(
                Map.of(
                    "type", "flex",
                    "altText", "ご予約が確定しました",
                    "contents", Map.of(
                        "type", "bubble",

                        /* ===== HEADER ===== */
                        "header", Map.of(
                            "type", "box",
                            "layout", "vertical",
                            "backgroundColor", "#06C755",
                            "contents", List.of(
                                Map.of(
                                    "type", "text",
                                    "text", "ご予約完了",
                                    "color", "#FFFFFF",
                                    "weight", "bold",
                                    "align", "center"
                                )
                            )
                        ),

                        /* ===== BODY ===== */
                        "body", Map.of(
                            "type", "box",
                            "layout", "vertical",
                            "spacing", "md",
                            "contents", List.of(
                                Map.of(
                                    "type", "text",
                                    "text", "ご予約が確定しました",
                                    "weight", "bold",
                                    "size", "lg"
                                ),
                                separator(),
                                textRow("予約日時",
                                        resv.getResvAt().format(fmt)),
                                textRow("店舗名", store.getName()),
                                textRow("人数",
                                        resv.getPartyCnt() + "名")
                            )
                        ),

                        /* ===== FOOTER ===== */
                        "footer", Map.of(
                            "type", "box",
                            "layout", "vertical",
                            "spacing", "sm",
                            "contents", List.of(
                                Map.of(
                                    "type", "button",
                                    "style", "primary",
                                    "color", "#06C755",
                                    "action", Map.of(
                                        "type", "uri",
                                        "label", "詳細を確認する",
                                        "uri", "https://example.com/reservation"
                                    )
                                )
                            )
                        )
                    )
                )
            )
        );
    }

    /* =========================
       예약 취소 Flex JSON ⭐ 추가
    ========================= */
    private Map<String, Object> buildReservationCancelledFlex(
            String lineUid,
            ReservationEntity resv,
            StoreEntity store
    ) {

        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");

        return Map.of(
            "to", lineUid,
            "messages", List.of(
                Map.of(
                    "type", "flex",
                    "altText", "ご予約がキャンセルされました",
                    "contents", Map.of(
                        "type", "bubble",

                        /* ===== HEADER ===== */
                        "header", Map.of(
                            "type", "box",
                            "layout", "vertical",
                            "backgroundColor", "#D9534F",
                            "contents", List.of(
                                Map.of(
                                    "type", "text",
                                    "text", "予約キャンセル通知",
                                    "color", "#FFFFFF",
                                    "weight", "bold",
                                    "align", "center"
                                )
                            )
                        ),

                        /* ===== BODY ===== */
                        "body", Map.of(
                            "type", "box",
                            "layout", "vertical",
                            "spacing", "md",
                            "contents", List.of(
                                Map.of(
                                    "type", "text",
                                    "text", "ご予約はキャンセルされました",
                                    "weight", "bold",
                                    "size", "lg"
                                ),
                                separator(),
                                textRow("予約日時",
                                        resv.getResvAt().format(fmt)),
                                textRow("店舗名", store.getName()),
                                textRow("人数",
                                        resv.getPartyCnt() + "名"),
                                Map.of(
                                    "type", "text",
                                    "text",
                                    "ご不明点がございましたらお問い合わせください。",
                                    "size", "sm",
                                    "color", "#777777",
                                    "wrap", true
                                )
                            )
                        ),

                        /* ===== FOOTER ===== */
                        "footer", Map.of(
                            "type", "box",
                            "layout", "vertical",
                            "spacing", "sm",
                            "contents", List.of(
                                Map.of(
                                    "type", "button",
                                    "style", "secondary",
                                    "action", Map.of(
                                        "type", "uri",
                                        "label", "お問い合わせ",
                                        "uri", "https://example.com/contact"
                                    )
                                )
                            )
                        )
                    )
                )
            )
        );
    }

    /* =========================
       공통 컴포넌트
    ========================= */
    private Map<String, Object> separator() {
        return Map.of("type", "separator");
    }

    private Map<String, Object> textRow(String label, String value) {
        return Map.of(
            "type", "box",
            "layout", "baseline",
            "contents", List.of(
                Map.of(
                    "type", "text",
                    "text", label,
                    "size", "sm",
                    "color", "#555555",
                    "flex", 2
                ),
                Map.of(
                    "type", "text",
                    "text", value,
                    "size", "sm",
                    "color", "#111111",
                    "flex", 5,
                    "wrap", true
                )
            )
        );
    }
}