package org.lgulab.tiny.http.server.request;

import java.io.IOException;
import java.io.OutputStream;

import org.lgulab.tiny.http.server.RequestParameters;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class HelloRequest {

	public void process(HttpExchange exchange, String docRoot, String uri) throws IOException {
		// TODO :
		// Hashtable params = parse ( sRequestBody ) ;

		if (uri.contains("fr")) {
			hello(exchange, docRoot, "Bonjour");
		} else {
			hello(exchange, docRoot, "Hello");
		}
	}

	public void hello(HttpExchange exchange, String docRoot, String hello) throws IOException {
		RequestParameters params = new RequestParameters(exchange);
		String name = params.getParameter("name");
		// --- Set response headers
		Headers headers = exchange.getResponseHeaders();
		headers.set("Content-Type", "text/html");
		exchange.sendResponseHeaders(200, 0); // arg2 = 0 = Chunked Encoding

		// --- Set response body content
		OutputStream out = exchange.getResponseBody();
		String s = "";

		if ( name != null ) {
			s = "<h1>" + hello + " " + name + "</h1>";
		}
		else {
			s = "<h1>" + hello + "</h1>";
		}
		out.write(s.getBytes());
		out.close();
	}

}
