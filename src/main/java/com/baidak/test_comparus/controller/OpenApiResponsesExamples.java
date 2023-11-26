package com.baidak.test_comparus.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenApiResponsesExamples {

    public static final String METHOD_NOT_ALLOWED_EXAMPLE = "{\"status\": 405, \"errors\": [\"Request method 'PATCH'" +
            " not supported\"]}";
    public static final String NOT_ACCEPTABLE_EXAMPLE = MediaType.APPLICATION_JSON_VALUE;
    public static final String INTERNAL_SERVER_ERROR_EXAMPLE = "{\"status\": 500, \"errors\": [\"Could not open JPA" +
            " EntityManager for transaction; nested exception is org.hibernate.exception.JDBCConnectionException\"]}";
}
