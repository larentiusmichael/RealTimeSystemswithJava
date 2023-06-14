/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;
/**
 *
 * @author LAURENTIUS MICHAEL
 */
public class ControlSystem {
    
    Plane plane;
    
    public ControlSystem(Plane plane) {
        this.plane = plane;
    }
    
    public void start() {
        new Thread(new wingsFlapLogic(plane)).start();
        new Thread(new engineLogic(plane)).start();
        new Thread(new oxygenMaskLogic(plane)).start();
        new Thread(new altitudeLogic(plane)).start();
        new Thread(new landingSpeedLogic(plane)).start();
        new Thread(new takeoffSpeedLogic(plane)).start();
        new Thread(new tailFlagLogic(plane)).start();
        new Thread(new enginePowerLogic(plane)).start();
    }
 
}

class wingsFlapLogic implements Runnable {
    
    int normalAltitude = 10000;
    int flapAngle = 0;
    
    Plane plane;
    
    public wingsFlapLogic(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput1";
        
        String key = "altitude";   //binding key
        
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
    
    public void processMessage(String currentAltitude) {
        
        int adjustAlt = normalAltitude - Integer.parseInt(currentAltitude);
        
        if(adjustAlt > 0) {
            if (adjustAlt < 100) {
                flapAngle = -10;
            } else if (adjustAlt < 200) {
                flapAngle = -25;
            } else {
                flapAngle = -45;
            }
        } else {
            if (adjustAlt > -100) {
                flapAngle = 10;
            } else if (adjustAlt > -200) {
                flapAngle = 25;
            } else {
                flapAngle = 45;
            }
        }
        
        try {
            plane.list2.put(new Data("altitudewingsflap", flapAngle));
        } catch (Exception e) {
            
        }
    }
}

class engineLogic implements Runnable {
    
    int normalAltitude = 10000;
    int changePower = 0;
    
    Plane plane;
    
    public engineLogic(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput1";
        
        String key = "altitude";   //binding key
        
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
    
    public void processMessage(String currentAltitude) {
        
        int adjustAlt = normalAltitude - Integer.parseInt(currentAltitude);
        
        if(adjustAlt > 0) {
            if (adjustAlt < 100) {
                changePower = -100;
            } else if (adjustAlt < 200) {
                changePower = -200;
            } else {
                changePower = -300;
            }
        } else {
            if (adjustAlt > -100) {
                changePower = 100;
            } else if (adjustAlt > -200) {
                changePower = 200;
            } else {
                changePower = 300;
            }
        }
        
        try {
            plane.list2.put(new Data("altitudeenginepower", changePower));
        } catch (Exception e) {
            
        }
    }
}

class oxygenMaskLogic implements Runnable {
    
    int normalIndicator = 2;
    
    Plane plane;
    
    public oxygenMaskLogic(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput1";
        
        String key = "cabinpressure";   //binding key
        
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
    
    public void processMessage(String indicator) {
        
        int signal = 0;
        int pressureIndicator = Integer.parseInt(indicator);
        
        if(pressureIndicator < normalIndicator) {
            if(plane.oxygenMask == true) {
                signal = 2;
                System.out.println("<ALERT>Sudden loss of cabin pressure is still hapenning!<ALERT>");
            } else {
                signal = 1;
                System.out.println("<ALERT>Sudden loss of cabin pressure is hapenning!<ALERT>");
            }
        }
        
        try {
            plane.list2.put(new Data("cabinpressureoxymask", signal));
        } catch (Exception e) {
            
        }
    }
}

class altitudeLogic implements Runnable {
    
    int normalIndicator = 2;
    int changeAltitude = 0;
    
    Plane plane;
    
    public altitudeLogic(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput1";
        
        String key = "cabinpressure";   //binding key
        
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
    
    public void processMessage(String indicator) {
        
        int pressureIndicator = Integer.parseInt(indicator);
        
        if(pressureIndicator >= normalIndicator) {
            changeAltitude = 0;
        } else if(pressureIndicator == 1) {
            changeAltitude = -100;
        } else {
            changeAltitude = -200;
        }
        
        try {
            plane.list2.put(new Data("cabinpressurealtitude", changeAltitude));
        } catch (Exception e) {
            
        }
    }
}

class tailFlagLogic implements Runnable {
    
    int extension = 0;
    
    Plane plane;
    
