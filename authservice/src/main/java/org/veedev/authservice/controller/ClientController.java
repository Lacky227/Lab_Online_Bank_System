package org.veedev.authservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.veedev.authservice.dto.PhoneNumberRequest;
import org.veedev.authservice.model.Client;
import org.veedev.authservice.service.ClientService;

@RestController
@RequestMapping("/profile")
@AllArgsConstructor
@CrossOrigin
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public Client getProfile(@RequestBody PhoneNumberRequest request) {
        return clientService.getClient(request.getPhoneNumber());
    }
}
