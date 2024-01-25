package com.sysaid.assignment.exception;

public class ToManyActiveTaskException extends RuntimeException {
    private static final String message = "You have to many active tasks, please complete, add to wishlist or remove one of them first";

    public ToManyActiveTaskException() {
        super(message);
    }
}
