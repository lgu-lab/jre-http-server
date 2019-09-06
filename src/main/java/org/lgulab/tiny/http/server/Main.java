package org.lgulab.tiny.http.server;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		TinyHttpServer server = new TinyHttpServer( new RequestHandler("D:TMP") );
		server.startHttpServer();
	}

}
