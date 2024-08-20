package MiMiA98.atm.app;

import MiMiA98.atm.dao.CardDAO;
import MiMiA98.atm.dao.UtilDAO;
import MiMiA98.atm.entity.*;
import MiMiA98.atm.service.BankAccountService;
import MiMiA98.atm.service.CardService;
import MiMiA98.atm.service.UserAccountService;

import java.math.BigDecimal;
import java.util.Collection;

import static MiMiA98.atm.service.BankAccountServiceLocator.getService;

public class AutomatedTellerMachine {
    private final UserAccountService userAccountService = new UserAccountService();
    private final CardService cardService = new CardService(new CardDAO());
    private final UtilDAO utilDAO = new UtilDAO();
    private boolean isUserLoggedIn = false;
    private Card card;


    public Card chooseCard() {

        Collection<Card> cards = cardService.getAllCards();

        Card chosenCard = null;

        ListScreen<Card> listScreen = new ListScreen<>();
        for (Card availeCard : cards) {

            if (availeCard.isBlocked()) {
                continue;
            }
            listScreen.addItem(new ListScreen.Item<>(availeCard.toString(), availeCard));

        }
        Screen.display("Choose card!");
        try {
            chosenCard = listScreen.chooseItem();
        } catch (IllegalArgumentException e) {
            chooseCard();
        }

        return chosenCard;
    }

    public void login(Card card) {
        if (card.isBlocked()) {
            Screen.display("Card is blocked!");
            logout();
        }

        Screen.display("Welcome to ATM!");

        for (int i = 1; i <= 3; i++) {

            Screen.display("Add card PIN: ");
            String pin = Screen.getInputString();

            if (card.getPin().equals(pin)) {
                Screen.display("Welcome!");
                isUserLoggedIn = true;
                this.card = card;

                if (!card.isActivated()) {
                    Screen.display("Please change your PIN to activate the card!");
                    cardService.updateCardPin(card.getCardNumber(), pin);
                    card.setActivated(true);
                    login(card);
                }
                break;
            } else if (i == 3) {
                Screen.display("""
                        PIN tries exceeded!
                        Your card has been blocked!
                        """);
                cardService.updateCardBlockedState(card.getCardNumber(), true);
                logout();
            } else {
                Screen.display("Incorrect PIN! Try again!");
            }
        }
    }

    public void displayMainMenu() {
        validateLogin();

        MenuScreen menu = new MenuScreen(
                new MenuScreen.Option("Checking account", () -> displayCheckingAccount()),
                new MenuScreen.Option("Transfer", () -> transferMoney()),
                new MenuScreen.Option("View bank accounts info", () -> viewAccountsInfo()),
                new MenuScreen.Option("Change PIN", () -> changePin()),
                new MenuScreen.Option("Quit", () -> logout()));

        try {
            menu.navigate();
        } catch (IllegalArgumentException e) {
            displayMainMenu();
        }
    }

    private void displayCheckingAccount() {
        validateLogin();

        MenuScreen menu = new MenuScreen(
                new MenuScreen.Option("View balance", () -> viewBalance()),
                new MenuScreen.Option("Withdraw funds", () -> withdraw()),
                new MenuScreen.Option("Deposit", () -> deposit()),
                new MenuScreen.Option("Back", () -> displayMainMenu()),
                new MenuScreen.Option("Quit", () -> logout()));

        try {
            menu.navigate();
        } catch (IllegalArgumentException e) {
            displayCheckingAccount();
        }
    }

    private void viewBalance() {
        validateLogin();
        CheckingAccount checkingAccount = card.getCheckingAccount();
        BankAccountService bankAccountService = getService(checkingAccount);
        BigDecimal balance = bankAccountService.getBankAccount(checkingAccount.getAccountNumber()).getBalance();
        Screen.display("Your balance is: " + balance);

        navigateToMainMenuOrLogout();
    }

    private void withdraw() {
        validateLogin();
        CheckingAccount checkingAccount = card.getCheckingAccount();
        BankAccountService bankAccountService = getService(checkingAccount);

        Screen.display("Your balance is: " + bankAccountService.getBankAccount(checkingAccount.getAccountNumber()).getBalance());

        Screen.display("Enter withdraw amount!");
        BigDecimal withdrawAmount = BigDecimal.valueOf(Screen.getInputDouble());


        try {

            bankAccountService.withdraw(checkingAccount, withdrawAmount);
            Screen.display("Operation processed successfully!");
            Screen.display("Your new balance is: " + bankAccountService.getBankAccount(checkingAccount.getAccountNumber()).getBalance());

        } catch (Exception ex) {
            Screen.display("Error during withdrawal: " + ex.getMessage());
            retryMenuOptionOrLogout(() -> withdraw());
        }

        navigateToMainMenuOrLogout();
    }

