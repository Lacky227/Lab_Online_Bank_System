package org.veedev.authservice.service;

import org.veedev.authservice.model.Client;

public interface ClientService {
    Client getClient(String phoneNumber);
}
