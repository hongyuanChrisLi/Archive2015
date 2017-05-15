package server;

import java.net.*;
import java.io.*;
import java.util.*;


public class SingleSocketServer 
{
	static ServerSocket socket1;
	protected final static int port = 19999;
	static Socket conn;
	
	static boolean first;
	static StringBuffer process;
	static String timestamp;
	
	
	public static void main ( String[] args) throws IOException
	{
		try 
		{
			socket1 = new ServerSocket(port);
			System.out.println("Single Socket Server Initialized");
			
			int curchar = 0;
			
			while (true) 
			{
				conn = socket1.accept();
				
				BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
				InputStreamReader isr = new InputStreamReader(bis);
				process = new StringBuffer();
				
				while ( curchar != 13 )
				{
					process.append((char) curchar);
					curchar = isr.read();
				}
				
				System.out.println(process);
				
				timestamp = new java.util.Date().toString();
				String return_code = "Single Socket Server responded at " + timestamp + (char) 13;
				BufferedOutputStream bos = new BufferedOutputStream(conn.getOutputStream());
				OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
				osw.write(return_code);
				osw.flush();
			}
		}
		catch (IOException e) {}
		
		conn.close();
	}
}