package MiMiA98.atm.service;

import MiMiA98.atm.dao.CheckingAccountDAO;
import MiMiA98.atm.entity.BankAccount;
import MiMiA98.atm.entity.CheckingAccount;

import java.math.BigDecimal;

import static MiMiA98.atm.service.BankAccountServiceLocator.getService;

public class CheckingAccountService extends BankAccountService {
    private final CheckingAccountDAO checkingAccountDAO;

    public CheckingAccountService() {
        this.checkingAccountDAO = new CheckingAccountDAO();
    }

    public CheckingAccountService(CheckingAccountDAO checkingAccountDAO) {
        this.checkingAccountDAO = checkingAccountDAO;
    }


    @Override
    public void doDeposit(BankAccount bankAccount, BigDecimal depositAmount) {
        BigDecimal newBalance = getBankAccount(bankAccount.getAccountNumber()).getBalance().add(depositAmount);
        updateBalance(bankAccount.getAccountNumber(), newBalance);
    }

    @Override
    public void deposit(BankAccount bankAccount, BigDecimal depositAmount) {
        if (getCheckingAccount(bankAccount.getAccountNumber()) != null) {
            CheckingAccount checkingAccount = getCheckingAccount(bankAccount.getAccountNumber());
            validateAccountStatus(checkingAccount);
            BigDecimal newBalance = checkingAccount.getBalance().add(depositAmount);
            updateCheckingAccountBalance(bankAccount.getAccountNumber(), newBalance);
        }
    }

    public void createCheckingAccount(String accountNumber, String currency, String userId) {
        if (accountNumber == null || accountNumber.isEmpty()
                || currency == null || currency.isEmpty()
                || userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("All necessary information should be completed!");
        }
        checkingAccountDAO.createCheckingAccount(accountNumber, currency, userId);
    }

    @Override
    public CheckingAccount getBankAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        return checkingAccountDAO.readCheckingAccount(accountNumber);
    }

    public void updateBalance(String accountNumber, BigDecimal newBalance) {
        if (accountNumber == null || accountNumber.isEmpty() || newBalance == null || newBalance.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new IllegalArgumentException("Account number and new balance cannot be null or empty");
        }
        checkingAccountDAO.updateCheckingAccountBalance(accountNumber, newBalance);
    }

    public void updateCheckingAccountFrozenState(String accountNumber, boolean frozenState) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        checkingAccountDAO.updateCheckingAccountFrozenState(accountNumber, frozenState);
    }

    public void updateCheckingAccountClosedState(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        checkingAccountDAO.updateCheckingAccountClosedState(accountNumber);
    }

    public void deleteCheckingAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        checkingAccountDAO.deleteCheckingAccount(accountNumber);
    }

}
