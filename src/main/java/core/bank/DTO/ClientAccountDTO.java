package core.bank.DTO;

import core.bank.model.Account;

import java.util.List;

public class ClientAccountDTO {
    private Long clientId;
    private String clientName;
    private String clientSurname;
    private String email;
    private List<Account> accounts;

    public ClientAccountDTO(Long clientId, String clientName, String clientSurname,String email , List<Account> accounts) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.accounts = accounts;
        this.email = email;
    }



    public ClientAccountDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
