package com.motivation.customExceptions;

public class UsernameIsInUseException extends Exception{

    public UsernameIsInUseException(String message) {
        super(message);
    }
}
