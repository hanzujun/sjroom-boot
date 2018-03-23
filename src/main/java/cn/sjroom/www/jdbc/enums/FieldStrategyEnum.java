package cn.sjroom.www.jdbc.enums;

/**
 * <p>
 * 字段策略枚举类
 * </p>
 *
 * @author Zhouwei
 */
public enum FieldStrategyEnum {
    NULL(0, "null"),
    NOT_NULL(1, "not null");

    /**
     * 主键
     */
    private final int key;

    /**
     * 描述
     */
    private final String desc;

    FieldStrategyEnum(final int key, final String desc) {
        this.key = key;
        this.desc = desc;
    }

    public int getKey() {
        return this.key;
    }

    public String getDesc() {
        return this.desc;
    }

    public static FieldStrategyEnum getFieldStrategy(int key) {
        FieldStrategyEnum[] fss = FieldStrategyEnum.values();
        for (FieldStrategyEnum fs : fss) {
            if (fs.getKey() == key) {
                return fs;
            }
        }
        return FieldStrategyEnum.NOT_NULL;
    }

}
