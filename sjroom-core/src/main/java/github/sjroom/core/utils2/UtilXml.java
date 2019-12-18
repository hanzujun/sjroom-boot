//package github.sjroom.core.utils2;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonInclude.Include;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.MapperFeature;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.util.Date;
//
///**
// *
// */
//public abstract class UtilXml {
//    public final static XmlMapper xmlMapper = new XmlMapper();
//
//    static {
//        xmlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);
//        xmlMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//        xmlMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//        xmlMapper.setSerializationInclusion(Include.NON_NULL);
//        xmlMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false); // 不输出value=null的属性
//        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 不知道的属性，不异常
//        xmlMapper.setDateFormat(new SimpleDateFormat(UtilDate.PatternMvc));
//
//        //特殊的转换日期要求可在属性上增加 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//        xmlMapper.setTimeZone(UtilDate.tzShanghai);
//        JavaTimeModule timeModule = new JavaTimeModule();
//        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(UtilDate.DateFormatter));
//        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(UtilDate.DateFormatter));
//        xmlMapper.registerModule(timeModule);
//    }
//
//    public static <T> T readValue(String content, Class<T> clz) {
//        if (StringUtils.isBlank(content))
//            return null;
//        try {
//            return xmlMapper.readValue(content, clz);
//        } catch (IOException e) {
//            throw new IllegalArgumentException(content, e);
//        }
//    }
//
//    public static byte[] writeValueAsBytes(Object value) {
//        try {
//            return xmlMapper.writeValueAsBytes(value);
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
//
//    public static String writeValueAsString(Object value) {
//        try {
//            return xmlMapper.writeValueAsString(value);
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
//
//    public static byte[] writerWithDefaultPrettyPrinterAsBytes(Object value) {
//        try {
//            return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(value);
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
//
//
//    public static void main(String[] args) {
//        System.out.println(UtilXml.writeValueAsString(new Demo()));
//    }
//
//    @JacksonXmlRootElement(localName = "root")
//    private static class Demo {
//
//        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//        private Date d1 = new Date();
//        private String n1 = "name";
//
//        public Date getD1() {
//            return d1;
//        }
//
//        public void setD1(Date d1) {
//            this.d1 = d1;
//        }
//
//        public String getN1() {
//            return n1;
//        }
//
//        public void setN1(String n1) {
//            this.n1 = n1;
//        }
//    }
//}
