package com.hospital_app.user_service.infra.swagger;

import com.hospital_app.user_service.infra.adapter.in.controller.patient.dto.PatientExistsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(name = "Patient", description = "Patient related operations")
public interface PatientApi {

    @Operation(
            summary = "Check if a patient exists",
            description = "Verifies if a patient with the given ID exists in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Patient found",
                            content = @Content(schema = @Schema(implementation = PatientExistsResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Patient not found"
                    )
            }
    )
    @GetMapping("/patients/{id}/exists")
    ResponseEntity<PatientExistsResponseDto> patientExists(
            @Parameter(description = "Patient UUID", required = true)
            @PathVariable UUID id
    );
}
