package com.baidak.test_comparus.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
//TODO consider id exclusion?? Username should be unique?
@EqualsAndHashCode(exclude = {"id"})
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private UUID id;
    private String username;
    private String name;
    private String surname;
}
