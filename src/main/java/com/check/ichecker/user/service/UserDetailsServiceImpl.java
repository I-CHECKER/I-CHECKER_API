package com.check.ichecker.user.service;


import com.check.ichecker.user.Model.Role;
import com.check.ichecker.user.domain.Users;
import com.check.ichecker.user.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Users user = usersRepository.findByUserId(userId)
                .orElseThrow(()-> new UsernameNotFoundException("등록되지 않은 사용자 입니다"));

        boolean isManager = user.getRole() == Role.MANAGER;
        boolean isApproved = user.isApproved(); // 승인 여부

        return new UserDetailsImpl(user, isManager, isApproved);
    }
}