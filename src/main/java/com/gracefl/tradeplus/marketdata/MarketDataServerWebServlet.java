package com.gracefl.tradeplus.marketdata;

import java.util.Random;

import javax.annotation.PostConstruct;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gracefl.tradeplus.service.RunVolumeScanService;

/**
 * MarketDataServerResource controller
 */

//SEEMS Like this class is no longer called or used - CHECK AND DELETE IF SO


//@WebServlet
//	    (loadOnStartup = 0
	    //asyncSupported = true
//)
@Controller
//@Service
//@Transactional
//public class MarketDataServerWebServlet extends HttpServlet {
public class MarketDataServerWebServlet {

	
	
	
	@Autowired
	private static final ZContext CONTEXT = new ZContext();
	
	private static final long serialVersionUID = 1L;
	private final Logger log = LoggerFactory.getLogger(MarketDataServerWebServlet.class);
	private static Random rand = new Random(System.nanoTime());
	
	//@Autowired
	//RunVolumeScanService runVolumeScanService;
	
	@Autowired
	MarketDataManager marketDataManager;
	
	/* public void init() {
		try {
			log.info("runMarketDataServer");
			runMarketDataServer();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
    /*
     public String runMarketDataServer() {
     
    	log.info("runMarketDataServer");
        runVolumeScanService.runScan();
        return("congrats");
    }
    */

    //private void runMarketDataServer() throws JsonProcessingException {
	@PostConstruct
    public void init() {	
		
    	log.info("Marker data server push request for rates");
    	
		String identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
    	
        // Create and start thread for PULL socket
		//MarketDataManager marketDataManager = new MarketDataManager();
		//marketDataManager.setIdentity(identity);
        
        Thread pullThread = new Thread(marketDataManager);
        pullThread.start();

        // Sleep to make sure PULL socket is bound
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create context and socket for PUSH
        //ZContext ctx = new ZContext();
        try {
        	ZMQ.Socket pushSock = CONTEXT.createSocket(SocketType.PUSH);
	        //ZMQ.Socket pushSock = ctx.createSocket(SocketType.PUSH);
	        pushSock.setIdentity(identity.getBytes(ZMQ.CHARSET));
	        
	        //TODO this should be a configurable properties
	        pushSock.connect("tcp://localhost:32768");
	        //TODO this should be a user entered property
	        pushSock.send("TRACK_RATES;EURUSD;5;GBPUSD;5;GOLD;5;EURUSD;60;GBPUSD;60;GOLD;60".getBytes(), 0);
        } catch (ZMQException e) {
            e.printStackTrace();
        } finally {
        	CONTEXT.close();
        	CONTEXT.destroy();
        }
	}
}
