package github.sjroom.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码工具类
 *
 * @author L.cm
 */
@UtilityClass
public class CaptchaUtil {
    /**
     * 默认的验证码大小
     */
    private static final int WIDTH = 108, HEIGHT = 40, CODE_SIZE = 4;
    /**
     * 验证码随机字符数组
     */
    private static final char[] CHAR_ARRAY = "3456789ABCDEFGHJKMNPQRSTUVWXY".toCharArray();
    /**
     * 验证码字体
     */
    private static final Font[] RANDOM_FONT = new Font[]{
        new Font(Font.DIALOG, Font.BOLD, 33),
        new Font(Font.DIALOG_INPUT, Font.BOLD, 34),
        new Font(Font.SERIF, Font.BOLD, 33),
        new Font(Font.SANS_SERIF, Font.BOLD, 34),
        new Font(Font.MONOSPACED, Font.BOLD, 34)
    };

    /**
     * 生成验证码图片 bytes
     *
     * @param random Random
     * @param vCode  验证码数字
     * @return byte数组
     */
    public static byte[] generate(Random random, String vCode) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        try (FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream()) {
            drawGraphic(image, random, vCode);
            ImageIO.write(image, "JPEG", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成 base64 验证码
     *
     * @param random 随机
     * @param vCode  码
     * @return base64 二维码
     */
    public static String generateBase64(Random random, String vCode) {
        byte[] captchaByteArray = CaptchaUtil.generate(random, vCode);
        String captcha = Base64Util.encodeToString(captchaByteArray);
        return "data:image/jpeg;base64," + captcha;
    }

    /**
     * 生成验证码字符串
     *
     * @param random Random
     * @return 验证码字符串
     */
    public static String generateCode(Random random) {
        int count = CODE_SIZE;
        char[] buffer = new char[count];
        for (int i = 0; i < count; i++) {
            buffer[i] = CHAR_ARRAY[random.nextInt(CHAR_ARRAY.length)];
        }
        return new String(buffer);
    }

    private static void drawGraphic(BufferedImage image, Random random, String code) {
        // 获取图形上下文
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        // 图形抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 字体抗锯齿
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // 设定背景色，淡色
        g.setColor(getRandColor(random, 210, 250));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 画小字符背景
        int charCount = 20;
        Color color = null;
        for (int i = 0; i < charCount; i++) {
            color = getRandColor(random, 120, 200);
            g.setColor(color);
            String rand = String.valueOf(CHAR_ARRAY[random.nextInt(CHAR_ARRAY.length)]);
            g.drawString(rand, random.nextInt(WIDTH), random.nextInt(HEIGHT));
            color = null;
        }
        // 取随机产生的认证码(4位数字)
        char[] buffer = code.toCharArray();
        for (int i = 0; i < buffer.length; i++) {
            char xcode = buffer[i];
            //旋转度数 最好小于45度
            int degree = random.nextInt(25);
            if (i % 2 == 0) {
                degree = degree * (-1);
            }
            //定义坐标
            int x = 22 * i, y = 21;
            //计算弧度
            double radians = Math.toRadians(degree);
            //旋转区域
            g.rotate(radians, x, y);
            //设定字体颜色
            color = getRandColor(random, 20, 130);
            g.setColor(color);
            //设定字体，每次随机
            g.setFont(RANDOM_FONT[random.nextInt(RANDOM_FONT.length)]);
            //将认证码显示到图象中
            g.drawString(String.valueOf(xcode), x + 8, y + 10);
            //旋转之后，必须旋转回来
            g.rotate(-radians, x, y);
        }
        //图片中间曲线，使用上面缓存的color
        g.setColor(color);
        //width是线宽,float型
        BasicStroke bs = new BasicStroke(3);
        g.setStroke(bs);
        //画出曲线
        QuadCurve2D.Double curve = new QuadCurve2D.Double(0d, random.nextInt(HEIGHT - 8) + 4, WIDTH >> 1, HEIGHT >> 1, WIDTH, random.nextInt(HEIGHT - 8) + 4);
        g.draw(curve);
        // 销毁图像
        g.dispose();
    }

    /**
     * 给定范围获得随机颜色
     */
    private static Color getRandColor(Random random, int fc, int bc) {
        int colorMax = 255;
        if (fc > colorMax) {
            fc = 255;
        }
        if (bc > colorMax) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
