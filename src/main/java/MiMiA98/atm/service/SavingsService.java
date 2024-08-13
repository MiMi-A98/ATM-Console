package MiMiA98.atm.service;

import MiMiA98.atm.dao.SavingsDAO;
import MiMiA98.atm.entity.BankAccount;
import MiMiA98.atm.entity.SavingsAccount;

import java.math.BigDecimal;
import java.time.LocalDate;

import static MiMiA98.atm.service.BankAccountServiceLocator.getService;

public class SavingsService extends BankAccountService {
    private final SavingsDAO savingsDAO;

    public SavingsService() {
        this.savingsDAO = new SavingsDAO();
    }

    public SavingsService(SavingsDAO savingsDAO) {
        this.savingsDAO = savingsDAO;
    }

    private BigDecimal calculateInterest(BankAccount bankAccount) {
        if (bankAccount == null) {
            throw new IllegalArgumentException("Bank account is null!");
        }

        SavingsAccount savingsAccount = (SavingsAccount) bankAccount;

        BigDecimal interest = BigDecimal.valueOf(savingsAccount.getInterestRate());
        int creationYear = savingsAccount.getDateOfCreation().getYear();
        int presentYear = LocalDate.now().getYear();
        BigDecimal period = BigDecimal.valueOf(presentYear).subtract(BigDecimal.valueOf(creationYear));
        BigDecimal interestAmount = savingsAccount.getBalance().multiply(interest);
        return interestAmount.multiply(period);
    }

    @Override
    public void doWithdraw(BankAccount bankAccount, BigDecimal withdrawAmount) {
        BigDecimal newBalance = bankAccount.getBalance().subtract(withdrawAmount);
        updateBalance(bankAccount.getAccountNumber(), newBalance);
    }

    @Override
    public void doDeposit(BankAccount bankAccount, BigDecimal depositAmount) {
        BigDecimal newBalance = bankAccount.getBalance().add(depositAmount);
        updateBalance(bankAccount.getAccountNumber(), newBalance);
    }

    @Override
    public void doTransfer(BankAccount sourceAccount, BankAccount destinationAccount, BigDecimal transferAmount) {
        SavingsAccount savingsAccount = getBankAccount(sourceAccount.getAccountNumber());
        BigDecimal sourceNewBalance = savingsAccount.getBalance().subtract(transferAmount);

        BankAccountService bankAccountService = getService(destinationAccount);
        BankAccount destinationBankAccount = bankAccountService.getBankAccount(destinationAccount.getAccountNumber());

        if (LocalDate.now().isBefore(savingsAccount.getDateOfCreation().plusYears(savingsAccount.getTimePeriod()))) {
            BigDecimal destinationNewBalance = destinationBankAccount.getBalance().add(transferAmount);
            transferMoney(sourceAccount.getAccountNumber(), destinationAccount.getAccountNumber(), sourceNewBalance, destinationNewBalance);
        } else {
            BigDecimal transferAmountPlusInterest = transferAmount.add(calculateInterest(savingsAccount));
            BigDecimal destinationNewBalance = destinationBankAccount.getBalance().add(transferAmountPlusInterest);
            transferMoney(sourceAccount.getAccountNumber(), destinationAccount.getAccountNumber(), sourceNewBalance, destinationNewBalance);
        }
    }

    public void createSavingsAccount(String accountNumber, String currency, int timePeriod, String userId) {
        if (accountNumber == null || accountNumber.isEmpty() || userId == null || userId.isEmpty()
                || currency == null || currency.isEmpty() || timePeriod <= 0) {
            throw new IllegalArgumentException("All necessary information should be completed!");
        }
        savingsDAO.createSavingsAccount(accountNumber, currency, timePeriod, userId);
    }

    @Override
    public SavingsAccount getBankAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        return savingsDAO.readSavingsAccount(accountNumber);
    }

    @Override
    public void updateBalance(String accountNumber, BigDecimal newBalance) {
        if (accountNumber == null || accountNumber.isEmpty() || newBalance == null || newBalance.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new IllegalArgumentException("Account number or new balance is null or empty");
        }
        savingsDAO.updateSavingsAccountBalance(accountNumber, newBalance);
    }

    public void updateSavingsAccountInterestRate(String accountNumber, double newInterestRate) {
        if (accountNumber == null || accountNumber.isEmpty() || newInterestRate <= 0) {
            throw new IllegalArgumentException("Account number is null or empty, or new interest rate is zero!");
        }
        savingsDAO.updateSavingsAccountInterestRate(accountNumber, newInterestRate);
    }

    public void updateSavingsAccountTimePeriod(String accountNumber, int newPeriod) {
        if (accountNumber == null || accountNumber.isEmpty() || newPeriod <= 0) {
            throw new IllegalArgumentException("Account number is null or empty, or new period until maturity is zero!!");
        }
        savingsDAO.updateSavingsAccountTimePeriod(accountNumber, newPeriod);
    }

    public void updateSavingsAccountClosedState(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number is null or empty!");
        }
        savingsDAO.updateSavingsAccountClosedState(accountNumber);
    }

    public void updateSavingsAccountFrozenState(String accountNumber, boolean frozenState) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number is null or empty!");
        }
        savingsDAO.updateSavingsAccountFrozenState(accountNumber, frozenState);
    }

    public void deleteSavingsAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        savingsDAO.deleteSavingsAccount(accountNumber);
    }

}