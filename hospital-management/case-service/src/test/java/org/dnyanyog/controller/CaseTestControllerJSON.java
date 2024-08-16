package org.dnyanyog.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.dnyanyog.common.ResponseCode;
import org.dnyanyog.dto.CaseRequest;
import org.dnyanyog.entity.Cases;
import org.dnyanyog.repo.CaseServiceRepository;
import org.dnyanyog.services.CaseServiceImp;
import org.dnyanyog.utils.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class CaseTestControllerJSON {

  @Mock private CaseServiceRepository caseRepo;

  @InjectMocks private CaseServiceImp caseManagementService;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(caseManagementService).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testAddCase() throws Exception {
    CaseRequest request = new CaseRequest();
    request.setPatientNameEnglish("Samruddhi Patil");
    request.setCase_number("12345");
    request.setSymptoms("Fever, Cough");
    request.setPrescription("Rest, Hydration");
    request.setExamination_date("2023-06-01");

    Cases newCase = new Cases();
    newCase.setPatientId(IdGenerator.generatePatientId());
    // newCase.setCaseId(IdGenerator.generateCaseId());
    newCase.setPatientNameEnglish(request.getPatientNameEnglish());
    newCase.setCase_number(request.getCase_number());
    newCase.setSymptoms(request.getSymptoms());
    newCase.setPrescription(request.getPrescription());
    newCase.setExamination_date(request.getExamination_date());

    when(caseRepo.save(any(Cases.class))).thenReturn(newCase);

    mockMvc
        .perform(
            post("/cases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.CASE_ADDED.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.CASE_ADDED.getMessage()))
        .andExpect(jsonPath("$.patientId").value(newCase.getPatientId()));
    // .andExpect(jsonPath("$.caseId").value(newCase.generateCaseId()));
  }

  @Test
  public void testUpdateCase() throws Exception {
    String caseId = "testCaseId";
    CaseRequest request = new CaseRequest();
    request.setPatientNameEnglish("Samruddhi Patil");
    request.setCase_number("12345");
    request.setSymptoms("Fever, Cough");
    request.setPrescription("Rest, Hydration");
    request.setExamination_date("2023-06-01");

    Cases existingCase = new Cases();
    // existingCase.setCaseId(caseId);
    existingCase.setPatientId("testPatientId");
    existingCase.setPatientNameEnglish("Samruddhi Patil");
    existingCase.setCase_number("12345");
    existingCase.setSymptoms("Fever, Cough");
    existingCase.setPrescription("Rest, Hydration");
    existingCase.setExamination_date("2023-06-01");

    when(caseRepo.findByCaseId(caseId)).thenReturn(List.of(existingCase));
    when(caseRepo.save(any(Cases.class))).thenReturn(existingCase);

    mockMvc
        .perform(
            put("/cases/" + caseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.CASE_UPDATED.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.CASE_UPDATED.getMessage()))
        .andExpect(jsonPath("$.caseId").value(caseId));
  }

  @Test
  public void testSearchCase() throws Exception {
    String caseId = "testCaseId";

    Cases existingCase = new Cases();
    // existingCase.setCaseId(caseId);
    existingCase.setPatientId("testPatientId");
    existingCase.setPatientNameEnglish("Samruddhi Patil");
    existingCase.setCase_number("12345");
    existingCase.setSymptoms("Fever, Cough");
    existingCase.setPrescription("Rest, Hydration");
    existingCase.setExamination_date("2023-06-01");

    when(caseRepo.findByCaseId(caseId)).thenReturn(List.of(existingCase));

    mockMvc
        .perform(get("/cases/" + caseId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.SEARCH_CASE.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.SEARCH_CASE.getMessage()))
        .andExpect(jsonPath("$.caseId").value(caseId));
  }

  @Test
  public void testDeleteCase() throws Exception {
    String patientId = "testPatientId";

    Cases existingCase = new Cases();
    // existingCase.setCaseId("testCaseId");
    existingCase.setPatientId(patientId);

    when(caseRepo.findByPatientId(patientId)).thenReturn(List.of(existingCase));

    mockMvc
        .perform(delete("/cases/" + patientId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.DELETE_CASE.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.DELETE_CASE.getMessage()));
  }
}
