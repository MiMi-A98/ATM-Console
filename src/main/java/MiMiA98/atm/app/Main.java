package MiMiA98.atm.app;


import MiMiA98.atm.dao.UtilDAO;
import MiMiA98.atm.entity.Card;
import MiMiA98.atm.service.*;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        AutomatedTellerMachine atm = new AutomatedTellerMachine();

        try {
            setPersistenceUnit("h2_database");

            initializeData();

            Card card = atm.chooseCard();
            atm.login(card);
            atm.displayMainMenu();

        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public static void initializeData() {

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

    public static void setPersistenceUnit(String persistenceUnitName) {
        UtilDAO.setPersistenceUnit(persistenceUnitName);
    }
}