package com.lms.service;

import com.lms.dto.AuthRequest;
import com.lms.dto.AuthResponse;
import com.lms.dto.Role;
import com.lms.entity.Librarian;
import com.lms.entity.Member;
import com.lms.entity.User;
import com.lms.repository.LibrarianRepository;
import com.lms.repository.MemberRepository;
import com.lms.repository.UserRepository;
import com.lms.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final LibrarianRepository librarianRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponse register(AuthRequest authRequest, Role role) {
        if (userRepository.existsByEmail(authRequest.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        authRequest.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        User memberUser = User.builder()
                .email(authRequest.getEmail())
                .role(role)
                .password(authRequest.getPassword())
                .build();
        User user = userRepository.save(memberUser);
        if (Role.MEMBERS.equals(role)) {
            Member memberDto = Member.builder()
                    .name(authRequest.getName())
                    .mobile(authRequest.getMobile())
                    .user(user)
                    .build();
            memberRepository.save(memberDto);
        } else if (Role.LIBRARIAN.equals(role)) {
            Librarian librarianDto = Librarian.builder()
                    .name(authRequest.getName())
                    .user(user)
                    .build();
            librarianRepository.save(librarianDto);
        } else {
            throw new IllegalArgumentException("Invalid role");
        }
        String token = jwtUtil.generateToken(user);
        return AuthResponse.builder().userId(user.getId()).token(token).build();
    }

    public AuthResponse doLogin(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new InsufficientAuthenticationException("Unauthorised");
        }
        String token = jwtUtil.generateToken(user);
        return AuthResponse.builder().token(token).userId(user.getId()).build();
    }
}
