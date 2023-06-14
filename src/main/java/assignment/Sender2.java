/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author LAURENTIUS MICHAEL
 */
public class Sender2 implements Runnable {
    Plane plane;
    
    public Sender2(Plane plane) {
        this.plane = plane;
    }
    
    public void run() {
            
        plane.list2.parallelStream().forEachOrdered(element -> {
            try {
                action(element);
                plane.list2.take();
            } catch (IOException | TimeoutException ex) {
            } catch (InterruptedException ex) {
            }
        });
    }
    
    public static void action(Data i) throws IOException, TimeoutException{
        
        String exchangeName = "planeInput2" ;
        
        ConnectionFactory fa = new ConnectionFactory();
        try(Connection con = fa.newConnection()){
            Channel ch = con.createChannel();
            ch.exchangeDeclare(exchangeName, "direct");
            ch.basicPublish(exchangeName, i.key, false, null, String.valueOf(i.data).getBytes());
        }
        
    }
}
