package com.example.mindkeep.shared.domain;

import java.time.LocalDate;

public record DateRange(LocalDate since, LocalDate to) {

    public DateRange {
        if (since == null && to == null) {
            throw new IllegalArgumentException("At least one of 'since' or 'to' must be specified");
        }
        if (since != null && to != null && since.isAfter(to)) {
            throw new IllegalArgumentException("'since' cannot be after 'to'");
        }
    }

    public static DateRange currentMonth() {
        LocalDate now = LocalDate.now();
        return new DateRange(now.withDayOfMonth(1), now.withDayOfMonth(now.lengthOfMonth()));
    }

    public static DateRange defaultRange() {
        LocalDate now = LocalDate.now();
        return new DateRange(now.minusYears(100), now.plusYears(100));
    }

    public static DateRange of(LocalDate since, LocalDate to) {
        LocalDate defaultSince = since == null ? LocalDate.now().minusYears(100) : since;
        LocalDate defaultTo = to == null ? LocalDate.now().plusYears(100) : to;
        return new DateRange(defaultSince, defaultTo);
    }
}
