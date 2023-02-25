package com.driver.model;
//package com.example.Book_My_Show_Application.Entities;

import javax.persistence.*;

@Entity
@Table(name = "admin")

public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;

    private String username;

    private String password;

    //////////constructor/////////

    public Admin() {
    }
    /////////////////getter & setter///////////////////////


    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}