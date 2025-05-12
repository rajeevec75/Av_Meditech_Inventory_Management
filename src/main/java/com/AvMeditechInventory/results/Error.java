package com.AvMeditechInventory.results;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@XmlRootElement(name = "Error")
public class Error {

    private String errorCode;
    private String message;

    public Error() {
    }

    public Error(String ErrorCode, String Message) {
        this.errorCode = ErrorCode;
        this.message = Message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getErrorCodeInt() {
        return Integer.valueOf(errorCode);
    }

    public void setErrorCode(String ErrorCode) {
        this.errorCode = ErrorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String Message) {
        this.message = Message;
    }
}
