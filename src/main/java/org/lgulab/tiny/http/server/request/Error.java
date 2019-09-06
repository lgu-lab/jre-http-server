package org.lgulab.tiny.http.server.request;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class Error {

	public static void resourceNotFound(HttpExchange exchange, String uri ) throws IOException
	{
		//--- Set response headers
		Headers headers = exchange.getResponseHeaders();
		headers.set("Content-Type", "text/html");
		exchange.sendResponseHeaders(404, 0); // arg2 = 0 = Chunked Encoding 
		//--- Set response body content
		OutputStream out = exchange.getResponseBody();
		//out.write(PAGE_404.getBytes());
		writeNotFoundResponse( out, uri );
		out.close();

	}
	
	private static void writeNotFoundResponse( OutputStream out, String uri ) throws IOException
	{
		out.write("<html>".getBytes());
		
		out.write("<head>".getBytes());
		out.write("<title>Not found<title>".getBytes());
		out.write("</head>".getBytes());
		
		out.write("<body>".getBytes());
		out.write("<h1>404 : Not Found</h1>".getBytes());
		out.write(("<h2>URI : '" + uri + "' </h2>").getBytes());
		out.write("</body>".getBytes());

		out.write("</html>".getBytes());
		
	}

}
