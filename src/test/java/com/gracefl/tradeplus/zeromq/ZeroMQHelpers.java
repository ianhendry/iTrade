package com.gracefl.tradeplus.zeromq;

import org.zeromq.ZMQ;

public class ZeroMQHelpers {

	public void destroySocket(ZMQ.Socket s) {
	    if (s == null)
	        return;
/*
	    if (sockets.contains(s)) {
	        try {
	            s.setLinger(linger);
	        } catch (ZMQException e) {
	            if (e.getErrorCode() != ZMQ.ETERM()) {
	                throw e;
	            }
	        }
	        s.close();
	        sockets.remove(s);
	    }
*/
	}

}
