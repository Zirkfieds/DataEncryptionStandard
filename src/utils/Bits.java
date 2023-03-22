package utils;

import java.nio.charset.StandardCharsets;

public class Bits {

    private String string;
    private int[] bits;

    private final int bitCnt = 8;

    public Bits(String string) {
        this.string = string;
        str2bit();
    }

    public Bits(int[] bits) {
        this.bits = bits;
        bit2str();
    }

    public Bits(Bits b) {
        this.bits = b.getBits();
        this.string = b.getString();
    }

    public String getString() {
        return string;
    }

    public int[] getBits() {
        return bits;
    }

    private void bit2str() {
        int bl = bits.length;
        int sl = bl / bitCnt;

        StringBuilder sb = new StringBuilder(sl);
        for (int i = 0; i < sl; i++) {
            int b = 0x00;
            for (int j = 0; j < bitCnt; j++) {
                b += bits[i * bitCnt + j] << (bitCnt - 1 - j);
            }
            sb.append(Character.toChars(b));
        }

        this.string = sb.toString();
    }

    private void str2bit() {
        int l = string.length();
        int bl = l * bitCnt;

        bits = new int[bl];
        for (int i = 0; i < bl; i++) {
            bits[(i / bitCnt) * bitCnt - i % bitCnt + bitCnt - 1] = (string.charAt(i / bitCnt) >> (i % bitCnt)) & 0x1;
        }
    }

    public Bits leftshift(int digits) {
        int bl = bits.length;
        int[] newBits = new int[bl];
        for (int i = 0; i < bl; i++) {
            newBits[i] = bits[(i + digits) % bl];
        }
        this.bits = newBits;
        bit2str();
        return this;
    }

    public void showBits(int groupSize) {
        for (int i = 0; i < this.bits.length; i++) {
            System.out.print(bits[i]);
            System.out.print(i % 2* groupSize == 2 * groupSize -1 ? " " : (i % groupSize == groupSize -1 ? " " : ""));
        }
        System.out.println();
    }

    public void showString() {
        System.out.println(string);
    }


}
