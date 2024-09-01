package com.example.hrcoreapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departament")
    private int idDepartment;


    @Column(name = "nume_departament",nullable = false)
    private String nameDepartment;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<DepartmentalFunctions> functions = new ArrayList<>();

}
