package gui;

import encryption.DESDecrypter;
import encryption.DESEncrypter;
import encryption.DataEncryptionStandard;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DESApp extends JFrame implements ActionListener {

    private JTextArea plainTextArea, cipherTextArea;
    private JButton encryptButton, decryptButton, detailedValuesButton, clearAllButton;
    private JTextField keyTextField;

    private DataEncryptionStandard latestGenerated = null;

    public DESApp() {
        setTitle("DES Encryption/Decryption Demo");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        JLabel inputLabel = new JLabel("Plain Text:");
        topPanel.add(inputLabel, BorderLayout.NORTH);

        plainTextArea = new JTextArea(11, 40);
        JScrollPane inputScrollPane = new JScrollPane(plainTextArea);
        topPanel.add(inputScrollPane, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        JLabel outputLabel = new JLabel("Cipher Text:");
        bottomPanel.add(outputLabel, BorderLayout.NORTH);
        cipherTextArea = new JTextArea(12, 40);
        JScrollPane outputScrollPane = new JScrollPane(cipherTextArea);
        bottomPanel.add(outputScrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(this);
        buttonPanel.add(encryptButton);
        decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(this);
        buttonPanel.add(decryptButton);

        JLabel keyLabel = new JLabel("Key:");
        buttonPanel.add(keyLabel);
        keyTextField = new JTextField(16);
        buttonPanel.add(keyTextField);

        detailedValuesButton = new JButton("Overview");
        detailedValuesButton.addActionListener(this);
        buttonPanel.add(detailedValuesButton);

        clearAllButton = new JButton("Clear All");
        clearAllButton.addActionListener(this);
        buttonPanel.add(clearAllButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        setContentPane(mainPanel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == encryptButton) {
            String plainText = plainTextArea.getText();
            String key = keyTextField.getText();
            String cipherText = encrypt(plainText, key);
            cipherTextArea.setText(cipherText);
        } else if (e.getSource() == decryptButton) {
            String cipherText = cipherTextArea.getText();
            String key = keyTextField.getText();
            String plainText = decrypt(cipherText, key);
            plainTextArea.setText(plainText);
        } else if (e.getSource() == detailedValuesButton) {
            if (this.latestGenerated == null) {

                // TODO: pops up an alert dialog box

            } else {
                System.out.println(latestGenerated.getLogs());
            }
        } else if (e.getSource() == clearAllButton) {
            plainTextArea.setText(null);
            keyTextField.setText(null);
            cipherTextArea.setText(null);
        }
    }

    private String encrypt(String plainText, String key) {
        if (plainText.isEmpty() || key.isEmpty()) {

            // TODO: pops up an alert dialog box

            return null;
        }
        DESEncrypter enc = new DESEncrypter(
                plainText,
                key
        );
        this.latestGenerated = enc;
        return enc.encryption();
    }

    private String decrypt(String cipherText, String key) {
        if (cipherText.isEmpty() || key.isEmpty()) {

            // TODO: pops up an alert dialog box

            return null;
        }
        DESDecrypter dec = new DESDecrypter(
                cipherText,
                key
        );
        this.latestGenerated = dec;
        return dec.decryption();
    }
}

