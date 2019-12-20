package github.sjroom.common.exception;/**
 * @author manson.zhou on 2018/3/22.
 */

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2018-03-22 22-20
 */
public enum ExceptionStatusEnum {
    /**
     * 参数异常
     */
    ILLEGAL_PARAM("101", "参数[{0}]不能为空"),
    /**
     * 序列化异常
     */
    SERIALIZE_ERROR("102", "序列化异常"),

    /**
     * 成功
     */
    SUCCESS("200", "成功"),
    /**
     * 处理失败
     */
    FAIL("400", "处理失败！{0}"),
    /**
     * 系统错误
     */
    ERROR("500", "系统错误！{0}"),

    /**
     * mq系统错误
     */
    MQ_ERROR("505", "消息组件发生异常"),
    /**
     * mq系统错误
     */
    RETRY_UTIL_ERROR("506", "重试组件发生异常"),

    /**
     * 分布式锁错误
     */
    LOCKING_ERROR("510", "分布式锁组件发生异常");

    private String code;
    private String message;

    private ExceptionStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(String msg) {
        if (msg != null && msg.equals("")) {
            return message.replace("{0}", msg);
        } else {
            return message;
        }
    }

}
