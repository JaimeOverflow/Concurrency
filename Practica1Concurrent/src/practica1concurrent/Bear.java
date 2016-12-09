/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1concurrent;

import java.util.logging.Level;
import java.util.logging.Logger;
import static practica1concurrent.Practica1Concurrent.NUMBER_OF_EATINGS_OF_BEARS;
import static practica1concurrent.Practica1Concurrent.mutex;
import static practica1concurrent.Practica1Concurrent.isFull;
import static practica1concurrent.Practica1Concurrent.honey_consumed_by_bears;
import static practica1concurrent.Practica1Concurrent.honey_produced_in_pot;

/**
 *
 * @author jaimescript
 */
public class Bear implements Runnable {
    
    private int id;
    
    public Bear(int id) {
        this.id = id;
    }
    
    @Override
    public void run() {
        try {
            
            for (int i = 0; i < NUMBER_OF_EATINGS_OF_BEARS; i++) {
                //If the Pot is full of honey, then the Bear can eat all the Pot
                isFull.acquire();
                
                System.out.println("Bear with id: " + id + ", consume pot.");
                honey_consumed_by_bears += honey_produced_in_pot;
                
                //Bear eats all honey's pot
                honey_produced_in_pot = 0;

                //The Bees can produce honey again because we empty the Pot
                mutex.release();
            }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Bear.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
     }
    
}
