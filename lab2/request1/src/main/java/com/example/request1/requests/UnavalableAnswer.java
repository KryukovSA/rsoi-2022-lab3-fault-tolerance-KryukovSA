package com.example.request1.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnavalableAnswer
{
    public UnavalableAnswer(String message) {
        this.message = message;
    }
    private String message;
    private String details;
}
