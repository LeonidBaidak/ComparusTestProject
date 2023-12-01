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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter {

    private String username;
    private String firstName;
    private String surname;

    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(in = ParameterIn.QUERY,
            description = "Username as filter. Can be used with: '*', '_', '[A-S]', '[^A-B]'",
            name = "username",
            schema = @Schema(type = "string"))
    @Parameter(in = ParameterIn.QUERY,
            description = "First name as filter. Can be used with: '*', '_', '[A-S]', '[^A-B]'",
            name = "firstName",
            schema = @Schema(type = "string"))
    @Parameter(in = ParameterIn.QUERY,
            description = "Surname as filter. Can be used with: '*', '_', '[A-S]', '[^A-B]'",
            name = "surname",
            schema = @Schema(type = "string"))
    public @interface QueryParameters {
    }
}
