package com.uliana.MedicalSystemApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uliana.MedicalSystemApi.controller.DoctorController;
import com.uliana.MedicalSystemApi.dto.DoctorDTO;
import com.uliana.MedicalSystemApi.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DoctorService doctorService;

    private DoctorController doctorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doctorController = new DoctorController(doctorService);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
    }

    @Test
    void createDoctor() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(1L);
        doctorDTO.setName("John Doe");

        when(doctorService.create(any(DoctorDTO.class))).thenReturn(doctorDTO);

        mockMvc.perform(post("/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(doctorDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(doctorService).create(any(DoctorDTO.class));
    }

    @Test
    void getById() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(1L);
        doctorDTO.setName("Jane Smith");

        when(doctorService.getById(1L)).thenReturn(doctorDTO);

        mockMvc.perform(get("/doctor/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jane Smith"));

        verify(doctorService).getById(1L);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}