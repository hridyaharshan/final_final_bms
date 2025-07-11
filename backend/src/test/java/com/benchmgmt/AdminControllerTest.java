package com.benchmgmt;

import com.benchmgmt.dto.PreApprovedEmailRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for AdminController and pre-approved email logic.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String obtainAdminJwtToken() throws Exception {
        String loginRequest = "{" +
                "\"email\": \"ankit@bounteous.com\"," +
                "\"password\": \"1234\"}";
        String response = mockMvc.perform(post("/bms/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(response).get("token").asText();
    }

    private String uniqueTrainerEmail() {
        return "trainer_" + UUID.randomUUID() + "@example.com";
    }

    @Test
    @DisplayName("Should add a pre-approved trainer email successfully")
    void addPreApprovedEmail_Success() throws Exception {
        String adminJwt = obtainAdminJwtToken();
        String trainerEmail = uniqueTrainerEmail();
        PreApprovedEmailRequest request = new PreApprovedEmailRequest();
        request.setEmail(trainerEmail);
        mockMvc.perform(post("/bms/admin/add-trainer-email")
                .header("Authorization", "Bearer " + adminJwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Trainer email added successfully")));
    }

    @Test
    @DisplayName("Should not add duplicate pre-approved trainer email")
    void addPreApprovedEmail_Duplicate() throws Exception {
        String adminJwt = obtainAdminJwtToken();
        String trainerEmail = uniqueTrainerEmail();
        PreApprovedEmailRequest request = new PreApprovedEmailRequest();
        request.setEmail(trainerEmail);
        // First add
        mockMvc.perform(post("/bms/admin/add-trainer-email")
                .header("Authorization", "Bearer " + adminJwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        // Try to add again
        mockMvc.perform(post("/bms/admin/add-trainer-email")
                .header("Authorization", "Bearer " + adminJwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Email already exists")));
    }

    @Test
    @DisplayName("Should get all pre-approved trainer emails")
    void getAllPreApprovedEmails() throws Exception {
        String adminJwt = obtainAdminJwtToken();
        String trainerEmail1 = uniqueTrainerEmail();
        String trainerEmail2 = uniqueTrainerEmail();
        PreApprovedEmailRequest request1 = new PreApprovedEmailRequest();
        request1.setEmail(trainerEmail1);
        PreApprovedEmailRequest request2 = new PreApprovedEmailRequest();
        request2.setEmail(trainerEmail2);
        // Add two emails
        mockMvc.perform(post("/bms/admin/add-trainer-email")
                .header("Authorization", "Bearer " + adminJwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)));
        mockMvc.perform(post("/bms/admin/add-trainer-email")
                .header("Authorization", "Bearer " + adminJwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)));
        // Get all
        mockMvc.perform(get("/bms/admin/trainer-emails")
                .header("Authorization", "Bearer " + adminJwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItem(trainerEmail1)))
                .andExpect(jsonPath("$", hasItem(trainerEmail2)));
    }
} 