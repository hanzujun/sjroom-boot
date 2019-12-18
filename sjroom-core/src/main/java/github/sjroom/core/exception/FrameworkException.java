package github.sjroom.core.exception;

import github.sjroom.core.utils.StringUtil;

/**
 * 框架级级别异常
 *
 * @author manson.zhou
 */
public class FrameworkException extends RuntimeException {

    private static final long serialVersionUID = 5055293222416543609L;

    public FrameworkException() {
        super();
    }

    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameworkException(Throwable cause) {
        super(cause);
    }

    public FrameworkException(Throwable cause, String message, Object... args) {
        super(StringUtil.format(message, args), cause);
    }


}
