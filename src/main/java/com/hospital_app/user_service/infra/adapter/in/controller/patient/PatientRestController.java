package com.hospital_app.user_service.infra.adapter.in.controller.patient;

import com.hospital_app.user_service.application.port.in.user.ExistsByIdUserUseCase;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.infra.adapter.in.controller.patient.dto.PatientExistsResponseDto;
import com.hospital_app.user_service.infra.swagger.PatientApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("patient")
public class PatientRestController implements PatientApi {

    private final ExistsByIdUserUseCase existsByIdUserUseCase;

    public PatientRestController(ExistsByIdUserUseCase existsByIdUserUseCase) {
        this.existsByIdUserUseCase = existsByIdUserUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientExistsResponseDto> patientExists(@PathVariable UUID id) {
        return ResponseEntity.ok(new PatientExistsResponseDto(existsByIdUserUseCase.execute(id, Role.PATIENT)));
    }

}
