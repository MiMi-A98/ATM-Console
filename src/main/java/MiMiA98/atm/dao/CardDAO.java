package MiMiA98.atm.dao;

import MiMiA98.atm.entity.Card;
import MiMiA98.atm.entity.CheckingAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CardDAO {
    private final CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO();

    public void createCard(String cardNumber, String pin, String checkingAccountId) {
        EntityManager entityManager = UtilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try (entityManager) {
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
        try (EntityManager entityManager = UtilDAO.getEntityManagerFactory().createEntityManager()) {
            return entityManager.find(Card.class, cardNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Card> readAllCards() {
        try (EntityManager entityManager = UtilDAO.getEntityManagerFactory().createEntityManager()) {

            String jpql = """
                    select ca from Card ca
                    """;
            TypedQuery<Card> query = entityManager.createQuery(jpql, Card.class);

            return query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCardPin(String cardNumber, String newPIN) {
        EntityManager entityManager = UtilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try (entityManager) {
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
        EntityManager entityManager = UtilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try (entityManager) {
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
        EntityManager entityManager = UtilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try (entityManager) {
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
