package org.veedev.authservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.veedev.authservice.dto.PhoneNumberRequest;
import org.veedev.authservice.model.Client;
import org.veedev.authservice.service.ClientService;

@RestController
@RequestMapping("/profile")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public Client getProfile(@RequestHeader("X-Phone-Number") String phoneNumber) {
        return clientService.getClient(phoneNumber);
    }
}
