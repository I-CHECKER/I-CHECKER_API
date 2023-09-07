package com.check.ichecker.user.service;


import com.check.ichecker.jwt.TokenUtils;
import com.check.ichecker.user.Model.Role;
import com.check.ichecker.user.domain.Auth;
import com.check.ichecker.user.domain.AuthRepository;
import com.check.ichecker.user.domain.Users;
import com.check.ichecker.user.domain.UsersRepository;
import com.check.ichecker.user.dto.TokenResponse;
import com.check.ichecker.user.dto.UserSignInRequest;
import com.check.ichecker.user.dto.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    private final AuthenticationManager authenticationManager;

    public Optional<Users> findByUserId(String userId) {

        return usersRepository.findByUserId(userId);
    }

    @Transactional
    public boolean signUp(UserSignUpRequest userSignUpRequest) {

        boolean existUser = usersRepository.existsByUserId(userSignUpRequest.getUserId());

        if(existUser)
            return false;

        Users usersEntity =
                usersRepository.save(
                        Users.builder()
                                .password(passwordEncoder.encode(userSignUpRequest.getPassword()))
                                .grade(userSignUpRequest.getGrade())
                                .name(userSignUpRequest.getName())
                                .role(Role.USER)
                                .userId(userSignUpRequest.getUserId())
                                .build());

        String refreshToken = tokenUtils.saveRefreshToken(usersEntity);

        authRepository.save(
                Auth.builder().users(usersEntity).refreshToken(refreshToken).build());

        return true;
    }

    public TokenResponse login(UserSignInRequest request) throws Exception{

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserId(), request.getPassword()));

        String userId = request.getUserId();

        return createJwtToken(authentication, userId);
    }

    private TokenResponse createJwtToken(Authentication authentication, String userId) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = tokenUtils.generateToken(principal);


        return new TokenResponse(accessToken);
    }

    /*@Transactional
    public TokenResponse signIn(UserSignInRequest userSignInRequest) throws Exception {

        Users usersEntity =
                usersRepository
                        .findByUserId(userSignInRequest.getUserId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Auth authEntity =
                authRepository
                        .findByUsersId(usersEntity.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));

        if (!passwordEncoder.matches(userSignInRequest.getPassword(), usersEntity.getPassword())) {
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
        }

        accessToken = tokenUtils.generateJwtToken(authEntity.getUsers());
        refreshToken = tokenUtils.saveRefreshToken(usersEntity);
        authEntity.refreshUpdate(refreshToken);


        return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).build();
    }*/

    public List<Users> findUsers() {
        return usersRepository.findAll();
    }
}