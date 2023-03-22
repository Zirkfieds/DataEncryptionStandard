package utils;

import java.nio.charset.StandardCharsets;

public class Bits {

    private String string;
    private int[] bits;

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
        int sl = bl >> 3;

        StringBuilder sb = new StringBuilder(sl);
        for (int i = 0; i < sl; i++) {
            int b = 0x00;
            for (int j = 0; j < 8; j++) {
                b += bits[i * 8 + j] << (7 - j);
            }
            sb.append(Character.toChars(b));
        }

        this.string = sb.toString();
    }

    private void str2bit() {
        int l = string.length();
        int bl = l << 3;

        bits = new int[bl];
        for (int i = 0; i < bl; i++) {
            bits[(i / 8) * 8 - i % 8 + 7] = (string.charAt(i / 8) >> (i % 8)) & 0x1;
        }
    }

    public Bits shiftBits(int digits) {
        int bl = bits.length;
        int[] newBits = new int[bl];
        for (int i = 0; i < bl; i++) {
            newBits[i] = bits[(i + digits) % bl];
        }
        this.bits = newBits;
        bit2str();
        return this;
    }

    public void showBits() {
        for (int i = 0; i < this.bits.length; i++) {
            System.out.print(bits[i]);
            System.out.print(i % 8 == 7 ? " " : (i % 4 == 3 ? " " : ""));
        }
        System.out.println();
    }

    public void showString() {
        System.out.println(string);
    }


}
