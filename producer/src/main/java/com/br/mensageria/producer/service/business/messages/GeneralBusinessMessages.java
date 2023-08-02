package com.br.mensageria.producer.service.business.messages;

public enum GeneralBusinessMessages {

    GENERAL_INVALID_MESSAGE("Field % is invalid"),
    NULL_OR_EMPTY("Field % is null or empty"),
    ZIP_CODE("Field % is invalid. Must be an 8-position integer"),
    NUMERIC("Field % must be numeric type and not null"),
    BOOLEAN("Field % must be boolean type and not null"),
    NOT_FOUND("% not found"),
    EXISTS_IN_DATABASE("The informed % already exists in the database"),
    DATE("The % field is invalid. Report in American standard: yyyy-MM-dd")
    ;

    private final String message;

    GeneralBusinessMessages(String message) {
        this.message = message;
    }

    public String getMessage(final String field) {
        return message.replace("%", field);
    }
}
