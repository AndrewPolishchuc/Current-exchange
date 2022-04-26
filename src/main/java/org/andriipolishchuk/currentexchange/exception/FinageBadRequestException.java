package org.andriipolishchuk.currentexchange.exception;

public class FinageBadRequestException extends RuntimeException{
    public FinageBadRequestException() {
        super("Problem when requesting to Finage service!");
    }
}
