package com.gracefl.tradeplus.zeromq;

import java.util.Random;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.gracefl.tradeplus.helpers.json.InstrumentOHLC;

public class SubscriberExample {

	private static Random rand = new Random(System.nanoTime());
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
		//ZMQ.Context context = ZMQ.context(1);
		ZContext ctx = new ZContext();
        ZMQ.Socket subscriber = ctx.createSocket(SocketType.SUB);
        subscriber.setIdentity(identity.getBytes(ZMQ.CHARSET));

        // Synchronize with the publisher
		// Create context and socket for PUSH
        ZMQ.Socket pushSock = ctx.createSocket(SocketType.PUSH);
        
        subscriber.subscribe("".getBytes());
        subscriber.connect("tcp://localhost:32770");
        pushSock.connect("tcp://localhost:32768");
        pushSock.send("TRACK_RATES;EURUSD;5".getBytes(), 0);

        try {
            String msg = "";
            while (!msg.equalsIgnoreCase("END")) {
                msg = new String(subscriber.recv(0));
                System.out.println(msg);
                InstrumentOHLC newBar = convertToPojoBarData(msg);
                if (newBar.getTickVolume() > 1100) {
                	java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        catch(org.zeromq.ZMQException ex){
           // break;
        } finally {
			pushSock.close();
		    subscriber.close();
		 	ctx.close();
        }
    	
	}

	private static InstrumentOHLC convertToPojoBarData(String trackRatesResponse) {

		String[] ohlcData = trackRatesResponse.split(";");

		InstrumentOHLC newBar = new InstrumentOHLC(ohlcData[0].substring(0, ohlcData[0].indexOf("_")),
				ohlcData[0].substring(ohlcData[0].indexOf("_") + 1, ohlcData[0].indexOf(" ")),
				new Integer(ohlcData[0].substring(ohlcData[0].indexOf(" ") + 1, ohlcData[0].length())),
				new Double(ohlcData[1]), new Double(ohlcData[2]), new Double(ohlcData[3]), new Double(ohlcData[4]),
				new Integer(ohlcData[5]), 0, 0);

		return newBar;

	}
}
