package com.webengage.predict.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PredictResponseDto<T> {

    private String status;
    private String message;
    private T data;

    public PredictResponseDto () { }

    public static <E> PredictResponseDto<E> build(String status, String message, E data) {
        PredictResponseDto<E> predictResponseDto = new PredictResponseDto<E>();
        predictResponseDto.setStatus(status);
        predictResponseDto.setMessage(message);
        predictResponseDto.setData(data);
        return predictResponseDto;
    }
}
