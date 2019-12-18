package github.sjroom.core.code;

import github.sjroom.core.result.HttpStatus;
import github.sjroom.core.result.HttpStatus;

import java.io.Serializable;

/**
 * 状态码接口
 *
 * @author L.cm
 */
public interface IResultCode extends Serializable {
	/**
	 * 返回的code码
	 *
	 * @return code
	 */
	String getCode();

	/**
	 * 返回的消息
	 *
	 * @return 消息
	 */
	String getMsg();

	/**
	 *
	 * @return http code
	 */
	default IHttpStatus getHttpStatus() {
		return HttpStatus.InternalError;
	}
}
