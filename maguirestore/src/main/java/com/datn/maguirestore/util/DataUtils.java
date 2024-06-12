package com.datn.maguirestore.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * @author nguyenkhanhhoa
 */
@Slf4j
@NoArgsConstructor
public class DataUtils {

    private static final char KEY_ESCAPE = '\\';

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String trim(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        if (obj instanceof String) {
            return ((String) obj).trim();
        }
        return parseToString(obj);
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static void trimValue(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.getType().getName().equals(String.class.getName())) {
                    field.setAccessible(true);
                    String value = String.valueOf(FieldUtils.readDeclaredField(object, field.getName(), true));
                    if (value != null && !value.equals("null")) {
                        field.set(object, value.trim());
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public static String parseToString(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        return String.valueOf(obj);
    }

    public static String likeSpecialToStr(String str) {
        str = str.trim();
        str = str.replace("_", KEY_ESCAPE + "_");
        str = str.replace("%", KEY_ESCAPE + "%");
        return str;
    }

    public static String makeLikeStr(String str) {
        if (isNullOrEmpty(str)) {
            return "%%";
        }
        return "%" + str + "%";
    }

    public static String safeToString(Object obj1, String defaultValue) {
        if (obj1 == null || safeEqual(obj1.toString(), "null")) {
            return defaultValue;
        }
        return obj1.toString();
    }

    public static boolean safeEqual(String obj1, String obj2) {
        if (obj1 == null && obj2 == null) return true; else if (Objects.equals(obj1, obj2)) return true;
        return ((obj1 != null) && (obj2 != null) && obj1.equals(obj2));
    }

    public static String safeToString(Object obj1) {
        return safeToString(obj1, "");
    }

    public static String toUpperCase(String str) {
        if (isNullOrEmpty(str)) return str;
        return str.toUpperCase();
    }

    public static File multipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            return file;
        } catch (IOException e) {
            // Handle the exception, e.g., log it or return an error response.
            throw e;
        } finally {
            file.deleteOnExit();
        }
    }

    public static Instant parseToInstant_yyyy_MM_dd_HH_mm_ss(String dateString) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDate = LocalDateTime.parse(dateString, df);
        Instant instant = startDate.toInstant(ZoneOffset.UTC);
        return instant;
    }

    public static Instant getStartOfDay_yyyy_MM_dd_HH_mm_ss(String dateString) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(dateString, df);
        Instant instant = startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        return instant;
    }

    public static Instant getEndOfDay_yyyy_MM_dd_HH_mm_ss(String dateString) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, df);
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        Instant instant = endOfDay.toInstant(ZoneOffset.UTC);
        return instant;
    }

    public static Instant getCurrentDateTime() {
        return Instant.now().plus(7, ChronoUnit.HOURS);
    }

    public static String replaceSpecialCharacters(String str) {
        str = str.trim();
        str = str.replace("-", "");
        str = str.replace("%", "");
        str = str.replace("/", "");
        return str;
    }

    public static Instant toInstant(String instant) {
        return LocalDateTime.parse(instant).toInstant(ZoneOffset.UTC);
    }
}
