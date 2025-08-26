package com.hospital_app.user_service.infra.swagger;

import com.hospital_app.user_service.infra.adapter.in.controller.patient.dto.PatientExistsResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface PatientApi {

    default ResponseEntity<PatientExistsResponseDto> patientExists(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
