package com.check.ichecker.user.service;


import com.check.ichecker.jwt.TokenUtils;
import com.check.ichecker.user.Model.Role;
import com.check.ichecker.user.domain.Auth;
import com.check.ichecker.user.domain.AuthRepository;
import com.check.ichecker.user.domain.Users;
import com.check.ichecker.user.domain.UsersRepository;
import com.check.ichecker.user.dto.TokenResponse;
import com.check.ichecker.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;
    private final TokenUtils tokenUtils;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Users> findByUserId(String userId) {

        return usersRepository.findByUserId(userId);
    }

    @Transactional
    public boolean signUp(UserRequest userRequest) {

        boolean existUser = usersRepository.existsByUserId(userRequest.getUserId());

        if(existUser)
            return false;

        Users usersEntity =
                usersRepository.save(
                        Users.builder()
                                .password(passwordEncoder.encode(userRequest.getPassword()))
                                .grade(userRequest.getGrade())
                                .name(userRequest.getName())
                                .role(Role.USER)
                                .userId(userRequest.getUserId())
                                .build());

        String refreshToken = tokenUtils.saveRefreshToken(usersEntity);

        authRepository.save(
                Auth.builder().users(usersEntity).refreshToken(refreshToken).build());

        return true;
    }

    @Transactional
    public TokenResponse signIn(UserRequest userRequest) throws Exception {
        Users usersEntity =
                usersRepository
                        .findByUserId(userRequest.getUserId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Auth authEntity =
                authRepository
                        .findByUsersId(usersEntity.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));

        if (!passwordEncoder.matches(userRequest.getPassword(), usersEntity.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = "";
        String refreshToken = authEntity.getRefreshToken();

        if (tokenUtils.isValidRefreshToken(refreshToken)) {
            accessToken = tokenUtils.generateJwtToken(authEntity.getUsers());
            return TokenResponse.builder()
                    .ACCESS_TOKEN(accessToken)
                    .REFRESH_TOKEN(authEntity.getRefreshToken())
                    .build();
        } else {
            accessToken = tokenUtils.generateJwtToken(authEntity.getUsers());
            refreshToken = tokenUtils.saveRefreshToken(usersEntity);
            authEntity.refreshUpdate(refreshToken);
        }

        return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).build();
    }

    public List<Users> findUsers() {
        return usersRepository.findAll();
    }
}