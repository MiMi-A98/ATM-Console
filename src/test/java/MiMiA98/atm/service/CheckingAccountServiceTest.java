package MiMiA98.atm.service;

import MiMiA98.atm.dao.CheckingAccountDAO;
import MiMiA98.atm.entity.CheckingAccount;
import MiMiA98.atm.entity.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckingAccountServiceTest {

    @Mock
    CheckingAccountDAO checkingAccountDAO;

    @InjectMocks
    CheckingAccountService checkingAccountService;

    @BeforeEach
    public void setUp() {
        checkingAccountService = new CheckingAccountService(checkingAccountDAO);
    }

    @Test
    void doDeposit_validInputs_doesNotThrowError() {
        CheckingAccount checkingAccount = new CheckingAccount("c1", "USD", new UserAccount());
        BigDecimal depositAmount = BigDecimal.valueOf(100);

        when(checkingAccountService.getBankAccount(checkingAccount.getAccountNumber())).thenReturn(checkingAccount);

        assertDoesNotThrow(() -> checkingAccountService.doDeposit(checkingAccount, depositAmount));
    }

    @Test
    void doWithdraw_validCheckingAccount_doesNotThrowError() {
        CheckingAccount checkingAccount = new CheckingAccount("c1", "USD", new UserAccount());
        BigDecimal withdrawAmount = BigDecimal.valueOf(100);

        when(checkingAccountService.getBankAccount(checkingAccount.getAccountNumber())).thenReturn(checkingAccount);

        assertDoesNotThrow(() -> checkingAccountService.doDeposit(checkingAccount, withdrawAmount));
    }

    @Test
    void createCheckingAccount_validInputs_passesMethodCallVerification() {
        String accountNumber = "checking1";
        String currency = "USD";
        String userId = "user1";

        checkingAccountService.createCheckingAccount(accountNumber, currency, userId);

        verify(checkingAccountDAO).createCheckingAccount(accountNumber, currency, userId);
    }

    @Test
    void createCheckingAccount_nullAccountNumber_throwsException() {
        String currency = "USD";
        String userId = "user1";
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.createCheckingAccount(null, currency, userId));
    }

    @Test
    void createCheckingAccount_emptyAccountNumber_throwsException() {
        String currency = "USD";
        String userId = "user1";
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.createCheckingAccount("", currency, userId));
    }

    @Test
    void createCheckingAccount_nullCurrency_throwsException() {
        String accountNumber = "checking1";
        String userId = "user1";
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.createCheckingAccount(accountNumber, null, userId));
    }

    @Test
    void createCheckingAccount_emptyCurrency_throwsException() {
        String accountNumber = "checking1";
        String userId = "user1";
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.createCheckingAccount(accountNumber, "", userId));
    }

    @Test
    void createCheckingAccount_nullUserId_throwsException() {
        String accountNumber = "checking1";
        String currency = "USD";
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.createCheckingAccount(accountNumber, currency, null));
    }

    @Test
    void createCheckingAccount_emptyUserId_throwsException() {
        String accountNumber = "checking1";
        String currency = "USD";
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.createCheckingAccount(accountNumber, currency, ""));
    }

    @Test
    void getBankAccount_validAccountNumber_passesMethodCallVerification() {
        String accountNumber = "checking1";

        checkingAccountService.getBankAccount(accountNumber);

        verify(checkingAccountDAO).readCheckingAccount(accountNumber);
    }

    @Test
    void getBankAccount_validAccountNumber_resultNotNull() {
        String accountNumber = "checking1";
        CheckingAccount checkingAccount = new CheckingAccount(accountNumber, "USD", new UserAccount());
        when(checkingAccountDAO.readCheckingAccount(accountNumber)).thenReturn(checkingAccount);

        CheckingAccount result = checkingAccountService.getBankAccount(accountNumber);

        assertNotNull(result);
    }

    @Test
    void getBankAccount_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.getBankAccount(null));
    }

    @Test
    void getBankAccount_emptyAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.getBankAccount(""));
    }

    @Test
    void updateCheckingAccountBalance_validInputs_passesMethodCallVerification() {
        String accountNumber = "checking1";
        BigDecimal balance = BigDecimal.valueOf(100);

        checkingAccountService.updateBalance(accountNumber, balance);

        verify(checkingAccountDAO).updateCheckingAccountBalance(accountNumber, balance);
    }

    @Test
    void updateCheckingAccountBalance_nullAccountNumber_throwsException() {
        BigDecimal balance = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.updateBalance(null, balance));
    }

    @Test
    void updateCheckingAccountBalance_emptyAccountNumber_throwsException() {
        BigDecimal balance = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.updateBalance("", balance));
    }

    @Test
    void updateCheckingAccountBalance_nullBalance_throwsException() {
        String accountNumber = "checking1";
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.updateBalance(accountNumber, null));
    }

    @Test
    void updateCheckingAccountBalance_negativeBalance_throwsException() {
        String accountNumber = "checking1";
        BigDecimal balance = BigDecimal.valueOf(-100);
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.updateBalance(accountNumber, balance));
    }

    @Test
    void updateCheckingAccountFrozenState_validInputs_passesMethodCallVerification() {
        String accountNumber = "checking1";
        boolean frozenState = false;

        checkingAccountService.updateCheckingAccountFrozenState(accountNumber, frozenState);

        verify(checkingAccountDAO).updateCheckingAccountFrozenState(accountNumber, frozenState);
    }

    @Test
    void updateCheckingAccountFrozenState_nullAccountNumber_throwsException() {
        boolean frozenState = false;
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.updateCheckingAccountFrozenState(null, frozenState));
    }

    @Test
    void updateCheckingAccountFrozenState_emptyAccountNumber_throesException() {
        boolean frozenState = false;
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.updateCheckingAccountFrozenState("", frozenState));
    }

    @Test
    void updateCheckingAccountClosedState_validInputs_passesMethodCallVerification() {
        String accountNumber = "checking1";

        checkingAccountService.updateCheckingAccountClosedState(accountNumber);

        verify(checkingAccountDAO).updateCheckingAccountClosedState(accountNumber);
    }

    @Test
    void updateCheckingAccountClosedState_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.updateCheckingAccountClosedState(null));
    }

    @Test
    void updateCheckingAccountClosedState_emptyAccountNumber_throesException() {
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.updateCheckingAccountClosedState(""));
    }

    @Test
    void deleteCheckingAccount_validAccountNumber_passesMethodCallVerification() {
        String accountNumber = "checking1";

        checkingAccountService.deleteCheckingAccount(accountNumber);

        verify(checkingAccountDAO).deleteCheckingAccount(accountNumber);
    }

    @Test
    void deleteCheckingAccount_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.deleteCheckingAccount(null));
    }

    @Test
    void deleteCheckingAccount_emptyAccountNumber_throesException() {
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.deleteCheckingAccount(""));
    }

}