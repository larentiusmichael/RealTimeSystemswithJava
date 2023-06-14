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
public class Direction implements Runnable {
    Random rand = new Random();    
    Plane plane;
    int temp;
    
    public Direction(Plane plane) {
        this.plane = plane;
    }
      
    public void run() {                
        int change = rand.nextInt(100);   
        
        if (rand.nextBoolean()){            
            change*= -1;
        }
        
        temp = plane.direction;
        
        temp = temp + change;
        
        if(temp >= 360) {
            temp = temp - 360;
            plane.direction = temp;
        } else if (temp < 0) {
            temp = 360 + temp;
            plane.direction = temp;
        } else {
            plane.direction = temp;
        }
        
        System.out.println("Current Direction = " + plane.direction + " degree from departing point");
        
        try {
            plane.list1.put(new Data("direction", plane.direction));
            
        } catch (Exception e) {
            
        }
    }
}
