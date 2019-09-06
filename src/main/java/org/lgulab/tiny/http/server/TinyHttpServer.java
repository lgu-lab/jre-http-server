package org.lgulab.tiny.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class TinyHttpServer {

	private int port = 8080 ;
	
	private String context = "/" ;
	
	private HttpHandler handler = null ;
	
	private HttpServer httpServer = null ;
	
	public TinyHttpServer( int port, String context, HttpHandler handler) {
		super();
		this.port = port;
		this.context = context;
		this.handler = handler;
	}

	public TinyHttpServer( HttpHandler handler) {
		super();
		this.handler = handler;
	}


	public void startHttpServer() throws IOException
	{
		InetSocketAddress ad = new InetSocketAddress(port);
		HttpServer server = HttpServer.create(ad, 0);
		
		server.createContext(context, handler );
		server.setExecutor( Executors.newCachedThreadPool() );
		server.start();
		httpServer = server ;
		System.out.println("HTTP server started : port = " + port +" , context = '" + context + "'");
	}
	
	public void stopHttpServer() throws IOException
	{
		if ( httpServer != null )
		{
			httpServer.stop(0); // Stop immediately ( delay = 0 )  
			httpServer = null ;
		}
	}
	
}
