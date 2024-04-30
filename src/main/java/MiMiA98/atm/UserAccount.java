package MiMiA98.atm;

import java.util.ArrayList;
import java.util.Collection;

public class UserAccount {

    private final String accountNumber;
    private final String userName; //full name

    private final Collection<BankAccount> bankAccounts;

    public UserAccount(String accountNumber, String userName) {
        this.accountNumber = accountNumber;
        this.userName = userName;
        bankAccounts = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void addBankAccount(BankAccount bankAccount) {
        bankAccounts.add(bankAccount);
    }

    public Collection<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

}
