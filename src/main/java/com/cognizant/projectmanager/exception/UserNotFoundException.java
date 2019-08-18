package com.cognizant.projectmanager.exception;

/**
 * Created by hp on 10-08-2019.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(final String message) {
        super(message);
    }

    public UserNotFoundException(final String message, final Throwable th) {
        super(message, th);
    }

    public UserNotFoundException(final Throwable th) {
        super(th);
    }
}
