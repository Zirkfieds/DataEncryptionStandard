package encryption;

import utils.Bits;

import java.util.ArrayList;

public class DataEncryptionStandard {

    protected final static int[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    protected final static int[] IPinv = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    protected final static int[] PC1 = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };

    protected final static int[] leftshiftMap = {
            0, 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };

    protected final static int[] PC2 = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    protected final static int[] EBitSelection = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    protected final static int[][] selectionBlocks = {
            {
                    14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7,
                    0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
                    4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
                    15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13
            },
            {
                    15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10,
                    3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
                    0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
                    13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9,
            },
            {
                    10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8,
                    13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
                    13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
                    1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12,
            },
            {
                    7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15,
                    13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
                    10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
                    3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14,
            },
            {
                    2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9,
                    14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
                    4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
                    11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3,
            },
            {
                    12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11,
                    10, 15, 4, 2, 7, 12, 0, 5, 6, 1, 13, 14, 0, 11, 3, 8,
                    9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
                    4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13,
            },
            {
                    4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1,
                    13, 0, 11, 7, 4, 0, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
                    1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
                    6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12,
            },
            {
                    13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7,
                    1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
                    7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
                    2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
            },

    };

    protected final static int[] permutationBlock = {
        16, 7 , 20, 21,
        29, 12, 28, 17,
        1 , 15, 23, 26,
        5 , 18, 31, 10,
        2 , 8 , 24, 14,
        32, 27, 3 , 9 ,
        19, 13, 30, 6 ,
        22, 11, 4 , 25
    };

    protected String plainText;
    protected Bits plainTextInBits;

    protected String key;
    protected Bits keyInBits;
    protected final ArrayList<Bits> C = new ArrayList<>();
    protected final ArrayList<Bits> D = new ArrayList<>();
    protected final ArrayList<Bits> K = new ArrayList<>();

    public DataEncryptionStandard(String plainText, String key) {
        this.plainText = plainText;
        this.plainTextInBits = new Bits(plainText);
        if (plainTextInBits.getBits() == null) {
            System.out.println("Error.");
        }

        this.key = key;
        this.keyInBits = new Bits(key);
        if (keyInBits.getBits() == null) {
            System.out.println("Error.");
        }
    }

    protected Bits fFunction(Bits lastR, Bits k) {
        Bits e = bitMapping(lastR, DataEncryptionStandard.EBitSelection);
        int[] xorBits = bitXOR(e, k).getBits();

        ArrayList<Bits> hexabits = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int[] hexa = new int[6];
            System.arraycopy(xorBits, i * 6, hexa, 0, 6);
            hexabits.add(new Bits(hexa));
        }

        ArrayList<Bits> hexaSbits = new ArrayList<>();
        int s = 0;
        for (Bits b : hexabits) {
            hexaSbits.add(sFunction(b, DataEncryptionStandard.selectionBlocks[s]));
            s++;
        }

        Bits ret = hexaSbits.get(0);
        for (int i = 1; i < hexaSbits.size(); i++) {
            ret = bitConcat(ret, hexaSbits.get(i));
        }
        ret = bitMapping(ret, DataEncryptionStandard.permutationBlock);

        return ret;
    }

    private Bits sFunction(Bits b, int[] s) {

        int[] bBits = b.getBits();

        int[] row = {bBits[0], bBits[5]};
        int[] col = {bBits[1], bBits[2], bBits[3], bBits[4]};
        int rown = bit2dec(row);
        int coln = bit2dec(col);
        int ret = s[rown * 16 + coln];

        return new Bits(dec2bit(ret));
    }

    private int bit2dec(int[] bits) {
        int dec = 0;
        for (int i = bits.length - 1, pow = 0; i >= 0; i--, pow++) {
            dec += (bits[i] << pow);
        }
        return dec;
    }

    private int[] dec2bit(int dec) {
        String bin = Integer.toBinaryString(dec);
        char[] chars = bin.toCharArray();

        int cl = chars.length;
        int zeros = 4 - cl;

        int[] bits = new int[4];
        for (int i = 0; i < zeros; i++) {
            bits[i] = 0;
        }
        for (int c = 0, b = zeros; b < 4; c++, b++) {
            bits[b] = chars[c] - '0';
        }
        return bits;
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

    public Bits bitConcat(Bits a, Bits b) {
        int[] aBits = a.getBits();
        int[] bBits = b.getBits();
        int[] cBits = new int[aBits.length + bBits.length];

        System.arraycopy(aBits, 0, cBits, 0, aBits.length);
        System.arraycopy(bBits, 0, cBits, aBits.length, bBits.length);

        return new Bits(cBits);
    }

    public Bits bitXOR(Bits a, Bits b) {
        int[] aBits = a.getBits();
        int[] bBits = b.getBits();
        int[] cBits = new int[aBits.length];

        for (int i = 0; i < aBits.length; i++) {
            cBits[i] = aBits[i] ^ bBits[i];
        }
        return new Bits(cBits);
    }

    public Bits initialPermutation() {
        return bitMapping(this.plainTextInBits, DataEncryptionStandard.IP);
    }

    public void generateKeySequences() {
        Bits key0 = bitMapping(this.keyInBits, DataEncryptionStandard.PC1);

        int pc1l = DataEncryptionStandard.PC1.length;
        int pc2l = DataEncryptionStandard.PC2.length;

        Bits C0 = bitCopy(key0, 0, pc1l / 2);
        C.add(C0);

        Bits D0 = bitCopy(key0, pc1l / 2, pc1l / 2);
        D.add(D0);

        for (int i = 0; i < 16; i++) {
            C.add(new Bits(C0.leftshift(DataEncryptionStandard.leftshiftMap[i + 1])));
            D.add(new Bits(D0.leftshift(DataEncryptionStandard.leftshiftMap[i + 1])));
        }
        for (int i = 0; i < 16; i++) {
            Bits k = bitConcat(C.get(i), D.get(i));
            K.add(bitMapping(k, DataEncryptionStandard.PC2));
        }
    }

    public String getLogs() {
        return null;
    }

}
