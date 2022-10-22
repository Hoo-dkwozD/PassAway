package com.example.demo.exception;

public class CustomException extends IllegalArgumentException {
    public CustomException(String msg) {
        super(msg);
    }

}
