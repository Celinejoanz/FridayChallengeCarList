package com.example.car_listing;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {

    //private String category;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
