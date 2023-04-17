package gui;

import encryption.DESDecrypter;
import encryption.DESEncrypter;
import encryption.DataEncryptionStandard;
import utils.FileHelper;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import javax.swing.*;

public class DESApp extends JFrame implements ActionListener {

    private JTextArea plainTextArea, cipherTextArea;
    private JButton encryptButton, decryptButton, detailedValuesButton, clearAllButton;
    private JButton loadPlainTxtButton, loadCipherTxtButton, savePlainTxtButton, saveCipherTxtButton;
    private JTextField keyTextField;
    private JFileChooser fileChooser;

    private DataEncryptionStandard latestGenerated;

    static String lastUsedPath = "./";

    public DESApp() {
        setTitle("DES Encryption/Decryption Demo");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();

        JPanel plainPanel = new JPanel();
        plainPanel.setLayout(new GridLayout(1, 2));
        topPanel.setLayout(new BorderLayout());
        JLabel inputLabel = new JLabel("Plain Text Area");

        JPanel plainButtonPanel = new JPanel();
        plainButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        loadPlainTxtButton = new JButton("Load Plain Text");
        savePlainTxtButton = new JButton("Save Plain Text");

        plainPanel.add(inputLabel);
        plainButtonPanel.add(loadPlainTxtButton);
        loadPlainTxtButton.addActionListener(this);
        plainButtonPanel.add(savePlainTxtButton);
        savePlainTxtButton.addActionListener(this);
        plainPanel.add(plainButtonPanel);
        topPanel.add(plainPanel, BorderLayout.NORTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        plainTextArea = new JTextArea(6, 40);
        plainTextArea.setLineWrap(true);
        plainTextArea.setWrapStyleWord(true);
        plainTextArea.setToolTipText(
                "Enter text here or load a plain text file");
        JScrollPane inputScrollPane = new JScrollPane(plainTextArea);
        topPanel.add(inputScrollPane, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JPanel cipherPanel = new JPanel();
        cipherPanel.setLayout(new GridLayout(1, 2));
        JLabel outputLabel = new JLabel("Cipher Text Area");
        JPanel cipherButtonPanel = new JPanel();
        cipherButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        loadCipherTxtButton = new JButton("Load Cipher Text");
        saveCipherTxtButton = new JButton("Save Cipher Text");

        cipherPanel.add(outputLabel);
        cipherButtonPanel.add(loadCipherTxtButton);
        loadCipherTxtButton.addActionListener(this);
        cipherButtonPanel.add(saveCipherTxtButton);
        saveCipherTxtButton.addActionListener(this);
        cipherPanel.add(cipherButtonPanel);
        bottomPanel.add(cipherPanel, BorderLayout.NORTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        cipherTextArea = new JTextArea(6, 40);
        cipherTextArea.setLineWrap(true);
        cipherTextArea.setWrapStyleWord(true);
        cipherTextArea.setToolTipText(
                "Enter text here or load a cipher text file");

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
                "Enter the 16 bits key here, format: [0-9a-fA-f]+");
        buttonPanel.add(keyTextField);

        detailedValuesButton = new JButton("Logs");
        detailedValuesButton.addActionListener(this);
        buttonPanel.add(detailedValuesButton);

        clearAllButton = new JButton("Clear All");
        clearAllButton.addActionListener(this);
        buttonPanel.add(clearAllButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(lastUsedPath));
        fileChooser.setAcceptAllFileFilterUsed(true);

        setContentPane(mainPanel);
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        SwingUtilities.updateComponentTreeUI(this);
        SwingUtilities.updateComponentTreeUI(this.getContentPane());

        if (e.getSource() == encryptButton) {
            String plainText = plainTextArea.getText().strip();
            String key = keyTextField.getText();
            System.out.println("enc " + plainText + " (" + DataEncryptionStandard.str2hex(plainText) + ") " + key);
            String cipherText = encrypt(plainText, key);

            if (cipherText != null) {
                cipherTextArea.setText(cipherText);
                System.out.println();
            }
        } else if (e.getSource() == decryptButton) {
            String cipherText = cipherTextArea.getText().strip();
            String key = keyTextField.getText();
            System.out.println("dec " + cipherText + " (" + cipherText + ") " + key);
            String plainText = null;
            try {
                plainText = decrypt(cipherText, key);
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }

            if (plainText != null) {
                plainTextArea.setText(plainText);
                System.out.println();
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
        } else if (e.getSource() == loadCipherTxtButton) {
            int result = this.fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                FileHelper fh = new FileHelper(fileChooser.getSelectedFile().getAbsolutePath());
                fh.readAll();
                this.cipherTextArea.setText((fh.getAll()));
            }

        } else if (e.getSource() == saveCipherTxtButton) {
            FileHelper fhu = new FileHelper(lastUsedPath + "uc" + System.currentTimeMillis() + ".des");
            FileHelper fhp = new FileHelper(lastUsedPath + "hc" + System.currentTimeMillis() + ".des");
            String unicodeStr = cipherTextArea.getText();
            if (unicodeStr != null) {
                fhu.writeAll(unicodeStr);
                try {
                    fhp.writeAll(DataEncryptionStandard.hex2str(unicodeStr));
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
            }

        } else if (e.getSource() == loadPlainTxtButton) {
            int result = this.fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                FileHelper fh = new FileHelper(fileChooser.getSelectedFile().getAbsolutePath());
                fh.readAll();
                this.plainTextArea.setText(fh.getAll());
            }
        } else if (e.getSource() == savePlainTxtButton) {
            FileHelper fh = new FileHelper(lastUsedPath + "p"+ System.currentTimeMillis() + ".txt");
            String unicodeStr = plainTextArea.getText();
            if (unicodeStr != null) {
                fh.writeAll(unicodeStr);
            }
        }
    }

    private String encrypt(String plainText, String key) {

        String keyRegex = "[0-9a-fA-F]+";
        if (!key.matches(keyRegex) || key.length() != 16 || plainText.isEmpty()) {

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

        return enc.fullEncryption().strip();
    }

    private String decrypt(String cipherText, String key) throws UnsupportedEncodingException {

        String keyRegex = "[0-9a-fA-F]+";
        if (!key.matches(keyRegex) || key.length() != 16 || cipherText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please input both the cipher text and the key in the correct format!",
                    "Error", JOptionPane.ERROR_MESSAGE);

            return null;
        }
        DESDecrypter dec = new DESDecrypter(
                cipherText,
                key
        );
        this.latestGenerated = dec;

        return dec.fullDecryption().strip();
    }
}

