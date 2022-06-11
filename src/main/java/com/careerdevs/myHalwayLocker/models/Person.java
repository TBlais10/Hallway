package com.careerdevs.myHalwayLocker.models;


import com.careerdevs.myHalwayLocker.Auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person {
    @Id @GeneratedValue private Long id;

    @OneToOne
    @JoinColumn(name = "locker_id", referencedColumnName = "id")
    private Locker locker;

    private String firstName;
    private String lastName;
    private List<Integer> cohort;

    @OneToOne
    @JsonIgnoreProperties("password")
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    public Person() {}

    public Person(Long id, Locker locker, String firstName, String lastName, User user) {
        this.id = id;
        this.locker = locker;
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Integer> getCohort() {
        return cohort;
    }

    public void setCohort(List<Integer> cohort) {
        this.cohort = cohort;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
