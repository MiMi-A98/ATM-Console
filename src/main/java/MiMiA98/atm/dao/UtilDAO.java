package MiMiA98.atm.dao;

import MiMiA98.atm.service.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.h2.tools.Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
                initializeDemoData();
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

    public static void initializeDemoData() {

        CardService cardService = new CardService();
        UserAccountService userAccountService = new UserAccountService();
        CheckingAccountService checkingAccountService = new CheckingAccountService();
        SavingsService savingsService = new SavingsService();
        FixedTermService fixedTermService = new FixedTermService();

        userAccountService.createUserAccount("1001", "Ana B.");
        checkingAccountService.createCheckingAccount("ROC1111", "USD", "1001");
        cardService.createCard("1234567890", "1234", "ROC1111");
        savingsService.createSavingsAccount("ROS1111", "RON", 1, "1001");
        fixedTermService.createFixedTermAccount("ROFT1111", "1001", "RON", 1, BigDecimal.valueOf(100));

        userAccountService.createUserAccount("1002", "Bianca C.");
        checkingAccountService.createCheckingAccount("ROC1112", "USD", "1002");
        cardService.createCard("2345678901", "1234", "ROC1112");

        userAccountService.createUserAccount("1003", "Cristina D.");
        checkingAccountService.createCheckingAccount("ROC1113", "USD", "1003");
        cardService.createCard("3456789012", "1234", "ROC1113");
        savingsService.createSavingsAccount("ROS1112", "RON", 1, "1003");
    }
}
