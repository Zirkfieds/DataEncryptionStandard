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
        plainTextArea.setToolTipText(
                "Input format must be a 0x followed by a string of 16 characters selected from 0 to F");
        JScrollPane inputScrollPane = new JScrollPane(plainTextArea);
        topPanel.add(inputScrollPane, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        JLabel outputLabel = new JLabel("Cipher Text:");
        bottomPanel.add(outputLabel, BorderLayout.NORTH);

        cipherTextArea = new JTextArea(12, 40);
        cipherTextArea.setToolTipText(
                "Input format must be a 0x followed by a string of 16 characters selected from 0 to F");

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
        keyTextField.setToolTipText(
                "Key format must be a 0x followed by a string of 16 characters selected from 0 to F");
        buttonPanel.add(keyTextField);

        detailedValuesButton = new JButton("Logs");
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

            if (cipherText != null) {
                cipherTextArea.setText(cipherText);
            }
        } else if (e.getSource() == decryptButton) {
            String cipherText = cipherTextArea.getText();
            String key = keyTextField.getText();
            String plainText = decrypt(cipherText, key);

            if (plainText != null) {
                plainTextArea.setText(plainText);
            }

        } else if (e.getSource() == detailedValuesButton) {
            if (this.latestGenerated == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please encrypt/decrypt a text with a key first.",
                        "Error", JOptionPane.ERROR_MESSAGE);

            } else {
                JFrame detailedValuesWindow = new JFrame("Full Encryption/Decryption Process");
                detailedValuesWindow.setSize(480, 640);
                detailedValuesWindow.setLocationRelativeTo(null);
                JTextArea detailedValuesTextArea = new JTextArea();
                detailedValuesTextArea.setText(latestGenerated.getLogs());
                JScrollPane scrollPane = new JScrollPane(detailedValuesTextArea);
                detailedValuesWindow.add(scrollPane);
                detailedValuesWindow.setVisible(true);
                detailedValuesWindow.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        detailedValuesWindow.dispose();
                        detailedValuesTextArea.setText("");
                    }
                });
            }
        } else if (e.getSource() == clearAllButton) {
            plainTextArea.setText(null);
            keyTextField.setText(null);
            cipherTextArea.setText(null);
        }
    }

    private String encrypt(String plainText, String key) {

        String regex = "0[xX][0-9a-fA-F]+";
        if (!plainText.matches(regex) || !key.matches(regex) || plainText.length() != 18 || key.length() != 18) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please input both the plain text and the key in the correct format!",
                    "Error", JOptionPane.ERROR_MESSAGE);

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

        String regex = "0[xX][0-9a-fA-F]+";
        if (!cipherText.matches(regex) || !key.matches(regex) || cipherText.length() != 18 || key.length() != 18) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please input both the cipher text and the key in the correct format",
                    "Error", JOptionPane.ERROR_MESSAGE);

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

