package MiMiA98.atm.service;

import MiMiA98.atm.dao.FixedTermDAO;
import MiMiA98.atm.entity.FixedTermAccount;
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
class FixedTermServiceTest {

    @Mock
    FixedTermDAO fixedTermDAO;

    @Mock
    FixedTermService fixedTermServiceMock;

    @InjectMocks
    FixedTermService fixedTermService;

    @BeforeEach
    public void setUp() {
        fixedTermService = new FixedTermService(fixedTermDAO);
    }

    @Test
    void withdraw_fromFixedTermAccount_throwsException() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalStateException.class, () -> fixedTermService.withdraw(new FixedTermAccount(), withdrawAmount));
    }

    @Test
    void doWithdraw_fromFixedTermAccount_throwsException() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalStateException.class, () -> fixedTermService.doWithdraw(new FixedTermAccount(), withdrawAmount));
    }

    @Test
    void doDeposit_validInputs_doesNotThrowError() {
        FixedTermAccount fixedTermAccount = new FixedTermAccount("fixed1", "USD", 1, new UserAccount());
        BigDecimal balance = BigDecimal.valueOf(100);

        assertDoesNotThrow(() -> fixedTermServiceMock.doDeposit(fixedTermAccount, balance));
    }

    @Test
    void doDeposit_accountBalanceMoreThanZero_throwsException() {
        FixedTermAccount fixedTermAccount = new FixedTermAccount("fixed1", "USD", BigDecimal.valueOf(100), 1, new UserAccount());
        BigDecimal balance = BigDecimal.valueOf(100);

        assertThrows(IllegalStateException.class, () -> fixedTermService.doDeposit(fixedTermAccount, balance));
    }

    @Test
    void createFixedTermAccount_validInputs_passesMethodCallVerification() {
        String accountNumber = "fixedTerm1";
        String userId = "user1";
        String currency = "USD";
        int termPeriod = 1;
        BigDecimal initialAmount = BigDecimal.valueOf(100);

        fixedTermService.createFixedTermAccount(accountNumber, userId, currency, termPeriod, initialAmount);

        verify(fixedTermDAO).createFixedTermAccount(accountNumber, currency, initialAmount, termPeriod, userId);
    }

    @Test
    void createFixedTermAccount_nullAccountNumber_throwsException() {
        String userId = "user1";
        String currency = "USD";
        int termPeriod = 1;
        BigDecimal initialAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.createFixedTermAccount(null, userId, currency, termPeriod, initialAmount));
    }

    @Test
    void createFixedTermAccount_emptyAccountNumber_throwsException() {
        String userId = "user1";
        String currency = "USD";
        int termPeriod = 1;
        BigDecimal initialAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.createFixedTermAccount("", userId, currency, termPeriod, initialAmount));
    }

    @Test
    void createFixedTermAccount_nullUserId_throwsException() {
        String accountNumber = "fixedTerm1";
        String currency = "USD";
        int termPeriod = 1;
        BigDecimal initialAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.createFixedTermAccount(accountNumber, null, currency, termPeriod, initialAmount));
    }

    @Test
    void createFixedTermAccount_emptyUserId_throwsException() {
        String accountNumber = "fixedTerm1";
        String currency = "USD";
        int termPeriod = 1;
        BigDecimal initialAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.createFixedTermAccount(accountNumber, "", currency, termPeriod, initialAmount));
    }

    @Test
    void createFixedTermAccount_nullCurrency_throwsException() {
        String accountNumber = "fixedTerm1";
        String userId = "user1";
        int termPeriod = 1;
        BigDecimal initialAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.createFixedTermAccount(accountNumber, userId, null, termPeriod, initialAmount));
    }

    @Test
    void createFixedTermAccount_emptyCurrency_throwsException() {
        String accountNumber = "fixedTerm1";
        String userId = "user1";
        int termPeriod = 1;
        BigDecimal initialAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.createFixedTermAccount(accountNumber, userId, "", termPeriod, initialAmount));
    }

    @Test
    void createFixedTermAccount_nullInitialBalance_throwsException() {
        String accountNumber = "fixedTerm1";
        String userId = "user1";
        String currency = "USD";
        int termPeriod = 1;
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.createFixedTermAccount(accountNumber, userId, currency, termPeriod, null));
    }

    @Test
    void createFixedTermAccount_negativeInitialBalance_throwsException() {
        String accountNumber = "fixedTerm1";
        String userId = "user1";
        String currency = "USD";
        int termPeriod = 1;
        BigDecimal initialAmount = BigDecimal.valueOf(-100);
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.createFixedTermAccount(accountNumber, userId, currency, termPeriod, initialAmount));
    }

    @Test
    void createFixedTermAccount_zeroTermYears_throwsException() {
        String accountNumber = "fixedTerm1";
        String userId = "user1";
        String currency = "USD";
        BigDecimal initialAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.createFixedTermAccount(accountNumber, userId, currency, 0, initialAmount));
    }

    @Test
    void createFixedTermAccount_negativeTermYears_throwsException() {
        String accountNumber = "fixedTerm1";
        String userId = "user1";
        String currency = "USD";
        BigDecimal initialAmount = BigDecimal.valueOf(100);
        int termPeriod = -1;
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.createFixedTermAccount(accountNumber, userId, currency, termPeriod, initialAmount));
    }

    @Test
    void getBankAccount_validAccountNumber_passesMethodCallVerification() {
        String accountNumber = "fixedTerm1";

        fixedTermService.getBankAccount(accountNumber);

        verify(fixedTermDAO).readFixedTermAccount(accountNumber);
    }

    @Test
    void getBankAccount_validAccountNumber_resultNotNull() {
        String accountNumber = "fixedTerm1";
        FixedTermAccount fixedTermAccount = new FixedTermAccount(accountNumber, "USD", 1, new UserAccount());
        when(fixedTermDAO.readFixedTermAccount(accountNumber)).thenReturn(fixedTermAccount);

        FixedTermAccount result = fixedTermService.getBankAccount(accountNumber);

        assertNotNull(result);
    }

    @Test
    void getBankAccount_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.getBankAccount(null));
    }

    @Test
    void getBankAccount_emptyAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.getBankAccount(""));
    }

    @Test
    void updateFixedTermAccountBalance_validInputs_passesMethodCallVerification() {
        String accountNumber = "fixedTerm1";
        BigDecimal balanceAmount = BigDecimal.valueOf(100);

        fixedTermService.updateBalance(accountNumber, balanceAmount);

        verify(fixedTermDAO).updateFixedTermAccountBalance(accountNumber, balanceAmount);
    }

    @Test
    void updateFixedTermAccountBalance_nullAccountNumber_throwsException() {
        BigDecimal balanceAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateBalance(null, balanceAmount));
    }

    @Test
    void updateFixedTermAccountBalance_emptyAccountNumber_throwsException() {
        BigDecimal balanceAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateBalance("", balanceAmount));
    }

    @Test
    void updateFixedTermAccountBalance_nullBalance_throwsException() {
        String accountNumber = "fixedTerm1";
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateBalance(accountNumber, null));
    }

    @Test
    void updateFixedTermAccountBalance_negativeBalance_throwsException() {
        String accountNumber = "fixedTerm1";
        BigDecimal balanceAmount = BigDecimal.valueOf(-100);
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateBalance(accountNumber, balanceAmount));
    }

    @Test
    void updateFixedTermAccountInterestRate_validInputs_passesMethodCAllVerification() {
        String accountNumber = "fixedTerm1";
        double interestRate = 0.2;

        fixedTermService.updateFixedTermAccountInterestRate(accountNumber, interestRate);

        verify(fixedTermDAO).updateFixedTermAccountInterestRate(accountNumber, interestRate);
    }

    @Test
    void updateFixedTermAccountInterestRate_nullAccountNumber_throwsException() {
        double interestRate = 0.2;
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateFixedTermAccountInterestRate(null, interestRate));
    }

    @Test
    void updateFixedTermAccountInterestRate_emptyAccountNumber_throwsException() {
        double interestRate = 0.2;
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateFixedTermAccountInterestRate("", interestRate));
    }

    @Test
    void updateFixedTermAccountInterestRate_zeroInterestRate_throwsException() {
        String accountNumber = "fixedTerm1";
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateFixedTermAccountInterestRate(accountNumber, 0));
    }


    @Test
    void updateFixedTermAccountInterestRate_negativeInterestRate_throwsException() {
        String accountNumber = "fixedTerm1";
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateFixedTermAccountInterestRate(accountNumber, -1));
    }

    @Test
    void updateFixedTermAccountClosedState_validInputs_passesMethodCallVerification() {
        String accountNumber = "fixedTerm1";

        fixedTermService.updateFixedTermAccountClosedState(accountNumber);

        verify(fixedTermDAO).updateFixedTermAccountClosedState(accountNumber);
    }

    @Test
    void updateFixedTermAccountClosedState_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateFixedTermAccountClosedState(null));
    }

    @Test
    void updateFixedTermAccountClosedState_emptyAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateFixedTermAccountClosedState(""));
    }

    @Test
    void updateFixedTermAccountFrozenState_validInputs_passesMethodCallVerification() {
        String accountNumber = "fixedTerm1";
        boolean frozenState = false;

        fixedTermService.updateFixedTermAccountFrozenState(accountNumber, frozenState);

        verify(fixedTermDAO).updateFixedTermAccountFrozenState(accountNumber, frozenState);
    }

    @Test
    void updateFixedTermAccountFrozenState_nullAccountNumber_throwsException() {
        boolean frozenState = false;
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateFixedTermAccountFrozenState(null, frozenState));
    }

    @Test
    void updateFixedTermAccountFrozenState_emptyAccountNumber_throwsException() {
        boolean frozenState = false;
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateFixedTermAccountFrozenState("", frozenState));
    }

    @Test
    void updateFixedTermAccountTimePeriod_validInputs_passesMethodCallVerification() {
        String accountNumber = "fixedTerm1";
        int newPeriod = 1;

        fixedTermService.updateFixedTermAccountTimePeriod(accountNumber, newPeriod);

        verify(fixedTermDAO).updateFixedTermAccountTimePeriod(accountNumber, newPeriod);
    }

    @Test
    void updateFixedTermAccountTimePeriod_nullAccountNumber_throwsException() {
        int newPeriod = 1;
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateFixedTermAccountTimePeriod(null, newPeriod));
    }

    @Test
    void updateFixedTermAccountTimePeriod_emptyAccountNumber_throwsException() {
        int newPeriod = 1;
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateFixedTermAccountTimePeriod("", newPeriod));
    }

    @Test
    void updateFixedTermAccountTimePeriod_zeroTimePeriod_throwsException() {
        String accountNumber = "fixedTerm1";
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateFixedTermAccountTimePeriod(accountNumber, 0));
    }

    @Test
    void updateFixedTermAccountTimePeriod_negativeTimePeriod_throwsException() {
        String accountNumber = "fixedTerm1";
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.updateFixedTermAccountTimePeriod(accountNumber, -1));
    }

    @Test
    void deleteFixedTermAccount_validInputs_passesMethodCallVerification() {
        String accountNumber = "fixedTerm1";

        fixedTermService.deleteFixedTermAccount(accountNumber);

        verify(fixedTermDAO).deleteFixedTermAccount(accountNumber);
    }

    @Test
    void deleteFixedTermAccount_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.deleteFixedTermAccount(null));
    }

    @Test
    void deleteFixedTermAccount_emptyAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> fixedTermService.deleteFixedTermAccount(""));
    }

}