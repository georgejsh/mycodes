package krr.ui;

import java.awt.Desktop;
import java.io.File;
import java.net.URISyntaxException;

import krr.sldresn.EntryPoint;
import krr.sldresn.Utils;
import krr.sldresn.exceptions.ImproperClauseException;
import krr.ui.utils.DisplayUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * UI component for performing SLD resolution in GUI mode.
 * @author nithin
 *
 */
public class SLDInputDialog {

	public static  double CONVERSION_FACTOR = 0.7;
	public static final String RELATIVE_LOCATION_IMG_FOLDER = "krr" + File.separator +"ui"
			+ File.separator + "images" + File.separator;

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(300, 200);
		shell.setText("SLD Resolution");
		shell.setLayout(new GridLayout());

		Monitor primaryMonitor = shell.getDisplay().getPrimaryMonitor();
		Rectangle bounds = primaryMonitor.getBounds();
		int width = (int) (bounds.width * CONVERSION_FACTOR);
		int height = (int) (bounds.height * CONVERSION_FACTOR);
		shell.setSize(width, height);
		DisplayUtils.positionShellInCentre(shell);

		Composite child = new Composite(shell, SWT.NONE);
		child.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(4, false);
		child.setLayout(layout);
		
		Group radioComposite = new Group(child, SWT.NONE);
		radioComposite.setText("Input Options");
		GridData gridDataRadioComp = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridDataRadioComp.horizontalSpan = 4;
		radioComposite.setLayoutData(gridDataRadioComp);
		GridLayout layout1 = new GridLayout(2, true);
		radioComposite.setLayout(layout1);
		//radioComposite.setBackground(new Color(Display.getDefault(), 255, 0, 0));
		
		GridData gridDataRadioBtn = new GridData(GridData.FILL_HORIZONTAL);
		
		final Button readInputRadioBtn = new Button(radioComposite, SWT.RADIO);
		readInputRadioBtn.setText("Read Input From File");
		readInputRadioBtn.setLayoutData(gridDataRadioBtn);
		
		final Button typeInputRadioBtn = new Button(radioComposite, SWT.RADIO);
		typeInputRadioBtn.setText("Type Input");
		typeInputRadioBtn.setLayoutData(gridDataRadioBtn);
		
		final Composite browseComp = new Composite(child, SWT.NONE);
		GridData gridDatabBrowse = new GridData(GridData.FILL_HORIZONTAL);
		//gridData2.widthHint = 200;
		gridDatabBrowse.horizontalSpan = 4;
		browseComp.setLayoutData(gridDatabBrowse);
		GridLayout layoutBrowse = new GridLayout(4,false);
		browseComp.setLayout(layoutBrowse);
		
		final Label label = new Label(browseComp, SWT.NONE);
		label.setText("Test Case File");

