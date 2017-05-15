package file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.prefs.Preferences;

import infa.ObjContainer;


public class CtrlFileWriter
{
	//private String template_name;
	private Path template_path = Paths.get("./tmplt/file", "ctrl_file_template.txt");
	private Path ctrlfile_path = Paths.get("./tmp");
	private ArrayList<String> folder_lst;
	private ArrayList<ObjContainer> mgrt_lst;
	
	private final static Charset charset = Charset.forName("ISO-8859-1");
	
	private String src;
	private String dest;
	private String low_repo;
	private String up_repo;
	private String requester;
	
	public CtrlFileWriter (
			ArrayList<String> folder_lst, 
			ArrayList<ObjContainer> in_lst, 
			String requester, 
			String file_name, 
			String src,
			String dest){
		
		mgrt_lst = in_lst;
		this.folder_lst = folder_lst;
		this.requester = requester;
		this.src = src;
		this.dest = dest;
		
		this.ctrlfile_path = Paths.get( ctrlfile_path.toString() + "/" + file_name );
		
		LoadParms();
	}
	
	private void LoadParms () {
		Preferences prefs = Preferences.userNodeForPackage(this.getClass()).parent().node("gui/" + src);
		low_repo = prefs.get("REPO", "");
		
		prefs = Preferences.userNodeForPackage(this.getClass()).parent().node("gui/" + dest);
		up_repo = prefs.get("REPO", "");
	}
	
	public void WriteCtrlFile() throws IOException{
		
		CtrlTemplateReader src_ctr = new CtrlTemplateReader(template_path, "SOURCE");
		String lvl3_src_tmplt = src_ctr.ReadTemplate();
		
		CtrlTemplateReader ctr = new CtrlTemplateReader(template_path, "SPECIFICOBJECT");
		String lvl3_tmplt = ctr.ReadTemplate();
		String specific_objects = "";
		
		
		for ( int i = 0; i < this.mgrt_lst.size(); i++ ){
			
			String lvl3 = "";
			String ctrl_type = GetType (this.mgrt_lst.get(i).getValue("type"), this.mgrt_lst.get(i).getValue("subtype")) ;
			//this.mgrt_lst.get(i).print();
			
			if (ctrl_type.equals("Source Definition")) {
				lvl3 = lvl3_src_tmplt;
				String[] src_name_set = this.mgrt_lst.get(i).getValue("object").split("\\.");
				String ctrl_dbd_name = src_name_set[0];
				String ctrl_obj_name = src_name_set[1];
				lvl3 = lvl3.replaceAll("\\$dbd", ctrl_dbd_name);
				lvl3 = lvl3.replaceAll("\\$obj_name", ctrl_obj_name);
			} else {
				lvl3 = lvl3_tmplt;
				lvl3 = lvl3.replaceAll("\\$obj_name", this.mgrt_lst.get(i).getValue("object"));
			}
			
			lvl3 = lvl3.replaceAll("\\$obj_type", ctrl_type);
			lvl3 = lvl3.replaceAll("\\$obj_folder", this.mgrt_lst.get(i).getValue("folder"));
			lvl3 = lvl3.replaceAll("\\$obj_repo", low_repo);
			lvl3 = "\n\n\t" + lvl3.replaceAll("\n", "\n\t");
			
			specific_objects +=  lvl3;
			
			//System.out.println(lvl3);
		}
		
		//System.out.println(specific_objects);
		
		ctr.UpdateSection("FOLDERMAP");
		String lvl2_folder_tmplt = ctr.ReadTemplate();
		String map_folders = "";

		
		for (String folder : this.folder_lst ){
			String lvl2_folder = lvl2_folder_tmplt;
			lvl2_folder = lvl2_folder.replaceAll("\\$map_folder", folder);
			lvl2_folder = lvl2_folder.replaceAll("\\$src_repo", low_repo);
			lvl2_folder = lvl2_folder.replaceAll("\\$tgt_repo", up_repo);
			lvl2_folder = "\n\n\t" + lvl2_folder.replaceAll("\n", "\n\t");
			
			map_folders += lvl2_folder;
		}
		
		ctr.UpdateSection("RESOLVECONFLICT");
		String resolve_conflicts = ctr.ReadTemplate();
		resolve_conflicts = resolve_conflicts.replaceAll("\\$Contents_Specific_Objects", specific_objects);
		resolve_conflicts = "\n\t" + resolve_conflicts.replaceAll("\n", "\n\t");
		

		ctr.UpdateSection("IMPORTPARAMS");
		String import_params = ctr.ReadTemplate();

		import_params = import_params.replaceAll("\\$impctrl", "C:/Informatica/9.6.1/clients/PowerCenterClient/client/bin/impcntl.dtd");
		import_params = import_params.replaceAll("\\$checkin_flag", "YES");
		import_params = import_params.replaceAll("\\$checkin_comments", "Per " + requester);
		import_params = import_params.replaceAll("\\$retain_flag", "YES");
		//lvl1 =lvl1.replaceAll(",", "\n");


		import_params = import_params.replaceAll("\\$Contents_Folder_Map", map_folders);
		import_params = import_params.replaceAll("\\$Contents_Resolve_Conflicts", resolve_conflicts);

		BufferedWriter bw = Files.newBufferedWriter(ctrlfile_path, charset, StandardOpenOption.CREATE);
		bw.write(import_params);
		bw.close();
	}
	
	public String GetFilePath () {
		return ctrlfile_path.toString();
	}
	
	private String GetType (String type, String sub_type) {
		
		String ctrl_type = "none";
		
		if (sub_type.equals("none")) {
			
			switch (type) {
			case  "source" : 
				ctrl_type = "Source Definition";
				break;
			case "target" :
				ctrl_type = "Target Definition";
				break;
			default:
				ctrl_type = type.substring(0,1).toUpperCase() + type.substring(1);
				break;
			}
		}else if (type.equals("task")) {
				
			switch (sub_type ) {
			case "event_raise" : 
				ctrl_type = "Event-raise";
				break;
			case "event_wait" : 
				ctrl_type = "Event-wait";
				break;
			default:
				ctrl_type = sub_type.substring(0,1).toUpperCase() + sub_type.substring(1);
				break;	
			}
			
		}else if (type.equals("transformation")){
				
			switch (sub_type ) {
			case "lookup_procedure" :
				ctrl_type = "Lookup Procedure";
				break;
			default:
				ctrl_type = sub_type.substring(0,1).toUpperCase() + sub_type.substring(1);
				break;	
			}
		}
				
		
		return ctrl_type;
		
	}
}


