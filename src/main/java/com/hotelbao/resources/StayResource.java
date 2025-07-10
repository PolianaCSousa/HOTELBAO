package com.hotelbao.resources;

import com.hotelbao.dtos.StayDTO;
import com.hotelbao.services.StayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    //findAll
    @Operation(
            description = "Get all stays",
            summary = "Get all stays",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    @GetMapping(produces = "application/json")// Acha todas as estadias
    public ResponseEntity<Page<StayDTO>> findAll(Pageable pageable) {
        Page<StayDTO> stays = stayService.findAll(pageable);
        return ResponseEntity.ok(stays);
    }

    //findById
    @Operation(
            description = "Get a stay",
            summary = "Get a stay by its id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @GetMapping(value = "/{id}", produces = "application/json") // Acha as estadias por id da estadia
    public ResponseEntity<StayDTO> findById(@PathVariable Long id) {
        StayDTO stay = stayService.findById(id);
        return ResponseEntity.ok(stay);
    }


    //findUserStays
    @Operation(
            description = "Get user stays",
            summary = "Get all user stays by user id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @GetMapping(value = "/find-user-stays/{id}", produces = "application/json") // Acha todas as estadias por cliente
    public ResponseEntity<List<StayDTO>> findUserStays(@PathVariable Long id) {
        List<StayDTO> userStays = stayService.findByUser(id);
        return ResponseEntity.ok(userStays);
    }

    //findRoomStays
    @Operation(
            description = "Get rooms stays",
            summary = "Get all rooms stays by room id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @GetMapping(value = "/find-room-stays/{id}") // Acha todas as estadias por quarto
    public ResponseEntity<List<StayDTO>> findRoomStays(@PathVariable Long id) {
        List<StayDTO> roomStays = stayService.findByRoom(id);
        return ResponseEntity.ok(roomStays);
    }

    //insert
    @Operation(
            description = "Create a new stay",
            summary = "Create a new stay for a client",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
            }
    )
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> insert(@RequestBody StayDTO dto) {
        if(dto.getEndDate() == null){
            dto.setEndDate(dto.getStartDate().plusDays(1));
        }

        StayDTO existingStay = stayService.getRoomDate(dto.getRoomId(), dto.getEndDate(), dto.getStartDate());

        if (existingStay != null && existingStay.getId() != null) {
            return ResponseEntity.badRequest().body("Já existe uma estadia conflitante para este quarto e data.");
        }

        dto = stayService.insert(dto);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(dto.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(dto);
    }


    //update
    @Operation(
            description = "Update a stay",
            summary = "Update a stay",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
            }
    )
    @PostMapping(value = "/{id}",produces = "application/json") // Altera estadia
    public ResponseEntity<StayDTO> update(@PathVariable Long id, @RequestBody StayDTO dto) {
        dto = stayService.update(dto, id);

        return ResponseEntity.ok().body(dto);
    }


    //delete
    @Operation(
            description = "Delete a stay",
            summary = "Delete a stay by its id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        stayService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Delete all stays",
            summary = "Delete all stays",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @DeleteMapping
    public ResponseEntity<Void> deleteAllStays() {

        stayService.deleteAllStays();
        return ResponseEntity.noContent().build();
    }

}
