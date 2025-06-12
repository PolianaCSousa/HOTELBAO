package com.hotelbao.dtos;

import com.hotelbao.entities.Stay;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class StayDTO {

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    private Date startDate;
    private Date endDate; // se for branco é o próximo dia

    public StayDTO() {
    }

    public StayDTO(Long id, Date startDate, Date endDate) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
