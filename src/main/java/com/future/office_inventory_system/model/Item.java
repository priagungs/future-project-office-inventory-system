package com.future.office_inventory_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = TableName.ITEM)
public class Item {

    @Id
    @GeneratedValue
    private Long idItem;

    private String itemName;

    private String pictureURL;

    private Long price;

    private Integer totalQty;

    private Integer availableQty;

    private String description;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<Request> requests;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<UserHasItem> owners;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<ItemTransaction> itemTransactions;

    private Boolean isActive = true;


}
