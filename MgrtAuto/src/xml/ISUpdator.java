package xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;










import orcl.OracleJDBC;

public class ISUpdator {
	
	private Path file_path = Paths.get("./tmp");
	private Path src_file;
	private Path res_file;
	private OracleJDBC orcl_jdbc;
	private Statement stmt;
	private Hashtable<String, Boolean> wf_ht = new Hashtable<String, Boolean> ();
	
	
	private final String src_env;
	private final String tgt_env;
	
	public ISUpdator (String file_name, String src, String tgt, ArrayList<String> wf_lst ) throws SQLException {
		
		src_file = Paths.get(file_path.toString(), file_name);
		//System.out.println (src_file.toString());
		res_file = Paths.get(file_path.toString(), "upd_" + file_name );
		
		
		src_env = src;
		tgt_env = tgt;
		
		for (String wf : wf_lst) {
			this.wf_ht.put(wf, true);
		}
		
		orcl_jdbc = new OracleJDBC("ZCD");
		if (! orcl_jdbc.TestConnect()) {
			orcl_jdbc.UpdateEnv("ZC");
		}
		Connection con = orcl_jdbc.Connect();
		stmt = con.createStatement();
		
	}
	
	public void UpdateIS () throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerException, DOMException, SQLException {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setValidating(false);
		docFactory.setNamespaceAware(true);
		docFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		docFactory.setFeature("http://xml.org/sax/features/validation", false);
		docFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		docFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(this.src_file.toString());
		
		XPath xPath =  XPathFactory.newInstance().newXPath();
		String expression = "/POWERMART/REPOSITORY/FOLDER/WORKFLOW";
		
		NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
		
		
		TransformerFactory trans_fractory = TransformerFactory.newInstance();
		Transformer trans = trans_fractory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(res_file.toString()));
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			   Node nNode = nodeList.item(i);
			   Node folder_node = nNode.getParentNode();
			   NamedNodeMap folder_attr = folder_node.getAttributes();
			   Node folder = folder_attr.getNamedItem("NAME");
			   
			   NamedNodeMap attr = nNode.getAttributes();
			   Node wf = attr.getNamedItem("NAME");
			   Node service = attr.getNamedItem("SERVERNAME");
			   
			   String int_service = GetIntegrationService(folder.getNodeValue(), src_env, tgt_env, service.getNodeValue());
			   
			   if (wf_ht.containsKey(folder.getNodeValue() + "," + wf.getNodeValue())){
				   service.setTextContent(int_service);
				   System.out.println ("Service update: " + wf.getNodeValue() + " --> " + int_service);
				   trans.transform(source, result);
			   }
			   
			}
	
		File origin_xml = new File (src_file.toString());
		File upd_xml = new File (res_file.toString());
		origin_xml.delete();
		upd_xml.renameTo(origin_xml);
		
	}
	
	private String GetIntegrationService(String folder, String src_env, String tgt_env, String src_service) throws SQLException {
		
		String int_service = "";
		String sql = "";
		
		if ( folder.equals("DCR_FIX")){
			sql = "SELECT " + tgt_env + " FROM ETL.INTEGRATION_SERVICES "
				+ "WHERE " + src_env + " = \'" + src_service + "\'";
		}
		else {
			sql = "SELECT I." + tgt_env + " from ETL.INTEGRATION_SERVICES I, ETL.FOLDER_INTEGR_BRIDGE F "
				+ "WHERE F.INTEGR_IND = I.INTEGR_IND "
				+ "AND F.FOLDER = \'" + folder + "\'";
		}
		//System.out.println(sql);
		ResultSet rs = this.stmt.executeQuery(sql);
		
		while (rs.next()){
			int_service = rs.getString(tgt_env);
		}
		
		return int_service;
	}
}