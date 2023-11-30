package com.baidak.test_comparus.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private int status;

    @Singular
    private List<String> errors;

}
