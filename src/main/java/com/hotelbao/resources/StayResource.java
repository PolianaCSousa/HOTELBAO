package com.hotelbao.resources;

import com.hotelbao.dtos.StayDTO;
import com.hotelbao.services.StayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/stay")
public class StayResource {

    @Autowired
    private StayService stayService;

    @GetMapping// Acha todas as estadias
    public ResponseEntity<Page<StayDTO>> findAll(Pageable pageable) {
        Page<StayDTO> stays = stayService.findAll(pageable);
        return ResponseEntity.ok(stays);
    }

    @GetMapping(value = "/{id}") // Acha as estadias por id da estadia
    public ResponseEntity<StayDTO> findById(@PathVariable Long id) {
        StayDTO stay = stayService.findById(id);
        return ResponseEntity.ok(stay);
    }

    @GetMapping(value = "/find-user-stays/{id}") // Acha todas as estadias por cliente
    public ResponseEntity<List<StayDTO>> findUserStays(@PathVariable Long id) {
        List<StayDTO> userStays = stayService.findByUser(id);
        return ResponseEntity.ok(userStays);
    }

    @GetMapping(value = "/find-room-stays/{id}") // Acha todas as estadias por quarto
    public ResponseEntity<List<StayDTO>> findRoomStays(@PathVariable Long id) {
        List<StayDTO> roomStays = stayService.findByRoom(id);
        return ResponseEntity.ok(roomStays);
    }

    @PostMapping
    public ResponseEntity<StayDTO> insert(@RequestBody StayDTO dto) {
        dto = stayService.insert(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PostMapping(value = "/{id}") // Altera estadia
    public ResponseEntity<StayDTO> update(@PathVariable Long id, @RequestBody StayDTO dto) {
        dto = stayService.update(dto, id);

        return ResponseEntity.ok().body(dto);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        stayService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
