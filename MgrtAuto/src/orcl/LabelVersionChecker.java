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
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

import file.GenericReader;
import infa.ObjContainer;
import orcl.OracleJDBC;

public class LabelVersionChecker {
	
	private String crq;
	private Statement stmt;
	private Path icli_sql_file = Paths.get("./tmplt/sql/icil.sql");
	private ArrayList<String> obj_lst = new  ArrayList<String>();
	private ArrayList<String> warn_lst = new ArrayList<String>();
	private Hashtable<String, String> warn_hash = new Hashtable<String, String> ();
	
	public LabelVersionChecker (String in_crq, Statement stmt, ArrayList<String> in_lst) {
		
		this.crq = in_crq;
		this.stmt = stmt;
		this.obj_lst = in_lst;
	}
	
	//public boolean CheckLabelVersion
	
	public ArrayList<String> CheckLabelVersion () throws IOException, SQLException {
		
		//ArrayList<String> res_lst = new ArrayList<String> ();
		
		String sql = this.ReadSql();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()){
			String warn_obj = rs.getString("OBJ");
			String warn_dtl = rs.getString("OBJ") + "," + rs.getString("WARN");
			warn_hash.put(warn_obj, warn_dtl);
		}
		
		
		for ( String mgrt_obj : obj_lst ) {
			
			if ( ! warn_hash.containsKey(mgrt_obj) ) continue;
			warn_lst.add(mgrt_obj + "," + warn_hash.get(mgrt_obj));
		}
		
		//System.out.println(warn_lst);
		
		return warn_lst;
	}
	
	private String ReadSql () throws IOException {
		String sql;
		
		GenericReader gr = new GenericReader(icli_sql_file);
		sql = gr.ReadFile2Str();
		sql = sql.replaceAll("\\$crq", this.crq);
		
		return sql;
	}
}