    public tailFlagLogic(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput1";
        
        String key = "direction";   //binding key
        
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
    
    public void processMessage(String degree) {
        
        int directionDegree = Integer.parseInt(degree);
        
        if(directionDegree < 90) {
            extension = -100;
        } else if (directionDegree < 180) {
            extension = -50;
        } else if (directionDegree < 270) {
            extension = -50;
        } else {
            extension = -100;
        }
        
        try {
            plane.list2.put(new Data("directiontailflag", extension));
        } catch (Exception e) {
            
        }
    }
}

class enginePowerLogic implements Runnable {
    
    int changeSpeed = 0;
    
    Plane plane;
    
    public enginePowerLogic(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput1";
        
        String key = "weather";   //binding key
        
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
    
    public void processMessage(String code) {
        
        int weather = Integer.parseInt(code);
        
        if(weather == 0) {
            changeSpeed = -100;
        } else if (weather == 1) {
            changeSpeed = -75;
        } else if (weather == 2) {
            changeSpeed = -50;
        } else if (weather == 3) {
            changeSpeed = -25;
        } else {
            changeSpeed = 0;
        }
        
        try {
            plane.list2.put(new Data("weatherengine", changeSpeed));
        } catch (Exception e) {
            
        }
    }
}

class landingSpeedLogic implements Runnable {
    
    int cruisingAltitude = 10000;
    int speed = 0;
    
    Plane plane;
    Random random = new Random();
    
    public landingSpeedLogic(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput1";
        
        String key = "landingaltitude";   //binding key
        
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
        
        int alt = Integer.parseInt(altitude);
        
        if(alt >= cruisingAltitude) {
            speed = plane.enginePower;
        } else if(alt < cruisingAltitude && alt >= 9000) {
            speed = random.nextInt((1000 - 900) + 1) + 900;
        } else if(alt < 9000 && alt >= 8000) {
            speed = random.nextInt((900 - 800) + 1) + 800;
        } else if(alt < 8000 && alt >= 7000) {
            speed = random.nextInt((800 - 700) + 1) + 700;
        } else if(alt < 7000 && alt >= 6000) {
            speed = random.nextInt((700 - 600) + 1) + 600;
        } else if(alt < 6000 && alt >= 5000) {
            speed = random.nextInt((600 - 500) + 1) + 500;
        } else if(alt < 5000 && alt >= 4000) {
            speed = random.nextInt((500 - 400) + 1) + 400;
        } else if(alt < 4000 && alt >= 3000) {
            speed = random.nextInt((400 - 300) + 1) + 300;
        } else if(alt < 3000 && alt >= 2000) {
            speed = random.nextInt((300 - 200) + 1) + 200;
        } else if(alt < 2000 && alt >= 1000) {
            speed = random.nextInt((200 - 100) + 1) + 100;
        } else {
            speed = random.nextInt((100 - 50) + 1) + 50;
        }
        
        try {
            plane.list2.put(new Data("landingspeed", speed));
        } catch (Exception e) {
            
        }
    }
}

class takeoffSpeedLogic implements Runnable {
    
    int cruisingAltitude = 10000;
    int speed = 0;
    
    Plane plane;
    Random random = new Random();
    
    public takeoffSpeedLogic(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
        String exchangeName = "planeInput1";
        
        String key = "takeoffaltitude";   //binding key
        
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
        
        int alt = Integer.parseInt(altitude);
        
        if(alt >= cruisingAltitude) {
            speed = plane.enginePower;
        } else if(alt < cruisingAltitude && alt >= 9000) {
            speed = random.nextInt((1000 - 900) + 1) + 900;
        } else if(alt < 9000 && alt >= 8000) {
            speed = random.nextInt((900 - 800) + 1) + 800;
        } else if(alt < 8000 && alt >= 7000) {
            speed = random.nextInt((800 - 700) + 1) + 700;
        } else if(alt < 7000 && alt >= 6000) {
            speed = random.nextInt((700 - 600) + 1) + 600;
        } else if(alt < 6000 && alt >= 5000) {
            speed = random.nextInt((600 - 500) + 1) + 500;
        } else if(alt < 5000 && alt >= 4000) {
            speed = random.nextInt((500 - 400) + 1) + 400;
        } else if(alt < 4000 && alt >= 3000) {
            speed = random.nextInt((400 - 350) + 1) + 350;
        } else if(alt < 3000 && alt >= 2000) {
            speed = random.nextInt((350 - 300) + 1) + 300;
        } else if(alt < 2000 && alt >= 1000) {
            speed = random.nextInt((300 - 200) + 1) + 200;
        } else {
            speed = random.nextInt((200 - 100) + 1) + 100;
        }
        
        try {
            plane.list2.put(new Data("takeoffspeed", speed));
        } catch (Exception e) {
            
        }
    }
}




