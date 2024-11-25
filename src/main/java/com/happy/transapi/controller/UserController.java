package com.happy.transapi.controller;

import com.happy.transapi.entities.Users;
import com.happy.transapi.exceptions.InvalidRequestException;
import com.happy.transapi.reponses.GenericResponse;
import com.happy.transapi.repositories.UsersRepository;
import com.happy.transapi.requests.user.CreateUserRequest;
import com.happy.transapi.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/api/v2")
public class UserController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UsersRepository usersRepository;

    @PostMapping("/register")
    public <T> ResponseEntity createLead(@RequestBody @Valid CreateUserRequest request, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        Users user = new Users();
        user.setName(request.getName());
        user.setMobile(request.getMobile());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setLoginBy(request.getLoginBy());
        user.setLoginRawData(request.getLoginRawData());
        usersRepository.save(user);

        String token = jwtUtils.generateJwt(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        return new ResponseEntity(new GenericResponse<String>(200, "Successfully Created"), headers, HttpStatus.OK);
    }
}
