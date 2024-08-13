package MiMiA98.atm.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UtilDAO {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("atm_database");

    public void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

}
