package github.sjroom.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别
 *
 * @author think
 */
@Getter
@AllArgsConstructor
public enum SexEnum {

    MAN(1, "男"),
    WOMAN(2, "女");

    private int sex;
    private String name;
}
