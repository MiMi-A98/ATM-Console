package MiMiA98.atm;

import java.math.BigDecimal;

public interface Transactional {

    void deposit(BigDecimal depositAmount);

    void withdraw(BigDecimal withdrawAmount);

}
