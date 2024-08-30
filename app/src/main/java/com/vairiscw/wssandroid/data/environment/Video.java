package com.vairiscw.wssandroid.data.environment;

public class Video {
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

    public Video(Long id, String designation) {
        this.id = id;
        this.designation = designation;
    }
}
