package domain_logic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public interface Product {
    BigDecimal getPrice();
    Date getDateOfInspection() throws ParseException;
    int getVendingMachineSlot();
}
