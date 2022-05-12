package com.careerdevs.myHalwayLocker.models;


import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
public class Stuff {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String content;

    public Stuff() {}

    public Stuff(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
