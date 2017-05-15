package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingPanel extends JPanel implements FocusListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 107128551307202530L;
	private JLabel label ;
	private Preferences prefs;
	
	private JTextField arch_dir_text;
	
	public SettingPanel () {
		prefs = Preferences.userNodeForPackage(this.getClass()).parent().node("SETTINGS");
		setLayout(new GridBagLayout() );
		SetPanelLayout ();
		LoadSettings();
		
	}
	
	
	private void SetPanelLayout () {
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Settings"));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.insets = new Insets(10,0,0,0);
		label = new JLabel("Archive Dir:	");
		add(label, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		arch_dir_text = new JTextField(20);
		arch_dir_text.addFocusListener(this);
		add(arch_dir_text, gbc);

	}


	private void LoadSettings () {
		arch_dir_text.setText(prefs.get("ARCH", ""));
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == arch_dir_text) {
			String arch_dir = arch_dir_text.getText();
			System.out.println("The value is " + arch_dir);
			//crq_text.selectAll();
			prefs.put("ARCH", arch_dir);
		}
		
		
	}

}