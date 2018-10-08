package com.future.OfficeInventorySystem.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@TableGenerator(name = "request_generator")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "request_generator")
    private Long idRequest;

    private User user;

    private Item item;

    private Date requestDate;

    private Integer reqQty;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUser", nullable = false)
    private User superior;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUser", nullable = false)
    private User administrator;




}