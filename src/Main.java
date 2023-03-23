import encryption.DESDecrypter;
import encryption.DESEncrypter;
import encryption.DataEncryptionStandard;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("DES Algorithm implementation in Java");

        Character[] charsInput = {
                0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7,
                0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF
        };

        System.out.println("------------------------------------------------");
        DESEncrypter enc = new DESEncrypter(
                "0x8787878787878787",
                "0x0E329232EA6D0D73"
        );
        System.out.println("Encryption result: " + enc.encryption());

        System.out.println("------------------------------------------------");
        DESDecrypter dec = new DESDecrypter(
                "0x0000000000000000",
                "0x0E329232EA6D0D73"
        );
        System.out.println("Decryption result: " + dec.decryption());

    }

}