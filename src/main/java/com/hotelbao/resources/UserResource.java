package com.hotelbao.resources;
import com.hotelbao.dtos.UserDTO;
import com.hotelbao.dtos.UserInsertDTO;
import com.hotelbao.projections.RoomDetailsProjection;
import com.hotelbao.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

@RestController
@RequestMapping(value = "/user")

public class UserResource {

    @Autowired
    private UserService userService;

    //findAll
    @Operation(
            description = "Get all users",
            summary = "Get all users",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<UserDTO> users = userService.findAll(pageable);
        return ResponseEntity.ok(users);
    }


    //findById
    @Operation(
            description = "Get a user",
            summary = "Get a user by its id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    //busca valor estadia mais cara por usuario
    @Operation(
            description = "Get most expensive user stay",
            summary = "Get most expensive user stay by passing the user id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @GetMapping(value = "/expensive/{id}")
    public ResponseEntity<RoomDetailsProjection> getExpensiveStay(@PathVariable Long id) {
        RoomDetailsProjection room = userService.expensiveStay(id);
        return ResponseEntity.ok(room);
    }

    //busca valor estadia mais barata por usuario
    @Operation(
            description = "Get most cheap user stay",
            summary = "Get most cheap user stay by passing the user id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @GetMapping(value = "/cheap/{id}")
    public RoomDetailsProjection getCheapStay(@PathVariable Long id) {
        return userService.cheapStay(id);
    }

    //busca estadia valor total por usuario
    @Operation(
            description = "Get total value of a user stay",
            summary = "Get total value of a user stay",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @GetMapping(value = "/total/{id}")
    public RoomDetailsProjection getTotalStay(@PathVariable Long id) {
        return userService.totalStay(id);
    }

    @Operation(
            description = "Get user Nfe",
            summary = "Get the user nfe",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @GetMapping(value = "/nfe/{id}")
    public String getNfe(@PathVariable Long id) {
        return userService.getNfe(id);
    }


    //insert
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
    @PostMapping(produces = "application/json")
    public ResponseEntity<UserDTO> insert(@RequestBody @Valid UserInsertDTO userDTO) {
        UserDTO user = userService.insert(userDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }


    //update
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
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserDTO> update(@Valid @PathVariable Long id, @RequestBody UserDTO dto) {
        dto = userService.update(id,dto);

        return ResponseEntity.ok().body(dto);
    }


    //delete
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
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //signup
    @Operation(
            description = "Sign Up",
            summary = "You can sign up.",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
            }
    )
    @PostMapping(value="/signup", produces = "application/json")
    public ResponseEntity<UserDTO>
    signup(@Valid @RequestBody UserInsertDTO dto) { //o @Valid serve pra aplicar as validaçoes que adicionamos no DTO (@Size, @Positive, ect.)
        UserDTO user = userService.signup(dto);

        //essa uri que retornamos já está atendendo ao HATEOAS - eu insiro um produto e ja digo pro cara o que eu posso fazer com aquele produto, e nesse caço ele foi no cabeçalho da resposta
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest(). //pega o caminho da minha aplicação
                        path("/{id}").//ele adiciona o id na rota
                        buildAndExpand(user.getId()).
                toUri();

        return ResponseEntity.created(uri).body(user);
    }
}
