package com.example.demo.data;

import java.io.Serializable;

public class TypeBean implements Serializable {
    //recipe's type entity
    //The id of the type.
    private Integer id;
    //The name of the type.
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}
