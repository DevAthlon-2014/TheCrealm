package com.crealmcode.effectrun.state.exception;

/**
 * Created by TheCrealm on 18.10.2014.
 */
public class StateException extends RuntimeException {

    public StateException(Throwable cause) {

        super(cause);
    }

    public StateException(String message, Throwable cause) {

        super(message, cause);
    }

    public StateException(String message) {

        super(message);
    }

    public StateException() {

    }
}
