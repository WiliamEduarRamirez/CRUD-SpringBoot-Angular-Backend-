package com.wiliams.springboot.controller;

import com.wiliams.springboot.models.entity.Client;
import com.wiliams.springboot.models.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
    public Client details(@PathVariable Long id) {
        return _clientService.findById(id);
    }

    @PostMapping("/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public Client add(@RequestBody Client client) {
        /*client.setCreateAt(new Date());*/
        return _clientService.save(client);
    }

    @PutMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Client edit(@RequestBody Client client, @PathVariable Long id) {
        Client currentClient = _clientService.findById(id);

        currentClient.setNames(client.getNames());
        currentClient.setLastnames(client.getLastnames());
        currentClient.setEmail(client.getEmail());

        return _clientService.save(currentClient);
    }

    @DeleteMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        _clientService.delete(id);
    }

}
