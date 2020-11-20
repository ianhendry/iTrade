package com.gracefl.tradeplus.zeromq;

import java.io.IOError;

import java.util.Random;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import org.zeromq.ZMQ.Poller;
import org.zeromq.ZContext;

public class PushPullTestPyton {
	
	private static Random rand = new Random(System.nanoTime());
	
	    public static void main(String[] args) throws IOError
	    {
	    	//ZMQ.Socket receiver;
	    	
	        try (ZContext context = new ZContext()) {
	        	System.out.println("Starting.....");
	                 //  Socket to receive messages on
	                 ZMQ.Socket receiver = context.createSocket(SocketType.PULL);
	             //  Set random identity to make tracing easier
	                 String identity = String.format(
	                     "%04X-%04X", rand.nextInt(), rand.nextInt()
	                 );
	                 receiver.setIdentity(identity.getBytes(ZMQ.CHARSET));
	                 receiver.connect("tcp://localhost:32768");
	                 //receiver.setReceiveTimeOut(1000);
	                 
	                 Poller poller = context.createPoller(1);
	                 
	                 poller.register(receiver, Poller.POLLIN);
	                 
	                 //receiver.monitor("tcp://localhost:5556", ZMQ.EVENT_ALL);
	                 
	                 //  Socket to send messages to
	                 ZMQ.Socket sender = context.createSocket(SocketType.PUSH);
	                 sender.connect("tcp://localhost:32769");
	                 
	                 sender.send("HEARTBEAT");
	                 /*
	                 if (poller.pollin(0)) {
	                 
                         ZMsg msg = ZMsg.recvMsg(receiver);
                         System.out.print("RECEIVED " + msg.toString());
                         msg.getLast().print(identity);
                         msg.destroy();
                     }
	                 */
	                 
	                 //  Process tasks forever
	                 int requestNbr = 0;
	                 while (!Thread.currentThread().isInterrupted()) {
	                     //  Tick once per second, pulling in arriving messages
	                     for (int centitick = 0; centitick < 100; centitick++) {
	                         poller.poll(10);
	                         if (poller.pollin(0)) {
	                             ZMsg msg = ZMsg.recvMsg(receiver);
	                             msg.getLast().print(identity);
	                             msg.destroy();
	                         }
	                         System.out.print(".");
	                     }
	                     sender.send(String.format("request #%d", ++requestNbr), 0);
	                 }
	            // clean up
	            //poller.unregister(receiver);
	            //poller.close();
	            receiver.setLinger(0); 
	            receiver.close();
	            sender.setLinger(0); 
	            sender.close();
	            context.close();
	            context.destroy();
	            System.out.println("--END--");
	            
	        } catch (IOError e) {
	        	System.out.println("Exception " + e.getMessage());
	        } finally {
	        	
	        }
	    }
	}