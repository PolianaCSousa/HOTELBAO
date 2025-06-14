package com.hotelbao.dtos;

import com.hotelbao.entities.Stay;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.Date;

public class StayDTO {

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    private LocalDateTime startDate;
    private LocalDateTime endDate; // se for branco é o próximo dia

    public StayDTO() {
    }

    public StayDTO(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public StayDTO(Stay stay) {
        this.id = stay.getId();
        this.startDate = stay.getStartDate();
        this.endDate = stay.getEndDate();
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
                '}';
    }
}
