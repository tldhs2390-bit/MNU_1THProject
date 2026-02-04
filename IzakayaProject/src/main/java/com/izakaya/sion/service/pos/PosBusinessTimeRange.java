package com.izakaya.sion.service.pos;

import java.time.*;

public final class PosBusinessTimeRange {

    private static final ZoneId ZONE = ZoneId.of("Asia/Seoul");
    private static final LocalTime DAY_CUTOFF = LocalTime.of(4, 0); // ✅ 04:00 기준

    private PosBusinessTimeRange() {}

    /**
     * ✅ 오늘 영업일 기준 (03:00 ~ 다음날 03:00)
     *
     * 규칙:
     * - 00:00 ~ 02:59 → 어제 영업일
     * - 03:00 ~ 23:59 → 오늘 영업일
     *
     * 결과 범위:
     *   businessDate 03:00 ~ businessDate+1 03:00
     */
    public static Range todayBusinessRange() {

        ZonedDateTime now = ZonedDateTime.now(ZONE);
        LocalDate today = now.toLocalDate();
        LocalTime nowTime = now.toLocalTime();

        // ✅ 03:00 이전이면 어제 영업일
        LocalDate businessDate =
                nowTime.isBefore(DAY_CUTOFF)
                        ? today.minusDays(1)
                        : today;

        LocalDateTime from = LocalDateTime.of(businessDate, DAY_CUTOFF);
        LocalDateTime to   = LocalDateTime.of(businessDate.plusDays(1), DAY_CUTOFF);

        return new Range(from, to);
    }

    public record Range(LocalDateTime from, LocalDateTime to) {}
}