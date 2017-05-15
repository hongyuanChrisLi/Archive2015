package auto;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import infa.ObjContainer;
import infa.PMREPBuilder;
import orcl.LabelReadyReporter;
import orcl.OracleJDBC;
import orcl.FieldsComparator;
import orcl.LabelVersionChecker;
import file.DirManager;
import file.GenericReader;
import file.LogReader;
import file.PersisProcessor;
import file.CtrlTemplateReader;
import file.CtrlFileWriter;
import file.PersisUpdator;
import gui.SpecEditor;
import xml.ISUpdator;



public class MgrtAuto {
	
	private final String src;
	private final String dest; 
	private final String crq;
	private final String requester;
	
	private String low_domain;
	private String low_repo;
	private String low_repo_user;
	private String low_repo_pass;
	
	private String up_domain;
	private String up_repo;
	private String up_repo_user;
	private String up_repo_pass;

	private String cmd = "";
	private PMREPBuilder rep_cmd ;
	private boolean is_group = true;
	private ArrayList<ObjContainer> shared_mgrt_lst;
	private ArrayList<ObjContainer> dm_mgrt_lst;
	private ArrayList<String> mgrt_str_lst;
	private ArrayList<String> mgrt_wf_lst;
	private ArrayList<String> folder_lst;
	private ArrayList<String> shared_folder_lst;
	
	
	//private ArrayList<String> filter_lst;
	private Hashtable<String, Boolean> selected_obj = new Hashtable<String, Boolean> ();
	private Statement src_stmt;
	private Statement dest_stmt;
	
	private final String prefix ;
	private final String persis_file ;
	private final String shared_persis_file;
	private final String dm_persis_file;
	private final String dpdt_file;
	private final String dm_ctrl_file;
	private final String shared_ctrl_file;
	private final String shared_export_xml ;
	private final String dm_export_xml ;
	
	private boolean proc_shared = false;
	private boolean proc_dm = false;

	
	private static final String work_dir = "./tmp/";
	private static final boolean LOW_ENV = true;
	private static final boolean UP_ENV = false;
	private static final String	PERSIS_FILTER = "FILTER";
	private static final String	PERSIS_ADD = "ADD";
	private static final String	PERSIS_LABEL = "LABEL";
	private final static int REGULAR_OPT = 1;
	private final static int DPDT_OPT = 2;
	
	private int exit_val = 0;
	private String seperator = "";
	private boolean is_run = true;
	
	public MgrtAuto (String [] specs) {
		this.src = specs[0];
		this.dest = specs[1];
		this.crq = specs[2];
		this.requester = specs[3];
		
		GetCredentials();
		CleanupTmpDir();
		
		char[] chars_sep = new char[30];
		Arrays.fill(chars_sep, '=');
		seperator = new String(chars_sep);
		
		
		prefix = "RFC" + crq.substring(10,15);
		persis_file = prefix + "_persis.lst";
		shared_persis_file = prefix + "_shared_persis.lst";
		dm_persis_file = prefix + "_dm_persis.lst";
		dpdt_file = prefix + "_dpdt.lst";
		dm_ctrl_file = prefix + "_dm_ctrl";
		shared_ctrl_file = prefix + "_shared_ctrl";
		shared_export_xml = prefix + "_shared.xml";
		dm_export_xml = prefix + "_dm.xml";
		
		
	}
 
	private void GetCredentials () {
		Preferences prefs = Preferences.userNodeForPackage(this.getClass()).parent().node("gui/" + src);
		//System.out.println(prefs.absolutePath());
		low_domain = prefs.get("DOMAIN", "");
		low_repo= prefs.get("REPO", "");
		low_repo_user= prefs.get("REPO_USER", "");
		low_repo_pass= prefs.get("REPO_PASS", "");
		
		
		prefs = Preferences.userNodeForPackage(this.getClass()).parent().node("gui/" + dest);
		
		up_domain = prefs.get("DOMAIN", "");
		up_repo= prefs.get("REPO", "");
		up_repo_user= prefs.get("REPO_USER", "");
		up_repo_pass= prefs.get("REPO_PASS", "");

		
		//System.out.println(src);
		
		//System.out.println(low_domain + ", " + low_repo + ", " + low_repo_user + ", " + low_repo_pass);
		
	}
	
	public void SetIsNotGroup ( ArrayList<String> in_list ){
		is_group = false;
		//filter_lst = in_list;
		
		for(String obj : in_list ) {
			selected_obj.put(obj, false);
		}
	}
	
	public void AutomateMigration () throws IOException, SQLException, InterruptedException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		// *************************** PMREP Command
		
