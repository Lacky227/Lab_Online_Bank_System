package org.veedev.authservice.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.veedev.authservice.model.Client;
import org.veedev.authservice.repository.ClientRepository;
import org.veedev.authservice.service.ClientService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public Client getClient(String phoneNumber) {
        Optional<Client> client = clientRepository.findByPhoneNumber(phoneNumber);
        if (client.isEmpty()) {
            throw new RuntimeException("No client found with phone number " + phoneNumber);
        }
        return client.get();
    }
}
