package com.example.auth_service.service;

import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.LoginResponse;
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.dto.RegisterResponse;
import com.example.auth_service.entities.User;
import com.example.auth_service.exception.AppException;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.utils.JwtUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    Argon2 argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id,
            16,
            32);

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getPasswordConfirm())){
            throw new AppException(400, "Password and Confirm password must be same");
        }
        if (userRepository.findByUserCode(registerRequest.getUserCode()) != null){
            throw new AppException(409, "User is existed");
        }
        char[] password = registerRequest.getPassword().toCharArray();
        String hash = argon2.hash(3, // Number of iterations
                64 * 1024, // 64mb
                1, // how many parallel threads to use
                password);
        User newUser = User.builder()
                .userCode(registerRequest.getUserCode())
                .password(hash)
                .role(registerRequest.getRole())
                .build();
        userRepository.save(newUser);
        return RegisterResponse.builder().message("Register successfully").build();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUserCode(loginRequest.getUserCode());
        if (user == null){
            throw new AppException(400, "Username or password invalid");
        }
        if (!argon2.verify(user.getPassword(), loginRequest.getPassword().toCharArray())){
            throw new AppException(400, "Username or password invalid");
        }
        String token = jwtUtil.generateToken(user.getUserCode(), user.getRole());
        return LoginResponse.builder().token(token).build();
    }
}
