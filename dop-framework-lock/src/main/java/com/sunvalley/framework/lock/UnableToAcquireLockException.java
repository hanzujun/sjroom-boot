package com.sunvalley.framework.lock;

import com.sunvalley.framework.core.exception.FrameworkException;

/**
 *
 * @Author: Katrel.Zhou
 * @Date: 2019/5/16 10:34
 * @Version 1.0.0
 */
public class UnableToAcquireLockException extends FrameworkException {

    private static final long serialVersionUID = 4121125716772813963L;


    public UnableToAcquireLockException(String message) {
        super(message);
    }

    public UnableToAcquireLockException(Throwable cause) {
        super(cause);
    }

    public UnableToAcquireLockException(Throwable cause, String message) {
        super(cause, message);
    }

    public UnableToAcquireLockException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
