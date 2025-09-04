package com.hospital_app.user_service.infra.mapper.dto;

import com.hospital_app.common.db.pagination.ApplicationPage;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.CreateUserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.UpdateUserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.UserResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoMapperTest {

    private final UserDtoMapper mapper = new UserDtoMapper();

    @Test
    void toDomain_fromCreateRequest_setsAllFields() {
        CreateUserRequestDto dto = new CreateUserRequestDto(
                "John Doe",
                "johndoe",
                "john.doe@example.com",
                "supersecurepwd",
                "ADMIN"
        );

        User user = mapper.toDomain(dto);

        assertNull(user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("johndoe", user.getUsername());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("supersecurepwd", user.getPasswordHash());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void toDomain_fromUpdateRequest_setsAllFieldsAndId() {
        UUID id = UUID.randomUUID();
        UpdateUserRequestDto dto = new UpdateUserRequestDto(
                "Jane Roe",
                "janeroe",
                "jane.roe@example.com",
                "NURSE"
        );

        User user = mapper.toDomain(dto, id);

        assertEquals(id, user.getId());
        assertEquals("Jane Roe", user.getName());
        assertEquals("janeroe", user.getUsername());
        assertEquals("jane.roe@example.com", user.getEmail());
        assertEquals(Role.NURSE, user.getRole());
        assertNull(user.getPasswordHash());
    }

    @Test
    void toResponseDto_fromDomain_mapsAllFields() {
        User user = new User();
        UUID id = UUID.randomUUID();
        user.setId(id);
        user.setName("Ana Silva");
        user.setUsername("anasilva");
        user.setEmail("ana.silva@example.com");
        user.setRole(Role.PATIENT);
        user.setEnabled(true);

        UserResponseDto dto = mapper.toResponseDto(user);

        assertEquals(id, dto.id());
        assertEquals("Ana Silva", dto.name());
        assertEquals("anasilva", dto.username());
        assertEquals("ana.silva@example.com", dto.email());
        assertEquals("PATIENT", dto.role());
        assertTrue(dto.enabled());
    }

    @Test
    void toResponseDto_list_mapsEachItem() {
        User u1 = new User();
        u1.setId(UUID.randomUUID());
        u1.setName("A");
        u1.setUsername("a");
        u1.setEmail("a@example.com");
        u1.setRole(Role.DOCTOR);
        u1.setEnabled(true);

        User u2 = new User();
        u2.setId(UUID.randomUUID());
        u2.setName("B");
        u2.setUsername("b");
        u2.setEmail("b@example.com");
        u2.setRole(Role.NURSE);
        u2.setEnabled(false);

        List<UserResponseDto> list = mapper.toResponseDto(List.of(u1, u2));
        assertEquals(2, list.size());
        assertEquals("DOCTOR", list.get(0).role());
        assertEquals("NURSE", list.get(1).role());
    }

    @Test
    void toResponseDto_page_mapsMetadataAndContent() {
        User u = new User();
        u.setId(UUID.randomUUID());
        u.setName("C");
        u.setUsername("c");
        u.setEmail("c@example.com");
        u.setRole(Role.ADMIN);
        u.setEnabled(true);

        ApplicationPage<User> page = new ApplicationPage<>(
                1, // pageNumber
                5, // pageSize
                3, // totalPages
                12, // totalElements
                false, // isLast
                false, // isFirst
                List.of(u)
        );

        ApplicationPage<UserResponseDto> dtoPage = mapper.toResponseDto(page);

        assertEquals(1, dtoPage.getPageNumber());
        assertEquals(5, dtoPage.getPageSize());
        assertEquals(3, dtoPage.getTotalPages());
        assertEquals(12, dtoPage.getTotalElements());
        assertFalse(dtoPage.isLast());
        assertFalse(dtoPage.isFirst());
        assertEquals(1, dtoPage.getContent().size());
        assertEquals("ADMIN", dtoPage.getContent().getFirst().role());
    }
}
