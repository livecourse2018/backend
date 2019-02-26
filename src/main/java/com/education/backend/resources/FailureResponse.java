package com.education.backend.resources;

public class FailureResponse {
    private int errorCode;
    private String status;
    private String message;

    public FailureResponse() {}

    public FailureResponse(int errorCode, String status, String message) {
        this.errorCode = errorCode;
        this.status = status;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
