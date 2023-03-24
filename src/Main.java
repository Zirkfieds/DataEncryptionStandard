import encryption.DESDecrypter;
import encryption.DESEncrypter;
import encryption.DataEncryptionStandard;
import gui.DESApp;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("DES Algorithm implementation in Java");

        /*
                0x8787878787878787 -> Plain text
                0x0E329232EA6D0D73 -> Key
                0x0000000000000000 -> Cipher text

                0x0123456789ABCDEF
                0xFEDCBA9876543210
                0xB293D553EA7031C3

         */

        DESApp app = new DESApp();
        app.setVisible(true);

    }

}