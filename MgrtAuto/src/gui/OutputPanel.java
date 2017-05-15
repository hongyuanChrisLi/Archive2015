package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import custom.CustOutputStream;

public class OutputPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8130648670494751518L;
	private JTextArea text_pane;
	
	public OutputPanel () {
		setLayout(new BorderLayout() );
		CustSetLayout();
	}

	private void CustSetLayout() {
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Process Output"));
		
		text_pane = new JTextArea();
		text_pane.setSize(300, 400);
		JScrollPane scroll_pane = new JScrollPane (text_pane);
		scroll_pane.setPreferredSize(new Dimension(250, 400));
		add(scroll_pane, BorderLayout.CENTER);
		
		
		//System.setOut (new PrintStream (out));
		
	}
	
	public CustOutputStream GetOutputHandler () {
		CustOutputStream out = new CustOutputStream(text_pane);
		return out;
	}
}