package org.lgulab.tiny.http.server.request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class StaticFileRequest {
	
	private final static int BUFFER_SIZE = 1024;
	
	//private final static String PAGE_404 = "<html><head><title>Not found<title></head><body><h1>404 Not Found</h1></body></html>";

	public void process(HttpExchange exchange, String docRoot, String uri ) throws IOException
	{
		String fullFileName = docRoot + uri ;
		File file = new File(fullFileName);
		if ( file.exists() )
		{
			// TODO : store & get from cache
			
			//byte[] content = loadFileContent( fullFileName );

			//--- Set response headers
			Headers headers = exchange.getResponseHeaders();
			if ( uri.endsWith(".html") || uri.endsWith(".htm") )
			{
				headers.set("Content-Type", "text/html");
			}
			else if ( uri.endsWith(".js") )
			{
				//headers.set("Content-Type", "application/x-javascript");
				headers.set("Content-Type", "text/javascript");
			}
			else if ( uri.endsWith(".css") )
			{
				headers.set("Content-Type", "text/css");
			}
			else if ( uri.endsWith(".png") )
			{
				headers.set("Content-Type", "image/png");
			}
			else if ( uri.endsWith(".gif") )
			{
				headers.set("Content-Type", "image/gif");
			}
			exchange.sendResponseHeaders(200, 0); // arg2 = 0 = Chunked Encoding 

			//--- Set response body content
			OutputStream out = exchange.getResponseBody();
			//out.write(content);
			int iSize = writeFileContent(out, fullFileName );
			out.close();
			System.out.println("writeFileContent(out, '" + fullFileName + "') : size = " + iSize );
		}
		else
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
	}
	
//	private String loadTextFileContent( String filename )
//	{
//		try {
//			StringBuffer sb = new StringBuffer(BUFFER_SIZE * 4 );
//			
//			//FileInputStream fis = new FileInputStream(filename);
//			FileReader fileReader = new FileReader(filename);
//			BufferedReader bufferedReader = new BufferedReader(fileReader);
//			//byte buf[] = new byte[BUFSIZ];
//			char buffer[] = new char[BUFFER_SIZE];
//			int len = 0;
//   
//			//while ((len = fis.read(buf)) > 0)
//			while ((len = bufferedReader.read(buffer)) > 0)
//			{
//			    //out.write(buf, 0, len);
//				sb.append(buffer, 0, len);
//			}
//			bufferedReader.close();
//			fileReader.close();
//
//			return sb.toString() ;
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			return null ;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			return null ;
//		}		
//	}
	
	private int writeFileContent( OutputStream out, String filename )
	{
		int iTotalSize = 0 ;
		FileInputStream fis = null ;
		try {
			fis = new FileInputStream(filename);
			byte buffer[] = new byte[BUFFER_SIZE];
			int len = 0;
			while ( (len = fis.read(buffer)) > 0 )
			{
			    out.write(buffer, 0, len);
			    iTotalSize = iTotalSize + len ;
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			return -1 ;
		} catch (IOException e) {
			//e.printStackTrace();
			return -2 ;
		} finally {
			close(fis);
		}
		return iTotalSize ;
	}
	
	private void close( FileInputStream fis )
	{
		if ( fis != null )
		{
			try {
				fis.close();
			} catch (IOException e) {
				// Nothing to do 
			}
		}
	}

	private void writeNotFoundResponse( OutputStream out, String uri ) throws IOException
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
