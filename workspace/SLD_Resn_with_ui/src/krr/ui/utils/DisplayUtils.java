package krr.ui.utils;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * This class contains utility methods related to display of widgets 
 * @author Nithin Joshua
 * @since 12th July 2011
 */
public class DisplayUtils {

	/**
	 * Positions shell in centre with respect to Screen or Shells parent
	 * @param shell - shell under consideration
	 * @param wrtScreen - true if to be positioned in center of screen
	 * 			   false if to be positioned in center of shell's parent 
	 */
	public static void positionShellInCentre(Shell shell,boolean wrtScreen){
		positionShell(shell,wrtScreen,0.5,0.5);
	}	
	
	/**
	 * Positions shell in centre with respect to Screen or Shells parent
	 */
	public static void positionShellInCentre(Shell shell){
		positionShellInCentre(shell,true);
	}
	
	/**
	 * Positions shell with respect to Screen or Shells parent
	 * @param shell - shell under consideration
	 * @param wrtScreen - true if to be positioned with respect to screen
	 * 			   false if to be positioned wirh respect to shell's parent 
	 * @param widthratio - (Range 0 - 1)
	 * @param heightratio - (Range 0 - 1)
	 */
	
	public static void positionShell(Shell shell,boolean wrtScreen,double widthratio,double heightratio){
		if(widthratio > 1 || heightratio > 1) {
			return;
		}
		if(widthratio < 0 || heightratio < 0) {
			return;
		}
		Rectangle bounds = null;
		if(wrtScreen) {
			Monitor primary = shell.getDisplay().getPrimaryMonitor();
			bounds = primary.getBounds();
		} else {
			//wrt to shells parent
			Composite parent = shell.getParent();
			bounds = parent.getBounds();
		}
	    Rectangle rect = shell.getBounds();
	    
	    int x = (int) (bounds.x + (bounds.width - rect.width) * widthratio);
	    int y = (int) (bounds.y + (bounds.height - rect.height) *  heightratio);
	    shell.setLocation(x, y);
	}
	
	public static void setAllEnabled(Control control, boolean enabled) {
		if(control == null){
			return;
		}
		if(!(control instanceof Composite)){
			control.setEnabled(enabled);
			return;
		}
		Control[] children = ((Composite) control).getChildren();
		if(children.length == 0){
			control.setEnabled(enabled);
			if(control instanceof Group) {
				 ((Group) control).setForeground(new Color(Display.getDefault(), 204, 204, 204));
			}
		}
		for(Control c : children){
			setAllEnabled(c, enabled);
		}
	}
	
	
}
