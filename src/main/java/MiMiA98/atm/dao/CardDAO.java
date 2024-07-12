package MiMiA98.atm.dao;

import MiMiA98.atm.entity.Card;
import MiMiA98.atm.entity.CheckingAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

public class CardDAO {
    private CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO();
    private UtilDAO utilDAO = new UtilDAO();

    public void createCard(String cardNumber, String pin, String checkingAccountId) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            CheckingAccount checkingAccount = checkingAccountDAO.readCheckingAccount(checkingAccountId);
            entityTransaction.begin();
            Card card = new Card(cardNumber, pin, checkingAccount);
            entityManager.persist(card);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public Card readCard(String cardNumber) {
        EntityManager entityManager = utilDAO.getEntityManager();
        try {
            return entityManager.find(Card.class, cardNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateCardPin(String cardNumber, String newPIN) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Card card = readCard(cardNumber);
            card.setPin(newPIN);
            entityManager.merge(card);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void updateBlockedState(String cardNumber, boolean isBlocked) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Card card = readCard(cardNumber);
            card.setBlocked(isBlocked);
            entityManager.merge(card);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void deleteCard(String cardNumber) {
        EntityManager entityManager = utilDAO.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Card card = readCard(cardNumber);
            entityManager.remove(card);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            System.out.println(e.getMessage());
        }
    }
}
