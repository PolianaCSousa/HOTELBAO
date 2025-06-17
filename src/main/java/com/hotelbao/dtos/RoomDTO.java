package com.hotelbao.dtos;

import com.hotelbao.entities.Room;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


//o DTO está pronto
public class RoomDTO {

    private Long id;
    @Size(min = 5, max = 200, message = "Deve conter entre 5 e 200 caracteres")
    @NotBlank(message = "Campo obrigatório")
    private String description;
    @Positive(message = "Preço deve ser um valor positivo")
    private double price;
    private String image_url;

    public RoomDTO() {}

    public RoomDTO(Long id, String description, double price, String image_url) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
