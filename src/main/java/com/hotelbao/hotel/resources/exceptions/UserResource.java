package com.hotelbao.hotel.resources.exceptions;

import com.hotelbao.hotel.dto.UserDTO;
import com.hotelbao.hotel.dto.UserInsertDTO;
import com.hotelbao.hotel.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Controller/Resource for Users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping(produces = "application/json")
    @Operation(
            description = "Get all users",
            summary = "Get all users",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")

    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<UserDTO> users = userService.findAll(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping (value = "/{id}", produces = "application/json")
    @Operation(
            description = "Create a new user",
            summary = "Create a new user",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
            }
    )

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {
        UserDTO user = userService.insert(dto);

        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().
                        path("/{id}").
                        buildAndExpand(user.getId()).
                toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(
            description = "Update a user",
            summary = "Update a user",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> update(@Valid @PathVariable Long id, @RequestBody UserDTO dto) {
        dto = userService.update(id,dto);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value="/{id}")
    @Operation(
            description = "Delete a user",
            summary = "Delete a user",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
