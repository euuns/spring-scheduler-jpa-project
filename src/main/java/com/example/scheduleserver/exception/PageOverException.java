package com.example.scheduleserver.exception;

public class PageOverException extends RuntimeException{
    public PageOverException() {
        super("This page is inaccessible.");
    }
}
