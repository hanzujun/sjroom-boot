package github.sjroom.core.result;

import github.sjroom.core.code.IHttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

/**
 * httpCode
 */
@AllArgsConstructor
public enum HttpStatus implements IHttpStatus {

	InternalError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR),//500 服务器内部错误
	IllegalArgument(HttpServletResponse.SC_BAD_REQUEST),//409
	UnAuthorized(HttpServletResponse.SC_UNAUTHORIZED),//401 未登录
	Forbidden(HttpServletResponse.SC_FORBIDDEN),//403 禁止访问
	UnProcessable(422),//422 无法正确处理，例如断言错误，可以使用
	;
	@Getter
	private Integer statusCode;


}
