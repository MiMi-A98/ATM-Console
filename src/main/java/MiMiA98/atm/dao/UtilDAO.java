package MiMiA98.atm.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.h2.tools.Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UtilDAO {

    private static EntityManagerFactory entityManagerFactory;

    public void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void initializePersistenceUnit() throws IOException {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream("database-config.properties")) {
            properties.load(input);
        }

        String persistenceName = properties.getProperty("persistenceContext");

        switch (persistenceName) {
            case "h2_database" -> {
                entityManagerFactory = Persistence.createEntityManagerFactory(persistenceName);
                startH2Server();
            }
            case "mySQL_database" -> {
                Map<String, String> configOverride = new HashMap<>();
                configOverride.put("jakarta.persistence.jdbc.url", properties.getProperty("jdbc.connectionUrl"));
                configOverride.put("jakarta.persistence.jdbc.user", properties.getProperty("username"));
                configOverride.put("jakarta.persistence.jdbc.password", properties.getProperty("password"));
                entityManagerFactory = Persistence.createEntityManagerFactory(persistenceName, configOverride);
            }
            case null, default -> throw new RuntimeException("Persistence context wrong or null!");
        }
    }

    public static void startH2Server() {
        try {
            Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            System.out.println("H2 Console started at: http://localhost:8082");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
