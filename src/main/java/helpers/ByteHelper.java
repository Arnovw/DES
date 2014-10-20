/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.math.BigInteger;
import java.util.Arrays;

/**
 *
 * @author arno
 */
public class ByteHelper {

    // joins two blocks each containing 4 significant bits (4 right most bits contain data)
    public static byte joinBlocks(byte block1, byte block2) {
        return (byte) (block1 << 4 | block2);
    }
    
       // based on http://stackoverflow.com/a/784842
    public static byte[] concatBlocks(byte[] left, byte[] right) {
        byte[] result = Arrays.copyOf(left, left.length + right.length);
        System.arraycopy(right, 0, result, left.length, right.length);
        return result;
    }

    // Herschikt de bits in de source byte array volgens de positions array
    // - Lengte van resultaat wordt bepaald door lengte van positions array
    public static byte[] permutate(byte[] source, int[] positions) {

        byte[] newBlock = new byte[positions.length / 8];
        int byteIndex = -1;

        // voor elke positie
        for (int i = 0; i < positions.length; i++) {
            // neem de index van de bit
            int bitIndex = positions[i] - 1;
            // neem de waarde van de bit (0 of 1)
            byte bit = getBit(source, bitIndex);

            if (i % 8 == 0) {
                byteIndex++;
            }

            // zet de bit in de nieuwe blok op de juiste plaats
            newBlock[byteIndex] = (byte) ((bit << (bitIndex % 8)) | newBlock[byteIndex]);
        }

        return newBlock;
    }

    // http://n3vrax.wordpress.com/2011/07/23/des-algorithm-java-implementation/
    public static byte[] permutFunc(byte[] input, int[] table) {
        int nrBytes = (table.length - 1) / 8 + 1;
        byte[] out = new byte[nrBytes];
        for (int i = 0; i < table.length; i++) {
            int val = getBitInt(input, table[i] - 1);
            setBit(out, i, val);
        }
        return out;
    }

    // Kijkt of een bepaalde bit in een byte array gelijk aan 1 is
    public static byte getBit(byte[] data, int pos) {
        int posByte = pos / 8;
        int posBit = pos % 8;
        byte valByte = data[posByte];

        return (byte) (isBitSet(valByte, posBit) ? 1 : 0);
    }

    // Kijkt of een bepaalde bit in een byte gelijk aan 1 is
    // Source: http://stackoverflow.com/questions/1034473/java-iterate-bits-in-byte-array
    public static Boolean isBitSet(byte value, int bit) {
        return (value & (1 << bit)) != 0;
    }

    // Voert XOR uit op twee byte arrays
    // Arrays moeten van de zelfde lengte zijn
    public static byte[] xorByteBlocks(byte[] blockOne, byte[] blockTwo) {
        byte[] newBlock = new byte[blockOne.length];

        for (int i = 0; i < newBlock.length; i++) {
            newBlock[i] = (byte) (blockOne[i] ^ blockTwo[i]);
        }

        return newBlock;
    }

    // Zorgt voor de left shift
    // Source: http://www.herongyang.com/Java/Bit-String-Left-Rotation-All-Bits-in-Byte-Array.html
    public static byte[] rotateLeft(byte[] in, int len, int step) {
        int numOfBytes = (len - 1) / 8 + 1;
        byte[] out = new byte[numOfBytes];
        for (int i = 0; i < len; i++) {
            int val = getBitInt(in, (i + step) % len);
            setBit(out, i, val);
        }
        return out;
    }

    //nick: Arno heeft hier een functie voor weet niet zeker of zelfde resultaat, later testen.
    // Haalt de bit op op positie pos in de byte array data
    // Source: http://www.herongyang.com/Java/Bit-String-Get-Bit-from-Byte-Array.html
    public static int getBitInt(byte[] data, int pos) {
        int posByte = pos / 8;
        int posBit = pos % 8;
        byte valByte = data[posByte];
        int valInt = valByte >> (8 - (posBit + 1)) & 0x0001;
        return valInt;
    }
    
    //nick: misschien deze functie zelf uitschrijven, deze komt rechstreeks van de site.
    // Stelt de bit op op positie pos in de byte array data
    // Source: http://www.herongyang.com/Java/Bit-String-Get-Bit-from-Byte-Array.html
    public static void setBit(byte[] data, int pos, int val) {
        int posByte = pos / 8;
        int posBit = pos % 8;
        byte oldByte = data[posByte];
        oldByte = (byte) (((0xFF7F >> posBit) & oldByte) & 0x00FF);
        byte newByte = (byte) ((val << (8 - (posBit + 1))) | oldByte);
        data[posByte] = newByte;
    }

    // http://stackoverflow.com/a/6393904
    public static void printByteArray(byte[] bytes) {
        for (byte b : bytes) {
            System.out.print(Integer.toBinaryString(b & 255 | 256).substring(1) + " ");
        }
        System.out.println();
    }

    public static byte[] convertBinaryStringToByteArray(String binaryString) {
        byte[] bytes = new BigInteger(binaryString.replace(" ", ""), 2).toByteArray();

        // als eerste bit == 1 wordt een extra byte met allemaal nullen toegevoegd
        if (binaryString.charAt(0) == '1') {
            return Arrays.copyOfRange(bytes, 1, bytes.length);
        }
        return bytes;
    }
}
