/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author LAURENTIUS MICHAEL
 */
public class Plane {
    Mode mode;
    LinkedBlockingQueue<Data> list1;
    LinkedBlockingQueue<Data> list2;
    int altitude = 0;
    int wingsFlapAngle = 180;
    int enginePower = 1000;
    int direction = 180;
    int tailFlag = 150;
    boolean oxygenMask = false;
    boolean seatbeltIndicator = false;
    boolean landingGear = false;
    
//    //tweak
//    ArrayBlockingQueue<Data> list1;
    
    public Plane(Mode mode, LinkedBlockingQueue<Data> list1, LinkedBlockingQueue<Data> list2) {
        this.mode = mode;
        this.list1 = list1;
        this.list2 = list2;
    }
    
    public void setMode(Mode mode) {
        this.mode = mode;
    }
    
    public void progress(){
        mode.doProcesses(this);
    }   

}
