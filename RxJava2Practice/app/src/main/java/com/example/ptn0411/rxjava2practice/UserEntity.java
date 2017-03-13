package com.example.ptn0411.rxjava2practice;

/**
 * Created by tominaga on 2017/03/11.
 */

public class UserEntity {

    public String name;
    public String company;
    public String email;
    public String bio;
    public String created_at;
    public String updated_at;

    public UserEntity(String name, String company, String email,
                String bio, String created_at, String updated_at) {

        this.name = name;
        this.company = company;
        this.email = email;
        this.bio = bio;
        this.created_at = created_at;
        this.updated_at = updated_at;

    }

}
