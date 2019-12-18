package github.sjroom.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @Author: Katrel.Zhou
 * @Date: 2019/5/16 10:34
 * @Version 1.0.0
 */
public class ResourceUtils extends org.springframework.util.ResourceUtils{
    public static final String ENTER_NEW_LINE                   = System.getProperty("line.separator");
    public static final String JAR_PACKAGE_PATH_SEPARATOR       = "/";
    /**
     * 根据当前文件的classLoader 加载资源
     * @param path
     * @return
     */
    public static InputStream getResourceAsStream(String path) {
        return ResourceUtils.class.getClassLoader().getResourceAsStream(path);
    }

    /**
     * 获取文件内容
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(getResourceAsStream(filePath)));
        while(true) {
            String str = null;
            if((str = br.readLine()) == null){
                break;
            }
            content.append(str).append(ENTER_NEW_LINE);
        }
        return content.toString();
    }
}
