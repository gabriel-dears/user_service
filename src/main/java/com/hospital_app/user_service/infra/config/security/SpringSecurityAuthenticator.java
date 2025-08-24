package com.hospital_app.user_service.infra.config.security;

import com.hospital_app.user_service.application.port.out.security.Authenticator;
import com.hospital_app.user_service.application.port.out.security.dto.AuthDetailsDto;
import com.hospital_app.user_service.domain.exception.RoleNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuthenticator implements Authenticator {

    private final AuthenticationManager authenticationManager;

    public SpringSecurityAuthenticator(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthDetailsDto authenticate(String username, String password) {
        var authenticatedUser = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        UserDetailsAdapter userDetails = (UserDetailsAdapter) authenticatedUser.getPrincipal();
        GrantedAuthority role = userDetails.getAuthorities().stream().findFirst().orElseThrow(() -> new RoleNotFoundException("Role not found"));
        String replacedRole = role.getAuthority().replace("ROLE_", "");
        return new AuthDetailsDto(
                username,
                replacedRole
        );
    }
}
