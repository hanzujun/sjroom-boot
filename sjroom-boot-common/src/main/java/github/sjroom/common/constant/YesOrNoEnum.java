package github.sjroom.common.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesOrNoEnum {

    YES(1, "是"),
    NO(2, "否");

    private int type;
    private String name;
}
