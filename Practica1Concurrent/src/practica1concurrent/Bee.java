/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1concurrent;

import java.util.logging.Level;
import java.util.logging.Logger;
import static practica1concurrent.Practica1Concurrent.POT_SIZE;
import static practica1concurrent.Practica1Concurrent.MAX_HONEY_PRODUCED_BY_A_BEE;
import static practica1concurrent.Practica1Concurrent.mutex;
import static practica1concurrent.Practica1Concurrent.isFull;
import static practica1concurrent.Practica1Concurrent.honey_produced_in_pot;

/**
 *
 * @author jaimescript
 */
public class Bee implements Runnable {

    private int id;
    
    public Bee(int id) {
        this.id = id;
    }
    
    @Override
    public void run() {
        try {
            
            for (int i = 0; i < MAX_HONEY_PRODUCED_BY_A_BEE; i++) {
                
                //Only one Bee can do critical section at time
                mutex.acquire();

                //Each Bee produce 1 honey
                honey_produced_in_pot += 1;
                System.out.println("Bee with id: " + id + ", has produced a unit: " + honey_produced_in_pot + " al pot.");
                
                //If this Bee fills the Pot, then:
                //1- the Bee wake up the Bear
                //2- All Bees stop producing honey (mutex.acquire())
                if (honey_produced_in_pot == POT_SIZE) {
                    System.out.println("Bee with id: " + id + ", wake up the bear.");
                    isFull.release();
                    mutex.acquire();
                }
                
                
                mutex.release();
                
            }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Bee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
