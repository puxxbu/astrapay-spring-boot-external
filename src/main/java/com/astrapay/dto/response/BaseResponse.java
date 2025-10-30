package com.astrapay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private String status;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>("OK", message, data);
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>("OK", "Success", data);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>("ERROR", message, null);
    }
}