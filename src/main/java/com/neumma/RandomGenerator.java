package com.neumma;

import java.util.Random;

/**
 * Used to provide single instance of Random generator.
 */
public class RandomGenerator {
    private static Random r = new Random();

    /**
     * Returns a random integer in given range.
     * @param min Minimum value
     * @param max Maximum value
     * @return Random number from min-max range.
     */
    public static int getRandomInteger(int min, int max) {
        return min + r.nextInt(max-min + 1);
    }
}
