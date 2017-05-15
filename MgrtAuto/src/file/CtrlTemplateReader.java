package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CtrlTemplateReader
{
	private Path template_path ;
	private String section_name;
	
	public CtrlTemplateReader (Path in_tmplt_path, String in_section)
	{
		
		template_path = in_tmplt_path;
		section_name = in_section;
	}
	
	public String ReadTemplate() throws IOException
	{
		FileReader fr = new FileReader(template_path.toString());
		BufferedReader bw = new BufferedReader(fr);
		String readBuff = bw.readLine();
		String section_ind =  "";
		String section_content = "";
		
		//System.out.println("Start to read");
		
		while (readBuff != null)
		{
			if ( section_ind.equals("{#" + section_name + "}") && ! readBuff.equals("{#}")){
				if ( ! section_content.equals("")){
					section_content +=  "\n" ;
				}
				section_content +=  readBuff;
			} else if (readBuff.equals("{#" + section_name + "}")){
				section_ind = readBuff;
			} else if (readBuff.equals("{#}") &&  ! section_content.equals("")){
				break;
			}
			readBuff = bw.readLine();
			//System.out.println("Finish a line: " + readBuff);
			//System.out.println(section_ind);
		}
		bw.close();
		fr.close();
		//System.out.println(section_content);
		return section_content;
		
	}
	
	public void UpdateSection(String in_sect) {
		section_name = in_sect;
	}
	
}