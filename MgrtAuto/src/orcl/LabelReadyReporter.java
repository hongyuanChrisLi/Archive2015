package orcl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import file.GenericReader;

public class LabelReadyReporter {
	
	private long start_ts;
	private String user;
	private Statement stmt;
	private Path label_file = Paths.get("./tmp/");
	private Path sql_file = Paths.get("./tmplt/sql/label.sql");
	private Hashtable<String, Boolean> label_ht = new  Hashtable<String, Boolean> ();
	
	public LabelReadyReporter (String label_file, Statement stmt, long start_ts, String user) {
		this.stmt = stmt;
		this.start_ts = start_ts;
		this.user = user;
		this.label_file = Paths.get(this.label_file.toString() + label_file ) ;
	}
	
	public Hashtable<String, Boolean> getHash () throws IOException, SQLException {
		
		GenericReader gr = new GenericReader(sql_file);
		String sql = gr.ReadFile2Str();
		sql = sql.replaceAll("\\$start_ts", String.valueOf(start_ts) );
		sql = sql.replaceAll("\\$user", "\'" + user + "\'");
		
		//System.out.println(sql);
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			label_ht.put(rs.getString("RES"), true);
		}
		
		return label_ht;
	}
	
}



