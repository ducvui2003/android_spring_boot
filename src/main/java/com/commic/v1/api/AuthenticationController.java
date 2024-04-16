package com.commic.v1.api;

import com.commic.v1.dto.requests.AuthenticationRequest;
import com.commic.v1.dto.requests.LogoutRequest;
import com.commic.v1.dto.responses.JwtResponse;
import com.commic.v1.services.authentication.IAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody AuthenticationRequest request){
        JwtResponse jwtObj = authenticationService.login(request);
        return ResponseEntity.ok(jwtObj);
    }

    @PostMapping("/logout")
    ResponseEntity<String> logout(@RequestBody LogoutRequest request){
        authenticationService.logout(request);
        return ResponseEntity.ok("logout");
    }

    //admin
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("/c")
//    ResponseEntity<String> c(){
//        return ResponseEntity.ok("c");
//    }
}
