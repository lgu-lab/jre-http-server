package org.lgulab.tiny.http.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class RequestUtil {

	private final static int BUFFER_SIZE = 1024;
	
	static public final String getRequestBodyAsString(HttpExchange exchange) throws IOException
	{
		return getRequestBodyAsString(exchange.getRequestBody());
	}
	
	static public final String getRequestBodyAsString(InputStream is) throws IOException
	{
		StringBuffer sb = new StringBuffer(BUFFER_SIZE * 4 ); // Destination
		
		InputStreamReader isr = new InputStreamReader(is);
		//BufferedReader bufferedReader = new BufferedReader(isr);
		char buffer[] = new char[BUFFER_SIZE];
		int len = 0;
		//while ( (len = bufferedReader.read(buffer)) > 0 )
		while ( (len = isr.read(buffer)) > 0 )
		{
			sb.append(buffer, 0, len); // Add to destination
		}
		//bufferedReader.close();
		isr.close();
		
		return sb.toString();
	}

	static byte[] getRequestBodyAsBytes(HttpExchange exchange) throws IOException
	{
		return getRequestBodyAsBytes(exchange.getRequestBody());
	}
	
	static byte[] getRequestBodyAsBytes(InputStream is) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream( BUFFER_SIZE * 4 ); // Destination
		
		// BufferedInputStream bis = new BufferedInputStream(is);
		byte buffer[] = new byte[BUFFER_SIZE];
		int len = 0;
		//while ((len = bis.read(buffer)) > 0)
		while ( (len = is.read(buffer)) > 0 )
		{
			baos.write(buffer, 0, len); // Add to destination
		}
		// bis.close();
		
		return baos.toByteArray() ;
		//return baos.toString();
	}

}
