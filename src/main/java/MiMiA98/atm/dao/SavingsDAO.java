package MiMiA98.atm.dao;

import MiMiA98.atm.entity.SavingsAccount;
import MiMiA98.atm.entity.UserAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.math.BigDecimal;

public class SavingsDAO {
    private final UtilDAO utilDAO = new UtilDAO();
    private final UserAccountDAO userAccountDAO = new UserAccountDAO();

    public void createSavingsAccount(String accountNumber, String currency, int timePeriod, String userId) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
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
        } finally {
            entityManager.close();
        }
    }

    public SavingsAccount readSavingsAccount(String accountNumber) {
        try (EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager()) {
            return entityManager.find(SavingsAccount.class, accountNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }

    public void updateSavingsAccountBalance(String accountNumber, BigDecimal newBalance) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
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
        } finally {
            entityManager.close();
        }
    }

    public void updateSavingsAccountInterestRate(String accountNumber, double newInterestRate) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
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
        } finally {
            entityManager.close();
        }
    }

    public void updateSavingsAccountTimePeriod(String accountNumber, int newPeriod) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
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
        } finally {
            entityManager.close();
        }
    }

    public void updateSavingsAccountFrozenState(String accountNumber, boolean frozenState) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            SavingsAccount savingsAccount = readSavingsAccount(accountNumber);
            savingsAccount.setFrozen(frozenState);
            entityManager.merge(savingsAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void updateSavingsAccountClosedState(String accountNumber) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            SavingsAccount savingsAccount = readSavingsAccount(accountNumber);
            savingsAccount.closeAccount();
            entityManager.merge(savingsAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void deleteSavingsAccount(String accountNumber) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            SavingsAccount savingsAccount = readSavingsAccount(accountNumber);
            entityManager.remove(savingsAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }
}
