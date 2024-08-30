package com.vairiscw.wssandroid.data.environment;

public class Time {
    Long id;
    String designation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Time(Long id, String designation) {
        this.id = id;
        this.designation = designation;
    }
}
