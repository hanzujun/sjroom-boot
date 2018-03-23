package cn.sjroom.www.jdbc.generatecode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.support.json.JSONUtils;

/**
 * <B>说明：有道翻译，将中文翻译成英文</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-03-09 21:57
 */
public class YoudaoTranslation {

    protected static Logger logger = LoggerFactory.getLogger(YoudaoTranslation.class);

    public static String APP_KEY = "0f7e03740ae8212b";
    public static String APP_SECRET = "iRxQUBEXSkEKyfB7iZgQHgKgsKrldgXp";


    public static void main(String[] args) throws Exception {
        String englishName = YoudaoTranslation.toEnglish("状态 1:申请,2:审批驳回,3:审批通过");
        System.out.println("englishName = " + englishName);
    }

    /**
     * @param msg
     * @return
     */
    public static String toEnglish(String msg) {
        try {
            String salt = String.valueOf(System.currentTimeMillis());
            String from = "auto";
            String to = "auto";
            String sign = md5(APP_KEY + msg + salt + APP_SECRET);
            Map params = new HashMap();
            params.put("q", msg);
            params.put("from", from);
            params.put("to", to);
            params.put("sign", sign);
            params.put("salt", salt);
            params.put("appKey", APP_KEY);
            String result = requestForHttp("http://openapi.youdao.com/api", params);
            LinkedHashMap linkedHashMap = (LinkedHashMap) JSONUtils.parse(result);
            if (linkedHashMap.get("translation") != null) {
                List<String> result1 = (List) linkedHashMap.get("translation");
                String result2 = result1.get(0).replace("= ", "=").replace(" ", "_").toUpperCase();
                if (result2.endsWith(".")) {
                    return result2.substring(0, result2.length() - 1);
                }
            }
        } catch (Exception ex) {
            logger.error("toEnglish ex:{}", ex.getMessage());
        }
        return msg;
    }


    public static String requestForHttp(String url, Map requestParams) throws Exception {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        /**HttpPost*/
        HttpPost httpPost = new HttpPost(url);
        System.out.println(JSONUtils.toJSONString(requestParams));
        List params = new ArrayList();
        Iterator it = requestParams.entrySet().iterator();
        while (it.hasNext()) {
            Entry en = (Entry) it.next();
            String key = (String) en.getKey();
            String value = (String) en.getValue();
            if (value != null) {
                params.add(new BasicNameValuePair(key, value));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        /**HttpResponse*/
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try {
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "utf-8");
            EntityUtils.consume(httpEntity);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 生成32位MD5摘要
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = string.getBytes("utf-8");
            /** 获得MD5摘要算法的 MessageDigest 对象 */
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            /** 使用指定的字节更新摘要 */
            mdInst.update(btInput);
            /** 获得密文 */
            byte[] md = mdInst.digest();
            /** 把密文转换成十六进制的字符串形式 */
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 进行URL编码
     *
     * @param input
     * @return
     */
    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }
}
