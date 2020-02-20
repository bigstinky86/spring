package com.task.restservice.Category;


import com.task.restservice.Page.Pages;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Fetch(value = FetchMode.SELECT)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Set<Pages> pagesSet = new HashSet<>();

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
        this.name = name;
    }

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

}
