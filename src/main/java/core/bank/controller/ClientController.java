package core.bank.controller;

import core.bank.exception.ResourceNotFoundException;
import core.bank.model.Client;
import core.bank.repositories.ClientRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients(@RequestParam(required = false) String name){
        List<Client> clients = new ArrayList<Client>();

        if(name == null){
            clientRepository.findAll().forEach(clients::add);
        } else {
            clientRepository.findByName(name).forEach(clients::add);
        }
        if (clients.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clients,HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") long id){
        Client client = clientRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not Found Client with Id = " + id));

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client){
        Client _client = clientRepository.save(new Client(
                client.getName(),
                client.getSurname(),
                client.getCitizenship(),
                client.getGender(),
                client.getPersonalNumber(),
                client.getEmail()
        ));
        return new ResponseEntity<>(_client, HttpStatus.CREATED);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client client){
        Client _client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found client with id" + id));

        _client.setName(client.getName());
        _client.setSurname(client.getSurname());
        _client.setCitizenship(client.getCitizenship());
        _client.setGender(client.getGender());
        _client.setPersonalNumber(client.getPersonalNumber());
        _client.setEmail(client.getEmail());

        return new ResponseEntity<>(clientRepository.save(_client), HttpStatus.OK);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") long id){
        clientRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("clients")
    public ResponseEntity<HttpStatus> deleteAllClients(){
        clientRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
