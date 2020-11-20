package com.gracefl.tradeplus.marketdata;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefl.tradeplus.helpers.json.InstrumentOHLC;

import java.io.Closeable;
import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarketDataManager implements Runnable, Closeable {
	
	@Autowired
    private ScanDataFiveMinBars scanDataFiveMinBars;
	
	@Autowired
    private ScanDataOneHourBars scanDataOneHourBars;
	
	private static Random rand = new Random(System.nanoTime());
	
	private final static Logger log = LoggerFactory.getLogger(MarketDataManager.class);
	
	@Autowired
	private static final ZContext CONTEXT = new ZContext();
	
	private volatile boolean running = true;
    
    @Override 
    public void run() {
    	
        // Set up context and socket
        String identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
        
        ZMQ.Socket subscriber = CONTEXT.createSocket(SocketType.SUB);
        subscriber.setIdentity(identity.getBytes(ZMQ.CHARSET));
        
        // Synchronize with the publisher
		// Create context and socket for PUSH
        ZMQ.Socket pushSock = CONTEXT.createSocket(SocketType.PUSH);
        
        subscriber.subscribe("".getBytes());
        subscriber.connect("tcp://localhost:32770");
        pushSock.connect("tcp://localhost:32768");
        pushSock.send("TRACK_RATES;EURUSD;5;GBPUSD;5;GOLD;5;EURUSD;60;GBPUSD;60;GOLD;60".getBytes(), 0);
        
		while(running){
        	
            try {
            	String msg = "";
                while (!msg.equalsIgnoreCase("END")) {
                	
                	msg = new String(subscriber.recv(0));
                	
                    ObjectMapper objectMapper = new ObjectMapper();
                    InstrumentOHLC newBar = objectMapper.readValue(msg, InstrumentOHLC.class);
                    
                    log.debug("RECIEVED " + msg);
                    log.debug("newBar " + newBar);
                    log.debug("scanData " + scanDataFiveMinBars);
                    
                    // scan for volume etc
                    // refactor this out into seperate worker class
                    if (newBar.getPeriod().equals("M5")) {
                    	scanDataFiveMinBars.scanBar(newBar);
                    } else if (newBar.getPeriod().equals("H1")) {
                    	scanDataOneHourBars.scanBar(newBar);
                    } else {
                    	log.error("NO SCANNER CONFIGUTED FOR THIS TIMEFRAME");
                    }
                    
                }
            }
            catch(org.zeromq.ZMQException | JsonProcessingException ex){
                break;
            } finally {
            	subscriber.close();
            	CONTEXT.close();
            }
        }

    }

    public void close() throws IOException {
        running = false;
        // Destroy context, which will destroy sockets
        CONTEXT.close();
    }

}