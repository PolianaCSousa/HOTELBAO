package com.hotelbao.entities;

import com.hotelbao.dtos.RoomDTO;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

// a entity do java já é a model e a migration do laravel
@Entity //informa que room será uma tabela no banco
@Table(name="tb_room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //o que isso faz?

    private Long id;
    private String description;
    private Float price;
    private String image_url;
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    private void prePersist() {createdAt = Instant.now();}

    @PreUpdate void preUpdate() {updatedAt = Instant.now();}

    //relacionamento aqui depois

    //construtores
    public Room(Long id,String description, Float price, String image_url) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
    }

    public Room() {}

    public Room(RoomDTO dto) {
        this.id = dto.getId();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
        this.image_url = dto.getImage_url();
    }

    //getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    //equals e hash code do id
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Room room)) return false;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    //toString

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image_url='" + image_url + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }


}
