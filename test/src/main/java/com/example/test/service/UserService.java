package com.example.test.service;

import com.example.test.DTO.Request.LoginRequest;
import com.example.test.DTO.Request.UserRequest;
import com.example.test.DTO.Request.UserUpdateRequest;
import com.example.test.DTO.Response.UserResponse;
import com.example.test.entity.Client;
import com.example.test.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public Client createUser(UserRequest request){
        Client user = new Client();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setDob(request.getDob());
        user.setSex(request.getSex());
        user.setLocal(request.getLocal());

        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public Client updateUser(String userId, UserUpdateRequest request) {
        Client user = getUser(userId);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setDob(request.getDob());
        user.setSex(request.getSex());
        user.setLocal(request.getLocal());
        return userRepository.save(user);
    }

    public List<Client> getUsers(){
        return userRepository.findAll();
    }

    public Client getUser(String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không thể tìm thấy người dùng "));
    }

    public List<Client> searchUsers(String keyword) {
        return userRepository.findByNameContainingIgnoreCase(keyword);
    }

    // New authentication methods
    public Client authenticate(LoginRequest loginRequest) {
//        Optional<Client> userOptional = userRepository.findByUsername(loginRequest.getUsername());
//
//        if (userOptional.isPresent()) {
//            Client user = userOptional.get();
//            // In a real application, you should use a password encoder
//            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//                return user;
//            }
//        }
//
//        throw new RuntimeException("Sai tên đăng nhập hoặc mật khẩu");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Sai tên đăng nhập hoặc mật khẩu"));

        boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!authenticated) throw new RuntimeException("Sai tên đăng nhập hoặc mật khẩu");
        return user;
    }

}