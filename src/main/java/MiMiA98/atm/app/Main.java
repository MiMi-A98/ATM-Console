package MiMiA98.atm.app;


import MiMiA98.atm.dao.UtilDAO;
import MiMiA98.atm.entity.Card;

public class Main {

    public static void main(String[] args) {
        AutomatedTellerMachine atm = new AutomatedTellerMachine();
        try {

            UtilDAO.initializePersistenceUnit();

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
}