package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class LogReader {
	
	private Path dir_path = Paths.get("./tmp");
	private Path log_path ;
	
	public LogReader ( String log_file) {
		log_path = Paths.get(dir_path.toString() + "/" + log_file);
	}
	
	public void SetLogName (String log_file) {
		log_path = Paths.get(dir_path.toString() + "/" + log_file);
	}
	
	public void ReadLog () throws IOException {
		FileReader fr = new FileReader(log_path.toString());
		BufferedReader bw = new BufferedReader(fr);
		String readBuff = bw.readLine();
		boolean error_flag = false;
		
		//System.out.println("Start to read");
		
		
		
		while (readBuff != null)
		{
			
			
			if ( readBuff.contains("<Error>")
				|| readBuff.contains("INVALID")) {
				System.out.println(readBuff);
				error_flag = true;
			}
			
			if ( readBuff.contains("<Warning> :  The Integration Service")) {
				System.out.println(readBuff);
			}
			
			readBuff = bw.readLine();
		}
		bw.close();
		fr.close();
		
		
		if (error_flag) {
			throw new IllegalArgumentException("Migration Failed with Errors");
		}
		
	}
}