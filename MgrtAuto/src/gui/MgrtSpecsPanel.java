package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

public class MgrtSpecsPanel extends JPanel  implements ItemListener, ActionListener, FocusListener {

	private static final long serialVersionUID = -6686611101253926078L;
	private JLabel label;
	private JComboBox<String> src_combo;
	private JComboBox<String> tgt_combo;
	private JTextField crq_text;
	private JTextField req_text;
	private JCheckBox check_box;
	private JButton start_button;
	private ActionListener parent_listener;
	
	private String src_env;
	private String tgt_env;
	private String crq;
	private String requester;
	private boolean is_group = false;
	
	private Preferences prefs;
	
	public MgrtSpecsPanel (ActionListener in_listener) throws BackingStoreException {
		prefs = Preferences.userNodeForPackage(this.getClass());
		setLayout(new GridBagLayout() );
		parent_listener = in_listener;
		SetLayout();
		LoadSpecs();
		
	}
	
	
	private void SetLayout () throws BackingStoreException {
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Migration Specs"));
		
		
		Preferences prefs = Preferences.userNodeForPackage(this.getClass());
		String[] nodes = prefs.childrenNames();
		
		for (int i = 0 ; i < nodes.length; i++ ){
			//System.out.println ("This node is " + nodes[i] );
			Preferences node_prefs = prefs.node(nodes[i]);
			
			//String version = node_prefs.get(""), def)
		}
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.insets = new Insets(10,0,0,0);
		label = new JLabel("Src Env:");
		add(label, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		String[] petStrings = { "", "ZCD", "ZC", "ZSI" };
		src_combo = new JComboBox<String>(petStrings);
		src_combo.addActionListener(this);
		add(src_combo, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		label = new JLabel("Tgt Env:");
		add(label, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		String[] someStrings = { "", "ZCD", "ZC", "ZSI", "FSTE"};
		tgt_combo = new JComboBox<String>(someStrings);
		tgt_combo.addActionListener(this);
		add(tgt_combo, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(0,0,0,2);
		label = new JLabel("CRQ Num:");
		add(label, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		crq_text = new JTextField(10);
		crq_text.addFocusListener(this);
		add(crq_text, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(0,0,0,2);
		label = new JLabel("Requester: ");
		add(label, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		req_text = new JTextField(5);
		req_text.addFocusListener(this);
		add(req_text, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		label = new JLabel("Is Group?:");
		add(label, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		check_box = new JCheckBox("");
		check_box.addItemListener(this);
		add(check_box, gbc);
		
		
		//
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		start_button = new JButton("Start");
		start_button.setActionCommand("Start");
		start_button.addActionListener( parent_listener);
		add(start_button, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		JButton cancel_button = new JButton("Cancel");
		cancel_button.setActionCommand("Cancel");
		cancel_button.addActionListener( parent_listener);
		add(cancel_button, gbc);
	}
	
	public boolean IsGroup () {
		return is_group;
	}
	
	public String[] GetSpecs () {
		
		String[] specs = {src_env, tgt_env, crq, requester};
		System.out.println(Arrays.toString(specs));
		return specs;
	}
	
	public void SaveSpecs () {
		
		prefs.put("SRC_ENV", src_combo.getSelectedItem().toString());
		prefs.put("TGT_ENV", tgt_combo.getSelectedItem().toString());
		prefs.put("CRQ", crq_text.getText());
		prefs.put("REQUESTER", req_text.getText());
		prefs.put("IS_GROUP", String.valueOf(check_box.isSelected()));
		
	}
	
	private void LoadSpecs () {
		
		src_combo.setSelectedItem(prefs.get("SRC_ENV", ""));
		tgt_combo.setSelectedItem(prefs.get("TGT_ENV", ""));
		crq_text.setText(prefs.get("CRQ", ""));
		req_text.setText(prefs.get("REQUESTER", ""));
		check_box.setSelected(Boolean.valueOf(prefs.get("IS_GROUP","")));
		
		src_env = prefs.get("SRC_ENV", "");
		tgt_env = prefs.get("TGT_ENV", "");
		crq = prefs.get("CRQ", "");
		requester = prefs.get("REQUESTER", "");
		is_group = Boolean.valueOf(prefs.get("IS_GROUP",""));
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		 Object source = e.getItemSelectable();
		 
		 if (source == check_box) {
			 System.out.println ("Modified the checkbox" );
		 }
		 
		 if (e.getStateChange() == ItemEvent.SELECTED) {
			 System.out.println("This is group migration");
			 is_group = true;
		 }else if( e.getStateChange() == ItemEvent.DESELECTED ) {
			 System.out.println("This is NOT group migration");
			 is_group = false;
		 }
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Object source = e.getSource();
		
		if ( source == src_combo) {
			
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) source;
			src_env = (String) cb.getSelectedItem();
			System.out.println ("Source Env is " + src_env);
		}else if (source == tgt_combo) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) source;
			tgt_env = (String) cb.getSelectedItem();
			System.out.println ("Target Env is " + tgt_env);
		}/*else if (source == crq_text) {
			String crq_num = crq_text.getText();
			System.out.println("The value is " + crq_num);
			crq_text.selectAll();
		}*/
	}


	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
		Object source = e.getSource();
		if (source == crq_text) {
			crq = crq_text.getText();
			//System.out.println("The value is " + crq);
			crq_text.selectAll();
		}else if (source == req_text ) {
			requester = req_text.getText();
			//System.out.println("The value is " + crq);
			req_text.selectAll();
		}
	}

}