package com.astrapay.util;

import com.astrapay.dto.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {

    public static <T> ResponseEntity<BaseResponse<T>> success(String message, T data, HttpStatus status) {
        return new ResponseEntity<>(BaseResponse.success(message, data), status);
    }


    public static <T> ResponseEntity<BaseResponse<T>> created(String message, T data) {
        return new ResponseEntity<>(BaseResponse.success(message, data), HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<BaseResponse<T>> noContent() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<BaseResponse<Object>> error(String message, HttpStatus status) {
        return new ResponseEntity<>(BaseResponse.error(message), status);
    }
}