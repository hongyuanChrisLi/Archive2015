package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import infa.PMREPBuilder;
import orcl.OracleJDBC;

public class EnvProfilePanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3315535291749506553L;
	private JLabel label;
	
	private JTextField domain_field;
	private JTextField repo_field;
	private JTextField infa_user_field;
	private JPasswordField infa_pass_field;
	private JCheckBox infa_check_box;
	private JTextField db_host_field;
	private JTextField db_port_field;
	private JTextField db_service_field;
	private JTextField db_user_field;
	private JPasswordField db_pass_field;
	
	private JButton but_infa_test;
	private JButton but_db_test;
	
	private Preferences prefs;
	
	private static final String INFA_TEST_COMMAND = "TEST INFA CONNECT";
	private static final String DB_TEST_COMMAND = "TEST DB CONNECT";
	private static final String SAVE_COMMAND = "SAVE";
	
	public EnvProfilePanel () {
		setLayout(new BorderLayout() );
		CreateWelcome();
		//CreateForm();
		//prefs = Preferences.userNodeForPackage(this.getClass());
	}
	
	private void CreateForm () {
		removeAll();
		updateUI(); 
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Environment Profile"));
		CreateInfaPanel();
		//add(new JSeparator(SwingConstants.HORIZONTAL));
		CreateOraclPanel();
		CreateButtonPanel();
	}
	
	private void CreateWelcome() {
		removeAll();
		updateUI(); 
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Environment Profile"));
		label = new JLabel("Wellcome");
		add(label, BorderLayout.NORTH);
	}
	
	
	public void LoadEnvProfile (String node_name, boolean is_profile) {
		
		if (is_profile) {
			CreateForm();
			prefs = Preferences.userNodeForPackage(this.getClass()).node(node_name);
			domain_field.setText(prefs.get("DOMAIN", ""));
			repo_field.setText(prefs.get("REPO", ""));
			infa_user_field.setText(prefs.get("REPO_USER", ""));
			infa_pass_field.setText(prefs.get("REPO_PASS", ""));
			infa_check_box.setSelected(Boolean.valueOf(prefs.get("REPO_VERSION","")));
			db_host_field.setText(prefs.get("DB_HOST", ""));
			db_port_field.setText(prefs.get("PORT", ""));
			db_service_field.setText(prefs.get("SERVICE", ""));
			db_user_field.setText(prefs.get("DB_USER", ""));
			db_pass_field.setText(prefs.get("DB_PASS", ""));
			//System.out.println("Set Domain value");
			
		}else {
			CreateWelcome();
		}
			
		
		
	}
	
	public void removeEnvProfile (String node_name) throws BackingStoreException {
		prefs = Preferences.userNodeForPackage(this.getClass()).node(node_name);
		prefs.removeNode();
		removeAll();
		updateUI(); 
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Environment Profile"));
		prefs = Preferences.userNodeForPackage(this.getClass());
		label = new JLabel("Wellcome");
		add(label, BorderLayout.NORTH);
	}
	
	private void CreateInfaPanel () {
		
		JPanel infa_pan = new JPanel (new GridBagLayout() ) ;
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.insets = new Insets(5,0,0,0);
		label = new JLabel("Informatica");
		label.setFont (label.getFont().deriveFont (14.0f));
		infa_pan.add(label, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		label = new JLabel("Domain");
		infa_pan.add(label, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		domain_field = new JTextField(10);
		//domain_field.addFocusListener(this);
		infa_pan.add(domain_field, gbc);
		
		

		gbc.gridx = 0;
		gbc.gridy = 2;
		label = new JLabel("Repo");
		infa_pan.add(label, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		repo_field = new JTextField(10);
		//domain_field.addFocusListener(this);
		infa_pan.add(repo_field, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		label = new JLabel("Username");
		infa_pan.add(label, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		infa_user_field = new JTextField(10);
		//domain_field.addFocusListener(this);
		infa_pan.add(infa_user_field, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		label = new JLabel("Password");
		infa_pan.add(label, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		infa_pass_field = new JPasswordField(10);
		//domain_field.addFocusListener(this);
		infa_pan.add(infa_pass_field, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		label = new JLabel("Version Control ");
		infa_pan.add(label, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		infa_check_box= new JCheckBox("");
		//domain_field.addFocusListener(this);
		infa_pan.add(infa_check_box, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0,0,0,0);
		gbc.anchor = GridBagConstraints.PAGE_START;
		but_infa_test= new JButton("Test Connect");
		but_infa_test.setActionCommand(INFA_TEST_COMMAND);
		but_infa_test.addActionListener( this);
		infa_pan.add(but_infa_test, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.insets = new Insets(5,0,5,0);
		label = new JLabel("");
		infa_pan.add(label, gbc);
		
		infa_pan.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
		add(infa_pan,BorderLayout.NORTH);
		
		
	}
	
	private void CreateOraclPanel () {
		
		JPanel orcl_pan = new JPanel (new GridBagLayout() ) ;
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.insets = new Insets(5,0,0,0);
		label = new JLabel("    Oracle    ");
		label.setFont (label.getFont().deriveFont (14.0f));
		orcl_pan.add(label, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		label = new JLabel("Host");
		orcl_pan.add(label, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		db_host_field = new JTextField(10);
		//domain_field.addFocusListener(this);
		orcl_pan.add(db_host_field, gbc);
		
		

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		label = new JLabel("Port");
		orcl_pan.add(label, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		db_port_field = new JTextField(10);
		//domain_field.addFocusListener(this);
		orcl_pan.add(db_port_field, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		label = new JLabel("Serivice");
		orcl_pan.add(label, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		db_service_field = new JTextField(10);
		//domain_field.addFocusListener(this);
		orcl_pan.add(db_service_field, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		label = new JLabel("Username ");
		orcl_pan.add(label, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		db_user_field = new JTextField(10);
		//domain_field.addFocusListener(this);
		orcl_pan.add(db_user_field, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		label = new JLabel("Password ");
		orcl_pan.add(label, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		db_pass_field = new JPasswordField(10);
		//domain_field.addFocusListener(this);
		orcl_pan.add(db_pass_field, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10,0,0,0);
		gbc.anchor = GridBagConstraints.PAGE_START;
		but_db_test = new JButton("Test Connect");
		but_db_test.setActionCommand(DB_TEST_COMMAND);
		but_db_test.addActionListener( this );
		orcl_pan.add(but_db_test, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.insets = new Insets(5,0,5,0);
		label = new JLabel("");
		orcl_pan.add(label, gbc);
		
		add(orcl_pan,BorderLayout.CENTER);
		//orcl_pan.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
	}
	
	private void CreateButtonPanel () {
		
		JPanel but_pan = new JPanel (new GridBagLayout() ) ;
		
		GridBagConstraints gbc = new GridBagConstraints();
		/*gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10,0,0,0);
		label = new JLabel("");
		but_pan.add(label, gbc);
		*/
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0,10,0,10);
		gbc.anchor = GridBagConstraints.PAGE_START;
		JButton save_button = new JButton("Save");
		save_button.setActionCommand(SAVE_COMMAND);
		save_button.addActionListener(this);
		but_pan.add(save_button, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0,10,0,0);
		JButton cancel_button = new JButton("Cancel");
		cancel_button.setActionCommand("Cancel");
		//save_button.addActionListener( start_listener);
		but_pan.add(cancel_button, gbc);
		
		
		/*gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(0,0,20,0);
		label = new JLabel("");
		but_pan.add(label, gbc);*/
		
		
		add(but_pan,BorderLayout.SOUTH);
	}
	
	private void SaveForm () {
		System.out.println("This is saved at " + prefs.name());
		prefs.put("DOMAIN", domain_field.getText());
		prefs.put("REPO", repo_field.getText());
		prefs.put("REPO_USER", infa_user_field.getText());
		prefs.put("REPO_PASS", String.valueOf(infa_pass_field.getPassword()));
		prefs.put("REPO_VERSION", String.valueOf(infa_check_box.isSelected()));
		prefs.put("DB_HOST", db_host_field.getText());
		prefs.put("PORT", db_port_field.getText());
		prefs.put("SERVICE", db_service_field.getText());
		prefs.put("DB_USER", db_user_field.getText());
		prefs.put("DB_PASS", String.valueOf(db_pass_field.getPassword()));
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String command = e.getActionCommand();
		
		if (command.equals(SAVE_COMMAND))
		{
			SaveForm();
		}else if (command.equals(INFA_TEST_COMMAND)) {
			String cmd = "pmrep.exe connect"
					+ " -r " + repo_field.getText()
					+ " -d " + domain_field.getText()
					+ " -n " + infa_user_field.getText()
					+ " -x " + String.valueOf(infa_pass_field.getPassword());
			
			PMREPBuilder rep_cmd;
			try {
				rep_cmd = new PMREPBuilder (cmd, false);
				rep_cmd.RunPMREP();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}else if (command.equals(DB_TEST_COMMAND)) {
			SaveForm();
			OracleJDBC jdbc = new OracleJDBC (prefs.name());
			jdbc.Connect();
		}
		
	}
}