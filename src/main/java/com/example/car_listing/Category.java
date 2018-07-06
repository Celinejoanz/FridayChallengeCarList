package com.example.car_listing;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {

    private String category;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
