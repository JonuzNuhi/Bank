package core.bank.controller;

import core.bank.DTO.ClientAccountDTO;
import core.bank.exception.ResourceNotFoundException;
import core.bank.model.Account;
import core.bank.model.Client;
import core.bank.repositories.AccountRepository;
import core.bank.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class AccountController {

   @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/clients/{clientId}/accounts")
    public ResponseEntity<ClientAccountDTO> getAllAccountsByClientId(@PathVariable(value = "clientId") Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found client with Id: " + clientId));

        List<Account> accounts = accountRepository.findByClientId(clientId);

        ClientAccountDTO clientAccountDTO = new ClientAccountDTO(
                client.getId(),
                client.getName(),
                client.getSurname(),
                client.getEmail(),
                accounts
        );

        return new ResponseEntity<>(clientAccountDTO, HttpStatus.OK);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountByClientId(@PathVariable(value = "id") long id){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found comment with id"));
        return new ResponseEntity<>(account,HttpStatus.OK);
    }

    @PostMapping("/clients/{clientId}/accounts")
    public ResponseEntity<Account> createAccount(@PathVariable(value = "clientId") long clientId,
                                                 @RequestBody Account accountRequest){
        Account account = clientRepository.findById(clientId).map(client -> {
            accountRequest.setClient(client);
            return accountRepository.save(accountRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found client with id: " + clientId));

        return new ResponseEntity<>(account,HttpStatus.CREATED);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") long id, @RequestBody Account accountRequest){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccountId " + id + " not found"));
        account.setAccountNumber(accountRequest.getAccountNumber());
        account.setAccountType(accountRequest.getAccountType());
        account.setBalance(accountRequest.getBalance());
        account.setCurrency(accountRequest.getCurrency());
        account.setStatus(accountRequest.getStatus());
        account.setInterestRate(accountRequest.getInterestRate());

        return new ResponseEntity<>(accountRepository.save(account), HttpStatus.OK);
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable("id") long id){
        accountRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/clients/{clientId}/accounts")
    public ResponseEntity<List<Account>> deleteAllAccountsOfClient(@PathVariable(value = "clientId") long clientId){

        if(!clientRepository.existsById(clientId)){
            throw new ResourceNotFoundException("Not found client with id: " + clientId);
        }

        accountRepository.deleteByClientId(clientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }







}
