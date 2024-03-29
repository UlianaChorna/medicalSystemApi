package com.uliana.MedicalSystemApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String gender;
    private Integer age;
    private String email;
    private String password;
    private boolean confirmed;


    @ManyToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.DETACH
    })
    private Set<Reception> reception;
}
