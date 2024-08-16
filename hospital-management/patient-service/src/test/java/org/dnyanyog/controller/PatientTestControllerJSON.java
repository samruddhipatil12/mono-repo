package org.dnyanyog.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.dnyanyog.common.ResponseCode;
import org.dnyanyog.dto.PatientRequest;
import org.dnyanyog.entity.Patients;
import org.dnyanyog.repo.PatientsRepository;
import org.dnyanyog.services.PatientManagementServiceImp;
import org.dnyanyog.utils.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(MockitoExtension.class)
public class PatientTestControllerJSON {

  @Mock private PatientsRepository patientRepo;

  @InjectMocks private PatientManagementServiceImp patientService;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private WebApplicationContext context;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testAddPatientSuccess() throws Exception {
    PatientRequest request = new PatientRequest();
    request.setAddress("Wagholi");
    request.setBirth_date("2003-01-01");
    request.setFirst_examination_date("2023-06-01");
    request.setGender("Female");
    request.setMobile("1234567890");
    request.setPatientNameEnglish("Samruddhi Patil");
    request.setPatient_name_marathi("समृद्धी पाटील");

    Patients patient = new Patients();
    patient.setPatientId(IdGenerator.generatePatientId());
    patient.setAddress("Wagholi");
    patient.setBirth_date("2003-01-01");
    patient.setFirst_examination_date("2023-06-01");
    patient.setGender("Female");
    patient.setMobile("1234567890");
    patient.setPatientNameEnglish("Samruddhi Patil");
    patient.setPatient_name_marathi("समृद्धी पाटील");

    when(patientRepo.save(any(Patients.class))).thenReturn(patient);

    mockMvc
        .perform(
            post("/patients/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.PATIENT_ADDED.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.PATIENT_ADDED.getMessage()))
        .andExpect(jsonPath("$.address").value("Wagholi"))
        .andExpect(jsonPath("$.birth_date").value("2003-01-01"))
        .andExpect(jsonPath("$.first_examination_date").value("2023-06-01"))
        .andExpect(jsonPath("$.gender").value("Female"))
        .andExpect(jsonPath("$.mobile").value("1234567890"))
        .andExpect(jsonPath("$.patientNameEnglish").value("Samruddhi Patil"))
        .andExpect(jsonPath("$.patient_name_marathi").value("समृद्धी पाटील"));
  }

  @Test
  public void testAddPatientFailure() throws Exception {
    PatientRequest request = new PatientRequest();
    request.setAddress("Wagholi");
    request.setBirth_date("2003-01-01");
    request.setFirst_examination_date("2023-06-01");
    request.setGender("Female");
    request.setMobile("1234567890");
    request.setPatientNameEnglish("Samruddhi Patil");
    request.setPatient_name_marathi("समृद्धी पाटील");

    when(patientRepo.save(any(Patients.class))).thenThrow(DataIntegrityViolationException.class);

    mockMvc
        .perform(
            post("/patients/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.PATIENT_FAILED.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.PATIENT_FAILED.getMessage()));
  }

  @Test
  public void testUpdatePatientSuccess() throws Exception {
    String patientId = "123456";
    PatientRequest request = new PatientRequest();
    request.setAddress("Wagholi");
    request.setBirth_date("2003-01-01");
    request.setFirst_examination_date("2023-06-01");
    request.setGender("Female");
    request.setMobile("1234567890");
    request.setPatientNameEnglish("Samruddhi Patil");
    request.setPatient_name_marathi("समृद्धी पाटील");

    Patients patient = new Patients();
    patient.setPatientId(patientId);
    patient.setAddress("Wagholi");
    patient.setBirth_date("2003-01-01");
    patient.setFirst_examination_date("2023-06-01");
    patient.setGender("Female");
    patient.setMobile("1234567890");
    patient.setPatientNameEnglish("Samruddhi Patil");
    patient.setPatient_name_marathi("समृद्धी पाटील");

    when(patientRepo.findByPatientId(patientId)).thenReturn(Collections.singletonList(patient));
    when(patientRepo.save(any(Patients.class))).thenReturn(patient);

    mockMvc
        .perform(
            post("/patients/update/" + patientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.PATIENT_UPDATED.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.PATIENT_UPDATED.getMessage()))
        .andExpect(jsonPath("$.address").value("Wagholi"))
        .andExpect(jsonPath("$.birth_date").value("2003-01-01"))
        .andExpect(jsonPath("$.first_examination_date").value("2023-06-01"))
        .andExpect(jsonPath("$.gender").value("Female"))
        .andExpect(jsonPath("$.mobile").value("1234567890"))
        .andExpect(jsonPath("$.patientNameEnglish").value("Samruddhi Patil"))
        .andExpect(jsonPath("$.patient_name_marathi").value("समृद्धी पाटील"));
  }

  @Test
  public void testUpdatePatientFailure() throws Exception {
    String patientId = "123456";
    PatientRequest request = new PatientRequest();
    request.setAddress("Wagholi");
    request.setBirth_date("2003-01-01");
    request.setFirst_examination_date("2023-06-01");
    request.setGender("Female");
    request.setMobile("1234567890");
    request.setPatientNameEnglish("Samruddhi Patil");
    request.setPatient_name_marathi("समृद्धी पाटील");

    when(patientRepo.findByPatientId(patientId)).thenReturn(Collections.emptyList());

    mockMvc
        .perform(
            post("/patients/update/" + patientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.PATIENT_NOT_UPDATED.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.PATIENT_NOT_UPDATED.getMessage()));
  }

  @Test
  public void testSearchPatientSuccess() throws Exception {
    String patientId = "123456";
    Patients patient = new Patients();
    patient.setPatientId(patientId);
    patient.setAddress("Wagholi");
    patient.setBirth_date("2003-01-01");
    patient.setFirst_examination_date("2023-06-01");
    patient.setGender("Female");
    patient.setMobile("1234567890");
    patient.setPatientNameEnglish("Samruddhi Patil");
    patient.setPatient_name_marathi("समृद्धी पाटील");

    when(patientRepo.findByPatientId(patientId)).thenReturn(Collections.singletonList(patient));

    mockMvc
        .perform(post("/patients/search/" + patientId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.SEARCH_PATIENT.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.SEARCH_PATIENT.getMessage()))
        .andExpect(jsonPath("$.address").value("Wagholi"))
        .andExpect(jsonPath("$.birth_date").value("2003-01-01"))
        .andExpect(jsonPath("$.first_examination_date").value("2023-06-01"))
        .andExpect(jsonPath("$.gender").value("Female"))
        .andExpect(jsonPath("$.mobile").value("1234567890"))
        .andExpect(jsonPath("$.patientNameEnglish").value("Samruddhi Patil"))
        .andExpect(jsonPath("$.patient_name_marathi").value("समृद्धी पाटील"));
  }

  @Test
  public void testSearchPatientFailure() throws Exception {
    String patientId = "123456";

    when(patientRepo.findByPatientId(patientId)).thenReturn(Collections.emptyList());

    mockMvc
        .perform(post("/patients/search/" + patientId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.PATIENT_NOT_UPDATED.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.PATIENT_NOT_UPDATED.getMessage()));
  }
}
