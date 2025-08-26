package com.hospital_app.user_service.infra.swagger;

import com.hospital_app.user_service.infra.adapter.in.controller.doctor.dto.DoctorExistsResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface DoctorApi {

    default ResponseEntity<DoctorExistsResponseDto> doctorExists(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
