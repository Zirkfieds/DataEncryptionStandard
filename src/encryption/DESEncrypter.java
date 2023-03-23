package encryption;

import utils.Bits;

import java.util.ArrayList;

public class DESEncrypter extends DataEncryptionStandard {

    public DESEncrypter(String plainText, String key) {
        super(plainText, key);
        System.out.println("plainText in hex: " + plainText);
        System.out.println("key in hex: " + key);
    }

    public String encryption() {
        Bits mappedPlainTextInBits = initialPermutation();
//        mappedPlainTextInBits.showBits(4);
        int mbl = DESEncrypter.IP.length;
        generateKeySequences();

        Bits L0 = bitCopy(mappedPlainTextInBits, 0, mbl / 2);
        Bits R0 = bitCopy(mappedPlainTextInBits, mbl / 2, mbl / 2);

        Bits lastL = L0;
        Bits lastR = R0;
        for (int n = 0; n < 16; n++) {
            Bits Ln = lastR;
            Bits f = fFunction(lastR, K.get((n + 1) % K.size()));
            Bits Rn = bitXOR(lastL, f);
            lastL = Ln;
            lastR = Rn;
        }
        Bits preInvBits = bitConcat(lastR, lastL);
        Bits result = bitMapping(preInvBits, DESEncrypter.IPinv);

        result.syncFromBits();
        return "0x" + result.getHex();

    }

}