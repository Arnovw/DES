package cryptogen.des;

import helpers.ByteHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Peter.
 */
public class DesAlgorithm {

    public static final int blockSizeInBytes = 8;

    private static final int[] initialPermutation = new int[] {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };

    private static final int[] inverseInitialPermutation = new int[] {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
    };

    public static byte[] encryptBlock(byte[] block, byte[][][] subKeys) throws IllegalArgumentException {
        byte[] tempBlock = block;
        for (int i = 0; i < subKeys.length; i++) {
            tempBlock = encryptBlock(tempBlock, subKeys[i]);
        }
        return tempBlock;
    }

    public static byte[] encryptBlock(byte[] block, byte[][] subKeys) throws IllegalArgumentException {
        if (block.length != 8)
            throw new IllegalArgumentException("Block not 8 length");

        //long millis1 = System.nanoTime();
        final byte[] permutatedBlock = ByteHelper.permutFunc(block, initialPermutation);
        //long millis2 = System.nanoTime();
        //System.out.println("Time permutation " + (millis2 - millis1));

        byte[] prevLeft, prevRight, left, right;
        // verdeel in initiele linkse en rechtse blok
        prevLeft = Arrays.copyOfRange(permutatedBlock, 0, (int) Math.ceil(permutatedBlock.length / 2.0));
        prevRight = Arrays.copyOfRange(permutatedBlock, permutatedBlock.length / 2, permutatedBlock.length);

        // bereken L1 R1 tem L15 R15
        for (int i = 1; i <= 16; i++) {

            // bereken linkse en rechtse blok
            left = prevRight;

            //long millisBeforeXorFeistel = System.nanoTime();
            right = ByteHelper.xorByteBlocks(prevLeft, Feistel.executeFunction(prevRight, subKeys[i - 1]));
            //System.out.println("time xor feistel " + (System.nanoTime() - millisBeforeXorFeistel));

            // voorbereiding volgende iteratie
            prevLeft = left;
            prevRight = right;
        }

        // swap voor laatste iteratie
        left = prevRight;
        right = prevLeft;

        //long millis3 = System.nanoTime();
        //System.out.println("Time iterations " + (millis3 - millis2));

        return ByteHelper.permutFunc(concatBlocks(left, right), inverseInitialPermutation);
    }

    public static byte[] decryptBlock(byte[] block, byte[][][] reversedSubKeys) throws IllegalArgumentException {
        return encryptBlock(block, reversedSubKeys);
    }

    public static byte[] decryptBlock(byte[] block, byte[][] reversedSubKeys) throws IllegalArgumentException {
        return encryptBlock(block, reversedSubKeys);
    }


    private static byte[][][] reverseSubKeys(byte[][][] subKeys) {
        byte[][][] reversedSubKeys = new byte[subKeys.length][][];
        for (int i = 0; i < subKeys.length; i++) {
            int reversedI = subKeys.length - 1 - i;
            reversedSubKeys[reversedI] = new byte[subKeys[i].length][];
            for (int j = 0; j < subKeys[i].length; j++) {
                int reversedJ = subKeys[i].length - 1 - j;
                reversedSubKeys[reversedI][reversedJ] = subKeys[i][j];
            }
        }
        return reversedSubKeys;
    }

    // based on http://stackoverflow.com/a/784842
    private static byte[] concatBlocks(byte[] left, byte[] right) {
        byte[] result = Arrays.copyOf(left, left.length + right.length);
        System.arraycopy(right, 0, result, left.length, right.length);
        return result;
    }
}
