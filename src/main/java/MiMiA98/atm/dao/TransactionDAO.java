package MiMiA98.atm.dao;

import MiMiA98.atm.app.screen.Screen;
import MiMiA98.atm.entity.BankAccount;
import MiMiA98.atm.entity.Transaction;
import MiMiA98.atm.service.BankAccountServiceLocator;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

public class TransactionDAO {

    public void createTransaction(Transaction.Type transactionType, BankAccount senderAccount, BankAccount receiverAccount, BigDecimal transactionAmount) {
        EntityManager entityManager = UtilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try (entityManager) {
            entityTransaction.begin();
            if (senderAccount == null) {
                BankAccount recieverBankAccount = BankAccountServiceLocator.getService(receiverAccount).getBankAccount(receiverAccount.getAccountNumber());
                Transaction transaction = new Transaction(transactionType, null, recieverBankAccount, transactionAmount);
                entityManager.persist(transaction);
            } else if (receiverAccount == null) {
                BankAccount senderBankAccount = BankAccountServiceLocator.getService(senderAccount).getBankAccount(senderAccount.getAccountNumber());
                Transaction transaction = new Transaction(transactionType, senderBankAccount, null, transactionAmount);
                entityManager.persist(transaction);
            } else {
                BankAccount senderBankAccount = BankAccountServiceLocator.getService(senderAccount).getBankAccount(senderAccount.getAccountNumber());
                BankAccount recieverBankAccount = BankAccountServiceLocator.getService(receiverAccount).getBankAccount(receiverAccount.getAccountNumber());
                Transaction transaction = new Transaction(transactionType, senderBankAccount, recieverBankAccount, transactionAmount);
                entityManager.persist(transaction);
            }
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public List<Transaction> readTransactions(String bankAccountId) {
        try (EntityManager entityManager = UtilDAO.getEntityManagerFactory().createEntityManager()) {

            String jpql = """
                    select tr from Transaction tr
                    where tr.senderBankAccount.accountNumber = :bankAccountId
                    or tr.receiverBankAccount.accountNumber = :bankAccountId
                    """;
            TypedQuery<Transaction> query = entityManager.createQuery(jpql, Transaction.class);
            query.setParameter("bankAccountId", bankAccountId);

            return query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> readAllTransactions() {
        try (EntityManager entityManager = UtilDAO.getEntityManagerFactory().createEntityManager()) {

            String jpql = """
                    select tr from Transaction tr
                    """;
            TypedQuery<Transaction> query = entityManager.createQuery(jpql, Transaction.class);

            return query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTransactions(String bankAccountId) {
        EntityManager entityManager = UtilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try (entityManager) {
            entityTransaction.begin();
            String jpql = """
                    delete tr from Transaction tr
                    where tr.senderBankAccount.accountNumber = :bankAccountId
                    or tr.receiverBankAccount.accountNumber = :bankAccountId
                    """;
            Query query = entityManager.createQuery(jpql);
            query.setParameter("bankAccountId", bankAccountId);

            int deleteCount = query.executeUpdate();

            entityTransaction.commit();
            Screen.display("Number of transactions deleted: " + deleteCount);
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }
}
