package com.isi.myfinelproject;

public class HelperClass {
    String username, email,  password;
    int age;

    public <string> HelperClass(string username, string email, string password, int age) {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {

        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public HelperClass(String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;

    }

    public HelperClass(){

    }
}
