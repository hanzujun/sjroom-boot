package com.github.sjroom.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * jdk提供的序列化
 *
 * @author luobinwen
 */
@Slf4j
public class JdkSerializerUtil {

  public static Object[] deserialize(byte[] bytes, Class<?>[] clz) {

    try (InputStream in = new ByteArrayInputStream(bytes)) {
      Object[] result = new Object[clz.length];
      ObjectInputStream ois = new ObjectInputStream(in);
      for (int i = 0; i < clz.length; i++) {
        result[i] = ois.readObject();
      }

      return result;
    } catch (Exception e) {
      log.error("java object参数反序列化异常!", e);
    }
    return null;
  }

  /**
   * object参数反序列化异常
   */
  public static <T> T deserialize(byte[] bytes, Class<T> type) {
    try {
      ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
      ObjectInputStream inputStream = new ObjectInputStream(bin);
      Object object = inputStream.readObject();
      inputStream.close();
      bin.close();
      return (T) object;
    } catch (Exception e) {
      log.error("java object参数反序列化异常!", e);
    }
    return null;
  }

  /**
   * object参数序列化异常
   */
  public static <T> byte[] serialize(T object) {
    try {
      ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
      ObjectOutputStream stream = new ObjectOutputStream(byteArrayOS);
      stream.writeObject(object);
      stream.close();
      byteArrayOS.close();
      return byteArrayOS.toByteArray();
    } catch (Exception e) {
      log.error("java object参数序列化异常!", e);
    }
    return null;
  }

  public static byte[] serialize(Object[] params) {

    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      ObjectOutputStream oos = new ObjectOutputStream(out);
      for (Object p : params) {
        oos.writeObject(p);
      }

      oos.flush();
      return out.toByteArray();
    } catch (Exception e) {
      log.error("java object参数序列化异常!", e);
    }
    return null;
  }
}
