package org.veedev.accountservice.util;

import lombok.experimental.UtilityClass;
import java.util.Random;

@UtilityClass
public class AccountUtil {
    private final Random random = new Random();

    public static String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }
    public static String generatePinCode(){
        StringBuilder pinCodeBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pinCodeBuilder.append(random.nextInt(10));
        }
        return pinCodeBuilder.toString();
    }
}
