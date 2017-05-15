package custom;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

import auto.MgrtAuto;

public class CustRunnable implements Runnable {

	private Thread thread;
	private String thread_name;
	private String [] specs;
	
	private boolean is_group = true;
	private ArrayList<String> mgrt_list;
	private MgrtAuto migration_auto;
	
	public CustRunnable (String name, String [] specs) {
		thread_name = name;
		this.specs = specs;
		//System.out.
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Running " + thread_name);
		migration_auto = new MgrtAuto(specs);
	
		if ( ! is_group ) {
			migration_auto.SetIsNotGroup(this.mgrt_list);
		}
		
		if ( !Thread.currentThread().isInterrupted()) {
			
			try {
				migration_auto.AutomateMigration();
			} catch (IOException | SQLException | InterruptedException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				StringWriter error_msg  = new StringWriter ();
				e.printStackTrace(new PrintWriter(error_msg));
				System.out.println(error_msg.toString());
			}
		}
		
	
	}
	
	public void start() {
		
		System.out.println("Starting " + thread_name);
		if (thread == null)
		{
			thread = new Thread(this, thread_name);
			thread.start();
		}
	}
	
	
	public void Cancel () {
		if (migration_auto != null)
		{
			migration_auto.CancelProcess();
		}
	}
	
	public void LoadMgrtList (ArrayList<String> in_list) {
		
		this.is_group = false;
		this.mgrt_list = in_list;
	}
	
}