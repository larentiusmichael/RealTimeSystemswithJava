/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author LAURENTIUS MICHAEL
 */
public class Actuator {
    Plane plane;
    
    public Actuator(Plane plane) {
        this.plane = plane;
    }
    
    public void start() {
        new Thread(new wingsFlap(plane)).start();
        new Thread(new Engine(plane)).start();
        new Thread(new OxygenMask(plane)).start();
        new Thread(new Altitude(plane)).start();
        new Thread(new landingEnginePower(plane)).start();
        new Thread(new takeoffEnginePower(plane)).start();
        new Thread(new tailFlag(plane)).start();
        new Thread(new enginePower(plane)).start();
    }
}

class wingsFlap implements Runnable {
    
    Plane plane;
    
    public wingsFlap(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput2";
        
        String key = "altitudewingsflap";   //binding key
        
        //1. Create a connection factory
        ConnectionFactory cf = new ConnectionFactory();
        
        try{
            //2. Use CF(Connection Factory) to create a connection
            Connection con = cf.newConnection();
                        
            //3. Use the connection to create channel
            Channel chan = con.createChannel();
                
            //4. Set up a queue/exchange through the channel
            chan.exchangeDeclare(exchangeName,"direct"); //name, type
       
            //5. Get queue name
            String qName = chan.queueDeclare().getQueue();
        
            //6. Bind queue, exchange, any key
            chan.queueBind(qName, exchangeName, key);    //queue, the exchange, the key
            
                
            try {
            // 7. consume or retrieve the message
                chan.basicConsume(qName, true, (x, msg)->{
                    String message = new String(msg.getBody(),"UTF-8");
                    System.out.println("Change Wings Flap Angle: " + message + " degree");
                    processMessage(message);
                }, x->{}); //queue, 2 lambda functions
            } catch (IOException ex) {
                
            }
                
        } catch (IOException ex) {
            
        } catch (TimeoutException ex) {
            
        }
    }
    
    public void processMessage(String changeAngle) {
        
        int changeDegree = Integer.parseInt(changeAngle);
        
        plane.wingsFlapAngle = plane.wingsFlapAngle + changeDegree;
        
        System.out.println("Current Wings Flap Angle: "+ plane.wingsFlapAngle + " degree");
        
        plane.wingsFlapAngle = 180;
    }
}

class Engine implements Runnable {
    
    Plane plane;
    
    public Engine(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput2";
        
        String key = "altitudeenginepower";   //binding key
        
        //1. Create a connection factory
        ConnectionFactory cf = new ConnectionFactory();
        
        try{
            //2. Use CF(Connection Factory) to create a connection
            Connection con = cf.newConnection();
                        
            //3. Use the connection to create channel
            Channel chan = con.createChannel();
                
            //4. Set up a queue/exchange through the channel
            chan.exchangeDeclare(exchangeName,"direct"); //name, type
       
            //5. Get queue name
            String qName = chan.queueDeclare().getQueue();
        
            //6. Bind queue, exchange, any key
            chan.queueBind(qName, exchangeName, key);    //queue, the exchange, the key
            
                
            try {
            // 7. consume or retrieve the message
                chan.basicConsume(qName, true, (x, msg)->{
                    String message = new String(msg.getBody(),"UTF-8");
                    System.out.println("Change Engine Power: " + message + " km/h");
                    processMessage(message);
                }, x->{}); //queue, 2 lambda functions
            } catch (IOException ex) {
                
            }
                
        } catch (IOException ex) {
            
        } catch (TimeoutException ex) {
            
        }
    }
    
    public void processMessage(String changePower) {
        
        int powerChange = Integer.parseInt(changePower);
        
        plane.enginePower = plane.enginePower + powerChange;
        
        System.out.println("Current Engine Power: "+ plane.enginePower + " km/h");
        
        plane.enginePower = 1000;
    }
}

class OxygenMask implements Runnable {
    
    Plane plane;
    
