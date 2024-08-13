package MiMiA98.atm.service;

import MiMiA98.atm.dao.FixedTermDAO;
import MiMiA98.atm.entity.BankAccount;
import MiMiA98.atm.entity.FixedTermAccount;

import java.math.BigDecimal;

import static MiMiA98.atm.service.BankAccountServiceLocator.getService;

public class FixedTermService extends BankAccountService {
    private final FixedTermDAO fixedTermDAO;

    public FixedTermService() {
        this.fixedTermDAO = new FixedTermDAO();
    }

    public FixedTermService(FixedTermDAO fixedTermDAO) {
        this.fixedTermDAO = fixedTermDAO;
    }

    private BigDecimal calculateInterest(BankAccount bankAccount) {
        if (bankAccount == null) {
            throw new IllegalArgumentException("Bank account is null!");
        }

        FixedTermAccount fixedTermAccount = (FixedTermAccount) bankAccount;

        BigDecimal interest = BigDecimal.valueOf(fixedTermAccount.getInterestRate());
        int creationYear = fixedTermAccount.getDateOfCreation().getYear();
        int matureYear = fixedTermAccount.getDateOfMaturity().getYear();
        BigDecimal timePeriod = BigDecimal.valueOf(matureYear).subtract(BigDecimal.valueOf(creationYear));

        BigDecimal interestAmount = fixedTermAccount.getBalance().multiply(interest);
        return interestAmount.multiply(timePeriod);
    }

    @Override
    public void withdraw(BankAccount bankAccount, BigDecimal withdrawAmount) {
        doWithdraw(bankAccount, withdrawAmount);
    }

    @Override
    void doWithdraw(BankAccount bankAccount, BigDecimal withdrawAmount) {
        throw new IllegalStateException("Can't withdraw from fix term account");
    }

    @Override
    public void doDeposit(BankAccount bankAccount, BigDecimal depositAmount) {
        if (bankAccount.getBalance().compareTo(BigDecimal.valueOf(0)) > 0) {
            throw new IllegalStateException("Can't deposit money in an initialized fix term account");
        } else {
            updateBalance(bankAccount.getAccountNumber(), depositAmount);
        }
    }

    @Override
    public void transfer(BankAccount sourceAccount, BankAccount destinationAccount, BigDecimal transferAmount) {

        FixedTermAccount fixedTermAccount = fixedTermDAO.readFixedTermAccount(sourceAccount.getAccountNumber());
        if ((fixedTermAccount.getBalance().compareTo(transferAmount)) == 0 && (transferAmount.compareTo(BigDecimal.valueOf(0)) > 0)) {

            validateAccountStatus(fixedTermAccount);
            if (!fixedTermAccount.isMatured()) {
                throw new IllegalStateException("Didn't reach day of maturity!");
            }
            BigDecimal transferAmountPlusInterest = transferAmount.add(calculateInterest(fixedTermAccount));
            updateFixedTermAccountBalance(sourceAccount.getAccountNumber(), fixedTermAccount.getBalance().subtract(transferAmount));
            deposit(destinationAccount, transferAmountPlusInterest);
            updateFixedTermAccountClosedState(destinationAccount.getAccountNumber());
        } else {
            throw new IllegalArgumentException("Provided withdraw amount is lower than zero or lower than balance!");
        }
    }

    public void createFixedTermAccount(String accountNumber, String userId, String currency, int termYears, BigDecimal initialAmount) {
        if (accountNumber == null || accountNumber.isEmpty() || userId == null || userId.isEmpty()
                || currency == null || currency.isEmpty() || termYears <= 0 || initialAmount == null
                || initialAmount.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new IllegalArgumentException("All necessary information should be completed!");
        }
        fixedTermDAO.createFixedTermAccount(accountNumber, currency, initialAmount, termYears, userId);
    }

    @Override
    public FixedTermAccount getBankAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        return fixedTermDAO.readFixedTermAccount(accountNumber);
    }

    public void updateBalance(String accountNumber, BigDecimal newBalance) {
        if (accountNumber == null || accountNumber.isEmpty() || newBalance == null || newBalance.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new IllegalArgumentException("Account number or new balance is null or empty");
        }
        fixedTermDAO.updateFixedTermAccountBalance(accountNumber, newBalance);
    }

    public void updateFixedTermAccountInterestRate(String accountNumber, double newInterestRate) {
        if (accountNumber == null || accountNumber.isEmpty() || newInterestRate <= 0) {
            throw new IllegalArgumentException("Account number is null or empty, or new interest rate is zero!");
        }
        fixedTermDAO.updateFixedTermAccountInterestRate(accountNumber, newInterestRate);
    }

    public void updateFixedTermAccountClosedState(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number is null or empty!");
        }
        fixedTermDAO.updateFixedTermAccountClosedState(accountNumber);
    }

    public void updateFixedTermAccountFrozenState(String accountNumber, boolean frozenState) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number is null or empty!");
        }
        fixedTermDAO.updateFixedTermAccountFrozenState(accountNumber, frozenState);
    }

    public void updateFixedTermAccountTimePeriod(String accountNumber, int newPeriod) {
        if (accountNumber == null || accountNumber.isEmpty() || newPeriod <= 0) {
            throw new IllegalArgumentException("Account number is null or empty, or new period until maturity is zero!!");
        }
        fixedTermDAO.updateFixedTermAccountTimePeriod(accountNumber, newPeriod);
    }

    public void deleteFixedTermAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number is null or empty!");
        }
        fixedTermDAO.deleteFixedTermAccount(accountNumber);
    }
}
