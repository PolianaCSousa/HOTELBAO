package com.hotelbao.services;

import com.hotelbao.dtos.RoomDTO;
import com.hotelbao.entities.Room;
import com.hotelbao.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    //sempre buscaremos os dados no repository e converteremos para DTO

    //findAll
    @Transactional(readOnly = true)
    public Page<RoomDTO> findAll(Pageable pageable) {

        Page<Room> rooms = roomRepository.findAll(pageable);

        return rooms.map(room -> new RoomDTO(room));
    }

    //findById
    @Transactional(readOnly = true)
    public RoomDTO findById(Long id) {

        Optional<Room> room = roomRepository.findById(id);
        RoomDTO roomDTO = new RoomDTO(room.get());

        return roomDTO;
    }

    //insert
    @Transactional
    public RoomDTO insert(RoomDTO roomDTO) {
        Room entity = new Room();

        entity.setDescription(roomDTO.getDescription());
        entity.setPrice(roomDTO.getPrice());
        entity.setImage_url(roomDTO.getImage_url());
        entity = roomRepository.save(entity);

        return new RoomDTO(entity);

    }

    //update
    @Transactional
    public RoomDTO update(RoomDTO roomDTO, Long id) {
        Room entity = roomRepository.getReferenceById(id);

        entity.setDescription(roomDTO.getDescription());
        entity.setPrice(roomDTO.getPrice());
        entity.setImage_url(roomDTO.getImage_url());
        entity = roomRepository.save(entity);

        return new RoomDTO(entity);
    }

    //delete
    @Transactional
    public void delete(Long id) {


        roomRepository.deleteById(id);

    }
}