    public OxygenMask(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput2";
        
        String key = "cabinpressureoxymask";   //binding key
        
        //1. Create a connection factory
        ConnectionFactory cf = new ConnectionFactory();
        
        try{
            //2. Use CF(Connection Factory) to create a connection
            Connection con = cf.newConnection();
                        
            //3. Use the connection to create channel
            Channel chan = con.createChannel();
                
            //4. Set up a queue/exchange through the channel
            chan.exchangeDeclare(exchangeName,"direct"); //name, type
       
            //5. Get queue name
            String qName = chan.queueDeclare().getQueue();
        
            //6. Bind queue, exchange, any key
            chan.queueBind(qName, exchangeName, key);    //queue, the exchange, the key
            
                
            try {
            // 7. consume or retrieve the message
                chan.basicConsume(qName, true, (x, msg)->{
                    String message = new String(msg.getBody(),"UTF-8");
                    processMessage(message);
                }, x->{}); //queue, 2 lambda functions
            } catch (IOException ex) {
                
            }
                
        } catch (IOException ex) {
            
        } catch (TimeoutException ex) {
            
        }
    }
    
    public void processMessage(String signal) {
        
        int pressureSignal = Integer.parseInt(signal);
        
        if(pressureSignal == 1) {
            plane.oxygenMask = true;
            System.out.println("Oxygen mask is being dropped down");
        } else if(pressureSignal == 2) {
            System.out.println("Oxygen mask is still being dropped down");
        } else {
            
        }
    }
}

class Altitude implements Runnable {
    
    Plane plane;
    
    public Altitude(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput2";
        
        String key = "cabinpressurealtitude";   //binding key
        
        //1. Create a connection factory
        ConnectionFactory cf = new ConnectionFactory();
        
        try{
            //2. Use CF(Connection Factory) to create a connection
            Connection con = cf.newConnection();
                        
            //3. Use the connection to create channel
            Channel chan = con.createChannel();
                
            //4. Set up a queue/exchange through the channel
            chan.exchangeDeclare(exchangeName,"direct"); //name, type
       
            //5. Get queue name
            String qName = chan.queueDeclare().getQueue();
        
            //6. Bind queue, exchange, any key
            chan.queueBind(qName, exchangeName, key);    //queue, the exchange, the key
            
                
            try {
            // 7. consume or retrieve the message
                chan.basicConsume(qName, true, (x, msg)->{
                    String message = new String(msg.getBody(),"UTF-8");
                    processMessage(message);
                }, x->{}); //queue, 2 lambda functions
            } catch (IOException ex) {
                
            }
                
        } catch (IOException ex) {
            
        } catch (TimeoutException ex) {
            
        }
    }
    
    public void processMessage(String altitude) {
        
        int changeAltitude = Integer.parseInt(altitude);
        
        plane.altitude = plane.altitude + changeAltitude;
        
        if(changeAltitude == 0) {
            plane.altitude = plane.altitude + changeAltitude;
        } else {
            System.out.println("Change Plane Altitude due to low cabin pressure: " + changeAltitude + " metre(s)");
            plane.altitude = plane.altitude + changeAltitude;
            System.out.println("Current Plane Altitude to tackle low cabin pressure: " + plane.altitude + " metre(s)");
        }
    }
}

class tailFlag implements Runnable {
    
    Plane plane;
    
    public tailFlag(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput2";
        
        String key = "directiontailflag";   //binding key
        
        //1. Create a connection factory
        ConnectionFactory cf = new ConnectionFactory();
        
        try{
            //2. Use CF(Connection Factory) to create a connection
            Connection con = cf.newConnection();
                        
            //3. Use the connection to create channel
            Channel chan = con.createChannel();
                
            //4. Set up a queue/exchange through the channel
            chan.exchangeDeclare(exchangeName,"direct"); //name, type
       
            //5. Get queue name
            String qName = chan.queueDeclare().getQueue();
        
            //6. Bind queue, exchange, any key
            chan.queueBind(qName, exchangeName, key);    //queue, the exchange, the key
            
                
            try {
            // 7. consume or retrieve the message
                chan.basicConsume(qName, true, (x, msg)->{
                    String message = new String(msg.getBody(),"UTF-8");
                    System.out.println("Extend Tail Flag: " + message + " centimetre(s)");
                    processMessage(message);
                }, x->{}); //queue, 2 lambda functions
            } catch (IOException ex) {
                
            }
                
        } catch (IOException ex) {
            
        } catch (TimeoutException ex) {
            
        }
    }
    
    public void processMessage(String extension) {
        
        int tailExtension = Integer.parseInt(extension);
        
        plane.tailFlag = plane.tailFlag + tailExtension;
        
        System.out.println("Current Tail Flag Length: "+ plane.tailFlag + " centimetre(s)");
        
        plane.tailFlag = 150;
    }
}

class enginePower implements Runnable {
    
    Plane plane;
    
