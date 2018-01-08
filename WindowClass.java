package Editor;

import java.awt.event.*;
import java.awt.*;

public class WindowClass implements WindowListener
{
	public void windowIconified(WindowEvent e)
	{
		
	}
	
	public void windowDeiconified(WindowEvent e)
	{
		
	}
	
	public void windowOpened(WindowEvent e)
	{
		
	}
	
	public void windowClosed(WindowEvent e)
	{
		
	}
	
	public void windowActivated(WindowEvent e)
	{
		
	}
	
	public void windowDeactivated(WindowEvent e)
	{
		
	}
	
	public void windowClosing(WindowEvent e)
	{
		Window w=e.getWindow();
		
		w.setVisible(false);
		w.dispose();
		System.exit(1);
	}
}