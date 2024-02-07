package com.example.foojeserver.controllers;

import com.example.foojeserver.model.Client;
import com.example.foojeserver.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientRepo clientRepo;

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable Long clientId) {
        return clientRepo.findById(clientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientRepo.findAll();
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/add")
    public ResponseEntity<Client> addCart(@RequestBody Client client) {
        Client savedClient = clientRepo.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @PostMapping("/login")
    public String login(@RequestBody Client clientLoginRequest) {
        Client existingClient = clientRepo.findByEmail(clientLoginRequest.getEmail());

        if (existingClient != null && existingClient.getPassword().equals(clientLoginRequest.getPassword())) {
            return "Zalogowano jako klient;" + existingClient.getCLIENT_ID();
        } else {
            return "Błąd logowania;0";
        }
    }

    @PutMapping("updateClient/{clientId}")
    public ResponseEntity<String> updateClient(@PathVariable long clientId, @RequestBody Client updatedClient) {
        try {
            Optional<Client> existingClientOptional = clientRepo.findById(clientId);

            if (existingClientOptional.isPresent()) {
                Client existingClient = existingClientOptional.get();

                if(updatedClient.getPhoneNumber().equals("656656656")) existingClient.setPhoneNumber(existingClient.getPhoneNumber());
                else existingClient.setPhoneNumber(updatedClient.getPhoneNumber());

                if(updatedClient.getEmail().equals("emailExample")) existingClient.setEmail(existingClient.getEmail());
                else existingClient.setEmail(updatedClient.getEmail());

                if(updatedClient.getPassword().equals("passwordExample")) existingClient.setPassword(existingClient.getPassword());
                else existingClient.setPassword(updatedClient.getPassword());

                existingClient.setName(existingClient.getName());
                existingClient.setLastname(existingClient.getLastname());

                clientRepo.save(existingClient);

                return ResponseEntity.ok("Zaktualizowano klienta o ID: " + clientId);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie znaleziono klienta o ID: " + clientId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd podczas aktualizacji klienta");
        }
    }
}
