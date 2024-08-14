package MiMiA98.atm.app;


import MiMiA98.atm.entity.Card;
import MiMiA98.atm.service.*;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        AutomatedTellerMachine atm = new AutomatedTellerMachine();

        try {
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

        userAccountService.createUserAccount("User1", "Ana B.");
        checkingAccountService.createCheckingAccount("Checking1", "USD", "User1");
        cardService.createCard("Card1", "1234", "Checking1");
        savingsService.createSavingsAccount("Savings1", "RON", 1, "User1");
        fixedTermService.createFixedTermAccount("FixedTerm1", "User1", "RON", 1, BigDecimal.valueOf(100));

        userAccountService.createUserAccount("User2", "Bianca C.");
        checkingAccountService.createCheckingAccount("Checking2", "USD", "User2");
        cardService.createCard("Card2", "1234", "Checking2");

        userAccountService.createUserAccount("User3", "Cristina D.");
        checkingAccountService.createCheckingAccount("Checking3", "USD", "User3");
        cardService.createCard("Card3", "1234", "Checking3");
        savingsService.createSavingsAccount("Savings3", "RON", 1, "User3");
    }
}