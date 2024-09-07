package com.pfa.project.auth;

import com.pfa.project.Dto.UserDto;
import com.pfa.project.Entities.Admin;
import com.pfa.project.Entities.Enum.OrderStatus;
import com.pfa.project.Entities.Enum.Role;
import com.pfa.project.Entities.Enum.Status;
import com.pfa.project.Entities.Enum.TokenType;
import com.pfa.project.Entities.Order;
import com.pfa.project.Entities.User;
import com.pfa.project.Entities.token.Token;
import com.pfa.project.Entities.token.TokenRepository;
import com.pfa.project.Repository.AdminRepository;
import com.pfa.project.Repository.OrderRepository;
import com.pfa.project.Repository.UserRepository;
import com.pfa.project.Config.Jwt.JwtService;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository ;
    private final OrderRepository orderRepository ;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(Status.ACTIVE)
                .role(request.getRole())
                .build();

        var savedUser = userRepository.save(user);

        Order order = new Order();
        order.setTotalAmount(0L);
        order.setUser(savedUser);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepository.save(order);

        var jwtToken = jwtService.generateToken(savedUser);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return new AuthenticationResponse(user, jwtToken);
    }



    public String generateTokenWithUserId(User user) {
        var jwtToken = jwtService.generateToken(user);
        return jwtToken;
    }

    private void saveUserToken(User user, String jwtToken){
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);

    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
