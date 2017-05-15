package orcl;

import infa.ObjContainer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import file.GenericReader;
import orcl.OracleJDBC;



public class FieldsComparator{
	
	private ArrayList<String> src_lst = new ArrayList<String>();
	private ArrayList<String> tgt_lst =  new ArrayList<String>();
	private Path src_sql_file = Paths.get("./tmplt/sql/src_fields.sql");
	private Path tgt_sql_file = Paths.get("./tmplt/sql/tgt_fields.sql");
	private Statement src_stmt;
	private Statement dest_stmt;
	
	private Hashtable<String, Boolean> listed_obj_ht = new Hashtable<String, Boolean> (); 
	
	private final static int SRC_NAME_IDX = 1;
	
	public FieldsComparator (
			ArrayList<ObjContainer> in_lst, 
			Hashtable<String, Boolean> listed_obj_ht, 
			Statement src_stmt, 
			Statement dest_stmt){
		
		
		this.src_stmt = src_stmt;
		this.dest_stmt = dest_stmt;
		this.listed_obj_ht = listed_obj_ht;
		FilterDefs(in_lst);
		
	}
	
	public Hashtable<String, Boolean> CompareFields() throws IOException, SQLException{
		
		String src_sql = this.ReadSql ("SOURCE");
		String tgt_sql = this.ReadSql("TARGET");
		Collection<String> low_src_res = new ArrayList<String>();
		Collection<String> low_tgt_res = new ArrayList<String>();
		Collection<String> upper_src_res = new ArrayList<String>();
		Collection<String> upper_tgt_res = new ArrayList<String>();
		
		Hashtable<String, Boolean> res_objs = new Hashtable<String, Boolean> ();
		
		if ( ! src_lst.isEmpty() ){
			low_src_res = this.GetRes (src_stmt, src_sql);
			upper_src_res = this.GetRes (dest_stmt, src_sql);
			
			Collection<String> low_up = new ArrayList<String>(low_src_res);
			Collection<String> up_low = new ArrayList<String>(upper_src_res);
			Collection<String> lu_combine = new ArrayList<String>();
			
			low_up.removeAll(upper_src_res);
			up_low.removeAll(low_src_res);
			
			lu_combine.addAll(low_up);
			lu_combine.addAll(up_low);
			
			if (  lu_combine.isEmpty() ){
				System.out.println("Source defition compared. No additional sources added");
			}else {
				res_objs.putAll(ExtractBasics(lu_combine, "source"));
			}
		}
		
		if ( ! tgt_lst.isEmpty() ){
			low_tgt_res = this.GetRes (src_stmt, tgt_sql);
			upper_tgt_res = this.GetRes (dest_stmt, tgt_sql);
			
			Collection<String> low_up = new ArrayList<String>(low_tgt_res);
			Collection<String> up_low = new ArrayList<String>(upper_tgt_res);
			Collection<String> lu_combine = new ArrayList<String>();
			
			//printCollection(low_up, "target low_up before");
			low_up.removeAll(upper_tgt_res);
			//System.out.println("Changed ? " + String.valueOf(res));
			//printCollection(low_up , "target low_up after");
			//printCollection(up_low, "target up_low before");
			//printCollection(low_tgt_res, "Low target res");
			 up_low.removeAll(low_tgt_res);
			//System.out.println("Changed ? " + String.valueOf(res));
			//printCollection(up_low, "target up_low after");
			
			lu_combine.addAll(low_up);
			lu_combine.addAll(up_low);
			
			if (  lu_combine.isEmpty() ){
				System.out.println("Target defition compared. No additional targets added");
			}else {
				res_objs.putAll(ExtractBasics(lu_combine, "target"));
			}
			
		}
		
		//ArrayList<String> _tgt_res = new ArrayList<String>();
		return res_objs;
	}
	
	
	private Hashtable<String, Boolean> ExtractBasics (Collection<String> field_res, String type) {
		
		
		Hashtable<String, Boolean> def_objs = new Hashtable<String, Boolean>();
	
		Iterator<String> itr =  field_res.iterator();
		
		while (itr.hasNext()) {
			String[] field_dtls = itr.next().split(",");
			String def_obj_str =  field_dtls[0] + "," + field_dtls[1] + "," + type;
			def_objs.put(def_obj_str, true);
		}
		
		System.out.println("Addintional " + type + " Definitions are found: ");
		
		Set<String> keys = def_objs.keySet();
		
		for(String key : keys) {
			System.out.println(key);
		}
		
		return def_objs;
	}
	
	private ArrayList<String> GetRes ( Statement stmt, String sql) throws SQLException {
		
		ArrayList<String> res_lst = new ArrayList<String> ();
		//System.out.println(sql);
		
		ResultSet rs = stmt.executeQuery(sql);
		//int count = 0;
		while (rs.next()) {
			res_lst.add (rs. getString("RES"));
			//count += 1;
		}
		
		//System.out.println("count: " + count);
		return res_lst;
	}
	
	private String ReadSql (String def_ind) throws IOException {
		
		String sql_str = "";
		String objs_str = "";
		Path sql_file;
		String replace_key = "";
		ArrayList<String> sql_lst = null;
		if (def_ind.equals("SOURCE")) {
			
			sql_lst = this.src_lst;
			sql_file = src_sql_file;
			replace_key = "\\$src_list";
		
		}else if (def_ind.equals("TARGET")){
			
			sql_lst = this.tgt_lst;
			sql_file = tgt_sql_file;
			replace_key = "\\$tgt_list";
			
		}else{
			throw new IllegalArgumentException( "Incorrect Definition Indicator Specified: " + def_ind);
		}
		
			
		for (String ss : sql_lst){
			
			if ( ss == null || ss.equals("")){
				throw new IllegalArgumentException( def_ind + " Object Name Can't be null or empty");
			} else if ( objs_str.length() == 0 ) {
				objs_str = "'" + ss + "'";
			} else {
				objs_str += ",\n\t'" + ss + "'";
			}
			
		}
		
		
		GenericReader gr = new GenericReader(sql_file);
		sql_str = gr.ReadFile2Str();
		sql_str = sql_str.replaceAll(replace_key, objs_str);
		return sql_str;
	}
	
	private void FilterDefs(ArrayList<ObjContainer> in_lst){
		for (int i = 0; i < in_lst.size(); ++i ){
			if ( in_lst.get(i).getValue("type").equals("source" )){
				
				String folder = in_lst.get(i).getValue("folder");
				String src_full_name = in_lst.get(i).getValue("object");
				String[] src_name_set = src_full_name.split("\\.");

				
				if (listed_obj_ht.containsKey(src_name_set[SRC_NAME_IDX] + "," +  folder )) {
					continue;
				}
				
				this.src_lst.add(folder + "." + src_name_set[SRC_NAME_IDX]);
			
			} else if (in_lst.get(i).getValue("type").equals("target")){
				
				String folder = in_lst.get(i).getValue("folder");
				String tgt_name = in_lst.get(i).getValue("object");
				
				if (listed_obj_ht.containsKey( tgt_name + "," + folder )){ 
					continue;
				}
				this.tgt_lst.add(folder + "." + tgt_name);
			}
			
		}
		
		/*System.out.println(src_lst);
		System.out.println(tgt_lst);*/
	}
	
	/*private void printCollection (Collection<String> collect, String info ){
		
		System.out.println("collection: " + info);
		
		for ( String val : collect ) {
			System.out.println(val);
		}
	}*/
}