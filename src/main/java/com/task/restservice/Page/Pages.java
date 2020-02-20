package com.task.restservice.Page;

import com.task.restservice.Category.Category;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Pages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;
    private String name;
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = true, updatable = true, nullable = false)
    private Category category;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() { return this.category; }

    public Pages() {}

    public Pages(String name, String content, Category category) {
        this.name = name;
        this.content = content;
        this.category = category;
    }
}