		final Text testFileText = new Text(browseComp, SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 200;
		testFileText.setLayoutData(gridData);

		final Button browseButton = new Button(browseComp, SWT.PUSH);
		browseButton.setText("Browse");
		
		
		final Button clearButton = new Button(browseComp, SWT.PUSH);
		String path = "";
		try {
			path = SLDInputDialog.class.getProtectionDomain().getCodeSource()
								.getLocation().toURI().getPath();
			
			path = path + RELATIVE_LOCATION_IMG_FOLDER;
		} catch (URISyntaxException e) {
			//e.printStackTrace();
		}
		if(path.length() > 0) {
			Image clearImage = new Image(Display.getDefault(), path + "cross.png");
			clearButton.setImage(clearImage);
		} else {
			clearButton.setText("Clear");
		}
		
		
		
		final Group fileContentsGrp = new Group(child, SWT.NONE);
		fileContentsGrp.setText("File Contents");
		GridData gridData3 = new GridData(GridData.FILL_BOTH);
		//gridData2.widthHint = 200;
		gridData3.horizontalSpan = 4;
		fileContentsGrp.setLayoutData(gridData3);
		GridLayout layout2 = new GridLayout();
		fileContentsGrp.setLayout(layout2);
		
		final StyledText styledText = new StyledText(fileContentsGrp, SWT.BORDER);
		styledText.setText("");
		GridData gridData2 = new GridData(GridData.FILL_BOTH);
		//gridData2.widthHint = 200;
		//gridData2.horizontalSpan = 4;
		styledText.setLayoutData(gridData2);
		styledText.setEditable(false);
		//add listeners
		
		
		final Button performSLDButton = new Button(child, SWT.PUSH);
		GridData gridData4 = new GridData(SWT.CENTER,SWT.CENTER,true,false);
		gridData4.horizontalSpan = 4;
		//gridData2.widthHint = 200;
		//gridData2.horizontalSpan = 4;
		performSLDButton.setLayoutData(gridData4);
		performSLDButton.setText("Perform SLD Resolution");
		if(path.length() > 0) {
			Image clearImage = new Image(Display.getDefault(), path + "play.png");
			performSLDButton.setImage(clearImage);
		}
		performSLDButton.setEnabled(false);
		
		final Label errorLabel = new Label(child, SWT.NONE);
		GridData gridData5 = new GridData(SWT.LEFT,SWT.CENTER,true,false);
		gridData5.horizontalSpan = 4;
		gridData5.widthHint = 800;
		errorLabel.setLayoutData(gridData5);
		errorLabel.setForeground(new Color(Display.getDefault(), 255, 0, 0));
		errorLabel.setText("");
		
		readInputRadioBtn.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				DisplayUtils.setAllEnabled(browseComp, true);
				fileContentsGrp.setText("File Contents");
				styledText.setEditable(false);
				
				String trim = testFileText.getText().trim();
				if(trim.length() > 0) {
					File file = new File(trim);
					if(file.exists() && file.isFile()) {
						String fileContents = Utils.getFileContents(file);
						styledText.setText(fileContents);
					}
				}
			}
		
		});
		
		typeInputRadioBtn.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				DisplayUtils.setAllEnabled(browseComp, false);
				fileContentsGrp.setText("Input");
				styledText.setEditable(true);
			}
		
		});
		
		browseButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				FileDialog fileDialog = new FileDialog(Display.getDefault().getActiveShell());
				fileDialog.setFilterExtensions(new String[]{"*.txt"});
				String open2 = fileDialog.open();
				if(open2 != null) {
					testFileText.setText(open2);
					String fileContents = Utils.getFileContents(new File(open2));
					styledText.setText(fileContents);
					//performSLDButton.setEnabled(true);
				}
			}

		});
		
		clearButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				testFileText.setText("");
				styledText.setText("");
				//performSLDButton.setEnabled(false);
			}

		});
		
		performSLDButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try {
					performSLDButton.setEnabled(false);
					try {String imgFile = null;
						if(readInputRadioBtn.getSelection()) {
							imgFile = EntryPoint.performSLDResolutionFromFile(testFileText.getText().trim());
						} else {
							imgFile = EntryPoint.performSLDResolutionFromString(styledText.getText().trim());
						}
						
						if(imgFile == null) {
							MessageBox messageBox = new MessageBox(Display.getDefault().getActiveShell(), SWT.ICON_INFORMATION);
							messageBox.setText("Unsuccessful");
							messageBox.setMessage("SLD Resolution was unsuccessful on the input clauses.");
							messageBox.open();
							
						} else {
							MessageBox messageBox = new MessageBox(Display.getDefault().getActiveShell(), SWT.ICON_WORKING|SWT.YES|SWT.NO);
							messageBox.setText("Successful");
							messageBox.setMessage("SLD Resolution was successful on the input clauses.\n Click 'Yes' to open the Goal Tree");
							int open = messageBox.open();
							if(open == SWT.YES) {
								Desktop.getDesktop().open(new File(imgFile));
							}
						}
					} catch (ImproperClauseException e) {
						MessageBox messageBox = new MessageBox(Display.getDefault().getActiveShell(), SWT.ICON_ERROR);
						messageBox.setText("Error");
						messageBox.setMessage("Clause No "+(e.clauseNo+1)+": \""+e.clauseStr+ "\" is not proper");
						messageBox.open();
						e.printStackTrace();
					} catch (Exception e) {
						MessageBox messageBox = new MessageBox(Display.getDefault().getActiveShell(), SWT.ICON_ERROR);
						messageBox.setText("Error");
						messageBox.setMessage(e.getMessage());
						messageBox.open();
						e.printStackTrace();
					}
				} finally {
					performSLDButton.setEnabled(true);
				}
			}
		});
		styledText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				String text = styledText.getText();
				if(text.trim().length() > 0) {
					performSLDButton.setEnabled(true);
				} else {
					performSLDButton.setEnabled(false);
					return;
				}
				String errorMsg = "";
				boolean isError = false;
				try {
					EntryPoint.getErrorIfAny(styledText.getText().trim());
				} catch (ImproperClauseException e1) {
					//e1.printStackTrace();
					isError = true;
					errorMsg = "Clause No "+(e1.clauseNo+1)+": \""+e1.clauseStr+ "\" is not proper";
				}
				errorLabel.setText(errorMsg);
				if(isError) {
					performSLDButton.setEnabled(false);
				} else {
					performSLDButton.setEnabled(true);
				}
			}
		});
		
		readInputRadioBtn.setSelection(true);
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}