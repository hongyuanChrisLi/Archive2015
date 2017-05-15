package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenericReader{
	
	private Path file_path = Paths.get(".");
	private String file_name;
	
	public GenericReader(Path in_file){
		file_path = in_file;
	}
	
	public String ReadFile2Str() throws IOException{
		
		FileReader fr = new FileReader(file_path.toString());
		BufferedReader bw = new BufferedReader(fr);
		String readBuff = bw.readLine();
		String file_str = "";
		
		//System.out.println("Start to read");
		
		while (readBuff != null)
		{
			file_str += readBuff + "\n";
			readBuff = bw.readLine();
		}
		bw.close();
		fr.close();
	
		return file_str;
	}
}




