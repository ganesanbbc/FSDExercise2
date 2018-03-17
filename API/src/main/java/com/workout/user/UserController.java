package com.workout.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    public static final String REGISTER = "/register";
    @Autowired
    private UserService service;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.POST, value = REGISTER)
    public ResponseEntity<String> createUser(@RequestBody User userObj) {


        Long userExists = service.findByUserName(userObj);

        ResponseEntity<String> response = null;
        if (null != userExists && 0 != userExists) {
            response = new ResponseEntity<String>(new Gson().toJson(generateMessage("There is already a user registered with the email provided!", "Error")), HttpStatus.FORBIDDEN);
        } else {
            userObj.setPassword(passwordEncoder.encode(userObj.getPassword()));
            service.createUser(userObj);
            ;
            response = new ResponseEntity<String>(new Gson().toJson(generateMessage("User has been registered successfully!", "Success")), HttpStatus.OK);
        }
        return response;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public ResponseEntity<String> authenticateUser(@RequestHeader("userName") String user_name,
                                                   @RequestHeader("password") String password) {

        ResponseEntity<String> response = null;
        Long userId = service.authenticateUser(user_name, password);
        if (userId != null) {
            response = new ResponseEntity(new Gson().toJson(generateMessage("User Authenticated Successfully.", "Success", String.valueOf(userId))), HttpStatus.OK);
        } else {
            response = new ResponseEntity(new Gson().toJson(generateMessage("Invalid User Credentails.", "Error")), HttpStatus.FORBIDDEN);
        }

        return response;
    }

    private Map<String, Object> generateMessage(String... s) {
        Map<String, Object> message = new HashMap<String, Object>();
        message.put("message", s[0]);
        if (s.length == 1)
            message.put("Status", s[1]);
        if (s.length == 2)
            message.put("UserId", s[2]);
        return message;
    }

}
