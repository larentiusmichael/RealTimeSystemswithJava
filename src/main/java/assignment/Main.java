/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author LAURENTIUS MICHAEL
 */

public class Main {
    
    public static void main(String[] args) {
        
        int state = 0;
        boolean status = true;
        
        LinkedBlockingQueue<Data> list1 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<Data> list2 = new LinkedBlockingQueue<>();
        
//        //tweak
//        ArrayBlockingQueue<Data> list1 = new ArrayBlockingQueue<>(100);
        
        Plane plane = new Plane(new TakeOff(), list1, list2);
        
        ScheduledExecutorService manager = Executors.newScheduledThreadPool(1);
        manager.scheduleAtFixedRate(new Sender1(plane), 0, 3000, TimeUnit.MILLISECONDS);
        manager.scheduleAtFixedRate(new Sender2(plane), 0, 3000, TimeUnit.MILLISECONDS);
        
        new ControlSystem(plane).start();
        new Actuator(plane).start();
        
        
        Scanner input = new Scanner(System.in);
        
        while(status == true){
            switch (state) {
                case 0 -> {
                    System.out.println("Click <enter> to proceed to take off mode");
                    state++;
                }
                case 1 -> {
                    System.out.println("Click <enter> to proceed to cruising mode");
                    state++;
                }
                case 2 -> {
                    System.out.println("Click <enter> to proceed to landing mode");
                    state++;
                }
                default -> {
                    System.out.println("Click <enter> to proceed to silent mode");
                    state = 0;
                    //status = false;
                }
            }
            input.nextLine();
            doProcess(plane);
        }
        
    }
    
    public static void doProcess(Plane plane){
        plane.progress();
    }
}


    
