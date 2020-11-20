package com.gracefl.tradeplus.zeromq;

import java.util.Random;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ReqRecTest {
	
	private static Random rand = new Random(System.nanoTime());
	
	public static void main(String[] args) {
		
		String identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
		
		// TODO Auto-generated method stub
		// Create context and socket for PUSH
        ZContext ctx = new ZContext();
        ZMQ.Socket sock = ctx.createSocket(SocketType.PUSH);
        sock.setHWM(1);
        sock.setIdentity(identity.getBytes(ZMQ.CHARSET));
        if (sock.connect("tcp://localhost:32768")) {
        	System.out.println("SUCCESS - PUSH scoket connected:\t");
        }
        
        ZMQ.Socket pullSock = ctx.createSocket(SocketType.PULL);
        pullSock.setRcvHWM(1);
        if (pullSock.connect("tcp://localhost:32769")) {
        	System.out.println("SUCCESS - PULL scoket connected:\t");
        }
        
        // Sleep to make sure sockets are bound
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        String message = "HEARTBEAT";
        
        if (sock.send(message)) {
        	System.out.println("SUCCESS - message sent:\t" + message);
        } else {
        	System.out.println("FAILED to send message:\t" + message);
        }
        
        while(true){
        	
            try {
        		String response = new String(pullSock.recv(), ZMQ.CHARSET).trim();
                System.out.println("RECIEVED " + response);
            }
            catch(org.zeromq.ZMQException ex){
                break;
            } finally {
            	pullSock.close();
            	sock.close();
            	ctx.close();
            }
        }
        
	}

}
