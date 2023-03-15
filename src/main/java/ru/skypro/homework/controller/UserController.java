package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.entity.User;

import javax.validation.Valid;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "UserController")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserMapper userMapper;
    private final AvatarService avatarService;

    @Operation(summary = "updateUser", description = "updateUser")
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        printLogInfo("updateUser", "patch", "/me");
        return ResponseEntity.ok(userMapper.toDto(userService.updateUser(userDto)));
    }

    @Operation(summary = "setPassword", description = "setPassword")
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@Valid @RequestBody NewPasswordDto newPasswordDto) {
        userService.newPassword(newPasswordDto.getNewPassword(), newPasswordDto.getCurrentPassword());
        return ResponseEntity.ok(newPasswordDto);
    }

    @Operation(summary = "updateUserImage", description = "updateUserImage")
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultipartFile> updateUserImage(Authentication authentication, @RequestBody MultipartFile image) {
        printLogInfo("updateUserImage", "patch", "/me/image");
        User user = userService.findUserByName(authentication);
          return ResponseEntity.ok().build();
    }


    @Operation(summary = "getUser", description = "getUser")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        printLogInfo("getUser", "get", "/id");
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @Operation(summary = "getUsers", description = "getUsers")
    @GetMapping("/me")
    public ResponseWrapper<UserDto> getUsers() {
        printLogInfo("getUsers", "get", "/me");
        return ResponseWrapper.of(userMapper.toDto(userService.getUsers()));
    }

    private void printLogInfo(String name, String requestMethod, String path) {
        logger.info("Вызван метод " + name + ", адрес "
                + requestMethod.toUpperCase() + " запроса /users" + path);
    }
}
