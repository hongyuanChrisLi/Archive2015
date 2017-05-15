package auto;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.TimeZone;
import java.util.prefs.BackingStoreException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import infa.ObjContainer;
import infa.PMREPBuilder;
import orcl.OracleJDBC;
import orcl.FieldsComparator;
import orcl.LabelVersionChecker;
import file.CtrlTemplateReader;
import file.CtrlFileWriter;
import file.DirManager;
import file.LogReader;
import gui.SpecEditor;
import xml.ISUpdator;



public class MgrtMain {

	
	public static void main (String[] argv) 
			throws IOException, SQLException, 
			BackingStoreException, ParserConfigurationException, 
			SAXException, XPathExpressionException, TransformerException {
		
		/*String[] specs = {"ZCD", "ZC", "CRQ000000074185", "Rama"};
		MgrtAuto migration_auto = new MgrtAuto(specs);
		*/
		new SpecEditor();
	}
 
}