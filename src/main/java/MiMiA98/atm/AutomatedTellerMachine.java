package MiMiA98.atm;

import MiMiA98.atm.app.ListScreen;
import MiMiA98.atm.app.MenuScreen;
import MiMiA98.atm.app.Screen;

import java.math.BigDecimal;
import java.util.Collection;

public class AutomatedTellerMachine {

    private boolean isUserLoggedIn = false;
    private Card card;

    private void logout() {
        Screen.display("Good bye!");
        isUserLoggedIn = false;
        card = null;
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
                    changePin();
                    card.setActivated(true);
                    login(card);
                }
                break;
            } else if (i == 3) {
                Screen.display("""
                        PIN tries exceeded!
                        Your card has been blocked!
                        """);
                card.setBlocked(true);
                logout();
            } else {
                Screen.display("Incorrect PIN! Try again!");
            }
        }
    }

    public void displayMainMenu() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

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
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        MenuScreen menu = new MenuScreen(
                new MenuScreen.Option("View balance", () -> viewBalance()),
                new MenuScreen.Option("Withdraw funds", () -> withdraw()),
                new MenuScreen.Option("Deposit", () -> deposit()),
                new MenuScreen.Option("Quit", () -> logout()));

        try {
            menu.navigate();
        } catch (IllegalArgumentException e) {
            displayCheckingAccount();
        }
    }

    private void viewBalance() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }
        Screen.display("Your balance is: " + card.getCheckingAccount().getBalance());
    }

    private void withdraw() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        viewBalance();

        Screen.display("Enter withdraw amount!");
        BigDecimal withdrawAmount = BigDecimal.valueOf(Screen.getInputDouble());

        CheckingAccount checkingAccount = card.getCheckingAccount();

        try {
            checkingAccount.withdraw(withdrawAmount);
            Screen.display("Operation processed successfully!");
            Screen.display("Your new balance is: " + checkingAccount.getBalance());

        } catch (IllegalArgumentException ex) {
            Screen.display("Withdraw amount is too high! Your total balance is: " + checkingAccount.getBalance());
            Screen.display("Do you want to retry?");

            MenuScreen menu = new MenuScreen(
                    new MenuScreen.Option("Yes", () -> withdraw()),
                    new MenuScreen.Option("No", () -> logout()));

            try {
                menu.navigate();
            } catch (IllegalArgumentException e) {
                withdraw();
            }
        }
    }

    private void deposit() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        Screen.display("Enter deposit amount!");
        BigDecimal depositAmount = BigDecimal.valueOf(Screen.getInputDouble());
        CheckingAccount checkingAccount = card.getCheckingAccount();

        checkingAccount.deposit(depositAmount);
        Screen.display("Your total balance is: " + checkingAccount.getBalance());
    }

    private void changePin() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        Screen.display("Enter new pin.");
        String newPin = Screen.getInputString();

        try {
            card.setPin(newPin);
        } catch (IllegalArgumentException ex) {
            Screen.display("Pin should contain only digits and have a length of four characters!");
            Screen.display("Do you want to retry?");

            MenuScreen menu = new MenuScreen(
                    new MenuScreen.Option("Yes", () -> changePin()),
                    new MenuScreen.Option("No", () -> logout()));

            try {
                menu.navigate();
            } catch (IllegalArgumentException e) {
                changePin();
            }
        }
    }

    private void transferMoney() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        BankAccount transferFromAccount = chooseTransferFromAccount();
        BankAccount transferToAccount = chooseTransferToAccount(transferFromAccount);

        Screen.display("Enter transfer amount!");
        BigDecimal transferAmount = BigDecimal.valueOf(Screen.getInputDouble());

        transferFromAccount.transfer(transferAmount, transferToAccount);
    }

    private BankAccount chooseTransferFromAccount() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        UserAccount userAccount = card.getCheckingAccount().getUserAccount();
        Collection<BankAccount> bankAccounts = userAccount.getBankAccounts();

        BankAccount transferFromAccount = null;

        ListScreen listScreen = new ListScreen();
        for (BankAccount bankAccount : bankAccounts) {
            if ((bankAccount instanceof FixedTermAccount) && !((FixedTermAccount) bankAccount).isMatured()) {
                continue;
            }
            listScreen.addItem(new ListScreen.Item(bankAccount.toStringBasic(), bankAccount));
        }

        Screen.display("Choose account from which you want to transfer!");
        try {
            transferFromAccount = (BankAccount) listScreen.chooseItem();
        } catch (IllegalArgumentException e) {
            chooseTransferFromAccount();
        }

        return transferFromAccount;
    }

    private BankAccount chooseTransferToAccount(BankAccount transferFromAccount) {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        UserAccount userAccount = card.getCheckingAccount().getUserAccount();
        Collection<BankAccount> bankAccounts = userAccount.getBankAccounts();

        BankAccount transferToAccount = null;

        ListScreen listScreen = new ListScreen();
        for (BankAccount bankAccount : bankAccounts) {
            if (!(bankAccount instanceof FixedTermAccount) || !bankAccount.equals(transferFromAccount)) {
                continue;
            }
            listScreen.addItem(new ListScreen.Item(bankAccount.toStringBasic(), bankAccount));
        }

        Screen.display("Choose account to which you want to transfer!");
        try {
            transferToAccount = (BankAccount) listScreen.chooseItem();
        } catch (IllegalArgumentException e) {
            chooseTransferToAccount(transferFromAccount);
        }

        return transferToAccount;
    }


    private void viewAccountsInfo() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        UserAccount userAccount = card.getCheckingAccount().getUserAccount();
        Collection<BankAccount> bankAccountsMap = userAccount.getBankAccounts();

        for (BankAccount bankAccount : bankAccountsMap) {
            Screen.display(bankAccount.toStringBasic());
        }

    }
}