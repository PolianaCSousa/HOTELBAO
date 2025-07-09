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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;


@RestController
@RequestMapping(value = "/room") // /rooms é um endpoint
@Tag(name = "Room", description = "Controller for Rooms")
public class RoomResource {

    @Autowired
    private RoomService roomService;
    private RoomRepository roomRepository;


    //findAll
    @Operation(
            description = "Get all rooms",
            summary = "Get all rooms",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<RoomDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "20") Integer size,
                                                 @RequestParam(value = "direction",defaultValue = "ASC") String direction,
                                                 @RequestParam(value = "orderBy",defaultValue = "id") String orderBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);

        Page<RoomDTO> rooms = roomService.findAll(pageable);

        return ResponseEntity.ok().body(rooms);
    }


    //findById
    @Operation(
            description = "Get a room",
            summary = "Get a room by its id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<RoomDTO> findById(@PathVariable Long id) {

        RoomDTO room = roomService.findById(id);

        return ResponseEntity.ok().body(room);
    }

    //insert
    @Operation(
            description = "Create a new room",
            summary = "Create a new room",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
            }
    )
    @PostMapping(produces = "application/json")
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
    @Operation(
            description = "Update a room",
            summary = "Update a room",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @PostMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<RoomDTO> update(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {

        roomDTO = roomService.update(roomDTO, id);

        return ResponseEntity.ok().body(roomDTO);
    }

    //delete
    @Operation(
            description = "Delete a room",
            summary = "Delete a room",
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

        roomService.delete(id);

        return ResponseEntity.noContent().build();
    }


    @Operation(
            description = "Delete all rooms",
            summary = "Delete all rooms",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @DeleteMapping
    public ResponseEntity<Void> deleteAllRooms() {

        roomService.deleteAllRooms();
        return ResponseEntity.noContent().build();
    }

}



















