package com.ecom;

public enum ReturnCode {
    SUCCESS("Success"),
    FAIL("Fail"),
    PENDING("Pending");

    private final String message;

    ReturnCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }

    public boolean isFail() {
        return this == FAIL;
    }

    public boolean isPending() {
        return this == PENDING;
    }
}