package com.cognizant.projectmanager.util;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppUtil {

    public static LocalDate getLocalDate(final Date date) {
        return date == null ? null : date.toLocalDate();
    }

    public static LocalDateTime getLocalDateTime(final Timestamp time) {
        return time == null ? null : time.toLocalDateTime();
    }

    public static Date getSqlDate(final LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

    public static boolean getBoolean(final String value) {
        return  "Y".equalsIgnoreCase(value) ? true : false;
    }

    public static String getStrFromBoolean(final boolean value) {
        return value ? "Y" : "N";
    }

    public static <T> T getOptionalValue(final ResultSet rs, final String columnName, final Class<T> clazz) throws SQLException {
        final T value = rs.getObject(columnName, clazz);
        return rs.wasNull() ? null : value;
    }
}
