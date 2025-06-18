package com.beer.order_forcast.model;
 
import jakarta.persistence.*;
import lombok.Data;
 
@Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
 
    @Column(nullable = false, unique = true, length = 255)
    private String email;
 
    @Column(nullable = false, length = 255)
    private String password;
 
    @Column(nullable = false, length = 255)
    private String name;
 
    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;
 
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public boolean isAdmin() {
    return isAdmin;
}

public void setAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
}

public boolean isDeleted() {
    return isDeleted;
}

public void setDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
}

public String getRole() {
    return isAdmin ? "admin" : "user";
}





}
