package com.hotelbao.entities;
import com.hotelbao.dtos.StayDTO;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_stay")
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    private void prePersist() {createdAt = Instant.now();}

    @PreUpdate void preUpdate() {updatedAt = Instant.now();}

    @ManyToOne
    @JoinColumn(name = "user_id") // Define o nome da coluna de chave estrangeira
    private User user; // Relacionamento muitos-para-um com a entidade User

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // <editor-fold desc="Construtores">
    public Stay() {
    }

    public Stay(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Stay(Stay stay) {
        this.id = stay.id;
        this.startDate = stay.startDate;
        this.endDate = stay.endDate;
    }

    public Stay(StayDTO stayDTO) {
        this.id = stayDTO.getId();
        this.startDate = stayDTO.getStartDate();
        this.endDate = stayDTO.getEndDate();

        //atualiza o id do user e do room
        if (stayDTO.getUserId() != null) {
            User user = new User();
            user.setId(stayDTO.getUserId());
            this.setUser(user);
        }

        if (stayDTO.getRoomId() != null) {
            Room room = new Room();
            room.setId(stayDTO.getRoomId());
            this.setRoom(room);
        }

    }
    //</editor-fold>

    //getter e setter para o id do user e do room
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    // <editor-fold desc="Getter e Setter">
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    //</editor-fold>

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Stay stay)) return false;
        return Objects.equals(id, stay.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Stay{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", user=" + user +
                ", room=" + room +
                '}';
    }
}
