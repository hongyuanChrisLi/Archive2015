package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Set;

import infa.ObjContainer;
import custom.CustComparator;



public class PersisProcessor {
	
	private String work_dir = "./tmp/";
	private String file_name;
	private String shared_persis;
	private String dm_persis;
	private final int proc_opt;
	private ArrayList<ObjContainer> oc_lst = new ArrayList<ObjContainer>();
	private ArrayList<ObjContainer> shd_oc_lst = new ArrayList<ObjContainer> ();
	private ArrayList<ObjContainer> dm_oc_lst = new ArrayList<ObjContainer> ();
	private ArrayList<String> str_lst = new ArrayList<String> ();
	private ArrayList<String> wf_lst = new ArrayList<String> ();
	private Hashtable<String, Boolean> folder_ht = new Hashtable<String, Boolean> () ;
	private Hashtable<String, Boolean> shared_folder_ht = new Hashtable<String, Boolean> ();
	
	private final static int REGULAR_OPT = 1;
	private final static int DPDT_OPT = 2;
	
/*	private final static int START_IDX = 10;
	private final static int END_IDX = 14;*/
	
	public PersisProcessor(String in_file, String shared_persis, String dm_persis, int proc_opt)
	{
		file_name = in_file;
		this.shared_persis = shared_persis;
		this.dm_persis = dm_persis;
		this.proc_opt = proc_opt;
	}
	
	public void ProcessPersis () throws IOException
	{
	
		FileReader fileReader = new FileReader(work_dir + file_name);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		FileWriter	shared_file_writer = null;
		BufferedWriter shared_buffered_writer = null;
		FileWriter	dm_file_writer = null;
		BufferedWriter dm_buffered_writer = null;
		
		
		
		if  (proc_opt == REGULAR_OPT) {
			shared_file_writer = new FileWriter (work_dir + shared_persis);
			shared_buffered_writer = new BufferedWriter(shared_file_writer);
			
			dm_file_writer = new FileWriter (work_dir + dm_persis);
			dm_buffered_writer = new BufferedWriter(dm_file_writer);
		}
		
		String line = null;
		
		while ((line = bufferedReader.readLine()) != null) 
		{
			ObjContainer oc = new ObjContainer(line);
			if ( proc_opt == REGULAR_OPT) {
				
				if ( oc.getValue("folder").contains("_Shared_")){
					this.shd_oc_lst.add(oc);
					shared_buffered_writer.write(line + "\n");
				}else{
					this.dm_oc_lst.add(oc);
					dm_buffered_writer.write(line + "\n");
				}
			}

			this.oc_lst.add(oc);
			
		}
		bufferedReader.close();
		Collections.sort(this.oc_lst, new CustComparator());
		
		
		if (proc_opt == REGULAR_OPT) {
			shared_buffered_writer.close();
			dm_buffered_writer.close();
			Collections.sort(this.shd_oc_lst, new CustComparator());
			Collections.sort(this.dm_oc_lst, new CustComparator());
		}
		
		GenerateLists();
		
	}
	
	
	private void GenerateLists () {
		
		
		for (ObjContainer objc : this.oc_lst ) {
			
			
			String folder = objc.getValue("folder");
			String object = objc.getValue("object");
			String type = objc.getValue("type");
			
			String obj_str = folder + "," + object + "," + type;
 			str_lst.add(obj_str);

			folder_ht.put(folder, true);	
		}
		
		
		if (proc_opt == REGULAR_OPT) {
			
			for (ObjContainer objc : this. shd_oc_lst) {
				
				String folder = objc.getValue("folder");
				shared_folder_ht.put(folder, true);
			}
			
			for (ObjContainer objc : this.dm_oc_lst ) {
				
				String folder = objc.getValue("folder");
				String object = objc.getValue("object");
				String type = objc.getValue("type");
				
				if (type.equals("workflow")) {
					wf_lst.add(folder + "," + object);
				}
			}
			
		}
		
	}
	
	
	public ArrayList<ObjContainer> GetSharedObjLst () {
		return shd_oc_lst;
	}
	
	public ArrayList<ObjContainer> GetDMObjLst () {
		return dm_oc_lst;
	}
	
	public ArrayList<ObjContainer> GetAllObjLst () {
		return oc_lst;
	}
	
	public ArrayList<String> GetStrLst () {
		return str_lst;
	}
	
	public ArrayList<String> GetWFList () {
		return wf_lst;
	}
	
	public ArrayList<String> GetFolderList () {
		ArrayList<String> folder_lst = new ArrayList<String>();
		Set<String> keys = folder_ht.keySet();
		
		for (String key : keys) {
			folder_lst.add(key);
		}
		
		return folder_lst;
	}
	
	public ArrayList<String> GetSharedFolderList () {
		ArrayList<String> shared_folder_lst = new ArrayList<String>();
		Set<String> keys = shared_folder_ht.keySet();
		
		for (String key : keys) {
			shared_folder_lst.add(key);
		}
		
		return shared_folder_lst;
	}
 }
