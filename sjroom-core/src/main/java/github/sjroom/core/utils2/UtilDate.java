package github.sjroom.core.utils2;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date 工具类
 * <p>
 * 0、不使用SimpleDateFormat(创建开销大,线程不安全)
 * 1、FastDateFormat 是线程安全的format对象,FastDateFormat支持全局缓存，效率比ThreadLocal方案高
 * 2、默认TimeZone是东八区
 * 3、日期计算推荐LocalDate LocalTime对象, 避免使用可变的Calendar对象
 */
public abstract class UtilDate {


    private static final ConcurrentHashMap<String, TimeZone> timeZoneCache = new ConcurrentHashMap<>();

    public static final String PatternMvc = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";// mvc.format
    public static final String PatternDate = "yyyy-MM-dd";
    public static final TimeZone tzShanghai = TimeZone.getTimeZone("Asia/Shanghai");//东八区
    public static final TimeZone tzUTC = TimeZone.getTimeZone(ZoneOffset.UTC);//标准时区


    //This class is immutable and thread-safe.
    public static final DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern(PatternDate);


    /**
     * @param dateStr str格式 yyyy-MM-dd
     * @return LocalDate
     */
    public static LocalDate parseDateLocal(String dateStr) {
        if (StringUtils.isBlank(dateStr)) return null;
        return LocalDate.parse(dateStr, DateFormatter);
    }

    public static String formatDateStr(LocalDate localDate) {
        if (localDate == null) return null;
        return localDate.format(DateFormatter);
    }

    public static String format(Date date, String pattern) {
        return format(date, pattern, tzUTC, null);
    }

    public static String format(Date date, String pattern, TimeZone timeZone) {
        return format(date, pattern, timeZone, null);
    }

    public static String format(Date date, String pattern, Locale locale) {
        return format(date, pattern, tzUTC, locale);
    }

    public static String format(Date date, String pattern, TimeZone timeZone, Locale locale) {
        if (date == null) return null;
        FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
        return df.format(date);
    }

    public static Date parse(String dateStr, String pattern) {
        return parse(dateStr, pattern, tzUTC, null);
    }

    public static Date parse(String dateStr, String pattern, TimeZone timeZone) {
        return parse(dateStr, pattern, timeZone, null);
    }

    public static Date parse(String dateStr, String pattern, Locale locale) {
        return parse(dateStr, pattern, tzUTC, locale);
    }

    public static Date parse(String dateStr, String pattern, TimeZone timeZone, Locale locale) {
        if (StringUtils.isBlank(dateStr)) return null;
        FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException(dateStr, e);
        }
    }

    public static TimeZone getTimeZone(String id) {
        if (id == null) return TimeZone.getDefault();
        return timeZoneCache.computeIfAbsent(id, key -> TimeZone.getTimeZone(key));
    }

    public static TimeZone getTimeZone(Integer timezoneOffset) {
        return getTimeZone(timezoneOffset == null ? null : "GMT" + (timezoneOffset < 0 ? "" : "+") + timezoneOffset + ":00");
    }

}
