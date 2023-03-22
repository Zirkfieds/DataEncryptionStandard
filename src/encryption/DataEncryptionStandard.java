package encryption;

import utils.Bits;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class DataEncryptionStandard {

    private final static int[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    private final static int[] invIP = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    private final static int[] PC_1 = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };

    private final static int[] leftshiftMap = {
            0, 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };

    private final static int[] PC_2 = {
            14, 17, 11, 24, 1 , 5 ,
            3 , 28, 15, 6 , 21, 10,
            23, 19, 12, 4 , 26, 8 ,
            16, 7 , 27, 20, 13, 2 ,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    private String plainText;
    private Bits plainTextInBits;

    private String key;
    private Bits keyInBits;
    final ArrayList<Bits> C = new ArrayList<>();
    final ArrayList<Bits> D = new ArrayList<>();

    private String cipherText;

    public DataEncryptionStandard(String plainText, String key) {
        this.plainText = plainText;
        this.plainTextInBits = new Bits(plainText);

        this.key = key;
        this.keyInBits = new Bits(key);
    }

    public void encryption() {
        Bits mappedPlainTextInBits = initialPermutation();

        this.keyInBits.showBits();
        generateKeySequences();

    }

    public Bits bitMapping(Bits b, int[] map) {

        int[] bits = b.getBits();
        int[] mappedBits = new int[map.length];

        for (int i = 0; i < mappedBits.length; i++) {
            mappedBits[i] = bits[map[i] - 1];
        }

        return new Bits(mappedBits);
    }

    public Bits bitCopy(Bits src, int start, int length) {
        int[] srcBits = src.getBits();
        int[] dstBits = new int[length];
        System.arraycopy(srcBits, start, dstBits, 0, length);
        return new Bits(dstBits);
    }

    public Bits initialPermutation() {
        return bitMapping(this.plainTextInBits, DataEncryptionStandard.IP);
    }

    public void generateKeySequences() {
        Bits key0 = bitMapping(this.keyInBits, DataEncryptionStandard.PC_1);

        Bits C0 = bitCopy(key0, 0, PC_1.length / 2);
        C.add(C0);
        C0.showBits();

        Bits D0 = bitCopy(key0, PC_1.length / 2, PC_1.length / 2);
        D.add(D0);
        D0.showBits();


        for (int i = 0; i < 16; i++) {
            C.add(new Bits(C0.shiftBits(DataEncryptionStandard.leftshiftMap[i + 1])));
            D.add(new Bits(D0.shiftBits(DataEncryptionStandard.leftshiftMap[i + 1])));
        }

//        System.out.println("C: ");
//        for (Bits b : C) {
//            b.showBits();
//        }
//        System.out.println("D: ");
//        for (Bits b: D) {
//            b.showBits();
//        }

    }


}
