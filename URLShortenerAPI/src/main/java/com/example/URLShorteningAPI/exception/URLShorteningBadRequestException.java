package com.example.URLShorteningAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class URLShorteningBadRequestException extends RuntimeException {
    public URLShorteningBadRequestException(String message) {
        super(message);
    }
}