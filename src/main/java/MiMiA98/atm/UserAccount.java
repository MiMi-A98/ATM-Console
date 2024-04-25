package MiMiA98.atm;

import java.util.HashMap;
import java.util.Map;

public class UserAccount {

    private final String accountNumber;
    private final String userName; //full name

    private final Map<Integer, BankAccount> bankAccounts;

    public UserAccount(String accountNumber, String userName) {
        this.accountNumber = accountNumber;
        this.userName = userName;
        bankAccounts = new HashMap<>();
    }

    public String getUserName() {
        return userName;
    }

    public void addBankAccount(int accountCode, BankAccount bankAccount) {
        bankAccounts.put(accountCode, bankAccount);
    }

    public Map<Integer, BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public BankAccount getBankAccount(int accountCode) {
        return bankAccounts.get(accountCode);
    }
}
