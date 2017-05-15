package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import gui.MgrtSpecsPanel;
import gui.MgrtListPanel;
import gui.SettingTreePanel;
import gui.EnvProfilePanel;
import gui.OutputPanel;
import auto.MgrtAuto;
import custom.CustRunnable;

public class SpecEditor extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4292530756028846044L;
	private MgrtSpecsPanel left_pan;
	private MgrtListPanel right_pan;
	private OutputPanel output_pan;
	
	private CustRunnable mgrt_run;
	
	public SpecEditor () throws BackingStoreException {
		super("Migration Auto App");
		
		JTabbedPane tab_panels = new JTabbedPane();
		JPanel migr_dtl_tab = new JPanel(new BorderLayout());
		JPanel env_tab = new JPanel(new BorderLayout());
		JPanel setting_tab = new JPanel(new BorderLayout());
		
		left_pan = new MgrtSpecsPanel(this);

		right_pan = new MgrtListPanel();
		output_pan = new OutputPanel();
		
		migr_dtl_tab.add(left_pan, BorderLayout.WEST);
		migr_dtl_tab.add(right_pan, BorderLayout.CENTER);
		
		EnvProfilePanel env_pan = new EnvProfilePanel();
		SettingTreePanel tree_pan = new SettingTreePanel((JFrame) this, env_pan);
		
		env_tab.add(tree_pan, BorderLayout.WEST);
		env_tab.add(env_pan, BorderLayout.CENTER);
		
		SettingPanel set_pan = new SettingPanel();
		setting_tab.add(set_pan, BorderLayout.CENTER);
		
		tab_panels.addTab("Migration", migr_dtl_tab);
		tab_panels.addTab("Environments", env_tab);
		tab_panels.addTab("Settings", setting_tab);

		
		
		add (tab_panels, BorderLayout.NORTH);
		add (output_pan, BorderLayout.CENTER);
		
		setSize(500, 850);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.setOut (new PrintStream (output_pan.GetOutputHandler()));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if (e.getActionCommand().equals("Start")) {
			System.out.println("Start Button");
			
			String[] specs = left_pan.GetSpecs();
			left_pan.SaveSpecs();
			CustRunnable R1  = new CustRunnable("Migration", specs);;
			mgrt_run = R1;
			
			if ( ! left_pan.IsGroup()) {
				ArrayList<String> mgrt_list = right_pan.ReadMgrtList();
				R1.LoadMgrtList(mgrt_list);
			}
			
			R1.start();
			
		}else if (e.getActionCommand().equals("Cancel")){
			if ( mgrt_run != null ){
				mgrt_run.Cancel();
			}
		}
			
	}
	
}