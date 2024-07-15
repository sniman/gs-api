
/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.util;

import org.springframework.security.core.AuthenticationException;

public class CustomError extends AuthenticationException {
    private static final long serialVersionUID = 1L;

	public CustomError(String msg) {
        super(msg);
    }

    public CustomError(String msg, Throwable cause) {
        super(msg, cause);
    }
}
