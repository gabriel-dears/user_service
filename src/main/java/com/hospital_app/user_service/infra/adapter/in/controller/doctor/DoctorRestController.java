package com.hospital_app.user_service.infra.adapter.in.controller.doctor;

import com.hospital_app.user_service.application.port.in.user.ExistsByIdUserUseCase;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.infra.adapter.in.controller.doctor.dto.DoctorExistsResponseDto;
import com.hospital_app.user_service.infra.swagger.DoctorApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("doctor")
public class DoctorRestController implements DoctorApi {

    private final ExistsByIdUserUseCase existsByIdUserUseCase;

    public DoctorRestController(ExistsByIdUserUseCase existsByIdUserUseCase) {
        this.existsByIdUserUseCase = existsByIdUserUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorExistsResponseDto> doctorExists(@PathVariable UUID id) {
        return ResponseEntity.ok(new DoctorExistsResponseDto(existsByIdUserUseCase.execute(id, Role.DOCTOR)));
    }

}
