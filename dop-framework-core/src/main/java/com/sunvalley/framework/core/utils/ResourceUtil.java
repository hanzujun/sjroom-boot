package com.sunvalley.framework.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.*;

import java.io.IOException;
import java.util.Objects;

/**
 * 资源工具类
 *
 * @author L.cm
 */
@UtilityClass
public class ResourceUtil extends org.springframework.util.ResourceUtils {
    public static final String HTTP_REGEX = "^https?:.+$";
    public static final String FTP_URL_PREFIX = "ftp:";

    /**
     * 获取资源
     * <p>
     * 支持一下协议：
     * <p>
     * 1. classpath:
     * 2. file:
     * 3. ftp:
     * 4. http: and https:
     * 6. C:/dir1/ and /Users/lcm
     * </p>
     *
     * @param resourceLocation 资源路径
     * @return {Resource}
     * @throws IOException IOException
     */
    public static Resource getResource(String resourceLocation) throws IOException {
        Objects.requireNonNull(resourceLocation, "Resource location must not be null");
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(resourceLocation);
        }
        if (resourceLocation.startsWith(FTP_URL_PREFIX)) {
            return new UrlResource(resourceLocation);
        }
        if (resourceLocation.matches(HTTP_REGEX)) {
            return new UrlResource(resourceLocation);
        }
        if (resourceLocation.startsWith(FILE_URL_PREFIX)) {
            return new FileUrlResource(resourceLocation);
        }
        return new FileSystemResource(resourceLocation);
    }
}
