package com.opensource.todo.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer id;

    @NotBlank
    @Size(max = 20)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}