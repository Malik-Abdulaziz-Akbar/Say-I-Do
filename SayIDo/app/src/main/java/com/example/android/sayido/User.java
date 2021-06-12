package com.example.android.sayido;

class User {
    String name;
    String password;
    String email;
    String phone;

    User(String name, String gender, String email, String phone) {
        this.name = name;
        this.password = gender;
        this.email = email;
        this.phone = phone;
    }

    User() {
    }

    public void setName(String name) {
        this.name = name;
    }

    void setPassword(String gender) {
        this.password = gender;
    }

    void setEmail(String email) {
        this.email = email;
    }

    void setPhone(String phone) {
        this.phone = phone;
    }
}
