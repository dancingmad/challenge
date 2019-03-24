package com.irongrp.challenge.exception;

public class PackagerException extends RuntimeException {

    public PackagerException(String message) {
        super(message);
    }

    public PackagerException(String message,Throwable throwable) {
        super(message,throwable);
    }
}
