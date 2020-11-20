package com.gracefl.tradeplus.marketdata;

import java.io.Closeable;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Poller;
import org.zeromq.ZMQ.Socket;

// Receives information from the server
//@Component
public class Worker extends Thread implements Closeable {
	private final static Logger log = LoggerFactory.getLogger(MarketDataManager.class);
	
	private final AtomicBoolean isRunning = new AtomicBoolean(true);
	private final ZContext context;
	private final String endpoint;
	private final byte[] topic;

	private Socket pushSocket;
	private Socket pullSocket;
	private Poller poller;
	private int heartBeats;

	@Autowired
	MarketDataManager marketDataManager;

	public Worker(ZContext context, String endpoint, byte[] topic) {
		this.context = context;
		this.endpoint = endpoint;
		this.topic = topic;
	}

	public void processMessage(Socket socket) {
		this.heartBeats = 0;
		// This shouldn't block.. return immediately
		// String s = socket.recvStr();
		String response = new String(socket.recv(), ZMQ.CHARSET).trim();
		if (response != null) {
			log.info("Response: " + response);
			log.info("marketDataManager: " + marketDataManager);
		}
	}

	public void run() {
		init();
		// Fix slow subscriber
		// Sleep to make sure PULL socket is bound
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (isRunning.get()) {
			this.poller.poll(1000);
			if (poller.pollin(0)) {
				processMessage(pullSocket);
			}

			// else {
			// System.out.println("TICK MISSED");
			// if (++this.heartBeats > 3) {
			// System.out.println("CONNECTION TIMEDOUT");
			// init();
			// }
			// }
		}
		// Good bye
		shutdown();
	}

	private void init() {
		System.out.println("Initializing worker");
		// shutdown();
		this.heartBeats = 0;
		// ZMQ.Socket sock = ctx.createSocket(SocketType.PUSH);
		this.pushSocket = context.createSocket(SocketType.PUSH);
		this.pushSocket.setReceiveTimeOut(0);
		if (this.pushSocket.connect("tcp://localhost:32768")) {
			log.info("SUCCESS - PUSH scoket connected:\t");
		}

		this.pullSocket = context.createSocket(SocketType.PULL);
		this.pullSocket.setRcvHWM(1);
		if (this.pullSocket.connect("tcp://localhost:32769")) {
			log.info("SUCCESS - PULL scoket connected:\t");
		}

		this.poller = context.createPoller(1);
		this.poller.register(this.pullSocket, Poller.POLLIN);

		String message = "HEARTBEAT";
		pushSocket.send("TRACK_RATES;EURUSD;5;GBPUSD;5;GOLD;5;EURUSD;60;GBPUSD;60;GOLD;60".getBytes(), 0);

		/*
		 * if (pushSocket.send(message)) {
		 * System.out.println("SUCCESS - message sent:\t" + message); } else {
		 * System.out.println("FAILED to send message:\t" + message); }
		 */
	}

	private void shutdown() {
		log.info("Destroying worker");
		this.heartBeats = 0;
		if (this.pullSocket != null) {
			this.pullSocket.close();
			this.pullSocket = null;
		}
		if (this.pushSocket != null) {
			this.pushSocket.close();
			this.pushSocket = null;
		}
	}

	@Override
	public void close() {
		this.isRunning.set(false);
		// Utils.sleep(100);
	}
}