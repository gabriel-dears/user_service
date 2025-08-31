package com.hospital_app.user_service.infra.swagger;

import com.hospital_app.user_service.application.common.pagination.ApplicationPage;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.ChangeUserPasswordRequestDto;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.CreateUserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.UpdateUserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Tag(name = "User", description = "Operations related to user management")
public interface UserApi {

    @Operation(
            summary = "Find user by ID",
            description = "Returns a single user by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    ResponseEntity<UserResponseDto> findById(
            @Parameter(description = "ID of the user to be retrieved") @PathVariable UUID id
    );

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    ResponseEntity<UserResponseDto> create(
            @RequestBody @Valid CreateUserRequestDto createUserRequestDto,
            UriComponentsBuilder uriBuilder
    );

    @Operation(
            summary = "List users with pagination",
            description = "Returns paginated list of users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users retrieved",
                            content = @Content(schema = @Schema(implementation = ApplicationPage.class)))
            }
    )
    ResponseEntity<ApplicationPage<UserResponseDto>> findAll(
            @Parameter(description = "Page number") @RequestParam int pageNumber,
            @Parameter(description = "Page size") @RequestParam int pageSize
    );

    @Operation(
            summary = "Update user by ID",
            description = "Updates the user identified by the given ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    ResponseEntity<UserResponseDto> update(
            @Parameter(description = "ID of the user to update") @PathVariable UUID id,
            @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto
    );

    @Operation(
            summary = "Change user status",
            description = "Enable or disable a user",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User status changed"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    ResponseEntity<Void> changeStatus(
            @Parameter(description = "ID of the user") @PathVariable UUID id,
            @Parameter(description = "New status of the user") @RequestParam boolean enabled
    );

    @Operation(
            summary = "Change user password",
            description = "Changes the password of the user identified by the ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password changed"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    ResponseEntity<Void> changePassword(
            @Parameter(description = "ID of the user") @PathVariable UUID id,
            @RequestBody @Valid ChangeUserPasswordRequestDto changeUserPasswordRequestDto
    );

}
