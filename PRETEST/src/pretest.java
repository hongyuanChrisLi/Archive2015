import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.*;
 
public class pretest {
 
	public static void main(String[] argv) throws IOException {
 
		/*System.out.println("-------- Oracle JDBC Connection Testing ------");
 
		try {
 
			Class.forName("oracle.jdbc.driver.OracleDriver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
 
		}
 
		System.out.println("Oracle JDBC Driver Registered!");
 
		Connection connection = null;
 
		try {
 
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@MBCDWZCD-SCAN:1521/MBCDWZCDDB", "c2lv",
					"5K89!05T27");
 
		} catch (SQLException e) {
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
 
		}
 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}*/

		
		Runtime rt = Runtime.getRuntime();
		String[] commands = {"pmrep connect -r MBCDWRepZDev -h Domain_mbcdwzcdetl01 -o 6001 -n c2lv -x c2lv"};
		Process proc = rt.exec(commands);

		BufferedReader stdInput = new BufferedReader(new 
		     InputStreamReader(proc.getInputStream()));

		BufferedReader stdError = new BufferedReader(new 
		     InputStreamReader(proc.getErrorStream()));

		// read the output from the command
		System.out.println("Here is the standard output of the command:\n");
		String s = null;
		while ((s = stdInput.readLine()) != null) {
		    System.out.println(s);
		}

		// read any errors from the attempted command
		System.out.println("Here is the standard error of the command (if any):\n");
		while ((s = stdError.readLine()) != null) {
		    System.out.println(s);
		}
		
		
		Runtime rt1 = Runtime.getRuntime();
		String[] commands1 = {"pmrep executequery -q CRQ000000077100 -u output_group"};
		Process proc1 = rt1.exec(commands1);

		BufferedReader stdInput1 = new BufferedReader(new 
		     InputStreamReader(proc1.getInputStream()));

		BufferedReader stdError1 = new BufferedReader(new 
		     InputStreamReader(proc1.getErrorStream()));

		// read the output from the command
		System.out.println("Here is the standard output of the command:\n");
		String s1 = null;
		while ((s1 = stdInput1.readLine()) != null) {
		    System.out.println(s1);
		}

		// read any errors from the attempted command
		System.out.println("Here is the standard error of the command (if any):\n");
		while ((s1 = stdError1.readLine()) != null) {
		    System.out.println(s);
		}
		
		
	}
 
}