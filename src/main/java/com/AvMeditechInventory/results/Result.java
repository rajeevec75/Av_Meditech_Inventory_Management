package com.AvMeditechInventory.results;

public class Result {

    private final boolean success;
    private String message;

    public Result(final boolean success) {
        this.success = success;
    }

    public Result(final boolean success, final String message) {
        this(success);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
