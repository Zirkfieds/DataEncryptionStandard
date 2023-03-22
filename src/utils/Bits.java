package utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Bits {

    private String string;
    private int[] bits;
    private String hex;

    private final int bitCnt = 8;

//    private static final Map<String, String> hexMap = new HashMap<>();

    public Bits(String string) {
        if (string.length() == 18) {
            String regex = "0[xX][0-9a-fA-F]+";
            if (string.matches(regex)) {
                this.hex = string.substring(2).toUpperCase();
                System.out.println(hex);
                hex2bit();
                bit2str();
            }
        }
    }

    public Bits(int[] bits) {
        this.bits = bits;
        bit2str();
    }

    public Bits(Bits b) {
        this.bits = b.getBits();
        this.string = b.getString();
        bit2hex();
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
            bits[(i / bitCnt) * bitCnt - i % bitCnt + bitCnt - 1] = (hex.charAt(i / bitCnt) >> (i % bitCnt)) & 0x1;
        }
    }

    private void bit2hex() {
        int bl = bits.length;

        StringBuilder binString = new StringBuilder();
        for (int i : bits) {
            binString.append(i);
        }
        String bitString = binString.toString();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bl >> 2; i++) {
            String tmp = bitString.substring(i << 2, (i + 1) << 2);
            sb.append(Integer.toHexString(Integer.parseInt(tmp, 2)));
        }
        this.hex = sb.toString();
    }

    private void hex2bit() {
        int[] bits = new int[64];

        final String hexCodes = "0123456789ABCDEF";
        for (int i = 0; i < 64; i++) {
            bits[(i / 4) * 4 - i % 4 + 4 - 1] = (hexCodes.indexOf(hex.charAt(i / 4)) >> (i % 4)) & 0x1;
        }
//        System.out.println(bits);
        this.bits = bits;
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
            System.out.print(i % 2 * groupSize == 2 * groupSize - 1 ? " " : (i % groupSize == groupSize - 1 ? " " : ""));
        }
        System.out.println();
    }

    public void showString() {
        bit2str();
        System.out.println(string);
    }

    public void showHex() {
        bit2hex();
        System.out.println(hex);
    }


}
