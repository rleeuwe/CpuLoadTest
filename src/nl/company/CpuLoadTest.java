package nl.company;

import java.util.ArrayList;
import java.util.List;

public class CpuLoadTest {

    /**
     * Cpu / Core load test to detect the number of threads that can be executed
     * at the same time.
     * @param args not used
     */
    public static void main(String args[]) {
        System.out.println("Cpu/Core load test");
        System.out.println("Cpu's/Core's reported by (virtual) system is " + Runtime.getRuntime().availableProcessors());
        System.out.print("Calculating baseline...");
        long t0 = System.currentTimeMillis();
        testLoad(1);
        long t1 = System.currentTimeMillis();
        long t01 = t1-t0;
        t0 = System.currentTimeMillis();
        testLoad(1);
        t1 = System.currentTimeMillis();
        long t02 = t1 - t0;
        t0 = System.currentTimeMillis();
        testLoad(1);
        t1 = System.currentTimeMillis();
        long t03 = t1 - t0;
        int tbase = (int)((t01 + t02 + t03)/3f);
        System.out.println(tbase + " mSec.");

        long maxSpeed = 0;
        for (int n=0; n<16; n++) {
            t0 = System.currentTimeMillis();
            testLoad(n + 1);
            t1 = System.currentTimeMillis();
            long msec = (t1 - t0);
            System.out.print("Process took " + msec + " mSec for " + (n +1) + " threads. ");
            long speed = tbase * 100 * (n + 1) / msec;
            System.out.println("Speed is " + speed + " %");
            if (speed > maxSpeed) {
                maxSpeed = speed;
            }
            System.gc();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Number of threads that can be executed at the same time is " + (int)Math.ceil(maxSpeed/100f));
    }

    /**
     * Execute the load test
     * @param threads nr of threads to execute
     */
    private static void testLoad(int threads) {
        List<LoadThread> threadList = new ArrayList<>();
        for (int n=0; n<threads; n++) {
            LoadThread loadThread = new LoadThread();
            threadList.add(loadThread);
            loadThread.start();
        }
        boolean active = true;
        while (active){
            active = false;
            for (int n = 0; n < threadList.size(); n++) {
                if (threadList.get(n).isAlive()) {
                    active = true;
                }
            }
        }
    }
}
