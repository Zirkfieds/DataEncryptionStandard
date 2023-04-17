import gui.DESApp;

public class Main {
    public static void main(String[] args) {
        System.out.println("DES Algorithm implementation in Java");

        /*
                8787878787878787 -> Plain text
                0E329232EA6D0D73 -> Key
                0000000000000000 -> Cipher text

                0123456789ABCDEF
                FEDCBA9876543210
                B293D553EA7031C3
         */

        DESApp app = new DESApp();
        app.setVisible(true);

    }

}