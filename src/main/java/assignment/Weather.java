/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

/**
 *
 * @author LAURENTIUS MICHAEL
 */
public class Weather implements Runnable {
    Random rand = new Random();    
    Plane plane;
    
    public Weather(Plane plane) {
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
//        LinkedBlockingQueue<Data> list1 = new LinkedBlockingQueue<>();
//        LinkedBlockingQueue<Data> list2 = new LinkedBlockingQueue<>();
//        //ArrayBlockingQueue<Data> list1 = new ArrayBlockingQueue<>(100);
//        Plane plane = new Plane(new TakeOff(), list1, list2);
        
        int code = rand.nextInt(7);
        
        if(code == 0) {
            System.out.println("Weather Now: Heavy Rain and Thunderstorm");
        } else if(code == 1) {
            System.out.println("Weather Now: Heavy Rain/Snow");
        } else if(code == 2) {
            System.out.println("Weather Now: Light Rain/Snow");
        } else if(code == 3) {
            System.out.println("Weather Now: Windy");
        } else {
            System.out.println("Weather Now: Clear");
        }
        
//        //tweaks
//        switch (code) {
//            case 0:
//                System.out.println("Weather Now: Heavy Rain and Thunderstorm");
//                break;
//            case 1:
//                System.out.println("Weather Now: Heavy Rain/Snow");
//                break;
//            case 2:
//                System.out.println("Weather Now: Light Rain/Snow");
//                break;
//            case 3:
//                System.out.println("Weather Now: Windy");
//                break;
//            default:
//                System.out.println("Weather Now: Clear");
//                break;
//        }
        
        try {
            plane.list1.put(new Data("weather", code));
            
        } catch (Exception e) {
            
        }
    }
}
