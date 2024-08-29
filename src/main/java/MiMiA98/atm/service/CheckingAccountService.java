package MiMiA98.atm.service;

import MiMiA98.atm.dao.CheckingAccountDAO;
import MiMiA98.atm.entity.BankAccount;
import MiMiA98.atm.entity.CheckingAccount;
import MiMiA98.atm.entity.Transaction;

import java.math.BigDecimal;

public class CheckingAccountService extends BankAccountService {
    private final CheckingAccountDAO checkingAccountDAO;
    private final TransactionService transactionService = new TransactionService();

    public CheckingAccountService() {
        this.checkingAccountDAO = new CheckingAccountDAO();
    }

    public CheckingAccountService(CheckingAccountDAO checkingAccountDAO) {
        this.checkingAccountDAO = checkingAccountDAO;
    }

    @Override
    public void doWithdraw(BankAccount bankAccount, BigDecimal withdrawAmount) {
        BigDecimal newBalance = getBankAccount(bankAccount.getAccountNumber()).getBalance().subtract(withdrawAmount);
        updateBalance(bankAccount.getAccountNumber(), newBalance);
        transactionService.createTransaction(Transaction.Type.WITHDRAWAL, bankAccount, null, withdrawAmount);
    }

    @Override
    public void doDeposit(BankAccount bankAccount, BigDecimal depositAmount) {
        BigDecimal newBalance = getBankAccount(bankAccount.getAccountNumber()).getBalance().add(depositAmount);
        updateBalance(bankAccount.getAccountNumber(), newBalance);
        transactionService.createTransaction(Transaction.Type.DEPOSIT, null, bankAccount, depositAmount);
    }

    @Override
    void doTransfer(BankAccount sourceAccount, BankAccount destinationAccount, BigDecimal transferAmount) {
        CheckingAccount checkingAccount = getBankAccount(sourceAccount.getAccountNumber());
        BigDecimal accountBalance = checkingAccount.getBalance();

        if (accountBalance.compareTo(transferAmount) >= 0) {
            BigDecimal sourceNewBalance = accountBalance.subtract(transferAmount);

            BankAccountService bankAccountService = BankAccountServiceLocator.getService(destinationAccount);
            BankAccount destinationBankAccount = bankAccountService.getBankAccount(destinationAccount.getAccountNumber());
            BigDecimal destinationNewBalance = destinationBankAccount.getBalance().add(transferAmount);

            transferMoney(sourceAccount.getAccountNumber(), destinationAccount.getAccountNumber(), sourceNewBalance, destinationNewBalance);
            transactionService.createTransaction(Transaction.Type.TRANSFER, sourceAccount, destinationAccount, transferAmount);
        } else {
            throw new IllegalStateException("Transfer amount is higher than account balance!");
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
