package com.br.mensageria.producer.service.business.messages;

public enum ClientBusinessMessages {

    ACTIVE_FALSE("Field % must be informed if field active is false"),
    ;

    private final String message;

    ClientBusinessMessages(String message) {
        this.message = message;
    }

    public String getMessage(final String field) {
        return message.replace("%", field);
    }
}
