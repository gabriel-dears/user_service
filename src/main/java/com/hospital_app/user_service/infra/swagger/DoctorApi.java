package com.hospital_app.user_service.infra.swagger;

import com.hospital_app.user_service.infra.adapter.in.controller.doctor.dto.DoctorExistsResponseDto;
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

@Tag(name = "Doctor", description = "Doctor related operations")
public interface DoctorApi {

    @Operation(
            summary = "Check if a doctor exists",
            description = "Verifies if a doctor with the given ID exists in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Doctor found",
                            content = @Content(schema = @Schema(implementation = DoctorExistsResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Doctor not found"
                    )
            }
    )
    @GetMapping("/doctors/{id}/exists")
    ResponseEntity<DoctorExistsResponseDto> doctorExists(
            @Parameter(description = "Doctor UUID", required = true)
            @PathVariable UUID id
    );
}