    private void deposit() {
        validateLogin();

        Screen.display("Enter deposit amount!");
        BigDecimal depositAmount = BigDecimal.valueOf(Screen.getInputDouble());
        CheckingAccount checkingAccount = card.getCheckingAccount();

        BankAccountService bankAccountService = getService(checkingAccount);
        bankAccountService.deposit(checkingAccount, depositAmount);
        Screen.display("Your total balance is: " + bankAccountService.getBankAccount(checkingAccount.getAccountNumber()).getBalance());

        navigateToMainMenuOrLogout();
    }

    private void changePin() {
        validateLogin();

        Screen.display("Enter new pin.");
        String newPin = Screen.getInputString();

        try {
            cardService.updateCardPin(card.getCardNumber(), newPin);
        } catch (IllegalArgumentException ex) {
            Screen.display("Pin should contain only digits and have a length of four characters!");
            retryMenuOptionOrLogout(() -> changePin());
        }

        if (card.isActivated()) {
            navigateToMainMenuOrLogout();
        }
    }

    private void transferMoney() {
        validateLogin();

        BankAccount transferFromAccount = chooseTransferFromAccount();
        BankAccount transferToAccount = chooseTransferToAccount(transferFromAccount);

        Screen.display("Enter transfer amount!");
        BigDecimal transferAmount = BigDecimal.valueOf(Screen.getInputDouble());

        BankAccountService bankAccountService = getService(transferFromAccount);
        bankAccountService.transfer(transferFromAccount, transferToAccount, transferAmount);

        navigateToMainMenuOrLogout();
    }

    private BankAccount chooseTransferFromAccount() {
        validateLogin();

        UserAccount userAccount = card.getCheckingAccount().getUserAccount();
        String userId = userAccount.getAccountNumber();
        Collection<BankAccount> bankAccounts = userAccountService.getAllAssociatedAccounts(userId);

        BankAccount transferFromAccount = null;

        ListScreen<BankAccount> listScreen = new ListScreen<>();
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccounts.size() <= 1) {
                Screen.display("Unable to make operation! You only have one account!");
                break;
            } else {
                if ((bankAccount instanceof FixedTermAccount) && !((FixedTermAccount) bankAccount).isMatured()) {
                    continue;
                }
                listScreen.addItem(new ListScreen.Item<>(bankAccount.toStringBasic(), bankAccount));
            }
        }
        Screen.display("Choose account from which you want to transfer!");
        try {
            transferFromAccount = listScreen.chooseItem();
        } catch (IllegalArgumentException e) {
            chooseTransferFromAccount();
        }

        return transferFromAccount;
    }

    private BankAccount chooseTransferToAccount(BankAccount transferFromAccount) {
        validateLogin();

        UserAccount userAccount = card.getCheckingAccount().getUserAccount();
        String userId = userAccount.getAccountNumber();
        Collection<BankAccount> bankAccounts = userAccountService.getAllAssociatedAccounts(userId);

        BankAccount transferToAccount = null;

        ListScreen<BankAccount> listScreen = new ListScreen<>();
        for (BankAccount bankAccount : bankAccounts) {
            bankAccount.toStringBasic();
            if ((bankAccount instanceof FixedTermAccount) || (bankAccount.getAccountNumber()).equals(transferFromAccount.getAccountNumber())) {
                continue;
            }
            listScreen.addItem(new ListScreen.Item<>(bankAccount.toStringBasic(), bankAccount));
        }

        Screen.display("Choose account to which you want to transfer!");
        try {
            transferToAccount = listScreen.chooseItem();
        } catch (IllegalArgumentException e) {
            chooseTransferToAccount(transferFromAccount);
        }

        return transferToAccount;
    }


    private void viewAccountsInfo() {
        validateLogin();

        CheckingAccount checkingAccount = card.getCheckingAccount();
        UserAccount userAccount = checkingAccount.getUserAccount();
        Collection<BankAccount> bankAccounts = userAccountService.getAllAssociatedAccounts(userAccount.getAccountNumber());

        for (BankAccount bankAccount : bankAccounts) {
            Screen.display(bankAccount.toStringBasic());
        }

        navigateToMainMenuOrLogout();
    }

    private void navigateToMainMenuOrLogout() {
        validateLogin();

        Screen.display("Do you want to make another operation?");

        MenuScreen menu = new MenuScreen(
                new MenuScreen.Option("Yes", () -> displayMainMenu()),
                new MenuScreen.Option("No", () -> logout()));

        try {
            menu.navigate();
        } catch (IllegalArgumentException e) {
            navigateToMainMenuOrLogout();
        }
    }

    private void retryMenuOptionOrLogout(Runnable action) {
        Screen.display("Do you want to retry?");

        MenuScreen menu = new MenuScreen(
                new MenuScreen.Option("Yes", () -> action.run()),
                new MenuScreen.Option("No", () -> logout()));

        try {
            menu.navigate();
        } catch (IllegalArgumentException e) {
            action.run();
        }
    }

    private void validateLogin() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }
    }

    private void logout() {
        Screen.display("Good bye!");
        isUserLoggedIn = false;
        card = null;
        utilDAO.closeEntityManagerFactory();
    }
}