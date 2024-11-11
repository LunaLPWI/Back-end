package com.luna.luna_project.exceptions;

public class AdminAccessDeniedException extends RuntimeException {
    public AdminAccessDeniedException(String message) {
        super(message);
    }
}
