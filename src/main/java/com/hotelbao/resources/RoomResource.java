package com.hotelbao.resources;


import com.hotelbao.dtos.RoomDTO;
import com.hotelbao.entities.Room;
import com.hotelbao.repository.RoomRepository;
import com.hotelbao.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(value = "/room") // /rooms é um endpoint
public class RoomResource {

    @Autowired
    private RoomService roomService;
    private RoomRepository roomRepository;


    //findAll
    @GetMapping
    public ResponseEntity<Page<RoomDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "20") Integer size,
                                                 @RequestParam(value = "direction",defaultValue = "ASC") String direction,
                                                 @RequestParam(value = "orderBy",defaultValue = "id") String orderBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);

        Page<RoomDTO> rooms = roomService.findAll(pageable);

        return ResponseEntity.ok().body(rooms);
    }


    //findById
    @GetMapping(value = "/{id}")
    public ResponseEntity<RoomDTO> findById(@PathVariable Long id) {

        RoomDTO room = roomService.findById(id);

        return ResponseEntity.ok().body(room);
    }

    //insert
    @PostMapping
    public ResponseEntity<RoomDTO> insert(@RequestBody RoomDTO roomDTO) {

        roomDTO = roomService.insert(roomDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(roomDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(roomDTO);
    }

    //update
    @PostMapping(value = "/{id}")
    public ResponseEntity<RoomDTO> update(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {

        roomDTO = roomService.update(roomDTO, id);

        return ResponseEntity.ok().body(roomDTO);
    }

    //delete
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        roomService.delete(id);

        return ResponseEntity.noContent().build();
    }

}



















