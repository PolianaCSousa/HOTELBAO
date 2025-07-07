package com.hotelbao.resources;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbao.dtos.RoomDTO;
import com.hotelbao.entities.Room;
import com.hotelbao.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RoomResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String username;
    private String password;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2000L;

        username = "teste@testeintegrado.com";
        password = "123456";


    }

    @Test
    public void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
        ResultActions result = mockMvc.perform(
                get("") //todo por o caminho
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(jsonPath("$.content[0].description").value("Quarto Teste"));
        result.andExpect(jsonPath("$.content[1].description").value("Quarto Unico"));

    }

    @Test
    public void updateShouldReturnDtoWhenIdExists() throws Exception {

        RoomDTO dto = Factory.createRoomDTO();
        String dtoJson = objectMapper.writeValueAsString(dto);
        System.out.println(dtoJson);
        String descriptionExpected = dto.getDescription();

        ResultActions result =
                mockMvc.perform(
                        put("/room/{id}",existingId)
                                .header("Authorization","Bearer " // + token
                                )
                                .content(dtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.description").value(descriptionExpected));

    }


    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {

        RoomDTO dto = Factory.createRoomDTO();
        String dtoJson = objectMapper.writeValueAsString(dto);

        ResultActions result =
                mockMvc.perform(
                        put("/room/{id}",nonExistingId)
                                .header("Authorization","Bearer " // + token
                                        )
                                .content(dtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isNotFound());

    }


    @Test
    public void insertShouldReturnNewObjectWhenDataIsCorrect() throws Exception { //fiz sozinha :D

        RoomDTO dto = Factory.createRoomDTO();
        String dtoJson = objectMapper.writeValueAsString(dto);

        String descriptionExpected = dto.getDescription();

        ResultActions result =
                mockMvc.perform(
                        post("/room")
                                .header("Authorization","Bearer " // + token
                                )
                                .content(dtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.description").value(descriptionExpected));
    }


    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {

        ResultActions result =
                mockMvc.perform(
                        delete("/room/{id}",existingId)
                                .header("Authorization","Bearer " //+ token
                                )
                );

        result.andExpect(status().isNoContent());
    }


    @Test
    public void deleteShouldReturnNoFoundWhenIdDoesNotExists() throws Exception {

        ResultActions result =
                mockMvc.perform(
                        delete("/room/{id}",nonExistingId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isNotFound());

    }


    @Test
    public void findByIdShouldReturnRoomWhenIdExists() throws Exception {

        ResultActions result =
                mockMvc.perform(
                        get("/room/{id}",existingId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isOk());
        String resultJson = result.andReturn().getResponse().getContentAsString();
        System.out.println(resultJson);

        RoomDTO dto = objectMapper.readValue(resultJson, RoomDTO.class);

        Assertions.assertEquals(existingId, dto.getId());
        Assertions.assertEquals("Quarto Unico",dto.getDescription());
    }

}