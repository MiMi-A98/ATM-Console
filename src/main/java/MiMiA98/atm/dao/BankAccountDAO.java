package MiMiA98.atm.dao;

import MiMiA98.atm.entity.BankAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.math.BigDecimal;

public class BankAccountDAO {
    private final UtilDAO utilDAO = new UtilDAO();

    public void transferMoney(String sourceAccountNumber, String destinationAccountNumber, BigDecimal sourceBalance, BigDecimal destinationBalance) {
        EntityManager entityManager = utilDAO.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            BankAccount sourceAccount = entityManager.find(BankAccount.class, sourceAccountNumber);
            BankAccount destinationAccount = entityManager.find(BankAccount.class, destinationAccountNumber);

            sourceAccount.setBalance(sourceBalance);
            destinationAccount.setBalance(destinationBalance);

            entityManager.persist(sourceAccount);
            entityManager.persist(destinationAccount);

            entityTransaction.commit();
        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }
}
