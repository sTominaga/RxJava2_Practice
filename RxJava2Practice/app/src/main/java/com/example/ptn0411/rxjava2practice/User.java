package com.example.ptn0411.rxjava2practice;

/**
 * Created by tominaga on 2017/03/11.
 */

public class User {

    private String name;
    private String company;
    private String email;
    private String bio;
    private String created_at;
    private String updated_at;

    public User(String name, String company, String email,
                String bio, String created_at, String updated_at) {

        this.name = name;
        this.company = company;
        this.email = email;
        this.bio = bio;
        this.created_at = created_at;
        this.updated_at = updated_at;

    }

    public String getName() {
        return this.name;
    }

}
