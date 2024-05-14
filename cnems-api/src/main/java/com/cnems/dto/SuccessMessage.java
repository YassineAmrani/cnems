package com.cnems.dto;

public class SuccessMessage {
    private boolean success;
    private String result;

    public SuccessMessage(boolean success, String result) {
        setSuccess(success);
        setResult(result);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
