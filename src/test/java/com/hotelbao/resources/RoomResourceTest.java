package com.hotelbao.resources;

import com.hotelbao.dtos.RoomDTO;
import com.hotelbao.services.RoomService;
import com.hotelbao.util.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebMvcTest(value = RoomResource.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })

public class RoomResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService roomService;
    private RoomDTO roomDTO;
    private PageImpl<RoomDTO> page;
    private Long existingId;
    private Long nonExistingId;


    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2000L;
        roomDTO = Factory.createRoomDTO();
        roomDTO.setId(1L);
        page = new PageImpl<>(List.of(roomDTO));
    }

    @Test
    void findAllShouldReturnAllPage() throws Exception {

        when(roomService.findAll(any()))
                .thenReturn(page);

        ResultActions result =
                mockMvc.perform(
                        get("/room")
                                .accept("application/json")
                );

        result.andExpect(status().isOk());
    }


    @Test
    void findByIdShouldReturnRoomWhenIdExists() throws Exception {

        when(roomService.findById(existingId))
                .thenReturn(roomDTO);

        ResultActions result =
                mockMvc.perform(
                        get("/room{id}", existingId)
                                .accept("application/json")
                );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.description").value(roomDTO.getDescription()));
    }

}
