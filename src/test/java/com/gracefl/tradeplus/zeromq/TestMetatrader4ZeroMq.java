package com.gracefl.tradeplus.zeromq;

import org.zeromq.SocketType;
import org.zeromq.ZMQ.Socket;

import zmq.Ctx;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**

Hello World client
Connects REQ socket to tcp://localhost:5559
Sends "Hello" to server, expects "World" back

*/

public class TestMetatrader4ZeroMq {

	public static void main(String[] args)
    {
		//ZContext context;
        //ZMQ.Socket requester;
        
        try {
            //  Socket to talk to server
        	System.out.println("launch and connect client.");
        	Boolean connected = false;
        	
        	ZContext context = new ZContext();
            ZMQ.Socket requester = context.createSocket(SocketType.REQ);
            //requester.connect("tcp://localhost:5555");
            if (requester.connect("tcp://localhost:32769")) {
            	connected = true;
            	System.out.println("SUCCESS - REQ scoket connected:\t");
            }
            
            if (connected) {

	            //for (int request_nbr = 0; request_nbr < 3; request_nbr++) {
	            requester.send("HEARTBEAT", 0);
	            String reply = requester.recvStr(0);
	            System.out.println(
	                "Received reply " + " [" + reply + "]"
	            );
	            //}
            }
            
            requester.close();
            context.close();
            
        } catch(Exception e) {
        	System.out.println(e.getLocalizedMessage());
            
        } finally {
        	
        }
    }
}