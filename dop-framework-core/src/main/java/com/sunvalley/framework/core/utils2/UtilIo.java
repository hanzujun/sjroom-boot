package com.sunvalley.framework.core.utils2;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Auther: smj
 * @Date: 2019-07-30 10:06
 * @Description:
 */
@Slf4j
public abstract class UtilIo {
    public static final Charset utf8 = Charset.forName("UTF-8"),
            iso8859_1 = Charset.forName("ISO8859-1"),
            gbk = Charset.forName("GBK");

    private static final long KB = 1024, MB = 1024 * KB;
    public static final long GB = 1024 * MB;
    private static final long TB = 1024 * GB;
    private static final long PB = 1024 * TB;
    private static final long ZB = 1024 * PB;
    public static final int defaultBufferSize = 8 * 1024;

    private static final Map<String, String> contentTypeMap = new HashMap<>();

    static {
        contentTypeMap.put("png", "image/png");
        contentTypeMap.put("gif", "image/gif");
    }

    public static String prettyByte(Number num, Number divisor) {
        if (num == null) return "null/" + divisor + "B";
        if (divisor == null) return num + "/null B";
        long div = divisor.longValue();
        if (div == 0) return prettyByte(num) + "/0";
        return prettyByte(num.longValue() / div);
    }

    public static String prettyByte(Number sizeOfByte) {
        if (sizeOfByte == null) return null;

        long size = sizeOfByte.longValue();
        if (size == 0) return "0 KB";
        if (size < KB) return sizeOfByte + " B";

        DecimalFormat format = new DecimalFormat("#.##");
        if (size < MB) return format.format(1.0 * size / KB) + "KB";
        if (size < GB) return format.format(1.0 * size / MB) + "MB";
        if (size < TB) return format.format(1.0 * size / GB) + "GB";
        if (size < PB) return format.format(1.0 * size / TB) + "TB";
        if (size < ZB) return format.format(1.0 * size / PB) + "PB";
        return format.format(1.0 * size / ZB) + "ZB";
    }

