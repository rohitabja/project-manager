package com.cognizant.projectmanager.exception;

/**
 * Created by hp on 20-06-2019.
 */
public class TaskManagerException extends RuntimeException {

    public TaskManagerException() {
        super();
    }

    public TaskManagerException(final String message) {
        super(message);
    }

    public TaskManagerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TaskManagerException(final Throwable cause) {
        super(cause);
    }
}
