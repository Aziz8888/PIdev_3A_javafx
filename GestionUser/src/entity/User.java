/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Aziz
 */
public class User {
    private int id ;
    private String email,lastname,password,roles ;

    public User(String email, String lastname, String password, String roles) {
        this.email = email;
        this.lastname = lastname;
        this.password = password;
        this.roles = roles;
    }
    
    public User(int id,String email, String lastname, String password, String roles) {
        this.id = id;
        this.email = email;
        this.lastname = lastname;
        this.password = password;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" + "email=" + email + ", lastname=" + lastname + ", password=" + password + ", roles=" + roles + '}';
    }
    
}
