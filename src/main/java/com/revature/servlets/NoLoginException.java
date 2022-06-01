package com.revature.servlets;

public class NoLoginException extends Exception {
    public NoLoginException() {
        System.out.println("Cannot find session");
    }
}
