package github.sjroom.common.code;

/**
 * 通用结果接口
 *
 * @author manson.zhou
 */
public interface IResultCode {
    /**
     * 获取枚举中定义的异常码
     *
     * @return
     */
    String getCode();

    /**
     * 获取枚举中定义的异常信息
     *
     * @return
     */
    String getMsg();
}