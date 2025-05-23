package com.borisov.DZ_4.controllers;

import com.borisov.DZ_4.dto.UserCreateDTO;
import com.borisov.DZ_4.dto.UserResponseDTO;
import com.borisov.DZ_4.service.UserService;
import com.borisov.DZ_4.util.UserEmailAlreadyExistException;
import com.borisov.DZ_4.util.UserErrorResponse;
import com.borisov.DZ_4.util.UserNotFoundException;
import com.borisov.DZ_4.util.UserValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public List<UserResponseDTO> getUsers(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable("id") int id){
        UserResponseDTO user = userService.findById(id);
        return user;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(
            @RequestBody @Valid UserCreateDTO userCreateDTO,
            BindingResult bindingResult,
            HttpServletRequest request){
        //check valid and check email unique
        if (bindingResult.hasErrors()) throw new UserValidationException(getValidErrMsg(bindingResult));
        if (userService.existsByEmail(userCreateDTO.getEmail(), null))
            throw new UserEmailAlreadyExistException();

        int id = userService.save(userCreateDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/users/" + id)
                .build();
    }

    @PatchMapping("/{id}")
    public UserResponseDTO update(
            @PathVariable("id") int id,
            @RequestBody @Valid UserCreateDTO userCreateDTO,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new UserValidationException(getValidErrMsg(bindingResult));
        if (userService.existsByEmail(userCreateDTO.getEmail(), id))
            throw new UserEmailAlreadyExistException();

        return userService.updateById(id, userCreateDTO);
    }



    private String getValidErrMsg(BindingResult bindingResult){
        StringBuilder errMsg = new StringBuilder();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errMsg.append(fieldError.getField())
                    .append(" - ").append(fieldError.getDefaultMessage())
                    .append(";");
        }
        return errMsg.toString();
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e){
        LOGGER.error(e.getMessage());
        UserErrorResponse response = new UserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserValidationException e) {
        LOGGER.error(e.getMessage());
        UserErrorResponse response = new UserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserEmailAlreadyExistException e) {
        LOGGER.error(e.getMessage());
        UserErrorResponse response = new UserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
