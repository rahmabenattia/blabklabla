package com.isi.myfinelproject;

public class User {
    public String username , email;
    public Integer age;

    public User(String username, Integer age, String email, String password, String rad){

    }
    public User(String username,String email,Integer age ){
        this.username = username;
        this.email = email;
        this.age = age;
    }
}
