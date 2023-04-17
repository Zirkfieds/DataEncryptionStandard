package encryption;

import utils.Bits;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class DESDecrypter extends DataEncryptionStandard {

    public DESDecrypter(String cipherText, String key) {
        super(cipherText, key);
        System.out.println("cipherText in hex: " + cipherText);
        System.out.println("key in hex: " + key);
    }

    public void textPreprocess() {

        // no need to parse the hex string since it is already in hex
        String hexString = plainText;
        int hexLen = hexString.length();
        int segCnt = hexLen / 16;

        for (int i = 0; i < segCnt; i++) {
            P.add(new Bits(hexString.substring(i * 16, (i + 1) * 16)));
        }
    }

    public String fullDecryption() throws UnsupportedEncodingException {

        textPreprocess();
        generateKeySequences();

        StringBuilder sb = new StringBuilder();

        for (Bits seg : P) {
            sb.append(decryption(seg));
        }

        return hex2str(sb.toString());
//        return sb.toString();
    }

    public String decryption(Bits plainTextInBits) {

        Bits mappedPlainTextInBits = initialPermutation(plainTextInBits);
        int mbl = DESDecrypter.IP.length;

        Bits L0 = bitCopy(mappedPlainTextInBits, 0, mbl / 2);
        Bits R0 = bitCopy(mappedPlainTextInBits, mbl / 2, mbl / 2);

        Bits lastL = L0;
        Bits lastR = R0;
        for (int n = 15; n >= 0; n--) {
            // use the KeySequence K in reverse order to decrypt
            Bits Ln = lastR;
            Bits f = fFunction(lastR, K.get((n + 1) % K.size()));
            Bits Rn = bitXOR(lastL, f);
            lastL = Ln;
            lastR = Rn;
        }
        Bits preInvBits = bitConcat(lastR, lastL);
        Bits result = bitMapping(preInvBits, DESDecrypter.IPinv);

        result.syncFromBits();
        return result.getHex();
    }

    @Override
    public String getLogs() {
        StringBuilder decLog = new StringBuilder("Decryption Log\n");

        decLog.append("Sliced Plain Text:\n");
        for (Bits p : P) {
            p.syncFromBits();
            decLog.append("0x").append(p.getHex()).append('\n');
        }

        decLog.append("\nKeySequence KS:\n");
        int i = 1;
        for (Bits k : K) {
            k.syncFromBits();
            decLog.append("K").append(i).append("=0x").append(k.getHex()).append("\n");
            i++;
        }

        return decLog.toString();
    }

}
