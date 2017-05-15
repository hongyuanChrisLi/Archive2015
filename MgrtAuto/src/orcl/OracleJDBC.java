package orcl;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
//import java.io.*;
import java.util.prefs.Preferences;

 
public class OracleJDBC {
	
	private String service;
	private String host;
	private String port;
	private String username;
	private String password;
	private Preferences prefs;
	
	public OracleJDBC (String in_env_name)
	{
		this.SetDBInfo(in_env_name);
	}
	
	
	public Connection Connect()
	{
		//System.out.println("-------- Oracle JDBC Connection Testing ------");
 
		try {
 
			Class.forName("oracle.jdbc.driver.OracleDriver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return null;
 
		}
 
		//System.out.println("Oracle JDBC Driver Registered!");
 
		Connection connection = null;
 
		try {
 
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@//" + this.host + ":" + this.port + "/" + this.service, this.username ,
				this.password);
 
		} catch (SQLException e) {
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
 
		}
 
		if (connection != null) {
			System.out.println("Connected to " + this.service);
		} else {
			System.out.println("Failed to make connection!");
		}
		
		return connection;
	}
	
	public boolean TestConnect () {
		@SuppressWarnings("unused")
		Connection conn = null;
		boolean isvalid = true;
		try {
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@//" 
					+ this.host 
					+ ":" + this.port 
					+ "/" + this.service,
					this.username ,
					this.password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			isvalid = false;
		}
		
		return isvalid;
	}
	
	public void UpdateEnv (String env ) {
		this.SetDBInfo(env);
	}
	
	private void SetDBInfo (String env_name) {
		
		prefs = Preferences.userNodeForPackage(this.getClass()).parent().node("gui/" + env_name);
		//System.out.println(prefs.absolutePath());
		host = prefs.get("DB_HOST", "");
		port = prefs.get("PORT", "");
		service = prefs.get("SERVICE", "");
		username = prefs.get("DB_USER", "");
		password = prefs.get("DB_PASS", "");
		
	}
 
}