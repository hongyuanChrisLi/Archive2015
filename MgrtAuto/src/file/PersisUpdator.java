package file;


import infa.ObjContainer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;


public class PersisUpdator {
	
	private final static String work_dir = "./tmp/";
	private final static String PERSIS_ADD = "ADD";
	private final static String PERSIS_FILTER = "FILTER";
	private final static String PERSIS_LABEL = "LABEL";
	
	
	private final String persis_file;
	private String dpdt_file;
	private String upd_type;
	private  Hashtable<String, Boolean> sel_obj_ht;
	private ArrayList<String> sel_str_lst = new  ArrayList<String> ();
	
	private String tmp_file;
	private boolean is_append;
	private String src_file;
	
	
	
	public PersisUpdator( String persis_file, String upd_type, Hashtable<String, Boolean> sel_obj_ht) {
		this.persis_file = persis_file;
		this.upd_type = upd_type;
		this.sel_obj_ht = sel_obj_ht;

	}
	
	public void SetDpdtFile (String dpdt_file) {
		this.dpdt_file = dpdt_file;
	}
	
	public void UpdateFile () throws IOException, InterruptedException {
		
		LoadParms();
		
		File origin_persis = new File (work_dir  + this.persis_file);
		File upd_persis = new File (work_dir  + this.tmp_file);
		
		
		if (upd_type.equals(PERSIS_ADD)) {
			Files.copy(origin_persis.toPath(), upd_persis.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		
		FileReader file_reader = new FileReader(work_dir  + src_file);
		FileWriter file_writer = new FileWriter(work_dir + tmp_file, is_append);
		
		BufferedReader buff_reader = new BufferedReader(file_reader);
		BufferedWriter buff_writer = new BufferedWriter(file_writer);
		String line;
		
		while ( (line = buff_reader.readLine()) != null) {
			ObjContainer oc = new ObjContainer (line);
			String basic_obj = GetBasicObj(oc);
			
			if ( ! sel_obj_ht.containsKey(basic_obj) ) {
                //System.out.println("Pass: " + basic_obj);
				continue;
			}
			sel_obj_ht.put(basic_obj, true);
			buff_writer.write(line + "\n");
			sel_str_lst.add(basic_obj);
		}
		
		buff_reader.close();
		buff_writer.close();
		
		if (upd_type.equals(PERSIS_FILTER)) {
			
			
			boolean is_err_lst = false;
			Set<String> keys = sel_obj_ht.keySet();
			for (String key: keys) {
				
				if ( ! sel_obj_ht.get(key) ) {
					System.out.println ( "Object " +  key + " not found in deployment group" );
					is_err_lst = true;
				}
			}
			
			if (is_err_lst) {
				throw new InterruptedException("Some objects are not found in deployment group! ");
			}
		}
		
		
		
		origin_persis.delete();
		upd_persis.renameTo(origin_persis);
	}
	
	public ArrayList<String> GetSelStrList () {
		return sel_str_lst;
	}
	
	private void LoadParms () {
		
		
		switch (upd_type) {
		case PERSIS_ADD : 
			tmp_file = "persis_add.lst";
			is_append = true;
			src_file = dpdt_file;
			break;
		case PERSIS_FILTER : 
			tmp_file = "persis_filter.lst";
			is_append = false;
			src_file = persis_file;
			break;
		case PERSIS_LABEL : 
			tmp_file = "persis_label.lst";
			is_append = false;
			src_file = persis_file;
			break;
		default: break;
		}
		
	} 
	
	
	private String GetBasicObj (ObjContainer oc) {
		
		String basic_obj = "";
		String folder = oc.getValue("folder");
		String type = oc.getValue("type");
		String object = oc.getValue("object");
		
		if ( type.equals("source")){
			String[] obj_parts = object.split("\\.");
			object = obj_parts[1];
		}else{
			
		}
		
		switch (upd_type) {
		case PERSIS_LABEL :
			basic_obj = folder + "," + object;
			break;
		case PERSIS_ADD : 
			basic_obj = folder + "," + object + "," + type;
			break;
			
		case PERSIS_FILTER : 
			basic_obj = object + "," + folder;
			break;
		default: break;
		}
		
		return basic_obj;
	}
}