    public static void close(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    log.warn("忽略异常 {}", closeable, e);
                }
            }
        }
    }

    ///文件系统目录文件操作 start

    /**
     * 删除文件或目录
     */
    public static synchronized void delete(File file) throws IOException {
        if (!file.exists()) return;
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                delete(child);
            }
        }
        file.delete();
    }

    /**
     * 删除文件或目录
     */
    public static void delete(Path path) throws IOException {
        delete(path.toFile());
    }

    public static String getSeparator() {
        // 跟操作系统相关
        return FileSystems.getDefault().getSeparator();
    }

    public static void mkdirs(File file) {
        if (file.exists()) {
            if (!file.isDirectory()) throw new IllegalStateException(file + "不是目录");
            return;
        }
        file.mkdirs();
    }

    /**
     * 创建新文件(相当于touch)
     */
    public static void createNewFile(File file) {
        if (file.exists()) {
            if (!file.isFile()) throw new IllegalStateException(file + "不是文件");
            return;
        }

        mkdirs(file.getParentFile());
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new IllegalStateException(file + " 创建失败 " + e, e);
        }
    }
    ///文件系统目录文件操作 end


    /**
     * 加载资源
     *
     * @param name
     * @return 可能null：因为和{@linkplain ClassLoader#getResourceAsStream(String)}保持一致
     * @since 0.1.0
     */
    public static InputStream getResourceAsStream(String name) {
        if (name == null || name.isEmpty()) return null;

        String name0 = name.charAt(0) == '/' ? name.substring(1) : name;
        InputStream in = null;
        try {
            in = UtilIo.class.getClassLoader().getResourceAsStream(name0);
            if (in == null) {
                File file = new File(name0);
                if (!file.exists() && name != name0) file = new File(name);
                log.info("无法按ClassLoader.getResourceAsStream读取，尝试按磁盘文件方式读取：{}", file);
                in = new FileInputStream(file);
            }
        } catch (FileNotFoundException e) {
            log.warn("无法读取：{}", name, e);
        }
        return in;
    }

    /**
     * 读取流为字节
     *
     * @param in   输入流，本函数不负责关闭
     * @param size 预计大小 (如果&lt;8192使用8192)
     */
    public static ByteArrayOutputStream read(InputStream in, Integer size) {
        if (in == null) return null;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream(size == null || size < 8192 ? 8192 : size)) {
            byte[] buf = new byte[8192];
            for (int len; (len = in.read(buf)) != -1; ) {
                out.write(buf, 0, len);
            }
            return out;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ByteArrayOutputStream read(File file) throws IOException {
        long length = file.length();
        if (length > Integer.MAX_VALUE) throw new IllegalArgumentException("文件太大, length=" + length);

        try (FileInputStream in = new FileInputStream(file)) {
            return read(in, (int) length);
        }
    }

    /**
     * 读取为字符串
     *
     * @param name 默认按资源读取，如果不存在按文件读取
     */
    public static ByteArrayOutputStream readText(String name) {
        try (InputStream in = getResourceAsStream(name)) {
            return read(in, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see #readText(String)
     */
    public static String readText(String name, String charsetName) {
        ByteArrayOutputStream bout = readText(name);
        if (bout == null) return null;

        try {
            return bout.toString(charsetName);
        } catch (UnsupportedEncodingException e) {
            UnsupportedCharsetException ex = new UnsupportedCharsetException(charsetName);
            ex.addSuppressed(e);
            ;
            throw ex;
        }
    }

    public static String readText(InputStream in) {
        return readText(in, 0, "UTF-8");
    }

    public static String readText(InputStream in, int bufSize) {
        return readText(in, bufSize, "UTF-8");
    }

    public static String readText(InputStream in, int bufSize, String charsetName) {
        if (in == null) return null;
        try {
            return read(in, bufSize).toString(charsetName);
        } catch (UnsupportedEncodingException e) {
            UnsupportedCharsetException ex = new UnsupportedCharsetException(charsetName);
            ex.addSuppressed(e);
            ;
            throw ex;
        }
    }

    public static Properties loadProperties(String name) {
        Properties props = new Properties();
        try (InputStream in = getResourceAsStream(name)) {
            if (in != null) props.load(in);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return props;
    }

    /**
     * 从in读取写入到out，直到in结束（in和out并不关闭）
     */
    public static long write(InputStream in, OutputStream out) throws IOException {
        long size = 0;
        byte[] buf = new byte[8192];
        for (int len; (len = in.read(buf)) != -1; ) {
            size += len;
            out.write(buf, 0, len);
        }
        return size;
    }

    public static File touch(String file) {
        File f = new File(file);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new IllegalArgumentException("file=" + file + ", " + e, e);
            }
        }
        return f;
    }

    public static byte[] serialize(Object serializable) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(4096);
        try (ObjectOutputStream out = new ObjectOutputStream(bout)) {
            out.writeObject(serializable);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return bout.toByteArray();
    }

    public static Object deserialize(byte[] content) {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(content))) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public static byte[] gzip(byte[] content) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try (GZIPOutputStream out = new GZIPOutputStream(bout, content.length)) {
            out.write(content);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return bout.toByteArray();
    }

    public static byte[] gunzip(byte[] content) {
        try (GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(content), content.length)) {
            return read(in, content.length).toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static byte[] writeObjects(Integer bufSize, Object... objs) {
        if (bufSize == null || bufSize <= 0) bufSize = 8192;
        ByteArrayOutputStream bout = new ByteArrayOutputStream(bufSize);

        try (ObjectOutputStream objOut = new ObjectOutputStream(bout)) {
            objOut.writeInt(objs.length);
            for (Object obj : objs) {
                objOut.writeObject(obj);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return bout.toByteArray();
    }

    public static List<Object> readObjects(byte[] bytes) {
        return readObjects0(new ByteArrayInputStream(bytes));
    }

    public static List<Object> readObjects0(InputStream in) {
        List<Object> result = new ArrayList<>();
        try (ObjectInputStream objIn = new ObjectInputStream(in)) {
            for (int size = objIn.readInt(); size > 0; size--) {
                result.add(objIn.readObject());
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new IllegalArgumentException(e);
        }
        return result;
    }

    public static String getFileName(String pathname) {
        if (StringUtils.isEmpty(pathname)) return pathname;

        int pos = pathname.lastIndexOf('/');
        if (pos != -1) return StringUtils.substring(pathname, pos + 1, pathname.length());

        pos = pathname.lastIndexOf('\\');
        if (pos != -1) return StringUtils.substring(pathname, pos + 1, pathname.length());

        return pathname;
    }

    public static String getFileExtName(String pathname) {
        if (StringUtils.isEmpty(pathname)) return pathname;

        int pos = pathname.lastIndexOf('.');
        return pos == -1 ? "" : StringUtils.substring(pathname, pos + 1, pathname.length());
    }

    public static String getContentType(String pathname) {
        if (StringUtils.isEmpty(pathname)) return pathname;
        return contentTypeMap.get(UtilIo.getFileExtName(pathname).toLowerCase());
    }
}
