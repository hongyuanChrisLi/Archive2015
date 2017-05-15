package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class MgrtListPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5493880874206822617L;
	private JEditorPane text_pane ;
	private ArrayList<String> mgrt_in_lst = new ArrayList<String> ();
	
	public MgrtListPanel (){
		setLayout(new BorderLayout() );
		custSetLayout();
	}
	
	private void custSetLayout() {
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Migration List"));
		
		text_pane = new JEditorPane();
		text_pane.setSize(500, 400);
		JScrollPane scroll_pane = new JScrollPane (text_pane);
		scroll_pane.setPreferredSize(new Dimension(500, 400));
		add(scroll_pane, BorderLayout.CENTER);
		
	}
	
	public ArrayList<String> ReadMgrtList () {
		mgrt_in_lst.clear();
		String text = text_pane.getText();
		String[] text_lst = text.split("\n");
		String folder = "";
		
		for (int i = 0; i < text_lst.length; ++i ) {
			
			String line = text_lst[i].replaceAll("\\t", " ");
			line = line.replaceAll(" +", " ");
			line = line.replaceAll(" ", ",");
			line = line.trim();
			String[] obj_dtls = line.split(",");
			
			if ( obj_dtls.length > 1 ) {
				folder = obj_dtls[1];
			}
				
			String mgrt_obj = obj_dtls[0] + "," + folder;
			mgrt_in_lst.add(mgrt_obj);
			//System.out.println("This line: " + line.replaceAll("\\p{C}", "?"));
			//System.out.println("Object: " + obj_dtls[0] + ", folder: " + folder);
			System.out.println(mgrt_obj);
		}
		
		return mgrt_in_lst;
	}
}