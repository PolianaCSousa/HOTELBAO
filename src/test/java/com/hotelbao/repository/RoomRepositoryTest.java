package com.hotelbao.repository;

import com.hotelbao.entities.Room;
import com.hotelbao.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    private Long existingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
    }

    @Test
    @DisplayName(value = "Verificando se o objeto existe no BD depois de deletado.")
    public void deleteShouldDeleteObjectWhenIdExists() {
        roomRepository.deleteById(existingId);
        Optional<Room> optional = roomRepository.findById(existingId);

        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName(value = "Verificando o autoincremento da chave primária.")
    public void insertShouldPersistWithAutoincrementIdWhenIdNull() {
        Room room = Factory.createRoom();
        room.setId(0L);

        Room p = roomRepository.save(room);
        Optional<Room> obj
                = roomRepository.findById(p.getId());

        Assertions.assertTrue(obj.isPresent());
        Assertions.assertNotEquals(0, obj.get().getId());
        Assertions.assertEquals(26, obj.get().getId());
    }
}
