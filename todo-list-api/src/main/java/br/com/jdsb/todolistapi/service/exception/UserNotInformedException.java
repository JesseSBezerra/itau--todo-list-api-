package br.com.jdsb.todolistapi.service.exception;

public class UserNotInformedException extends RuntimeException{
    public UserNotInformedException(String message) {
        super(message);
    }
}
