package MiMiA98.atm.dao;

import MiMiA98.atm.entity.SavingsAccount;
import MiMiA98.atm.entity.UserAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.math.BigDecimal;

public class SavingsDAO {
    UserAccountDAO userAccountDAO = new UserAccountDAO();
    private UtilDAO utilDAO = new UtilDAO();

    public void createSavingsAccount(String accountNumber, String currency, int timePeriod, String userId) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            UserAccount userAccount = userAccountDAO.readUserAccount(userId);
            entityTransaction.begin();
            SavingsAccount savingsAccount = new SavingsAccount(accountNumber, currency, timePeriod, userAccount);
            entityManager.persist(savingsAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public SavingsAccount readSavingsAccount(String accountNumber) {
        EntityManager entityManager = utilDAO.getEntityManager();
        try {
            return entityManager.find(SavingsAccount.class, accountNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }

    public void updateSavingsAccountBalance(String accountNumber, BigDecimal newBalance) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            SavingsAccount savingsAccount = readSavingsAccount(accountNumber);
            savingsAccount.setBalance(newBalance);
            entityManager.persist(savingsAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void updateSavingsAccountInterestRate(String accountNumber, double newInterestRate) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            SavingsAccount savingsAccount = readSavingsAccount(accountNumber);
            savingsAccount.setInterestRate(newInterestRate);
            entityManager.persist(savingsAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void updateSavingsAccountTimePeriod(String accountNumber, int newPeriod) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            SavingsAccount savingsAccount = readSavingsAccount(accountNumber);
            savingsAccount.setTimePeriod(newPeriod);
            entityManager.persist(savingsAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void deleteSavingsAccount(String accountNumber) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            SavingsAccount savingsAccount = readSavingsAccount(accountNumber);
            entityManager.remove(savingsAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }
}
