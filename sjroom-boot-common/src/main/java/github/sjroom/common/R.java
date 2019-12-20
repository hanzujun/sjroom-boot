package github.sjroom.common;

import github.sjroom.common.code.IResultCode;
import github.sjroom.common.exception.ExceptionStatusEnum;
import lombok.Data;

/**
 * @param <T>
 * @author manson.zhou
 */
@Data
public class R<T> implements IResultCode {

    private String code;
    private String msg;

    private T data;

    public R() {
    }

    public R(T data) {
        this.data = data;
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, ExceptionStatusEnum.SUCCESS.getCode(), ExceptionStatusEnum.SUCCESS.getMessage());
    }


    private static <T> R<T> restResult(T data, String code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public Boolean getSuccess() {
        return code.endsWith("200");
    }
}
