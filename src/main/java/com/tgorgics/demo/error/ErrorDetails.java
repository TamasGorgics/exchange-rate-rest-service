package com.tgorgics.demo.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Builder
@ToString
@Data
public class ErrorDetails {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Builder.Default private Instant time = Instant.now();
    private String message;
    private HttpStatus status;
    private String error;

}
