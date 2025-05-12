package com.AvMeditechInventory.results;

public class ErrorDataResult<T> extends DataResult<T> {

    public ErrorDataResult() {
        super(null, false);
    }

    public ErrorDataResult(final String message) {
        super(null, false, message);
    }

    public ErrorDataResult(final T data) {
        super(data, false);
    }

    public ErrorDataResult(final T data, final String message) {
        super(data, false, message);
    }

    public ErrorDataResult(T data, boolean success) {
        super(data, success);
    }

    public ErrorDataResult(T data, boolean success, String message) {
        super(data, success, message);
    }

}
