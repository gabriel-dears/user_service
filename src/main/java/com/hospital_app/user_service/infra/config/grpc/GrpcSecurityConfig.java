package com.hospital_app.user_service.infra.config.grpc;

import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.check.AccessPredicate;
import net.devh.boot.grpc.server.security.check.GrpcSecurityMetadataSource;
import net.devh.boot.grpc.server.security.check.ManualGrpcSecurityMetadataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcSecurityConfig {

    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
        return (call, headers) -> null;
    }


    @Bean
    GrpcSecurityMetadataSource grpcSecurityMetadataSource() {
        ManualGrpcSecurityMetadataSource source = new ManualGrpcSecurityMetadataSource();
        source.setDefault(AccessPredicate.permitAll());
        return source;
    }

}
