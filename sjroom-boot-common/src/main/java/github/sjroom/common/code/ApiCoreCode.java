package github.sjroom.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务 code，需要配置 code 前缀，业务只需要关心code中间5位
 *
 * <p>
 * 业务标识：2位，开发人员按照业务模块定义，例如：(01)SKU。 业务错误码：3位，开发人员自己定义。
 * </p>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-05-27 08:34
 */
@Getter
@AllArgsConstructor
public enum ApiCoreCode implements IResultCode {
    NO_PLAT_CONTEXT("COR00201", "平台调用上下文为空"),
    NO_CALL_CONTEXT("COR00101", "业务调用上下文为空{0}");

    private String code;
    private String msg;
}
