package cryptogen;

import helpers.ByteHelper;
import org.junit.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by peter on 30/09/14.
 */
public class DesEncryptionTest {

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    // iterations in this example
    //
    //L1:
    //        1111 0000 1010 1010 1111 0000 1010 1010
    //R1:
    //        1110 1111 0100 1010 0110 0101 0100 0100
    //
    //L16:
    //        0100 0011 0100 0010 0011 0010 0011 0100
    //R16:
    //        0000 1010 0100 1100 1101 1001 1001 0101
    //
    // feistel f(R0,K1):  0010 0011 0100 1010 1010 1001 1011 1011
    //@Test
    public void testEncryptBlock() {

        byte[] key = ByteHelper.convertBinaryStringToByteArray("00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001".replace(" ", ""));
        byte[][] subKeys = new KeyCalculator().generateKeys(key);

        byte[] block = ByteHelper.convertBinaryStringToByteArray("00000001 00100011 01000101 01100111 10001001 10101011 11001101 11101111".replace(" ", ""));
        byte[] expectedEncryptedBlock = ByteHelper.convertBinaryStringToByteArray("10000101 11101000 00010011 01010100 00001111 00001010 10110100 00000101".replace(" ", ""));

        try {
            byte[] result = DesEncryption.encryptBlock(block, subKeys);

            System.out.println();
            System.out.println("expected vs actual result");
            ByteHelper.printByteArray(expectedEncryptedBlock);
            ByteHelper.printByteArray(result);
            System.out.println();

            assertEquals(expectedEncryptedBlock, result);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEncryptBlock2() {

        byte[] key = ByteHelper.convertBinaryStringToByteArray("1010011010111000011110011110100111011001100111111001100101110010".replace(" ", ""));
        byte[][] subKeys = new KeyCalculator().generateKeys(key);

        byte[] block = ByteHelper.convertBinaryStringToByteArray("0110100001100001011011000110110001101111011011110110111101101111".replace(" ", ""));
        byte[] expectedEncryptedBlock = ByteHelper.convertBinaryStringToByteArray("10001111 01101000 01001111 10101111 01010101 01011010 10111011 11011001".replace(" ", ""));

        try {
            byte[] result = DesEncryption.encryptBlock(block, subKeys);

            System.out.println();
            System.out.println("expected vs actual result");
            ByteHelper.printByteArray(expectedEncryptedBlock);
            ByteHelper.printByteArray(result);
            System.out.println();

            assertArrayEquals(expectedEncryptedBlock, result);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testDecryptBlock() {
        byte[] key = ByteHelper.convertBinaryStringToByteArray("1010011010111000011110011110100111011001100111111001100101110010".replace(" ", ""));
        byte[][] subKeys = new KeyCalculator().generateKeys(key);
        byte[][] reversedSubKeys = reverseSubKeys(subKeys);

        byte[] encryptedBlock = ByteHelper.convertBinaryStringToByteArray("10001111 01101000 01001111 10101111 01010101 01011010 10111011 11011001".replace(" ", ""));
        byte[] expectedDecryptedBlock = ByteHelper.convertBinaryStringToByteArray("0110100001100001011011000110110001101111011011110110111101101111".replace(" ", ""));

        try {
            byte[] result = DesEncryption.decryptBlock(encryptedBlock, reversedSubKeys);

            assertArrayEquals(expectedDecryptedBlock, result);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testEncryptDecrypt() {

        byte[][] subKeys = new KeyCalculator().generate("testkey");
        byte[][] reversedSubKeys = reverseSubKeys(subKeys);

        byte[] block = ByteHelper.convertBinaryStringToByteArray("0110100001100001011011000110110001101111011011110110111101101111".replace(" ", ""));

        byte[] encrypted = DesEncryption.encryptBlock(block, subKeys);
        byte[] decrypted = DesEncryption.decryptBlock(encrypted, reversedSubKeys);

        System.out.println();
        System.out.println("expected vs actual result (testEncryptDecrypt)");
        ByteHelper.printByteArray(block);
        ByteHelper.printByteArray(decrypted);
        System.out.println();

        assertArrayEquals(block, decrypted);
    }

    private static byte[][] reverseSubKeys(byte[][] subKeys) {
        byte[][] reversedSubKeys = new byte[subKeys.length][];
        for (int i = 0; i < subKeys.length; i++) {
            reversedSubKeys[i] = Arrays.copyOf(subKeys[subKeys.length - 1 - i], subKeys[subKeys.length - 1 - i].length);
        }
        return reversedSubKeys;
    }
}
