package com.izakaya.sion.service.pos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izakaya.sion.domain.Menu;
import com.izakaya.sion.entity.*;
import com.izakaya.sion.pos.dto.PosPayRequest;
import com.izakaya.sion.pos.dto.PosPayResponse;
import com.izakaya.sion.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PosPaymentService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;

    // ✅ 하드코딩 DRINK 매핑 (menuCode -> (name, priceYen))
    // - JS에서 { menuCode: 'SOUR_LEMON', qty: 1 } 처럼 보내면 여기서 처리
    private static final Map<String, DrinkDef> DRINK_MAP = new HashMap<>();
    static {
        // ===== 하이볼 =====
        DRINK_MAP.put("HB_KAKUBIN", new DrinkDef("角瓶ハイボール", 500));
        DRINK_MAP.put("HB_YUZU", new DrinkDef("柚子ハイボール", 550));
        DRINK_MAP.put("HB_UME", new DrinkDef("梅ハイボール", 550));
        DRINK_MAP.put("HB_EARL_GREY", new DrinkDef("アールグレイハイボール", 600));
        DRINK_MAP.put("HB_YANTAI", new DrinkDef("煙台ハイボール", 650));
        DRINK_MAP.put("HB_MIKAN", new DrinkDef("みかんハイボール", 650));
        DRINK_MAP.put("HB_JACK", new DrinkDef("ジャックダニエルハイボール", 650));
        DRINK_MAP.put("HB_HAKATA", new DrinkDef("博多ハイボール", 700));
        DRINK_MAP.put("HB_MEGA", new DrinkDef("メガハイボール", 950));

        // ===== サワー =====
        DRINK_MAP.put("SOUR_LEMON_HOUSE", new DrinkDef("自家製レモンサワー", 600));
        DRINK_MAP.put("SOUR_GRAPEFRUIT", new DrinkDef("グレープフルーツサワー", 550));
        DRINK_MAP.put("SOUR_GREEN_APPLE", new DrinkDef("青リンゴサワー", 550));
        DRINK_MAP.put("SOUR_UMEBOSHI", new DrinkDef("梅干しサワー", 600));
        DRINK_MAP.put("SOUR_CALPIS", new DrinkDef("カルピスサワー", 550));

        // ===== ビール =====
        DRINK_MAP.put("BEER_DRAFT_KIRIN", new DrinkDef("生ビール(キリン)", 600));
        DRINK_MAP.put("BEER_DRAFT_ASAHI", new DrinkDef("生ビール(アサヒ)", 600));
        DRINK_MAP.put("BEER_MEGA_KIRIN", new DrinkDef("メガ生ビール(キリン)", 950));
        DRINK_MAP.put("BEER_BOTTLE_SAPPORO", new DrinkDef("瓶ビール(サッポロ)", 700));
        DRINK_MAP.put("BEER_GUINNESS", new DrinkDef("ギネス", 750));
        DRINK_MAP.put("BEER_BUDWEISER", new DrinkDef("バドワイザー", 650));

        // ===== 日本酒 =====
        DRINK_MAP.put("SAKE_AMAGIRI", new DrinkDef("天霧 (結霧)", 650));
        DRINK_MAP.put("SAKE_DEWAZAKURA", new DrinkDef("出羽桜 (桜山)", 650));
        DRINK_MAP.put("SAKE_HOOH", new DrinkDef("鳳凰 (美田)", 800));
        DRINK_MAP.put("SAKE_HIROKI", new DrinkDef("廣喜 (大代)", 900));
        DRINK_MAP.put("SAKE_YONEYAMA", new DrinkDef("米鶴 (超辛)", 950));
        DRINK_MAP.put("SAKE_HITAKAMI", new DrinkDef("日高見 (真鯛)", 950));

        // ===== 기타 =====
        DRINK_MAP.put("SOJU_CHAMISUL", new DrinkDef("チャミスル", 500));
    }

    @Transactional
    public PosPayResponse pay(Long storeId, PosPayRequest req) {

        var store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("store not found"));

        if (req == null || req.getItems() == null || req.getItems().isEmpty()) {
            throw new IllegalArgumentException("cart empty");
        }

        // ✅ tableNo 필수 (JS에서 무조건 보냄)
        if (req.getTableNo() == null) {
            throw new IllegalArgumentException("tableNo required");
        }

        // 1) 주문 생성
        OrderEntity order = new OrderEntity();
        order.setStore(store);
        order.setTableNo(req.getTableNo());
        order.setStatus(OrderStatus.PAID);
        order.setCreatedAt(LocalDateTime.now()); // 기존 코드 유지
        orderRepository.save(order);

        long total = 0L;

        // 2) 주문 아이템 생성
        var items = new ArrayList<OrderItemEntity>();

        for (var it : req.getItems()) {
            // ✅ qty 검증은 공통
            if (it == null || it.getQty() == null || it.getQty() <= 0) continue;

            // =========================
            // ✅ 1) DB 메뉴(menuId) 처리
            // =========================
            if (it.getMenuId() != null) {

                Menu menu = menuRepository.findById(it.getMenuId())
                        .orElseThrow(() -> new IllegalArgumentException("menu not found: " + it.getMenuId()));

                int unitPriceInt = parsePriceYen(menu.getPriceText());
                long unitPrice = (long) unitPriceInt;

                OrderItemEntity oi = new OrderItemEntity();
                oi.setOrder(order);

                oi.setMenuName(menu.getName());
                oi.setUnitPrice(unitPrice);
                oi.setQuantity(it.getQty());
                oi.recalcLineTotal();

                total += (oi.getLineTotal() == null ? 0L : oi.getLineTotal());
                items.add(oi);
                continue;
            }

            // =========================
            // ✅ 2) 하드코딩 DRINK(menuCode) 처리
            // =========================
            String code = null;
            try {
                // PosPayRequest.Item에 menuCode 필드가 추가되어 있어야 동작합니다.
                code = it.getMenuCode();
            } catch (Exception ignored) {
                code = null;
            }

            if (code != null && !code.isBlank()) {
                DrinkDef def = DRINK_MAP.get(code.trim().toUpperCase());
                if (def == null) {
                    throw new IllegalArgumentException("unknown menuCode: " + code);
                }

                OrderItemEntity oi = new OrderItemEntity();
                oi.setOrder(order);

                oi.setMenuName(def.name);
                oi.setUnitPrice((long) def.priceYen);
                oi.setQuantity(it.getQty());
                oi.recalcLineTotal();

                total += (oi.getLineTotal() == null ? 0L : oi.getLineTotal());
                items.add(oi);
            }
        }

        if (items.isEmpty()) {
            throw new IllegalArgumentException("valid items empty");
        }

        orderItemRepository.saveAll(items);

        // ✅ 주문 합계 스냅샷 저장 (기존 기능 영향 없음)
        order.setTotalAmount(total);
        orderRepository.save(order);

        // 3) 결제 생성(PAID)
        PaymentEntity p = new PaymentEntity();
        p.setOrder(order);
        p.setPaidAmount(total);
        p.setStatus(PaymentStatus.PAID);

        // ✅ String -> Enum 변환 (기본 CASH)
        PaymentMethod method = PaymentMethod.CASH;
        if (req.getMethod() != null && !req.getMethod().isBlank()) {
            try {
                method = PaymentMethod.valueOf(req.getMethod().trim().toUpperCase());
            } catch (Exception ignored) {
                method = PaymentMethod.CASH;
            }
        }
        p.setMethod(method);

        p.setPaidAt(LocalDateTime.now());
        paymentRepository.save(p);

        return new PosPayResponse(order.getId(), p.getId(), total);
    }

    private int parsePriceYen(String priceText) {
        if (priceText == null) return 0;
        String digits = priceText.replace(",", "").replaceAll("[^0-9]", "");
        if (digits.isBlank()) return 0;
        try {
            return Integer.parseInt(digits);
        } catch (Exception e) {
            return 0;
        }
    }

    private static class DrinkDef {
        final String name;
        final int priceYen;
        DrinkDef(String name, int priceYen) {
            this.name = name;
            this.priceYen = priceYen;
        }
    }
}