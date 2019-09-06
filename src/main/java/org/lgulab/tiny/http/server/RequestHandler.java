package org.lgulab.tiny.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

import org.lgulab.tiny.http.server.request.HelloRequest;
import org.lgulab.tiny.http.server.request.StaticFileRequest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class RequestHandler implements HttpHandler {
	private String docRoot = "";

	private StaticFileRequest staticFileRequest = new StaticFileRequest();

	private HelloRequest helloRequest = new HelloRequest();

	public RequestHandler(String docRoot) {
		super();
		this.docRoot = docRoot;
	}

	public void handle(HttpExchange exchange) throws IOException {
		String sProtocol = exchange.getProtocol();
		String sReqMethod = exchange.getRequestMethod();
		URI uri = exchange.getRequestURI();

		System.out.println("-----");
		System.out.println("URI           : " + uri);
		System.out.println("Protocol      : " + sProtocol);
		System.out.println("RequestMethod : " + sReqMethod);

		InetSocketAddress remoteAddress = exchange.getRemoteAddress();
		System.out.println("RemoteAddress : " + remoteAddress);

		String sURI = uri.toString();
		if (sURI.startsWith("/hello/fr")) {
			helloRequest.process(exchange, docRoot, sURI);
		} else if (sURI.startsWith("/hello/en")) {
			helloRequest.process(exchange, docRoot, sURI);
		} else {
			staticFileRequest.process(exchange, docRoot, sURI);
		}
	}
}
