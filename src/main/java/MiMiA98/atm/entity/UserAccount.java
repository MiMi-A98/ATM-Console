package MiMiA98.atm.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "UserAccount")
public class UserAccount {

    @Id
    private String accountNumber;
    private String userName; //full name
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;

    public UserAccount() {
    }

    public UserAccount(String accNumber, String userName) {
        this.accountNumber = accNumber;
        this.userName = userName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Account number: " + getAccountNumber() + " , User name: " + getUserName();
    }
}
