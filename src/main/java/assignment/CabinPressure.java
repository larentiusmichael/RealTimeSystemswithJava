/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.util.Random;

/**
 *
 * @author LAURENTIUS MICHAEL
 */
public class CabinPressure implements Runnable {
    Random rand = new Random();    
    Plane plane;
    
    public CabinPressure(Plane plane) {
        this.plane = plane;
    }
      
    public void run() {                
        int indicator = rand.nextInt(5);   
        
        if(indicator < 2) {
            System.out.println("Current Cabin Pressure: Low");
        } else {
            if(plane.oxygenMask == true) {
                System.out.println("Current Cabin Pressure: Normal");
                plane.oxygenMask = false;
                System.out.println("Oxygen Mask is being pulled back");
            } else {
                System.out.println("Current Cabin Pressure: Normal");
            }
        }
        
        try {
            plane.list1.put(new Data("cabinpressure", indicator));
            
        } catch (Exception e) {
            
        }
    }
}
