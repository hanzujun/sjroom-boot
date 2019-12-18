package github.sjroom.core.code;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: manson.zhou
 * @Date: 2019/6/4 10:41
 */
@Slf4j
public enum LanguageEnum {
    zh,
    en;


    //根据name转换成Enum
    public static LanguageEnum byName(String lang) {
        for (LanguageEnum value : LanguageEnum.values()) {
            if (StringUtils.equalsIgnoreCase(value.name(), lang)) {//兼容模式
                return value;
            }
        }
        return null;//未匹配到enum
    }

    /**
     * 判断是否有效
     *
     * @param lang
     * @return
     */
    public static boolean isEffective(String lang) {
        for (LanguageEnum value : LanguageEnum.values()) {
            if (StringUtils.equals(value.name(), lang)) {//严格模式
                return true;
            }
        }
        return false;
    }

}
