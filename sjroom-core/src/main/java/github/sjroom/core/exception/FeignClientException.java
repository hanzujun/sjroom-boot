package github.sjroom.core.exception;

import github.sjroom.core.utils2.UtilCollection;
import github.sjroom.core.utils2.UtilJson;
import feign.FeignException;
import lombok.Getter;

import java.util.Map;

import static github.sjroom.core.exception.BusinessException.StateCode;

/**
 * @Author: manson.zhou
 * @Date: 2019/7/10 8:21
 * @Desc: feign client 调用的异常封装
 */
public class FeignClientException extends RuntimeException {

	private static final long serialVersionUID = 2189450345700733464L;

	@Getter
	private int httpStatus;
	@Getter
	private String code;
	/**
	 * 完整的restful方案输出
	 */
	@Getter
	private Map<String, Object> result;

	public FeignClientException() {
	}

	public FeignClientException(FeignException cause) {
		super(cause.getMessage(), cause);
		httpStatus = cause.status();
		String contentUTF8 = cause.contentUTF8();
		result = UtilJson.toMap(contentUTF8);
		if (!UtilCollection.isEmpty(result)) {
			code = (String) result.get(StateCode);
		}
	}

}
