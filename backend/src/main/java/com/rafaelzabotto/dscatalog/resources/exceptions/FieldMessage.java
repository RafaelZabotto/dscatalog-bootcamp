package com.rafaelzabotto.dscatalog.resources.exceptions;

import org.springframework.validation.FieldError;

import java.io.Serializable;

public class FieldMessage implements Serializable {
    private static final long serialVersionUID = -4451939166390389999L;

    private String fieldMessage;
    private String message;

    public FieldMessage() {

    }

    public FieldMessage(String fieldMessage, String message) {
        this.fieldMessage = fieldMessage;
        this.message = message;
    }

    public String getFieldMessage() {
        return fieldMessage;
    }

    public void setFieldMessage(String fieldMessage) {
        this.fieldMessage = fieldMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
