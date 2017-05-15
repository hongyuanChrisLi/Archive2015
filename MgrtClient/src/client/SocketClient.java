package client;


import java.net.*;
import java.io.*;


public class SocketClient 
{
	public static void main (String[] args)
	{
		String host = "mbcdwzcdetl01";
		int port = 19999;
		
		StringBuffer instr = new StringBuffer();
		String timestamp;
		System.out.println("SocketClient initialized");
		
		
		try 
		{
			InetAddress address = InetAddress.getByName(host);
			System.out.println (address.toString());
			Socket conn = new Socket (address, port);
			
			BufferedOutputStream bos = new BufferedOutputStream(conn.getOutputStream());
			
			OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
			
			timestamp = new java.util.Date().toString();
			String process = "Calling the Socket Server on " + host + " port " + port + 
					" at " + timestamp + (char) 13;
			
			osw.write(process);
			osw.flush();
			
		}
		catch (IOException e) {}
	}
}