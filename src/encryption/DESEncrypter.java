package encryption;

import utils.Bits;

public class DESEncrypter extends DataEncryptionStandard {

    public DESEncrypter(String plainText, String key) {
        super(plainText, key);
    }

    public void textPreprocess() {

        // converting string into hex string
        String hexString = str2hex(plainText);
        int hexLen = hexString.length();
        int spcCnt = 16 - hexLen % 16;
        int segCnt = hexLen / 16 + (spcCnt == 16 ? 0 : 1);

        // for encryption, append zeros at the last segment until it reaches 16 bits
        hexString += "0".repeat(spcCnt);

        for (int i = 0; i < segCnt; i++) {
            P.add(new Bits(hexString.substring(i * 16, (i + 1) * 16)));
        }
    }

    public String fullEncryption() {

        textPreprocess();
        generateKeySequences();

        StringBuilder sb = new StringBuilder();

        for (Bits seg : P) {
            sb.append(encryption(seg));
        }


        return sb.toString();
    }

    public String encryption(Bits plainTextInBits) {

        Bits mappedPlainTextInBits = initialPermutation(plainTextInBits);
        int mbl = DESEncrypter.IP.length;

        Bits L0 = bitCopy(mappedPlainTextInBits, 0, mbl / 2);
        Bits R0 = bitCopy(mappedPlainTextInBits, mbl / 2, mbl / 2);

        Bits lastL = L0;
        Bits lastR = R0;
        for (int n = 0; n < 16; n++) {
            // use the KeySequence K in natural order to encrypt
            Bits Ln = lastR;
            Bits f = fFunction(lastR, K.get((n + 1) % K.size()));
            Bits Rn = bitXOR(lastL, f);
            lastL = Ln;
            lastR = Rn;
        }
        Bits preInvBits = bitConcat(lastR, lastL);
        Bits result = bitMapping(preInvBits, DESEncrypter.IPinv);

        result.syncFromBits();
        return result.getHex();

    }

    @Override
    public String getLogs() {
        StringBuilder encLog = new StringBuilder("Encryption Log\n");

        encLog.append("Sliced Plain Text:\n");
        for (Bits p : P) {
            p.syncFromBits();
            encLog.append("0x").append(p.getHex()).append('\n');
        }

        encLog.append("\nKeySequence KS:\n");
        int i = 1;
        for (Bits k : K) {
            k.syncFromBits();
            encLog.append("K").append(i).append("=0x").append(k.getHex()).append("\n");
            i++;
        }

        return encLog.toString();
    }

}
