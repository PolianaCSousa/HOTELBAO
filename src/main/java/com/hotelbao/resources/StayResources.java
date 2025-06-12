package com.hotelbao.resources;

import com.hotelbao.dtos.StayDTO;
import com.hotelbao.entities.Stay;
import com.hotelbao.services.StayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping(value = "/stay")
public class StayResources {

    @Autowired
    private StayService stayService;

    @GetMapping
    public ResponseEntity<Page<Stay>> findAll(Pageable pageable) {
        Page<StayDTO> stays = stayService.findAll(pageable);
        return ResponseEntity.ok(stays);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StayDTO> findById(@PathVariable Long id) {
        StayDTO stay = stayService.findById(id);
        return ResponseEntity.ok(stay);
    }

}
