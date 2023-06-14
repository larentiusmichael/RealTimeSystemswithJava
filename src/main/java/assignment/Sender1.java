/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 * @author LAURENTIUS MICHAEL
 */
public class Sender1 implements Runnable {
    
    Plane plane;
    
    public Sender1(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
            
        plane.list1.parallelStream().forEachOrdered(element -> {
            try {
                action(element);
                plane.list1.take();
                
            } catch (IOException | TimeoutException ex) {
            } catch (InterruptedException ex) {
            }
            
        });
    }
    
    public static void action(Data i) throws IOException, TimeoutException{
        
        String exchangeName = "planeInput1" ;
        
        ConnectionFactory fa = new ConnectionFactory();
        try(Connection con = fa.newConnection()){
            Channel ch = con.createChannel();
            ch.exchangeDeclare(exchangeName, "direct");
            ch.basicPublish(exchangeName, i.key,  false, null, String.valueOf(i.data).getBytes());
        }
        
    }
    
}

