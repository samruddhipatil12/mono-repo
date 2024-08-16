package org.dnyanyog.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.dnyanyog.common.ResponseCode;
import org.dnyanyog.dto.AppointmentRequest;
import org.dnyanyog.entity.Appointment;
import org.dnyanyog.repo.AppointmentServiceRepository;
import org.dnyanyog.services.AppointmentServiceImp;
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
public class AppointmentTestControllerJSON {

  @Mock private AppointmentServiceRepository appointmentRepo;

  @InjectMocks private AppointmentServiceImp appointmentManagementService;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(appointmentManagementService).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testAddAppointment() throws Exception {
    AppointmentRequest request = new AppointmentRequest();
    request.setPatientNameEnglish("Samruddhi Patil");
    request.setExamination_date("2023-06-01");

    Appointment newAppointment = new Appointment();
    newAppointment.setAppointmentId(IdGenerator.generateAppointmentId());
    newAppointment.setPatientId(IdGenerator.generatePatientId());
    newAppointment.setPatientNameEnglish(request.getPatientNameEnglish());
    newAppointment.setExamination_date(request.getExamination_date());

    when(appointmentRepo.save(any(Appointment.class))).thenReturn(newAppointment);

    mockMvc
        .perform(
            post("/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.APPOINTMENT_ADDED.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.APPOINTMENT_ADDED.getMessage()))
        .andExpect(jsonPath("$.appointmentId").value(newAppointment.getAppointmentId()))
        .andExpect(jsonPath("$.patientId").value(newAppointment.getPatientId()));
  }

  @Test
  public void testUpdateAppointment() throws Exception {
    String patientId = "testPatientId";
    AppointmentRequest request = new AppointmentRequest();
    request.setPatientNameEnglish("Samruddhi Patil");
    request.setExamination_date("2023-06-01");

    Appointment existingAppointment = new Appointment();
    existingAppointment.setAppointmentId("testAppointmentId");
    existingAppointment.setPatientId(patientId);
    existingAppointment.setPatientNameEnglish("Samruddhi Patil");
    existingAppointment.setExamination_date("2023-06-01");

    when(appointmentRepo.findByPatientId(patientId)).thenReturn(List.of(existingAppointment));
    when(appointmentRepo.save(any(Appointment.class))).thenReturn(existingAppointment);

    mockMvc
        .perform(
            put("/appointments/" + patientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.APPOINTMENT_UPDATED.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.APPOINTMENT_UPDATED.getMessage()))
        .andExpect(jsonPath("$.patientId").value(patientId));
  }

  @Test
  public void testSearchAppointment() throws Exception {
    String appointmentId = "testAppointmentId";

    Appointment existingAppointment = new Appointment();
    existingAppointment.setAppointmentId(appointmentId);
    existingAppointment.setPatientId("testPatientId");
    existingAppointment.setPatientNameEnglish("Samruddhi Patil");
    existingAppointment.setExamination_date("2023-06-01");

    when(appointmentRepo.findByAppointmentId(appointmentId))
        .thenReturn(List.of(existingAppointment));

    mockMvc
        .perform(get("/appointments/" + appointmentId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.SEARCH_APPOINTMENT.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.SEARCH_APPOINTMENT.getMessage()))
        .andExpect(jsonPath("$.appointmentId").value(appointmentId));
  }

  @Test
  public void testDeleteAppointment() throws Exception {
    String appointmentId = "testAppointmentId";

    Appointment existingAppointment = new Appointment();
    existingAppointment.setAppointmentId(appointmentId);
    existingAppointment.setPatientId("testPatientId");

    when(appointmentRepo.findByAppointmentId(appointmentId))
        .thenReturn(List.of(existingAppointment));

    mockMvc
        .perform(delete("/appointments/" + appointmentId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(ResponseCode.DELETE_APPOINTMENT.getStatus()))
        .andExpect(jsonPath("$.message").value(ResponseCode.DELETE_APPOINTMENT.getMessage()));
  }
}
