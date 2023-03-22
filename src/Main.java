import encryption.DataEncryptionStandard;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("DES");

        Character[] charsInput = {
                0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7,
                0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF
        };

//        DataEncryptionStandard des = new DataEncryptionStandard("\u0001#Eg\u0089«Íï", "\u00134Wy\u009B¼ßñ");
        DataEncryptionStandard des = new DataEncryptionStandard(
                "\u0001#Eg\u0089«Íï",
                "\u00134Wy\u009B¼ßñ"
        );
        des.DES();
    }

}