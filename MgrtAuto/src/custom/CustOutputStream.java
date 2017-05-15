package custom;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class CustOutputStream extends OutputStream {

	/**
	 * 
	 */
	private final JTextArea dest;
	
	public CustOutputStream (JTextArea in_dest) {
		if (in_dest == null )
			throw new IllegalArgumentException ("Destination is null");
		dest = in_dest;
	}

	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void write (byte[] buffer, int offset, int length) throws IOException {
		final String text = new String (buffer, offset, length);
		SwingUtilities.invokeLater(new Runnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					dest.append(text);
					dest.setCaretPosition(dest.getDocument().getLength());
				}
			
			});
		
	}
	
	
}