    public enginePower(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput2";
        
        String key = "weatherengine";   //binding key
        
        //1. Create a connection factory
        ConnectionFactory cf = new ConnectionFactory();
        
        try{
            //2. Use CF(Connection Factory) to create a connection
            Connection con = cf.newConnection();
                        
            //3. Use the connection to create channel
            Channel chan = con.createChannel();
                
            //4. Set up a queue/exchange through the channel
            chan.exchangeDeclare(exchangeName,"direct"); //name, type
       
            //5. Get queue name
            String qName = chan.queueDeclare().getQueue();
        
            //6. Bind queue, exchange, any key
            chan.queueBind(qName, exchangeName, key);    //queue, the exchange, the key
            
                
            try {
            // 7. consume or retrieve the message
                chan.basicConsume(qName, true, (x, msg)->{
                    String message = new String(msg.getBody(),"UTF-8");
                    System.out.println("Change Engine Power to deal with the weather: " + message + " km/h");
                    processMessage(message);
                }, x->{}); //queue, 2 lambda functions
            } catch (IOException ex) {
                
            }
                
        } catch (IOException ex) {
            
        } catch (TimeoutException ex) {
            
        }
    }
    
    public void processMessage(String speed) {
        
        int power= Integer.parseInt(speed);
        
        plane.enginePower = plane.enginePower + power;
        
        System.out.println("Current Engine Power to deal with the weather: " + plane.enginePower + " km/h");
        
        plane.enginePower = 1000;
    }
}

class landingEnginePower implements Runnable {
    
    Plane plane;
    
    public landingEnginePower(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput2";
        
        String key = "landingspeed";   //binding key
        
        //1. Create a connection factory
        ConnectionFactory cf = new ConnectionFactory();
        
        try{
            //2. Use CF(Connection Factory) to create a connection
            Connection con = cf.newConnection();
                        
            //3. Use the connection to create channel
            Channel chan = con.createChannel();
                
            //4. Set up a queue/exchange through the channel
            chan.exchangeDeclare(exchangeName,"direct"); //name, type
       
            //5. Get queue name
            String qName = chan.queueDeclare().getQueue();
        
            //6. Bind queue, exchange, any key
            chan.queueBind(qName, exchangeName, key);    //queue, the exchange, the key
            
                
            try {
            // 7. consume or retrieve the message
                chan.basicConsume(qName, true, (x, msg)->{
                    String message = new String(msg.getBody(),"UTF-8");
                    processMessage(message);
                }, x->{}); //queue, 2 lambda functions
            } catch (IOException ex) {
                
            }
                
        } catch (IOException ex) {
            
        } catch (TimeoutException ex) {
            
        }
    }
    
    public void processMessage(String speed) {
        
        int currentSpeed = Integer.parseInt(speed);
        
        plane.enginePower = currentSpeed;
        
        System.out.println("Current Engine Power: "+ plane.enginePower + " km/h");
    }
}

class takeoffEnginePower implements Runnable {
    
    Plane plane;
    
    public takeoffEnginePower(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput2";
        
        String key = "takeoffspeed";   //binding key
        
        //1. Create a connection factory
        ConnectionFactory cf = new ConnectionFactory();
        
        try{
            //2. Use CF(Connection Factory) to create a connection
            Connection con = cf.newConnection();
                        
            //3. Use the connection to create channel
            Channel chan = con.createChannel();
                
            //4. Set up a queue/exchange through the channel
            chan.exchangeDeclare(exchangeName,"direct"); //name, type
       
            //5. Get queue name
            String qName = chan.queueDeclare().getQueue();
        
            //6. Bind queue, exchange, any key
            chan.queueBind(qName, exchangeName, key);    //queue, the exchange, the key
            
                
            try {
            // 7. consume or retrieve the message
                chan.basicConsume(qName, true, (x, msg)->{
                    String message = new String(msg.getBody(),"UTF-8");
                    processMessage(message);
                }, x->{}); //queue, 2 lambda functions
            } catch (IOException ex) {
                
            }
                
        } catch (IOException ex) {
            
        } catch (TimeoutException ex) {
            
        }
    }
    
    public void processMessage(String speed) {
        
        int currentSpeed = Integer.parseInt(speed);
        
        plane.enginePower = currentSpeed;
        
        System.out.println("Current Engine Power: "+ plane.enginePower + " km/h");
    }
}

class seatbeltIndicator implements Runnable {
    Phaser phaser;
    Plane plane;
    int indicator;
    
