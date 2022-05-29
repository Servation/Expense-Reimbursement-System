package com.revature.database;

import javax.persistence.*;

enum Type{
    Employee, Manager
}

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private int user_id;
    private String first_Name;
    private String last_Name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String username;
    private String password;

    public User(){}

    public User( String first_Name, String last_Name, String email, String type, String username, String password) {
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.email = email;
        this.type = Type.valueOf(type);
        this.username = username;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = Type.valueOf(type);
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

    @Override
    public String toString() {
        return "Name: " + first_Name + " " +last_Name +
                "\tEmail: " + email +
                "\tID: " + user_id +
                "\tType: " + type;
    }
}
