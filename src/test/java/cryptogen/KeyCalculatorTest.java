package cryptogen;

import helpers.ByteHelper;
import org.junit.*;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by peter on 1/10/14.
 */
public class KeyCalculatorTest {

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

    @Test
    public void testInitialPermutation() {
        byte[] key = ByteHelper.convertBinaryStringToByteArray("00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001".replace(" ", ""));
        byte[] expected = ByteHelper.convertBinaryStringToByteArray("1111000 0110011 0010101 0101111 0101010 1011001 1001111 0001111".replace(" ", ""));
        byte[] result = ByteHelper.permutFunc(key, KeyCalculator.permutatieTabel1);

        ByteHelper.printByteArray(expected);
        ByteHelper.printByteArray(result);

        assertArrayEquals(expected, result);
    }


    @Test
    public void testGenerate() {

        byte[] key = ByteHelper.convertBinaryStringToByteArray("00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001".replace(" ", ""));

        String[] expectedSubKeysStrings = new String[] {
                "000110 110000 001011 101111 111111 000111 000001 110010",
                "011110 011010 111011 011001 110110 111100 100111 100101",
                "010101 011111 110010 001010 010000 101100 111110 011001",
                "011100 101010 110111 010110 110110 110011 010100 011101",
                "011111 001110 110000 000111 111010 110101 001110 101000",
                "011000 111010 010100 111110 010100 000111 101100 101111",
                "111011 001000 010010 110111 111101 100001 100010 111100",
                "111101 111000 101000 111010 110000 010011 101111 111011",
                "111000 001101 101111 101011 111011 011110 011110 000001",
                "101100 011111 001101 000111 101110 100100 011001 001111",
                "001000 010101 111111 010011 110111 101101 001110 000110",
                "011101 010111 000111 110101 100101 000110 011111 101001",
                "100101 111100 010111 010001 111110 101011 101001 000001",
                "010111 110100 001110 110111 111100 101110 011100 111010",
                "101111 111001 000110 001101 001111 010011 111100 001010",
                "110010 110011 110110 001011 000011 100001 011111 110101"
        };

        //byte[][] expectedSubKeys = Arrays.stream(expectedSubKeysStrings)
        //                                 .map((s) -> convertBinaryStringToByteArray(s.replace(" ", "")))
        //                                 .collect(Collectors.toList())
        //                                 .toArray(new byte[16][6]);

        byte[][] expectedSubKeys = new byte[16][6];
        for(int i = 0; i < expectedSubKeys.length; i++) {
            expectedSubKeys[i] = ByteHelper.convertBinaryStringToByteArray(expectedSubKeysStrings[i].replace(" ", ""));
        }

        byte[][] subKeys = new KeyCalculator().generateKeys(key);

        Arrays.stream(expectedSubKeys).forEach((bytes) -> ByteHelper.printByteArray(bytes));
        System.out.println();

        Arrays.stream(subKeys).forEach((bytes) -> ByteHelper.printByteArray(bytes));
        System.out.println();

        assertArrayEquals(expectedSubKeys, subKeys);
    }

}
