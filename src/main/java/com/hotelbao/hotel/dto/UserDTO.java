package com.hotelbao.hotel.dto;
import com.hotelbao.hotel.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    private String name;
    @NotBlank(message = "Campo obrigatório")
    private String username;
    @Email(message = "Favor, informar um e-mail válido")
    private String email;
    private String phone;

    private Set<RoleDTO> roles = new HashSet<>();


    public UserDTO() {
    }

    public UserDTO(Long id, String name, String username, String email, String phone) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
        username = user.getUsername();
        email = user.getEmail();
        phone = user.getPhone();

        user.getRoles()
                .forEach(role ->
                        roles.add(new RoleDTO(role))
                );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }

}
