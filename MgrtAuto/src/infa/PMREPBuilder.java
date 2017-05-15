package infa;

//import java.util.Arrays;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PMREPBuilder {
	
	private String[] cmd_pmrep;
	//private String log;
	private Path log_path = Paths.get("./tmp/log");
	private final static Charset charset = Charset.forName("ISO-8859-1");
	private boolean islog;
	
	public PMREPBuilder (String in_cmd, boolean in_islog) throws IOException
	{
		cmd_pmrep = in_cmd.split(" ");
		islog = in_islog;
		//System.out.printf("String split\n");
		
		File log_file = new File(log_path.toString());
		if ( log_file.exists() ) {
			Files.delete(log_path);
		}
		
		log_file.createNewFile();

	}
	
	public int RunPMREP () throws IOException
	{
		ProcessBuilder probuilder = new ProcessBuilder (cmd_pmrep);
		Process process = probuilder.start();
		
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader (is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		
		if (islog) {
			
			BufferedWriter bw = Files.newBufferedWriter(log_path, charset, StandardOpenOption.APPEND);
			//System.out.printf("Output of running %s is: \n", Arrays.toString(cmd_pmrep));
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				bw.write(line, 0, line.length());
				bw.newLine();
			}
			
			bw.close();
		}else{
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
		
		br.close();
		
		process.destroy();
		int exit_val = process.exitValue();
		
		return exit_val;
	}
	
	public void UpdPMREP (String in_cmd) throws IOException
	{
		cmd_pmrep = in_cmd.split(" ");
	}
	
	/*public void SetLog
	{
		
	}*/
}