/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptogen;

import cryptogen.stenagography.Stenagography;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author cornel
 */
public class CryptoGen extends JFrame implements ActionListener {

    private JTextField txtDesDecryptedFile, txtDesEncryptedFile, txtDesKey, txtStenagoImage, txtStenagoFile;
    private JButton btnDesEncode, btnDesDecode, btnDesFile, btnDesOutputFile,
            btnStenagoEncode, btnStenagoDecode, btnStenagoImage, btnStenagoFile;
    private JTextArea txtStenagoText, txtConsole;
    private JCheckBox cbStenago, cbUse3des;

    public CryptoGen() {
        initGui();
    }

    public void initGui() {
        //configureren JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("DES Program");

        //hoofdpaneel aanmaken
        JPanel pMain = new JPanel(new BorderLayout());
        this.getContentPane().add(pMain);

        //constraints aanmaken voor objecten te positioneren
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        //paneel DES aanmaken
        JPanel pDes = new JPanel(new GridBagLayout());
        pDes.setBorder(BorderFactory.createTitledBorder("DES"));

        //compontenten toevoegen aan DES paneel
        gbc.gridx = 0;
        gbc.gridy = 0;
        pDes.add(new JLabel("Decrypted File:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        txtDesDecryptedFile = new JTextField(10);
        pDes.add(txtDesDecryptedFile, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        btnDesFile = new JButton("Select File");
        btnDesFile.addActionListener(this);
        pDes.add(btnDesFile, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        pDes.add(new JLabel("Encrypted File:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        txtDesEncryptedFile = new JTextField(10);
        pDes.add(txtDesEncryptedFile, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        btnDesOutputFile = new JButton("Select File");
        btnDesOutputFile.addActionListener(this);
        pDes.add(btnDesOutputFile, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        pDes.add(new JLabel("Key:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        txtDesKey = new JTextField(10);
        pDes.add(txtDesKey, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        cbUse3des = new JCheckBox("Use 3DES");
        pDes.add(cbUse3des, gbc);
 
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        btnDesEncode = new JButton("Start Encryption");
        btnDesEncode.addActionListener(this);
        pDes.add(btnDesEncode, gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;
        btnDesDecode = new JButton("Start Decryption");
        btnDesDecode.addActionListener(this);
        pDes.add(btnDesDecode, gbc);

        //des paneel toevoegen aan frame
        pMain.add(pDes, BorderLayout.WEST);

        //paneel stenagografie aanmaken
        JPanel pStenago = new JPanel(new GridBagLayout());
        pStenago.setBorder(BorderFactory.createTitledBorder("Stenagography"));

        //compontenten toevoegen aan Stenago paneel
        gbc.gridx = 0;
        gbc.gridy = 0;
        pStenago.add(new JLabel("Text:"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        pStenago.add(new JLabel("Image:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        txtStenagoText = new JTextArea(5, 25);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        JScrollPane spStenago = new JScrollPane(txtStenagoText);
        txtStenagoText.setBorder(border);
        pStenago.add(spStenago, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        txtStenagoImage = new JTextField(10);
        pStenago.add(txtStenagoImage, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        btnStenagoImage = new JButton("Select Image");
        btnStenagoImage.addActionListener(this);
        pStenago.add(btnStenagoImage, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        pStenago.add(new JLabel("File:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        txtStenagoFile = new JTextField(10);
        txtStenagoFile.setEditable(false);
        pStenago.add(txtStenagoFile, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        btnStenagoFile = new JButton("Select File");
        btnStenagoFile.addActionListener(this);
        pStenago.add(btnStenagoFile, gbc);
        gbc.gridx = 3;
        gbc.gridy = 3;
        cbStenago = new JCheckBox("Use file");
        cbStenago.addActionListener(this);
        pStenago.add(cbStenago);
        gbc.gridx = 1;
        gbc.gridy = 4;
        btnStenagoEncode = new JButton("Start Encryption");
        btnStenagoEncode.addActionListener(this);
        pStenago.add(btnStenagoEncode, gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;
        btnStenagoDecode = new JButton("Start Decryption");
        btnStenagoDecode.addActionListener(this);
        pStenago.add(btnStenagoDecode, gbc);

        //stenago paneel toevoegen aan frame
        pMain.add(pStenago, BorderLayout.EAST);

        //console venster maken
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        txtConsole = new JTextArea(20, 20);
        JScrollPane scrollPane = new JScrollPane(txtConsole);
        pMain.add(scrollPane, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        CryptoGen cg = new CryptoGen();

        cg.pack();
        cg.setResizable(false);
        cg.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDesFile) {
            //create file choose window
            JFileChooser fch = new JFileChooser();
            fch.showSaveDialog(this);

            //check of een file is geselecteerd
            if (fch.getSelectedFile() != null) {
                String filePath = fch.getSelectedFile().getAbsolutePath();
                txtDesDecryptedFile.setText(filePath);
                
                if(txtDesEncryptedFile.getText().equals(""))
                    txtDesEncryptedFile.setText(filePath + "." + "des");
            }
        } else if (e.getSource() == btnDesOutputFile) {
            //create file choose window
            JFileChooser fch = new JFileChooser();
            fch.showSaveDialog(this);

            //check of een file is geselecteerd
            if (fch.getSelectedFile() != null) {
                String filePath = fch.getSelectedFile().getAbsolutePath();
                txtDesEncryptedFile.setText(filePath);
                
                if(txtDesDecryptedFile.getText().equals("") && filePath.indexOf(".des") != -1)
                    txtDesDecryptedFile.setText(filePath.substring(0, filePath.indexOf(".des")));
            }
        } else if (e.getSource() == btnStenagoImage) {
            //create file choose window
            JFileChooser fch = new JFileChooser();
            fch.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("BMP", "bmp", "bmp");
            fch.setFileFilter(filter);
            fch.showSaveDialog(this);

            //check of een file is geselecteerd
            if (fch.getSelectedFile() != null) {
                txtStenagoImage.setText(fch.getSelectedFile().getAbsolutePath());
            }
        } else if (e.getSource() == btnStenagoEncode) {
            //set Console window
            Stenagography.console = txtConsole;
            Stenagography.DEBUG = true;

            txtConsole.append("Encoding started!" + "\n\r");

            //start encoding
            BufferedImage img;
            if (cbStenago.isSelected())                 
                img = Stenagography.encode(txtStenagoImage.getText(), txtStenagoFile.getText(), true);
             else 
                img = Stenagography.encode(txtStenagoImage.getText(), txtStenagoText.getText(), false);
            
            JFileChooser fch = new JFileChooser();

            int returnVal = fch.showSaveDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                writeImage(fch.getSelectedFile().getAbsolutePath(), "bmp", img);
            }

            txtStenagoText.setText("");

            txtConsole.append("Encoding finished!" + "\n\r");
        } else if (e.getSource() == btnStenagoDecode) {
            Stenagography.console = txtConsole;
            Stenagography.DEBUG = true;

            txtConsole.append("Decoding started!" + "\n\r");
             String str;
            if (cbStenago.isSelected())  
                str = Stenagography.decode(txtStenagoImage.getText(), true);
            else
                str = Stenagography.decode(txtStenagoImage.getText(), false);
            
            txtStenagoText.setText(str);

            txtConsole.append("Decoding finished!" + "\n\r");
        } else if (e.getSource() == btnDesEncode) {
            
            
            String inputFile = txtDesDecryptedFile.getText();
            String outputFile = txtDesEncryptedFile.getText();
            String key = txtDesKey.getText();

            if(key.equals("")) {
                JOptionPane.showMessageDialog(null, "You must enter a key first.", "No key", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(inputFile.equals("")) {
                JOptionPane.showMessageDialog(null, "You must choose a file first.", "No file", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if(outputFile.equals("")) {
                JOptionPane.showMessageDialog(null, "You must choose an output file location.", "No output file", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            txtConsole.append("Starting encryption process." + "\n\r");
            long before = System.currentTimeMillis();
            
            if(cbUse3des.isSelected()) {
                DesEncryption.encryptFile3DES(inputFile, outputFile, key);
            } else {
                DesEncryption.encryptFile(inputFile, outputFile, key);
            }
            
            
            long after = System.currentTimeMillis();
            txtConsole.append("Encryption completed."+ "\n\r");
            txtConsole.append("Duration: " + (after - before)/1000.0 + " seconds" + "\n\r");

            
        } else if (e.getSource() == btnDesDecode) {
            
            String inputFile = txtDesEncryptedFile.getText();
            String outputFile = txtDesDecryptedFile.getText();
            String key = txtDesKey.getText();

            if(key.equals("")) {
                JOptionPane.showMessageDialog(null, "You must enter a key first.", "No key", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(inputFile.equals("")) {
                JOptionPane.showMessageDialog(null, "You must choose a file first.", "No file", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if(outputFile.equals("")) {
                JOptionPane.showMessageDialog(null, "You must choose an output file location.", "No output file", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            txtConsole.append("Starting decryption process." + "\n\r");
            long before = System.currentTimeMillis();
            
            
            if(cbUse3des.isSelected()) {
                DesEncryption.decryptFile3DES(inputFile, outputFile, key);
            } else {
                DesEncryption.decryptFile(inputFile, outputFile, key);
            }
            
            long after = System.currentTimeMillis();
            txtConsole.append("Decryption completed."+ "\n\r");
            txtConsole.append("Duration: " + (after - before)/1000.0 + " seconds" + "\n\r");
            
        } else if (e.getSource() == btnStenagoFile) {
            //create file choose window
            JFileChooser fch = new JFileChooser();
            fch.showSaveDialog(this);

            //check of een file is geselecteerd
            if (fch.getSelectedFile() != null) {
                txtStenagoFile.setText(fch.getSelectedFile().getAbsolutePath());
            }
        } else if (e.getSource() == cbStenago) {
            if (cbStenago.isSelected()) {
                txtStenagoText.setEditable(false);
                txtStenagoText.setText("");
                txtStenagoFile.setEditable(true);
                txtStenagoText.setBackground(Color.GRAY);
            } else {
                txtStenagoText.setEditable(true);
                txtStenagoFile.setEditable(false);
                txtStenagoFile.setText("");
                txtStenagoText.setBackground(Color.WHITE);
            }
        }
    }

    public void writeImage(String path, String ext, BufferedImage img) {
        try {
            File f = new File(path);

            f.delete();
            ImageIO.write(img, ext, f);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error while saving file!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
