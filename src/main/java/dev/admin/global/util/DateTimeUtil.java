package dev.admin.global.util;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {

    public static LocalDateTime parseStartOfDay(String dateStr) {
        try {
            return LocalDate.parse(dateStr).atStartOfDay();
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.INVALID_DATE_FORMAT);
        }
    }

    public static LocalDateTime parseEndOfDay(String dateStr) {
        try {
            return LocalDate.parse(dateStr).atTime(LocalTime.MAX);
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.INVALID_DATE_FORMAT);
        }
    }

    public static void validateStartBeforeEnd(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new GeneralException(ErrorStatus.INVALID_DATE_FORMAT);
        }
    }
}
