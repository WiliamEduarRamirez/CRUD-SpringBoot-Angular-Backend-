package com.wiliams.springboot.controller;

import com.wiliams.springboot.models.entity.Client;
import com.wiliams.springboot.models.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClientResController {

    @Autowired
    private IClientService _clientService;

    @GetMapping("/clients")
    public List<Client> list() {
        return _clientService.findAll();
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Client client = null;
        Map<String, Object> response = new HashMap<>();

        try {
            client = _clientService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error en la BD");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (client == null) {
            response.put("message", "El cliente ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @PostMapping("/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> add(@RequestBody Client client) {
        Map<String, Object> response = new HashMap<>();
        Client newClient = null;

        try {
            newClient = _clientService.save(client);
        } catch (DataAccessException e) {
            response.put("message", "Error en la BD");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El Cliente ha sido creado con exito");
        response.put("client", newClient);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> edit(@RequestBody Client client, @PathVariable Long id) {
        Client updateClient = null;
        Client currentClient = null;
        Map<String, Object> response = new HashMap<>();

        try {
            currentClient = _clientService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error en la BD");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (currentClient == null) {
            response.put("message", "El cliente ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            currentClient.setNames(client.getNames());
            currentClient.setLastnames(client.getLastnames());
            currentClient.setEmail(client.getEmail());
            updateClient = _clientService.save(currentClient);
        } catch (DataAccessException e) {
            response.put("message", "Error en la BD");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El cliente ha sido editado con exito");
        response.put("client", updateClient);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Client currentClient = null;
        Map<String, Object> response = new HashMap<>();

        try {
            currentClient = _clientService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error en la BD");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (currentClient == null) {
            response.put("message", "El cliente ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            _clientService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error en la BD");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El cliente se elimino exitosamente");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
