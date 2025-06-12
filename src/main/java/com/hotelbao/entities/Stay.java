package com.hotelbao.entities;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_stay")
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    private void prePersist() {createdAt = Instant.now();}

    @PreUpdate void preUpdate() {updatedAt = Instant.now();}

    @ManyToOne
    @JoinColumn(name = "id_user") // Define o nome da coluna de chave estrangeira
    private User user; // Relacionamento muitos-para-um com a entidade User

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // <editor-fold desc="Construtores">
    public Stay() {
    }

    public Stay(Long id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Stay(Stay stay) {
        this.id = stay.id;
        this.startDate = stay.startDate;
    }
    //</editor-fold>

    // <editor-fold desc="Getter e Setter">
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
                ", role=" + role +
                '}';
    }
}
