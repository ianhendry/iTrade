package com.gracefl.tradeplus.zeromq;

import java.util.Random;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class MainRunnable {
	
	private static Random rand = new Random(System.nanoTime());
	
    public static void main(String[] args){
    	
    	String identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
    	
        // Create and start thread for PULL socket
        PullRunnable pullRunnable = new PullRunnable();
        pullRunnable.setIdentity(identity);
        
        Thread pullThread = new Thread(pullRunnable);
        pullThread.start();

        // Sleep to make sure PULL socket is bound
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create context and socket for PUSH
        ZContext ctx = new ZContext();
        ZMQ.Socket pushSock = ctx.createSocket(SocketType.PUSH);
        pushSock.setIdentity(identity.getBytes(ZMQ.CHARSET));
        
        pushSock.connect("tcp://localhost:32768");
        pushSock.send("TRACK_RATES;EURUSD;5".getBytes(), 0);
        
        
        while (pullThread.isAlive()) {
        	//wait for ever
        	
        }
    

        // Close runnable and join Pull process
        /*
        try {
            pullRunnable.close();
            pullThread.join();
        }
        catch(Exception e){
            e.printStackTrace();
        } finally {
        	// Destroy socket and context
            ctx.close();
            ctx.destroySocket(pushSock);
            ctx.destroy();

        }
*/
    }
}