package nl.company;

import java.util.Random;

/**
 * Simple class to generate some load on the system
 */
public class LoadThread extends Thread {

    private float result;

    private final static int LOOP = 2500;

    /**
     * Actual running code. Do some calculations to gernerate load on the system
     */
    public void run() {
        Random random = new Random(System.nanoTime());
        float f = random.nextFloat();
        for (int m = 0; m< LOOP; m++) {
            for (int n = 0; n < 1000000; n++) {
                f = f + 1.0000002f;
            }
        }
        result = f;
    }

}
