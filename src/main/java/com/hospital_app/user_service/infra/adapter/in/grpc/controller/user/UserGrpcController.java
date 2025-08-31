package com.hospital_app.user_service.infra.adapter.in.grpc.controller.user;

import com.hospital_app.proto.generated.user.*;
import com.hospital_app.user_service.application.port.in.user.ExistsByIdUserUseCase;
import com.hospital_app.user_service.application.port.in.user.FindByIdUserUseCase;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.mapper.grpc.UserGrpcMapper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class UserGrpcController extends UserServiceGrpc.UserServiceImplBase {

    private final FindByIdUserUseCase findByIdUserUseCase;
    private final ExistsByIdUserUseCase existsByIdUserUseCase;
    private final UserGrpcMapper userGrpcMapper;

    public UserGrpcController(FindByIdUserUseCase findByIdUserUseCase, ExistsByIdUserUseCase existsByIdUserUseCase, UserGrpcMapper userGrpcMapper) {
        this.findByIdUserUseCase = findByIdUserUseCase;
        this.existsByIdUserUseCase = existsByIdUserUseCase;
        this.userGrpcMapper = userGrpcMapper;
    }

    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        String userId = request.getUserId();
        User user = findByIdUserUseCase.execute(UUID.fromString(userId));
        GetUserResponse userResponse = userGrpcMapper.toResponseDto(user);
        responseObserver.onNext(userResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void doctorExists(UserExistsRequest request, StreamObserver<UserExistsResponse> responseObserver) {
        String userId = request.getUserId();
        boolean exists = existsByIdUserUseCase.execute(UUID.fromString(userId), Role.DOCTOR);
        responseObserver.onNext(UserExistsResponse.newBuilder().setExists(exists).build());
        responseObserver.onCompleted();
    }

    @Override
    public void patientExists(UserExistsRequest request, StreamObserver<UserExistsResponse> responseObserver) {
        String userId = request.getUserId();
        boolean exists = existsByIdUserUseCase.execute(UUID.fromString(userId), Role.PATIENT);
        responseObserver.onNext(UserExistsResponse.newBuilder().setExists(exists).build());
        responseObserver.onCompleted();
    }

}
