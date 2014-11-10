    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptogen;

import cryptogen.steganography.Steganography;
import helpers.ConsoleHelper;
import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author cornel
 */
public class CryptoGen extends JFrame implements ActionListener {

    private JTextField txtDesDecryptedFile, txtDesEncryptedFile, txtDesKey, txtStenagoImage, txtStenagoFile, txtStenagoOutputImage;
    private JButton btnDesEncode, btnDesDecode, btnDesFile, btnDesOutputFile,
            btnStenagoEncode, btnStenagoDecode, btnStenagoImage, btnStenagoFile, btnStenagoOutputImage;
    private JTextArea txtConsole;
    private JCheckBox cbUse3des;

    public CryptoGen() {
        initGui();
    }

    public void initGui() {
        //configureren JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Crypto Generator");

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

        
        //componenten toevoegen aan paneel
        // DES row 0
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
        
        // DES row 1
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
        // DES row 2
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
 
        // DES row 3
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

        //paneel steganography aanmaken
        JPanel pStenago = new JPanel(new GridBagLayout());
        pStenago.setBorder(BorderFactory.createTitledBorder("Stenagography"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        pStenago.add(new JLabel("Normal image:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        txtStenagoImage = new JTextField(10);
        pStenago.add(txtStenagoImage, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        btnStenagoImage = new JButton("Select image");
        btnStenagoImage.addActionListener(this);
        pStenago.add(btnStenagoImage, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        pStenago.add(new JLabel("Encoded image:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        txtStenagoOutputImage = new JTextField(10);
        pStenago.add(txtStenagoOutputImage, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        btnStenagoOutputImage = new JButton("Select image");
        btnStenagoOutputImage.addActionListener(this);
        pStenago.add(btnStenagoOutputImage, gbc);
        
        //compontenten toevoegen aan Stenago paneel
        gbc.gridx = 0;
        gbc.gridy = 2;
        pStenago.add(new JLabel("Input/output file:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        txtStenagoFile = new JTextField(10);
        pStenago.add(txtStenagoFile, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        btnStenagoFile = new JButton("Select file");
        btnStenagoFile.addActionListener(this);
        pStenago.add(btnStenagoFile, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        btnStenagoEncode = new JButton("Start Encryption");
        btnStenagoEncode.addActionListener(this);
        pStenago.add(btnStenagoEncode, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
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
        
        // add console textarea to helper class
        ConsoleHelper.console = this.txtConsole;
    }

    public static void main(String[] args) {
        // entry point
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
                String path = fch.getSelectedFile().getAbsolutePath();
                txtStenagoImage.setText(path);
                
                //autocomplete output path
                int dotPos = path.lastIndexOf(".");
                String outputPath = path.substring(0, dotPos) + ".encoded" + path.substring(dotPos);
                txtStenagoOutputImage.setText(outputPath);
            }
        } else if (e.getSource() == btnStenagoOutputImage) {
            //create file choose window
            JFileChooser fch = new JFileChooser();
            fch.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("BMP", "bmp", "bmp");
            fch.setFileFilter(filter);
            fch.showSaveDialog(this);

            //check of een file is geselecteerd
            if (fch.getSelectedFile() != null) {
                String path = fch.getSelectedFile().getAbsolutePath();
                txtStenagoOutputImage.setText(path);
            }
        } else if (e.getSource() == btnStenagoEncode) {
            
            String inputImage = txtStenagoImage.getText();
            String outputImage = txtStenagoOutputImage.getText();
            String inputFilePath = txtStenagoFile.getText();
            
            if(inputImage.equals("")) {
                ConsoleHelper.appendError("You must enter a normal image. This is the source image that will be used to embed the file into.");
                return;
            }
            
            if(outputImage.equals("")) {
                ConsoleHelper.appendError("You must choose a location for the encoded image.");
                return;
            }
            
            if(inputFilePath.equals("")) {
                ConsoleHelper.appendError("You must enter an input file that you want to have encrypted.");
                return;
            }
            
            if(!CryptoGen.isFile(inputImage)) {
                ConsoleHelper.appendError("You must enter a valid \"normal image\" path.");
                return;
            }
            
            if(!CryptoGen.isFile(inputFilePath)) {
                ConsoleHelper.appendError("You must enter a valid \"input file\" path.");
                return;
            }
            
            ConsoleHelper.start("steganography encoding");
            //start encoding
            BufferedImage img = Steganography.encode(inputImage, inputFilePath);
            writeImage(outputImage, "bmp", img);
            
            ConsoleHelper.finish("steganography encoding");
            
        } else if (e.getSource() == btnStenagoDecode) {
            
            String imagePath = txtStenagoOutputImage.getText();
            String outputPath = txtStenagoFile.getText();
            
            if(imagePath.equals("")) {
                ConsoleHelper.appendError("You must choose an encoded image. This is an image that has data embedded with steganography.");
                return;
            }
            
            if(outputPath.equals("")) {
                ConsoleHelper.appendError("You must choose a location for the output file.");
                return;
            }
            
            ConsoleHelper.start("steganography decoding");
            Steganography.decode(imagePath, outputPath);
            ConsoleHelper.finish("steganography decoding");
            
        } else if (e.getSource() == btnDesEncode) {
            
            String inputFile = txtDesDecryptedFile.getText();
            String outputFile = txtDesEncryptedFile.getText();
            String key = txtDesKey.getText();

            if(key.equals("")) {
                ConsoleHelper.appendError("You must enter a key first.");
                return;
            }
            
            if(inputFile.equals("")) {
                ConsoleHelper.appendError("You must choose a decrypted file first.");
                return;
            }

            if(outputFile.equals("")) {
                ConsoleHelper.appendError("You must choose a location for the encrypted file.");
                return;
            }

            if(!CryptoGen.isFile(inputFile)) {
                ConsoleHelper.appendError("You must enter a valid path to the decrypted file.");
                return;
            }
            
            
            if(cbUse3des.isSelected()) {
                ConsoleHelper.start("3des encryption");
                DesEncryption.encryptFile3DES(inputFile, outputFile, key);
                ConsoleHelper.finish("3des encryption");
            } else {
                ConsoleHelper.start("des encryption");
                DesEncryption.encryptFile(inputFile, outputFile, key);
                ConsoleHelper.finish("des encryption");
            }

            
        } else if (e.getSource() == btnDesDecode) {
            
            String inputFile = txtDesEncryptedFile.getText();
            String outputFile = txtDesDecryptedFile.getText();
            String key = txtDesKey.getText();

            if(key.equals("")) {
                ConsoleHelper.appendError("You must enter a key first.");
                return;
            }
            if(inputFile.equals("") || !CryptoGen.isFile(inputFile)) {
                ConsoleHelper.appendError("You must enter a valid path to the encrypted file.");
                return;
            }
            
            if(outputFile.equals("")) {
                ConsoleHelper.appendError("You must choose a location for the decrypted file.");
                return;
            }
            
            if(cbUse3des.isSelected()) {
                ConsoleHelper.start("3des decryption");
                DesEncryption.decryptFile3DES(inputFile, outputFile, key);
                ConsoleHelper.finish("3des decryption");
            } else {
                ConsoleHelper.start("des decryption");
                DesEncryption.decryptFile(inputFile, outputFile, key);
                ConsoleHelper.finish("des decryption");
            }
            
        } else if (e.getSource() == btnStenagoFile) {
            //create file choose window
            JFileChooser fch = new JFileChooser();
            fch.showSaveDialog(this);

            //check of een file is geselecteerd
            if (fch.getSelectedFile() != null) {
                txtStenagoFile.setText(fch.getSelectedFile().getAbsolutePath());
            }
        }
    }

    public void writeImage(String path, String ext, BufferedImage img) {
        try {
            File f = new File(path);

            f.delete();
            ImageIO.write(img, ext, f);
        } catch (Exception e) {
            ConsoleHelper.appendError("Error while saving file.");
            ConsoleHelper.appendError(e.getMessage());
        }
    }
    
    public static boolean isFile (String path) {
        File file = new File(path);
        if(file.exists())
            return true;
        else 
            return false;
    }
}
