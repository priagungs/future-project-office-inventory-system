package com.future.OfficeInventorySystem.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@TableGenerator(name = "employee_generator", initialValue = 16516000)
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "employee_generator")
    private Long idUser;

    private String username;

    private String password;

    private String name;

    private String picture; //path to an image in filesystem

    private String role;

    private String division;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    private User Superior;

    private Boolean isAdmin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idRequest", nullable = false)
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUserHasItem", nullable = false)
    private UserHasItem hasItem;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idTransaction", nullable = false)
    private Transaction transaction;


}