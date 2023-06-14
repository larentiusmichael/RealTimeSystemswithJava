/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package assignment;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Phaser;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author LAURENTIUS MICHAEL
 */
public interface Mode {
    public void doProcesses(Plane plane);
    public void changeMode(Plane plane);
}

class Silent implements Mode {
   
    public void doProcesses(Plane plane) {
        System.out.println("===PLANE NOW IN SILENT MODE===\n");
        
        ExecutorService ex = Executors.newCachedThreadPool();
        Future<Plane> status1 = ex.submit(new silentSeatbeltIndicator(plane));
        Future<Plane> status2 = ex.submit(new silentOxyMask(plane));
        Future<Plane> status3 = ex.submit(new silentEnginePower(plane));
        Future<Plane> status4 = ex.submit(new silentWingsFlap(plane));
        
        try{
            while(!status1.isDone() || !status2.isDone() || !status3.isDone() || !status4.isDone()){
                Thread.sleep(1000);
            }
        } catch(Exception e) {
            
        }
        
        ex.shutdown();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex1) {
           
        }
        changeMode(plane);
    }
    
    public void changeMode(Plane plane) {
        plane.setMode(new TakeOff());
    }
}

class TakeOff implements Mode {
    @Override
    public void doProcesses(Plane plane) {
        System.out.println("===PLANE NOW IN TAKE OFF MODE===\n");
        
        int indicator = 1;
        
        System.out.println("Plane ready to take off");
        
        System.out.println("Engine has been turned on");
        Random rand = new Random();
        plane.enginePower = rand.nextInt((200 - 100) + 1) + 100;
        
        Phaser phaser = new Phaser();
        phaser.register();
        new Thread(new seatbeltIndicator(phaser, plane, 1)).start();
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            
        }
        System.out.println("Plane is taking off.....");
        
        ScheduledExecutorService manager = Executors.newScheduledThreadPool(1);
        manager.scheduleAtFixedRate(new AltitudeTakeOff(plane), 0, 3000, TimeUnit.MILLISECONDS);
        
        try {
            while(plane.altitude < 10000) {
                if (plane.altitude > 3000 && indicator == 1) {
                    new Thread(new landingGear(phaser, plane, 0)).start();
                    indicator = 0;
                    phaser.arriveAndDeregister();
                }
                Thread.sleep(1000);
            }
            manager.shutdown();
            Thread.sleep(3000);
        } catch (Exception e) {
            
        }
        
        System.out.println("Plane has taken off successfully");
        changeMode(plane);
    }
    
    public void changeMode(Plane plane) {
        plane.setMode(new Cruising());
    }
}

class Cruising implements Mode {
    @Override
    public void doProcesses(Plane plane) {
        System.out.println("===PLANE NOW IN CRUSING MODE===\n");
        
        Phaser phaser = new Phaser();
        phaser.register();
        new Thread(new seatbeltIndicator(phaser, plane, 0)).start();
        phaser.arriveAndDeregister();
        
//        Random rand = new Random();
//        int duration = rand.nextInt(60000);
        
        //tweak
        int duration = 60000;
        
        ScheduledExecutorService manager = Executors.newScheduledThreadPool(1);
        manager.scheduleAtFixedRate(new AltitudeSensor(plane), 0, 5000, TimeUnit.MILLISECONDS);
        manager.scheduleAtFixedRate(new CabinPressure(plane), 0, 10000, TimeUnit.MILLISECONDS);
        manager.scheduleAtFixedRate(new Direction(plane), 0, 5000, TimeUnit.MILLISECONDS);
        manager.scheduleAtFixedRate(new Weather(plane), 0, 15000, TimeUnit.MILLISECONDS);
        
        try {
            Thread.sleep(duration);
            manager.shutdown();
            Thread.sleep(5000);
        } catch (Exception e) {
            
        }
        
        changeMode(plane);
    }
    
    public void changeMode(Plane plane) {
        plane.setMode(new Landing());
    }
}

class Landing implements Mode {
    @Override
    public void doProcesses(Plane plane) {
        System.out.println("===PLANE NOW IN LANDING MODE===\n");
        
        int indicator = 1;
        
        ScheduledExecutorService manager = Executors.newScheduledThreadPool(1);
        manager.scheduleAtFixedRate(new AltitudeLanding(plane), 0, 3000, TimeUnit.MILLISECONDS);
        
        try {
            while(plane.altitude != 0) {
                if (plane.altitude < 3000 && indicator == 1) {
                    Phaser phaser = new Phaser();
                    phaser.register();
                    new Thread(new seatbeltIndicator(phaser, plane, 1)).start();
                    new Thread(new landingGear(phaser, plane, 1)).start();
                    new Thread(new landingWingsFlap(phaser, plane)).start();
                    phaser.arriveAndDeregister();
                    indicator = 0;
                }
                Thread.sleep(1000);
            }
            manager.shutdown();
            Thread.sleep(3000);
        } catch (Exception e) {
            
        }
        
        System.out.println("Plane landed successfully");
        changeMode(plane);
    }
    
    public void changeMode(Plane plane) {
        plane.setMode(new Silent());
    }
}
