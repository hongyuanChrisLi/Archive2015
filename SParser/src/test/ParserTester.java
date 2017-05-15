package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;


public class ParserTester {
	
	public ParserTester () throws IOException, JSQLParserException {
		
		FileReader fr = new FileReader("./tmp/");
		BufferedReader bw = new BufferedReader(fr);
		String readBuff = bw.readLine();
		String sql = "";
		
		while (readBuff != null) {
			sql = readBuff + "\n";
			readBuff = bw.readLine();
		}
		bw.close();
		fr.close();
		
		
		Statement stmt = CCJSqlParserUtil.parse(sql);
		Select select_stmt = (Select) stmt;
		TablesNamesFinder table_name_finder = new  TablesNamesFinder();
		List<String> table_lst = table_name_finder.getTableList(select_stmt);
		
		
		for (String tab : table_lst) {
			System.out.println(tab);
		}
	}
	
	public static void main (String[] argv) throws IOException, JSQLParserException {
		new ParserTester();
	}
	
}

