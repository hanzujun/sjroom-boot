package github.sjroom.core.utils;

import lombok.experimental.UtilityClass;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字符集工具类
 *
 * @author L.cm
 */
@UtilityClass
public class Charsets {

    /**
     * 字符集ISO-8859-1
     */
    public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
    public static final String ISO_8859_1_NAME = ISO_8859_1.name();

    /**
     * 字符集GBK
     */
    public static final Charset GBK = Charset.forName(StringPool.GBK);
    public static final String GBK_NAME = GBK.name();

    /**
     * 字符集utf-8
     */
    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    public static final String UTF_8_NAME = UTF_8.name();
}
