package com.hotelbao.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hotelbao.entities.Stay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;

public class StayDTO {

    private Long id;

    @JsonProperty("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Campo obrigatório")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate; // se for branco é o próximo dia

    //adicionando os campos de user_id e room_id no DTO
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("room_id")
    private Long roomId;

    public StayDTO() {
    }

    public StayDTO(Long id, LocalDateTime startDate, LocalDateTime endDate, Long userId, Long roomId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.roomId = roomId;
    }

    public StayDTO(Stay stay) {
        this.id = stay.getId();
        this.startDate = stay.getStartDate();
        this.endDate = stay.getEndDate();

        if (stay.getUser() != null) {
            this.userId = stay.getUser().getId(); //busca o User associado com a estadia, com o metodo getUser(), e em seguida pega o id dele com getId() (metodo do User)
        }

        if (stay.getRoom() != null) {
            this.roomId = stay.getRoom().getId();
        }
    }

    public StayDTO(StayDTO roomDate) {
    }

    //geter e setter do id do user e do room
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "StayDTO{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", userId=" + userId +
                ", roomId=" + roomId +
                '}';
    }
}