    public seatbeltIndicator(Phaser phaser, Plane plane, int indicator) {
        this.phaser = phaser;
        this.plane = plane;
        this.indicator = indicator;
        phaser.register();
    }
    @Override
    public void run() {
        if(indicator == 1) {
            //---- start of phase 1 ---
            plane.seatbeltIndicator = true;
            System.out.println("Seatbelt indicator has been turned on");
        } else {
            //---- start of phase 1 ---
            plane.seatbeltIndicator = false;
            System.out.println("Seatbelt indicator has been turned off");
        }
        try{
            Thread.sleep(3000);
        }
        catch (Exception e) {}
        phaser.arriveAndDeregister();
        //---- end of phase 1 ---
    }
}

class landingGear implements Runnable {
    Phaser phaser;
    Plane plane;
    int indicator;
    
    public landingGear(Phaser phaser, Plane plane, int indicator) {
        this.phaser = phaser;
        this.plane = plane;
        this.indicator = indicator;
        phaser.register();
    }
    @Override
    public void run() {
        if(indicator == 1) {
            //---- start of phase 1 ---
            phaser.arriveAndAwaitAdvance();
            //---- end of phase 1 ---
            //---- start of phase 2 ---
            plane.landingGear = true;
            System.out.println("Landing gear has been dropped down");
        } else {
            //---- start of phase 1 ---
            phaser.arriveAndAwaitAdvance();
            //---- end of phase 1 ---
            //---- start of phase 2 ---
            plane.landingGear = false;
            System.out.println("Landing gear has been pulled back");
        }
        
        try{
            Thread.sleep(3000);
        }
        catch (Exception e) {}
        phaser.arriveAndDeregister();
        //---- end of phase 2 ---
    }
}

class landingWingsFlap implements Runnable {
    Phaser phaser;
    Plane plane;
    
    public landingWingsFlap(Phaser phaser, Plane plane) {
        this.phaser = phaser;
        this.plane = plane;
        phaser.register();
    }
    @Override
    public void run() {
        //---- start of phase 1 ---
        phaser.arriveAndAwaitAdvance();
        //---- end of phase 1 ---
        //---- start of phase 2 ---
        phaser.arriveAndAwaitAdvance();
        //---- end of phase 2 ---
        //---- start of phase 3 ---
        plane.wingsFlapAngle = plane.wingsFlapAngle + 45;
        System.out.println("Angle of wings flap has been adjusted to " + plane.wingsFlapAngle + " degree");
        try{
            Thread.sleep(3000);
        }
        catch (Exception e) {}
        phaser.arriveAndDeregister();
        //---- end of phase 3 ---
    }
}

class silentSeatbeltIndicator implements Callable<Plane> {
 
    Plane plane;
 
    public silentSeatbeltIndicator(Plane plane) {
        this.plane = plane;
    }
    
    
    @Override
    public Plane call() throws Exception {
        plane.seatbeltIndicator = false;
        System.out.println("Seatbelt indicator has been turned off");
        try{
            Thread.sleep(5000);
        } catch (Exception e) {
        
        }
        
        return plane;
    }
}

class silentEnginePower implements Callable<Plane> {
 
    Plane plane;
 
    public silentEnginePower(Plane plane) {
        this.plane = plane;
    }
    
    
    @Override
    public Plane call() throws Exception {
        plane.enginePower = 0;
        System.out.println("Engine has been turned off");
        try{
            Thread.sleep(5000);
        } catch (Exception e) {
        
        }
        return plane;
    }
}

class silentOxyMask implements Callable<Plane> {
 
    Plane plane;
 
    public silentOxyMask(Plane plane) {
        this.plane = plane;
    }
    
    
    @Override
    public Plane call() throws Exception {
        if(plane.oxygenMask == true) {
            plane.oxygenMask = false;
            System.out.println("Oxygen Mask is being pulled back");
        }
        
        try{
            Thread.sleep(5000);
        } catch (Exception e) {
        
        }
        
        return plane;
    }
}

class silentWingsFlap implements Callable<Plane> {
 
    Plane plane;
 
    public silentWingsFlap(Plane plane) {
        this.plane = plane;
    }
    
    
    @Override
    public Plane call() throws Exception {
        plane.wingsFlapAngle = 180;
        System.out.println("Wings Flap Angle has been adjusted to normal position");
        try{
            Thread.sleep(5000);
        } catch (Exception e) {
        
        }
        return plane;
    }
}