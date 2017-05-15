package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import gui.EnvProfilePanel;

public class SettingTreePanel extends JPanel 
	implements TreeSelectionListener, TreeModelListener, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -672941554526228165L;
	private JTree tree;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode top = 
			new DefaultMutableTreeNode("Environments");
    private static String ADD_COMMAND = "add";
    private static String REMOVE_COMMAND = "remove";
    private JFrame frame;
    private EnvProfilePanel env_pan;
    
    private String current_node;
	
	public SettingTreePanel (JFrame in_frame, EnvProfilePanel in_env_pan) throws BackingStoreException  {
		frame = in_frame;
		env_pan = in_env_pan;
		setLayout(new BorderLayout() );
		CreateTree();
		CreateTreeModifiers();
		CreateNodes(top);
		
	}

	
	private void CreateTree () {
		
		treeModel = new DefaultTreeModel(top);
		treeModel.addTreeModelListener(this);
		
		tree = new JTree(treeModel);
		tree.setEditable(true);
		tree.getSelectionModel().setSelectionMode
			(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		tree.addTreeSelectionListener(this);
		
		JScrollPane treeView = new JScrollPane(tree);
		add(treeView, BorderLayout.CENTER); 
	}
	
	private void CreateTreeModifiers () {
		
		JPanel button_panel = new JPanel (new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.ipadx = 20; 
		gbc.anchor = GridBagConstraints.PAGE_START;
		
		
		JButton addButton = new JButton("Add");
		addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);
        button_panel.add(addButton, gbc);
        
        
        gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.ipadx = 0; 
		gbc.gridwidth = 1;
        JButton remove_button = new JButton("Remove");
		remove_button.setActionCommand(REMOVE_COMMAND);
        remove_button.addActionListener(this);
        button_panel.add(remove_button, gbc);
        
        add(button_panel, BorderLayout.SOUTH);
	}
	
	private void CreateNodes(DefaultMutableTreeNode top) throws BackingStoreException {
		Preferences prefs = Preferences.userNodeForPackage(this.getClass());
		String[] nodes = prefs.childrenNames();
		
		for (int i = 0 ; i < nodes.length; i++ ){
			System.out.println ("This node is " + nodes[i] );
			DefaultMutableTreeNode env =  new DefaultMutableTreeNode(nodes[i]);
			top.add(env);
		}
		
	}
	
	private void addNode(DefaultMutableTreeNode parent, String child) {
		 DefaultMutableTreeNode childNode = 
	                new DefaultMutableTreeNode(child);
		 treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
		 //parent.add(childNode);
	}
	
	
	private void removeNode() {
		 TreePath currentSelection = tree.getSelectionPath();
		 if (currentSelection != null) {
			 DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                     (currentSelection.getLastPathComponent());
			 MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
			 if (parent != null) {
				 treeModel.removeNodeFromParent(currentNode);
	                return;
			 }
		 }
		 
	}
	
	private boolean dialogRemoveWarn () {
		
		boolean is_removed = false;
		
		final JOptionPane optionPane = new JOptionPane(
                "Are you sure to remove this environment profile ?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
		final JDialog dialog = new JDialog(frame,
                "Click a button",
                true);
		dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(
            JDialog.DO_NOTHING_ON_CLOSE);
        
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                //setLabel("Thwarted user attempt to close window.");
            }
        });
        
        optionPane.addPropertyChangeListener(
	        new PropertyChangeListener() {
	            public void propertyChange(PropertyChangeEvent e) {
	                String prop = e.getPropertyName();
	
	                if (dialog.isVisible()
	                 && (e.getSource() == optionPane)
	                 && (JOptionPane.VALUE_PROPERTY.equals(prop))) {
	                    //If you were going to check something
	                    //before closing the window, you'd do
	                    //it here.
	                    dialog.setVisible(false);
	                }
	            }
	        });
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        
        int value = ((Integer)optionPane.getValue()).intValue();
        if (value == JOptionPane.YES_OPTION) {
        	is_removed = true;
        } else if (value == JOptionPane.NO_OPTION) {    
        } else {
        }
        
        return is_removed;
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		
		 DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                 tree.getLastSelectedPathComponent();
		 
		 TreePath tree_path = e.getPath();
		 current_node = tree_path.getLastPathComponent().toString();
		 
		 if (node == null)
			    //Nothing is selected.     
			    return;
		 
		 Object nodeInfo = node.getUserObject();
		    if (node.isLeaf()) {
		    	System.out.println("Select something: " + current_node );
		    	env_pan.LoadEnvProfile(current_node, true);
		    } else {
		    	System.out.println("Mess with the folder");
		    	env_pan.LoadEnvProfile(current_node, false);
		    }
	}
	
	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode node 
			= (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());
		
		int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode)(node.getChildAt(index));
        try {
			env_pan.removeEnvProfile(current_node);
			env_pan.LoadEnvProfile(node.getUserObject().toString(), true);
		} catch (BackingStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        current_node =  node.getUserObject().toString();
        System.out.println("The user has finished editing the node.");
        System.out.println("New value: " + current_node);
        
        
	}

	@Override
	public void treeNodesInserted(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		
		if (ADD_COMMAND.equals(command)) {
			addNode(top, "New Env");
		}else if (REMOVE_COMMAND.equals(command)){
			if ( dialogRemoveWarn()){
				removeNode();
				try {
					env_pan.removeEnvProfile(current_node);
				} catch (BackingStoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}

}