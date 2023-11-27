package com.baidak.test_comparus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//TODO consider id exclusion?? Username should be unique?
@EqualsAndHashCode(exclude = {"id"})
@ToString
public class User {

    private UUID id;
    private String username;
    private String firstName;
    private String surname;
}
