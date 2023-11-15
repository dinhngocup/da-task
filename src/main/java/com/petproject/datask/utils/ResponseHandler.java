package com.petproject.datask.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("errorCode", status.value());
        map.put("data", responseObj);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("errorCode", status.value());

        return new ResponseEntity<>(map, status);
    }
}
