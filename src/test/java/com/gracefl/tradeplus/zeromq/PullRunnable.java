package com.gracefl.tradeplus.zeromq;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import org.zeromq.ZMQ.Poller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefl.tradeplus.helpers.json.InstrumentOHLC;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;

public class PullRunnable implements Runnable, Closeable {
	
    private volatile boolean running = true;
    private ZContext ctx;
    private String identity;

    private static Random rand = new Random(System.nanoTime());
    
   
    public void run() {
    	
        // Set up context and socket
        String identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
		ZContext ctx = new ZContext();
        ZMQ.Socket subscriber = ctx.createSocket(SocketType.SUB);
        subscriber.setIdentity(identity.getBytes(ZMQ.CHARSET));
        
        // Bind socket
        subscriber.subscribe("".getBytes());
        subscriber.connect("tcp://localhost:32770");

        while(running){
        	
            try {
            	String msg = "";
                while (!msg.equalsIgnoreCase("END")) {
                	
                	// get JSON message and convert to POJO
                    msg = new String(subscriber.recv(0));

                    System.out.println("RECIEVED " + msg);
                    
                    ObjectMapper objectMapper = new ObjectMapper();
                    InstrumentOHLC receivedBar = objectMapper.readValue(msg, InstrumentOHLC.class);
                    
                    System.out.println("------------------------------------------------------\n" + "Bar " 
                            + " added, close price = " + receivedBar.getClosePrice().doubleValue());
                    
                }
            }
            catch(org.zeromq.ZMQException | JsonProcessingException ex){
                break;
            } finally {
            	subscriber.close();
            }
        }

    }

    /*

    this was used during TA4J testing
    Bar newBar = convertToTa4JBar(receivedBar);

    private Bar convertToTa4JBar(InstrumentOHLC receivedBar) {
		// TODO Auto-generated method stub
    	LocalDateTime localTime = LocalDateTime.ofEpochSecond(receivedBar.getBarTime(), 0, ZoneOffset.UTC);
    	//LocalDateTime localTime = LocalDateTime.parse(receivedBar.getBarTime().toString());
    	ZoneId zone = ZoneId.of( "Asia/Shanghai" ) ;
		ZonedDateTime zoneDateTime = localTime.atZone( zone ) ;  
		
		Num openPrice = PrecisionNum.valueOf(receivedBar.getOpenPrice());
        Num highPrice = PrecisionNum.valueOf(receivedBar.getHighPrice());
        Num lowPrice = PrecisionNum.valueOf(receivedBar.getLowPrice());
        Num closePrice = PrecisionNum.valueOf(receivedBar.getClosePrice());
        Num volume = PrecisionNum.valueOf(receivedBar.getTickVolume());
        
    	return new BaseBar(Duration.ofMinutes(5), zoneDateTime, openPrice, highPrice, 
    			lowPrice, closePrice, volume, PrecisionNum.valueOf(1));
	}
*/
    
	public void close() throws IOException {
        running = false;

        // Destroy context, which will destroy sockets
        ctx.destroy();
    }

	public void setIdentity(String identity) {
		this.identity = identity;
	}

}