package com.baidak.test_comparus.dto.filter;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter {

    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(in = ParameterIn.QUERY,
            description = "Username as filter",
            name = "username",
            schema = @Schema(type = "string"))
    @Parameter(in = ParameterIn.QUERY,
            description = "Name as filter",
            name = "name",
            schema = @Schema(type = "string"))
    @Parameter(in = ParameterIn.QUERY,
            description = "Surname as filter",
            name = "surname",
            schema = @Schema(type = "string"))
    public @interface QueryParameters {
    }

    private UUID id;
    private String username;
    private String name;
    private String surname;
}
