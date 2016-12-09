/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1concurrent;

import java.util.concurrent.Semaphore;

/**
 *
 * @author jaimescript
 */
public class Practica1Concurrent {

    static final int HONEY_TO_PRODUCE = 100;
    static final int POT_SIZE = 10;
    static final int NUM_BEES = 10;
    
    static final int NUMBER_OF_EATINGS_OF_BEARS = HONEY_TO_PRODUCE/POT_SIZE;
    static final int MAX_HONEY_PRODUCED_BY_A_BEE = HONEY_TO_PRODUCE/NUM_BEES;
    
    
    static volatile int honey_produced_in_pot = 0;
    static volatile int honey_consumed_by_bears = 0;

    
    static Semaphore isFull = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {
        Thread[] beeThreads = new Thread[NUM_BEES];
        Thread bearThread = new Thread(new Bear(0));
        int i;

        //Initialize beeThreads and bearThread
        for (i = 0; i < NUM_BEES; i++) {
            beeThreads[i] = new Thread(new Bee(i));
            beeThreads[i].start();
        }
        
        
        bearThread.start();


        //Wait to finish beeThreads and bearThread
        for (i = 0; i < NUM_BEES; i++) {
            beeThreads[i].join();
        }

        
        bearThread.join();
        
        
        //All threads are done, we show all honey consumed by the Bear.
        System.out.println("Total of honey consumed by Bear: " + honey_consumed_by_bears);
    }
}
