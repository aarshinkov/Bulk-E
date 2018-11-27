package com.bulke.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @Column(name = "USER_ID")
    @SequenceGenerator(name = "SEQ_GEN_USER", sequenceName = "S_USERS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_USER")
    private Integer userId;

    @Column(name = "USERNAME")
    @Size(min = 4, max = 50)
    @NotNull
    private String username;

    @Column(name = "NAME", nullable = false)
    @Size(min = 4, max = 100)
    @NotNull
    private String name;

    @Column(name = "PASSWORD", nullable = false)
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "EMAIL")
    @Size(max = 200)
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLENAME"))
    private List<Role> roles = new ArrayList<>();

//    private String[] selectedRoles = new String[]{};

//    private Date createdOn;


    @Override
    public String toString() {
        return "userId=" + userId + ", username=" + username + ", name=" + name + ", password=" + password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
//
//    public String[] getSelectedRoles() {
//        return selectedRoles;
//    }
//
//    public void setSelectedRoles(String[] selectedRoles) {
//        this.selectedRoles = selectedRoles;
//    }
//
//    public Date getCreatedOn() {
//        return createdOn;
//    }
//
//    public void setCreatedOn(Date createdOn) {
//        this.createdOn = createdOn;
//    }
}