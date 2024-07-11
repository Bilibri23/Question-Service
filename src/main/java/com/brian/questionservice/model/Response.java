package com.brian.questionservice.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class Response {
    private  Integer id;
    private  String response;

}
