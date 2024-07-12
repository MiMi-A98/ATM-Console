package MiMiA98.atm.dao;

import MiMiA98.atm.entity.CheckingAccount;
import MiMiA98.atm.entity.UserAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.math.BigDecimal;

public class CheckingAccountDAO {
    private final UserAccountDAO userAccountDAO = new UserAccountDAO();
    private final UtilDAO utilDAO = new UtilDAO();


    public void createCheckingAccount(String accountNumber, String currency, String userId) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            UserAccount userAccount = userAccountDAO.readUserAccount(userId);
            entityTransaction.begin();
            CheckingAccount checkingAccount = new CheckingAccount(accountNumber, currency, userAccount);
            entityManager.persist(checkingAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public CheckingAccount readCheckingAccount(String accountNumber) {
        EntityManager entityManager = utilDAO.getEntityManager();
        return entityManager.find(CheckingAccount.class, accountNumber);
    }

    public void updateCheckingAccountBalance(String accountNumber, BigDecimal newBalance) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            CheckingAccount checkingAccount = readCheckingAccount(accountNumber);
            checkingAccount.setBalance(newBalance);
            entityManager.merge(checkingAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void updateCheckingAccountFrozenState(String accountNumber, boolean frozenState) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            CheckingAccount checkingAccount = readCheckingAccount(accountNumber);
            checkingAccount.setFrozen(frozenState);
            entityManager.merge(checkingAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void updateCheckingAccountClosedState(String accountNumber) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            CheckingAccount checkingAccount = readCheckingAccount(accountNumber);
            checkingAccount.closeAccount();
            entityManager.merge(checkingAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void deleteCheckingAccount(String accountNumber) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            CheckingAccount checkingAccount = readCheckingAccount(accountNumber);
            entityManager.remove(checkingAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

}
