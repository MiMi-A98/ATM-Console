package MiMiA98.atm.service;

import MiMiA98.atm.dao.TransactionDAO;
import MiMiA98.atm.entity.BankAccount;
import MiMiA98.atm.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class TransactionService {
    private final TransactionDAO transactionDAO;

    public TransactionService() {
        this.transactionDAO = new TransactionDAO();
    }

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public void createTransaction(Transaction.Type transactionType, BankAccount senderAccount, BankAccount receiverAccount, BigDecimal transactionAmount) {
        if (transactionType != null || transactionAmount != null && transactionAmount.compareTo(BigDecimal.ZERO) <= 0) {
            transactionDAO.createTransaction(transactionType, senderAccount, receiverAccount, transactionAmount);
        } else {
            throw new IllegalArgumentException("Transaction type or transaction amount is null! Or transaction amount is less or equal to zero!");
        }
    }

    public List<Transaction> readTransactions(String bankAccountId) {
        if (bankAccountId != null && !bankAccountId.isEmpty()) {
            return transactionDAO.readTransactions(bankAccountId);
        } else {
            throw new IllegalArgumentException("Bank account id is null or empty");
        }
    }

    public List<Transaction> readAllTransactions() {
        return transactionDAO.readAllTransactions();
    }

    public void deleteTransactions(String bankAccountId) {
        if (bankAccountId != null && !bankAccountId.isEmpty()) {
            transactionDAO.deleteTransactions(bankAccountId);
        } else {
            throw new IllegalArgumentException("Bank account id is null or empty");
        }
    }
}
