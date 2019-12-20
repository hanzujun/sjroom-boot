package github.sjroom.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.*;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public static final String ENTER_NEW_LINE = System.getProperty("line.separator");
    public static final String JAR_PACKAGE_PATH_SEPARATOR = "/";

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

    /**
     * 根据当前文件的classLoader 加载资源
     *
     * @param path
     * @return
     */
    public static InputStream getResourceAsStream(String path) {
        return ResourceUtils.class.getClassLoader().getResourceAsStream(path);
    }

    /**
     * 获取文件内容
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(getResourceAsStream(filePath)));
        while (true) {
            String str = null;
            if ((str = br.readLine()) == null) {
                break;
            }
            content.append(str).append(ENTER_NEW_LINE);
        }
        return content.toString();
    }
}
