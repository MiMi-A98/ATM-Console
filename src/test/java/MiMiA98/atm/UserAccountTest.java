package MiMiA98.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountTest {
    UserAccount userAccount;

    @BeforeEach
    void setUp() {
        userAccount = new UserAccount(1, "John Smith");
    }

    @Test
    void addBankAccount_withBankAccount_increasesUserBankAccountsCollectionSize() {
        BankAccount bankAccount = Mockito.mock(BankAccount.class);
        userAccount.addBankAccount(bankAccount);
        assertEquals(1, userAccount.bankAccounts.size());
    }
}