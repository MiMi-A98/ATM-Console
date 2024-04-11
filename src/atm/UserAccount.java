package atm;

import java.util.ArrayList;
import java.util.Collection;

public class UserAccount {

    private int accountNumber;
    private String userName; //full name
    Collection<BankAccount> bankAccounts;

    public UserAccount(int accountNumber, String userName) {
        this.accountNumber = accountNumber;
        this.userName = userName;
        bankAccounts = new ArrayList<>();
    }

    public void addBankAccount(BankAccount bankAccount) {
        bankAccounts.add(bankAccount);
    }
}
