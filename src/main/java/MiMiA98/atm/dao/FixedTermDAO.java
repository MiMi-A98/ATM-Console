package MiMiA98.atm.dao;

import MiMiA98.atm.entity.FixedTermAccount;
import MiMiA98.atm.entity.UserAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.math.BigDecimal;

public class FixedTermDAO {
    private final UserAccountDAO userAccountDAO = new UserAccountDAO();
    private final UtilDAO utilDAO = new UtilDAO();

    public void createFixedTermAccount(String accountNumber, String currency, BigDecimal balance, int timeOfMaturity, String userId) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            UserAccount userAccount = userAccountDAO.readUserAccount(userId);
            entityTransaction.begin();
            FixedTermAccount fixedTermAccount = new FixedTermAccount(accountNumber, currency, balance, timeOfMaturity, userAccount);
            entityManager.persist(fixedTermAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public FixedTermAccount readFixedTermAccount(String accountNumber) {
        try (EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();) {
            return entityManager.find(FixedTermAccount.class, accountNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }

    public void updateFixedTermAccountBalance(String accountNumber, BigDecimal newBalance) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            FixedTermAccount fixedTermAccount = readFixedTermAccount(accountNumber);
            fixedTermAccount.setBalance(newBalance);
            entityManager.merge(fixedTermAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void updateFixedTermAccountInterestRate(String accountNumber, double newInterestRate) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            FixedTermAccount fixedTermAccount = readFixedTermAccount(accountNumber);
            fixedTermAccount.setInterestRate(newInterestRate);
            entityManager.merge(fixedTermAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
        entityManager.close();
    }

    public void updateFixedTermAccountFrozenState(String accountNumber, boolean frozenState) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            FixedTermAccount fixedTermAccount = readFixedTermAccount(accountNumber);
            fixedTermAccount.setFrozen(frozenState);
            entityManager.merge(fixedTermAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void updateFixedTermAccountClosedState(String accountNumber) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            FixedTermAccount fixedTermAccount = readFixedTermAccount(accountNumber);
            fixedTermAccount.closeAccount();
            entityManager.merge(fixedTermAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void updateFixedTermAccountTimePeriod(String accountNumber, int newPeriod) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            FixedTermAccount fixedTermAccount = readFixedTermAccount(accountNumber);
            fixedTermAccount.setTimeOfMaturity(newPeriod);
            entityManager.persist(fixedTermAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void deleteFixedTermAccount(String accountNumber) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            FixedTermAccount fixedTermAccount = readFixedTermAccount(accountNumber);
            entityManager.remove(fixedTermAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }
}