		String[] method_order = {
				"LoadDBConn",
				"ConnectLowRepo",
				"GenerateObjList",
				"FilterObjList",
				"AnalyzeDepends",
				"CompareDefs",
				"LoadMgrtList",
				"CheckILIC",
				"GenerateCtrlFile",
				"ExportXML",
				"CleanupLowRep",
				"UpdateXML",
				"ConnectUpRepo",
				"ImportMaster",
				"CleanupUpRep",
				"ArchiveFiles"
		};
		rep_cmd = new PMREPBuilder (cmd, true);
		
		Class<?> c = Class.forName(this.getClass().getName());
		for (String method_name : method_order ){
				
				if (is_run) {
					Method  method = c.getDeclaredMethod (method_name);
					method.invoke(this);
				}else{
					throw new InterruptedException("Migration has been cancelled !");
				}
				
			}
	}
	
	
	public void CancelProcess ()  {
		is_run = false;
	}
	
	@SuppressWarnings("unused")
	private void ConnectLowRepo () throws IOException, InterruptedException {
		ConnectRepo(LOW_ENV);
	}
	
	@SuppressWarnings("unused")
	private void ConnectUpRepo () throws IOException, InterruptedException {
		ConnectRepo(UP_ENV);
	}
	
	private void ConnectRepo (boolean is_low) throws IOException, InterruptedException {
		/*
		 *	================= 
		 *	Connect to Repo
		 *	================= 
		*/
		String domain;
		String repo;
		String repo_user;
		String repo_pass;
		
		
		if (is_low) {
			PrintSectHeader("Connecting to " + low_repo);
			domain = low_domain;
			repo = low_repo;
			repo_user = low_repo_user;
			repo_pass = low_repo_pass;
		}else
		{
			PrintSectHeader("Connecting to " + up_repo);
			domain = up_domain;
			repo = up_repo;
			repo_user = up_repo_user;
			repo_pass = up_repo_pass;
		}
		
		rep_cmd.UpdPMREP("pmrep connect"
				+ " -r " + repo
				+ " -d " + domain
				+ " -n " + repo_user
				+ " -x " + repo_pass);
		
		exit_val = rep_cmd.RunPMREP();
		
		if ( exit_val == 0 ) {
			System.out.println("Connected\n");
			//PrintEnd();
		} else
		{
			System.out.println("Failed to connect to " + repo);
			String error_message = "Connection Failed";
			throw new InterruptedException(error_message);
		}
	}
	
	@SuppressWarnings("unused")
	private void GenerateObjList () throws IOException, InterruptedException {
		/*
		 *	================= 
		 *	Generate Object list
		 *	================= 
		*/
		PrintSectHeader("Generating Object List from " + crq);
		rep_cmd.UpdPMREP("pmrep executequery" 
				+ " -q " + crq 
				+ " -u " + work_dir + persis_file );
		exit_val = rep_cmd.RunPMREP();
		if ( exit_val == 0 ) {
			PrintEnd();
		} else
		{
			String error_message = "Failed to execute query";
			System.out.println(error_message);
			throw new InterruptedException(error_message);
		}
		
	}
	
	@SuppressWarnings("unused")
	private void FilterObjList () throws IOException, InterruptedException {
		/*
		 *	================= 
		 *	Filter Object list
		 *	================= 
		*/
		PrintSectHeader("Filtering Object List ...");
		if ( ! this.is_group ) {
			PersisUpdator persis_upd = new PersisUpdator (this.persis_file, MgrtAuto.PERSIS_FILTER,this.selected_obj );
			persis_upd.UpdateFile();
			PrintEnd ();
			
		}else {
			System.out.println("Skipped. Due to group migration\n");
		}
		
	} 
	
	@SuppressWarnings("unused")
	private void AnalyzeDepends () throws IOException, InterruptedException {
		/*
		 *	================= 
		 *	Analyze Dependencies
		 *	================= 
		*/
		System.out.println(seperator + "\nAnalysing Dependencies ...\n" + seperator);
		
		rep_cmd.UpdPMREP("pmrep listobjectdependencies"
				+ " -i " + work_dir + persis_file
				+ " -p children"
				+ " -u " + work_dir + dpdt_file);
		exit_val = rep_cmd.RunPMREP();
		
		if ( exit_val == 0 ) {
			PrintEnd();
		} else
		{
			String error_message = "Failed to list dependencies";
			System.out.println(error_message);
			throw new InterruptedException(error_message);
		}
		
		
	}
	
	
	@SuppressWarnings("unused")
	private void CompareDefs () throws IOException, SQLException, InterruptedException {

		/*
		 *	================= 
		 *	Compare Sources and Targets
		 *	================= 
		*/
		PrintSectHeader("Comparing Sources and Targets ...");
		PersisProcessor dpdt_read = new PersisProcessor (dpdt_file, "", "", DPDT_OPT);
		dpdt_read.ProcessPersis();
		ArrayList<ObjContainer> dpdt_lst = dpdt_read.GetAllObjLst();
	
		
		FieldsComparator fc = new FieldsComparator(dpdt_lst, selected_obj, src_stmt, dest_stmt);
		Hashtable<String, Boolean> add_def_objs = fc.CompareFields();
		
		//Set<String> Keys = add_def_objs.keySet();
		if ( ! add_def_objs.isEmpty() ) {
			PersisUpdator persis_upd = new PersisUpdator (this.persis_file, MgrtAuto.PERSIS_ADD,add_def_objs );
			persis_upd.SetDpdtFile(this.dpdt_file);
			persis_upd.UpdateFile();
		}
		folder_lst = dpdt_read.GetFolderList();
		PrintEnd();
	}
	
	
	@SuppressWarnings("unused")
	private void LoadMgrtList () throws IOException {
		PersisProcessor persis_read = new PersisProcessor(persis_file, shared_persis_file, dm_persis_file, REGULAR_OPT);
		persis_read.ProcessPersis();
		mgrt_str_lst = persis_read.GetStrLst();
		mgrt_wf_lst = persis_read.GetWFList();
		shared_mgrt_lst = persis_read.GetSharedObjLst();
		shared_folder_lst = persis_read.GetSharedFolderList();
		dm_mgrt_lst = persis_read.GetDMObjLst();
		
		if (shared_mgrt_lst.size() != 0 ) {
			proc_shared = true;
		}
		
		if (dm_mgrt_lst.size() != 0 ) {
			proc_dm = true;
		}
		
		System.out.println(shared_folder_lst);
		
		//
	}
	
	@SuppressWarnings("unused")
	private void GenerateCtrlFile () throws IOException{
		/*
		 *	================= 
		 *	Generate Control File
		 *	================= 
		*/

		PrintSectHeader("Generating Control File ..."); 
		
		CtrlFileWriter ctrl_file;
		
		if (proc_shared) {
			ctrl_file = new CtrlFileWriter(
					shared_folder_lst, 
					shared_mgrt_lst, 
					requester, 
					shared_ctrl_file,
					src,
					dest);
			ctrl_file.WriteCtrlFile();
			System.out.println("Control file generated for shared objects");
		} else {
			System.out.println("Skipped shared control file. No shared object to process");
		}
		
		if (proc_dm) {
			ctrl_file = new CtrlFileWriter(
					folder_lst, 
					dm_mgrt_lst, 
					requester, 
					dm_ctrl_file,
					src,
					dest);
			ctrl_file.WriteCtrlFile();
			System.out.println("Control file generated for DM objects");
		} else {
			System.out.println("Skipped DM control file. No DM object to process");
		}
		
		PrintEnd();
	}
	
	
	@SuppressWarnings("unused")
	private void CheckILIC () throws IOException, SQLException, InterruptedException {
		/*
		 *	================= 
		 *	Check Version Issues
		 *	================= 
		*/
		PrintSectHeader("Checking ICIL ..."); 
		//System.out.println(mgrt_str_lst);
		LabelVersionChecker lvc = new LabelVersionChecker(crq, src_stmt, mgrt_str_lst);
		ArrayList<String> warn_lst = lvc.CheckLabelVersion();
		
		
		if ( warn_lst.isEmpty() ) {
			System.out.println("No versioning issue found");
			PrintEnd();
		}else{
			String error_message = "Version issue found";
			System.out.println(error_message);
			
			for ( String warn_obj : warn_lst) {
				System.out.println(warn_obj);
			}
			
			throw new InterruptedException(error_message);
		}
	}
	
	
	@SuppressWarnings("unused")
	private void ExportXML () throws IOException, InterruptedException {

		/*
		 *	================= 
		 *	Export Objects to XML
		 *	================= 
		*/
		PrintSectHeader("Exporting Objects ...");
		
		
		if (proc_shared) {
			rep_cmd.UpdPMREP("pmrep objectexport " 
					+ " -i " + work_dir + shared_persis_file 
					+ " -m -s -b -r" 
					+ " -u " + work_dir + shared_export_xml );
			exit_val = rep_cmd.RunPMREP();
			if ( exit_val == 0 ) {
				System.out.println("Shared objects exported");
			} else
			{
				String error_message = "Failed to list dependencies";
				System.out.println(error_message);
				throw new InterruptedException(error_message);
			}
		}else{
			System.out.println("Skipped exporting shared xml. No shared object to process");
		}
		
		
		if (proc_dm) {
			rep_cmd.UpdPMREP("pmrep objectexport " 
					+ " -i " + work_dir + dm_persis_file 
					+ " -m -s -b -r" 
					+ " -u " + work_dir + dm_export_xml );
			exit_val = rep_cmd.RunPMREP();
			if ( exit_val == 0 ) {
				System.out.println("DM objects exported");
			} else
			{
				String error_message = "Failed to list dependencies";
				System.out.println(error_message);
				throw new InterruptedException(error_message);
			}
		}else{
			System.out.println("Skipped exporting DM xml. No DM object to process");
		}
		
		PrintEnd();
		
	}
	
	
	@SuppressWarnings("unused")
	private void UpdateXML () throws SQLException {

		/*
		 *	================= 
		 *	Update XML file
		 *	================= 
		*/
		PrintSectHeader("Updating XML ...");
		ArrayList<String> new_wf_lst = new ArrayList<String>();
		
		
		if ( mgrt_wf_lst.size() != 0 ) {
			new_wf_lst = ListSelWFs();
		}
		
		if ( new_wf_lst.size() == 0 ) {
			System.out.println ("Skipped. No new workflows found\n");
		}else {
		
			ISUpdator isu = new ISUpdator (dm_export_xml, src, dest, new_wf_lst);
			try {
				isu.UpdateIS();
			} catch (XPathExpressionException | DOMException
					| ParserConfigurationException | SAXException
					| TransformerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintEnd();
		}
	}
	
	private ArrayList<String> ListSelWFs () throws SQLException {
		
		ArrayList<String> exist_wf_lst = ListExistWFs();
		
		Collection<String> mgrt_wf_clct =  this.mgrt_wf_lst;
		Collection<String> exist_wf_clct = exist_wf_lst;
		
		mgrt_wf_clct.removeAll(exist_wf_clct);
		
		ArrayList<String> new_wf_lst = new ArrayList<String> ( mgrt_wf_clct);
		
		return new_wf_lst;
	}
	
	private ArrayList<String> ListExistWFs () throws SQLException {
		
		String wf_str = "'";
		int i;
		for (i = 0; i < mgrt_wf_lst.size() - 1 ; ++i ) {
			wf_str +=  mgrt_wf_lst.get(i) + "\',\'";
		}
		wf_str += mgrt_wf_lst.get(i) + "\'";
		
		String sql = "SELECT SUBJECT_AREA||','|| WORKFLOW_NAME AS WF "
				+ "FROM INFOMETA.REP_WORKFLOWS WHERE SUBJECT_AREA||',' || WORKFLOW_NAME IN ("
				+ wf_str + ")";
		
		ResultSet rs = dest_stmt.executeQuery(sql);
		
		ArrayList<String> wf_res_lst = new ArrayList<String> ();
		
		while (rs.next()) {
			wf_res_lst.add(rs.getString("WF"));
		}
		
		return wf_res_lst;
	}
	
	@SuppressWarnings("unused")
	private void LoadDBConn () throws SQLException {
		OracleJDBC src_orcl_jdbc = new OracleJDBC(src);
		Connection src_conn = src_orcl_jdbc.Connect();
		src_stmt = src_conn.createStatement();
			
		OracleJDBC dest_orcl_jdbc = new OracleJDBC(dest);
		Connection dest_conn = dest_orcl_jdbc.Connect();
		dest_stmt = dest_conn.createStatement();
	}
	
	
	@SuppressWarnings("unused")
	private void ImportMaster () throws IOException, InterruptedException, SQLException {
		if ( dest.equals("FSTE") ){
			ImportObjs();
		}else {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC")); 
			long start_ts = cal.getTimeInMillis() / 1000;
			ImportObjs();
			ApplyLabel(start_ts);
		}
	}
	
	private void ImportObjs () throws IOException, InterruptedException {
		/*
		 *	================= 
		 *	Import Objects
		 *	================= 
		*/
		PrintSectHeader("Importing Objects to " + up_repo + " ...");
		String shared_import_log = prefix + "_shared_import.log";
		String dm_import_log = prefix + "_dm_import.log";
		
		if (proc_shared) {
			rep_cmd.UpdPMREP("pmrep objectimport"
					+ " -i " + work_dir + shared_export_xml
					+ " -c " + work_dir + shared_ctrl_file
					+ " -l " + work_dir + shared_import_log);
			exit_val = rep_cmd.RunPMREP();
			if ( exit_val == 0 ) {
				System.out.println("Shared objects imported");
			} else
			{
				String error_message = "Failed to import objects" ;
				System.out.println(error_message);
				throw new InterruptedException(error_message);
			}
			LogReader log_r = new LogReader (shared_import_log) ;
			log_r.ReadLog();
			
		}else{
			System.out.println("No shared object to import");
		}
		
		
		if (proc_dm) {
			rep_cmd.UpdPMREP("pmrep objectimport"
					+ " -i " + work_dir + dm_export_xml
					+ " -c " + work_dir + dm_ctrl_file
					+ " -l " + work_dir + dm_import_log);
			exit_val = rep_cmd.RunPMREP();
			if ( exit_val == 0 ) {
				System.out.println("DM objects imported");
			} else
			{
				String error_message = "Failed to import DM objects" ;
				System.out.println(error_message);
				throw new InterruptedException(error_message);
			}
			LogReader log_r = new LogReader (dm_import_log) ;
			log_r.ReadLog();
		}else{
			System.out.println("No DM object to import");
		}
		PrintEnd();
	}
	
	
	private void ApplyLabel (long start_ts) throws IOException, SQLException, InterruptedException {
		
		PrintSectHeader("Applying Label ... ");
		rep_cmd.UpdPMREP("pmrep createlabel -a " + crq );
		exit_val = rep_cmd.RunPMREP();
		if ( exit_val == 0 ) {
			System.out.println ("Label " + crq + " is created");
			System.out.println ( "!! Deployment group and query need to be created !!" );
		} else{
			System.out.println ("Label " + crq + " exists and is ready for applying");
		}
		
		String label_file = prefix + "_label.lst";
		rep_cmd.UpdPMREP("pmrep executequery -q LatestCheckout -u " + work_dir + label_file );
		exit_val = rep_cmd.RunPMREP();
		if ( exit_val == 0 ) {
			System.out.println ("Query LatestCheckout executed");
		} else {
			String error_message = "Failed to execute query LatestCheckout";
			System.out.println(error_message);
			throw new InterruptedException(error_message);
		}
		
		LabelReadyReporter label_report = new LabelReadyReporter (label_file, dest_stmt, start_ts, up_repo_user);
		Hashtable<String, Boolean> label_ht = label_report.getHash();
		//System.out.println(label_ht.size());
		Set<String> keys = label_ht.keySet();
		for ( String key : keys) {
			System.out.println(key);
		}
		
		PersisUpdator persis_upd = new PersisUpdator (label_file, MgrtAuto.PERSIS_LABEL,label_ht );
		persis_upd.UpdateFile();
		ArrayList<String> label_str_lst = persis_upd.GetSelStrList();
		System.out.println ("Label Persistent File Updated");
		System.out.println (persis_upd.GetSelStrList());
		
		rep_cmd.UpdPMREP("pmrep applylabel -a " + crq + " -i " + work_dir + label_file + " -m"  );
		exit_val = rep_cmd.RunPMREP();
		if ( exit_val == 0 ) {
			System.out.println ("Label " + crq + " applied on: ");
			for (String obj : label_str_lst) {
				System.out.println(obj);
			}
		} else{
			System.out.println ("Label not applied");
		}
		PrintEnd();
	}
	
	
	@SuppressWarnings("unused")
	private void CleanupLowRep () throws IOException {
		rep_cmd.UpdPMREP("pmrep cleanup");
		rep_cmd.RunPMREP();
		PrintSectHeader ("Disconnected from " + low_repo);
	}
	
	@SuppressWarnings("unused")
	private void CleanupUpRep () throws IOException {
		rep_cmd.UpdPMREP("pmrep cleanup");
		rep_cmd.RunPMREP();
		PrintSectHeader ("Disconnected from " + up_repo);
	}
	
	@SuppressWarnings("unused")
	private void ArchiveFiles () throws IOException {
		DirManager dm = new DirManager (prefix);
		dm.MoveFiles();
		dm.RemoveOldDirs();
	}
	
	private void CleanupTmpDir ()  {
		DirManager dm = new DirManager (prefix);
		dm.CleanupTmp();
	}
	
	
	private void PrintSectHeader (String header) {
		
		System.out.println(seperator + "\n" + header + "\n" + seperator);
	}
	
	private void PrintEnd () {
		System.out.println("Completed. \n");
	}
	
	
	
	
}
