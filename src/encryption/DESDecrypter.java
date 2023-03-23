package encryption;

import utils.Bits;

import java.util.ArrayList;
import java.util.Collections;

public class DESDecrypter extends DataEncryptionStandard {

    public DESDecrypter(String cipherText, String key) {
        super(cipherText, key);
        System.out.println("cipherText in hex: " + cipherText);
        System.out.println("key in hex: " + key);
    }

    public String decryption() {
        Bits mappedPlainTextInBits = initialPermutation();
        int mbl = DESDecrypter.IP.length;
        generateKeySequences();

        Bits L0 = bitCopy(mappedPlainTextInBits, 0, mbl / 2);
        Bits R0 = bitCopy(mappedPlainTextInBits, mbl / 2, mbl / 2);

        Bits lastL = L0;
        Bits lastR = R0;
        for (int n = 15; n >= 0; n--) {
            Bits Ln = lastR;
            Bits f = fFunction(lastR, K.get((n + 1) % K.size()));
            Bits Rn = bitXOR(lastL, f);
            lastL = Ln;
            lastR = Rn;
        }
        Bits preInvBits = bitConcat(lastR, lastL);
        Bits result = bitMapping(preInvBits, DESDecrypter.IPinv);

        result.syncFromBits();
        return "0x" + result.getHex();
    }

    @Override
    public String getLogs() {
        StringBuilder decLog = new StringBuilder("Decryption Log\n");

        // TODO: toString() for the product from every step in the decryption

        return decLog.toString();
    }

}
