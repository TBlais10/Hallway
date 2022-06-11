package com.careerdevs.myHalwayLocker.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Locker {
    @Id @GeneratedValue private Long id;


    @OneToOne
    @JoinColumn(name = "locker_id", referencedColumnName = "id")
    private Person person;

    @OneToMany
    @JoinColumn(name = "locker_id", referencedColumnName = "id")
    private List<Content> myContent;

    public Locker() {
    }

    public Locker(Person person) {
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getStudent() {
        return person;
    }

    public void setStudent(Person person) {
        this.person = person;
    }

    public List<Content> getMyContent() {
        return myContent;
    }

    public void setMyContent(List<Content> myContent) {
        this.myContent = myContent;
    }
}
