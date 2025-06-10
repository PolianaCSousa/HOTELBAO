package com.hotelbao.dtos;

import com.hotelbao.entities.Room;

//o DTO está pronto
public class RoomDTO {

    private Long id;
    private String description;
    private Float price;
    private String image_url;

    public RoomDTO() {}

    public RoomDTO(Long id, String description, Float price, String image_url) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
    }

    public RoomDTO(Room entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.image_url = entity.getImage_url();
    }

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

    @Override
    public String toString() {
        return "RoomDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
