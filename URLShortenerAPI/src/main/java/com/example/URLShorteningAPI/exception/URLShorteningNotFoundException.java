package com.example.URLShorteningAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class URLShorteningNotFoundException extends RuntimeException {
    public URLShorteningNotFoundException(String message) {
        super(message);
    }
}
