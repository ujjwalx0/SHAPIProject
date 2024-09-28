package com.ShAssesment.Response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SHResponse<T> {
    private T data;
    private String message;
    private HttpStatus status;

}
