package MiMiA98.atm.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UtilDAO {

    private static String persistenceUnitName;
    private static EntityManagerFactory entityManagerFactory;

    public void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }


    public static void setPersistenceUnit(String persistenceUnit) {
        persistenceUnitName = persistenceUnit;
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }
}
