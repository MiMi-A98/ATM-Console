package MiMiA98.atm.dao;

import MiMiA98.atm.entity.BankAccount;
import MiMiA98.atm.entity.UserAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserAccountDAO {
    private final UtilDAO utilDAO = new UtilDAO();

    public void createUserAccount(String id, String userName) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            UserAccount userAccount = new UserAccount(id, userName);
            entityManager.persist(userAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.out.println("Error creating user account!");
        }
    }

    public UserAccount readUserAccount(String id) {
        EntityManager entityManager = utilDAO.getEntityManager();
        try {
            return entityManager.find(UserAccount.class, id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    public List<BankAccount> readAllAssociatedAccounts(String id) {
        EntityManager entityManager = utilDAO.getEntityManager();

        String jpql = """
                select ba from BankAccount ba
                join fetch ba.userAccount ua
                where ua.accountNumber = :userId
                """;
        
        TypedQuery<BankAccount> query = entityManager.createQuery(jpql, BankAccount.class);
        query.setParameter("userId", id);
        return query.getResultList();
    }

    public void updateUserName(String id, String name) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            UserAccount userAccount = readUserAccount(id);
            if (userAccount == null) {
                throw new PersistenceException("User account with ID " + id + "not found!");
            }
            userAccount.setUserName(name);
            entityManager.merge(userAccount);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.out.println("Error during update!");
        }
    }

    public void deleteUserAccount(String id) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            UserAccount userAccount = entityManager.find(UserAccount.class, id);
            if (userAccount != null) {
                entityManager.remove(userAccount);
            } else {
                throw new PersistenceException("User account with ID " + id + "not found!");
            }
            entityTransaction.commit();
        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.out.println("Error during deletion!");
        }
    }
}




