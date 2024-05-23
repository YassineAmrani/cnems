package com.cnems.exceptions;

public class CnemsException extends Exception{

    int status;

    String message;

    public CnemsException(int status, String message) {
        setStatus(status);
        setMessage(message);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
