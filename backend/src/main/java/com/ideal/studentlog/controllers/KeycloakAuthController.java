package com.ideal.studentlog.controllers;


import com.ideal.studentlog.services.KeycloakRestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "AuthController", description = "Keycloak authentication and authorization controller")
@RestController
@RequestMapping(path = "/keycloak", produces = MediaType.APPLICATION_JSON_VALUE)
public class KeycloakAuthController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${keycloak-user-info-uri}")
    private String keycloakUserInfo;

    @Value("${keycloak-user-creation-uri}")
    private String keycloakCreateUserEndPoint;

    @Autowired
    private KeycloakRestService keycloakRestService;

    @Operation(summary = "Provide username and password of keycloak user. Use the filed accessToken")
    @PostMapping
    public String getToken(String username, String password) {
        return keycloakRestService.login(username,password);
    }
    @GetMapping
    public String getUserInfo(HttpServletRequest httpServletRequest) {

        String token = httpServletRequest.getHeader("Authorization");
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        return restTemplate.postForObject(keycloakUserInfo, request, String.class);
    }

    @Operation(summary = "Provide username to create user")
    @PostMapping(path = "/{name}")
    public String createUser(HttpServletRequest httpServletRequest, @PathVariable("name") String name) {
        String token = httpServletRequest.getHeader("Authorization");
/*
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", token);
        headers.add("Content-Type", "application/json");
*/

        // create request body
        JSONObject request = new JSONObject();
        request.put("username", name);
     //   request.put("password", password);

        // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

        // send request and parse result
        ResponseEntity<String> loginResponse = restTemplate
                .exchange(keycloakCreateUserEndPoint, HttpMethod.POST, entity, String.class);

      /*  MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", name);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Void> response =  restTemplate.postForEntity(keycloakCreateUserEndPoint, request, Void.class);
*/
        return "User created successfully";
    }
}
