package com.br.mensageria.producer.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_role")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name", unique = true)
    private String role_name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_ecclesiastical_function")
    private Boolean is_ecclesiastical_function;

    @Column(name = "hierarchical_order")
    private Integer hierarchical_order;
}
