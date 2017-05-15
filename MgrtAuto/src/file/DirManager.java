package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;

import static java.nio.file.StandardCopyOption.*;


public class DirManager {
	
	private Path archive_root;
	private Path archive_path;
	private Path tmp_path = Paths.get("./tmp");
	private int x_days = 1; 
	
	public DirManager (String rfc_prefix) {
		Preferences prefs = Preferences.userNodeForPackage(this.getClass()).parent().node("SETTINGS");
		archive_root = Paths.get(prefs.get("ARCH", ""));
		Date date = new Date () ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String formatted_date = sdf.format(date);
		
		archive_path = Paths.get( archive_root.toString() + "/" +  rfc_prefix + "_" + formatted_date);
		
	}
	
	public void MoveFiles () throws IOException {
		Files.move(tmp_path, archive_path, REPLACE_EXISTING);
		File tmp = new File(tmp_path.toString());
		tmp.mkdir();
	}
	
	public void RemoveOldDirs () {
		File archive = new File (archive_root.toString());
	    String[] content = archive.list();
	    
	    for(int i = 0; i < content.length; i++) {
	    	
	        File subdir = new File (archive_root.toString() + "/" + content[i]);
	        long diff = new Date().getTime() - subdir.lastModified();
	        
	        if (diff > x_days * 24 * 60 * 60 * 1000 ) {
	        	
	        	File[] files = subdir.listFiles();
	        	
	        	for (File file : files) {
	        		file.delete();
	        	}
	        	
	        	subdir.delete();
	        }
	        
	    }
	}
	
	public void CleanupTmp () {
		File tmp = new File (tmp_path.toString());
		String[] content = tmp.list();
		
		
		  for(int i = 0; i < content.length; i++) {
			  File file = new File (tmp_path.toString() + "/" + content[i]);
			  file.delete();
		  }
	}
}