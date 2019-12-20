package github.sjroom.common.context;

/**
 * @Author: manson.zhou
 * @Date: 2019/7/30 10:14
 * @Desc: 上下文常量
 */
public interface ContextConstants {

    /**
     * 请求的requestId
     */
    String X_REQUEST_ID = "x-request-id";
    /**
     * 登录 token
     */
    String X_TOKEN = "x-token";

    /**
     * 调试模式:
     * 1.返回实体打印堆栈日志
     * 2.日志更新成需要的日志等级
     */
    String X_DEBUG = "x-debug";
}
