/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;

/**
 *
 * @author LAURENTIUS MICHAEL
 */

public class AltitudeSensor implements Runnable {
    
    Random rand = new Random();    
    Plane plane;
    
    public AltitudeSensor(Plane plane) {
        this.plane = plane;
    }
    
//    @Benchmark
//    @BenchmarkMode(org.openjdk.jmh.annotations.Mode.AverageTime)
//    @Measurement(iterations = 2)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Warmup(iterations = 1)
//    @Fork(1)
    
    public void run() {
        
//        //tweaks for benchmarking
//        Random rand = new Random(); 
//        //LinkedBlockingQueue<Data> list1 = new LinkedBlockingQueue<>();
//        LinkedBlockingQueue<Data> list2 = new LinkedBlockingQueue<>();
//        ArrayBlockingQueue<Data> list1 = new ArrayBlockingQueue<>(100);
//        Plane plane = new Plane(new TakeOff(), list1, list2);
        
        int change = rand.nextInt(100);   
        
        if (rand.nextBoolean()){            
            change*= -1;
        }
        
        plane.altitude = plane.altitude + change;
        
        System.out.println("Current Altitude = " + plane.altitude + " metre(s)");
        
        try {
            plane.list1.put(new Data("altitude", plane.altitude));
            
        } catch (Exception e) {
            
        }
    }
}

class AltitudeLanding implements Runnable {
    
    Random rand = new Random();    
    Plane plane;
    
    public AltitudeLanding(Plane plane) {
        this.plane = plane;
    }
      
    public void run() {                
        int decrease = rand.nextInt(1000);   
        
        int temp = plane.altitude;
        
        temp = temp - decrease;
        
        if(temp < 0) {
            plane.altitude = 0;
            System.out.println("Plane is landing.....");
        } else {
            plane.altitude = temp;
        }
        
        System.out.println("Current Altitude = " + plane.altitude + " metre(s)");
        
        try {
            plane.list1.put(new Data("landingaltitude", plane.altitude));
            
        } catch (Exception e) {
            
        }
        
    }
}

class AltitudeTakeOff implements Runnable {
    
    Random rand = new Random();    
    Plane plane;
    
    public AltitudeTakeOff(Plane plane) {
        this.plane = plane;
    }
      
    public void run() {                
        int increase = rand.nextInt(1000);   
        
        int temp = plane.altitude;
        
        temp = temp + increase;
        
        if(temp > 10000) {
            plane.altitude = 10000;
        } else {
            plane.altitude = temp;
        }
        
        System.out.println("Current Altitude = " + plane.altitude + " metre(s)");
        
        try {
            plane.list1.put(new Data("takeoffaltitude", plane.altitude));
            
        } catch (Exception e) {
            
        }
        
    }
}