package com.pranjal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RazorpayResponse {
    private String id;
    private String entity;
    private Integer amount;
    private String status;
    private String currency;
    private String created_at;
    private String receipt;
}
