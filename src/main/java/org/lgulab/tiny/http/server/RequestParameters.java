package org.lgulab.tiny.http.server;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Hashtable;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class RequestParameters {

	private final static String UTF8 = "UTF-8";

	private Hashtable<String, String> ht = new Hashtable<>();

	public RequestParameters(HttpExchange exchange) throws IOException {
		parseBody(exchange);
	}

	public String getParameter(String name) {
		return ht.get(name);
	}

	/**
	 * Loads parameters from request body
	 * 
	 * @param exchange
	 * @throws IOException
	 */
	private void parseBody(HttpExchange exchange) throws IOException {
		String sRequestBody = RequestUtil.getRequestBodyAsString(exchange.getRequestBody());

		System.out.println("Request body : ");
		System.out.println(sRequestBody);

		String[] params = sRequestBody.split("&");

		for (int i = 0; i < params.length; i++) {
			String s = params[i];
			System.out.println(" . " + i + " : " + s);

			int p = s.indexOf('=');
			if (p > 0) {
				String name = URLDecoder.decode(s.substring(0, p), UTF8);
				String value = URLDecoder.decode(s.substring(p + 1), UTF8);
				ht.put(name, value);
				System.out.println(" . " + i + " : " + name + " = " + value);
			} else {
				// Error ? ( never happend )
			}
		}
	}

}
