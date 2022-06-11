package com.careerdevs.myHalwayLocker.models;

import com.careerdevs.myHalwayLocker.Auth.User;

import java.util.List;

public class Teacher extends Person{
    private List<String> subjects;
    private boolean sub;

    public Teacher() {
    }

    public Teacher(Long id, Locker locker, String firstName, String lastName, User user, boolean sub) {
        super(id, locker, firstName, lastName, user);
        this.sub = sub;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public boolean isSub() {
        return sub;
    }

    public void setSub(boolean sub) {
        this.sub = sub;
    }
}
