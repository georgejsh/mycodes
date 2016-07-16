package krr.sldresn;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import krr.sldresn.exceptions.ImageGenException;
import krr.sldresn.exceptions.ImproperClauseException;
import krr.sldresn.model.Clause;
import krr.sldresn.model.SLDResnInfo;
import krr.sldresn.sld.SLDResn;
/**
 * Entry point for performing SLD resolution in non GUI mode.
 * @author nithin
 *
 */
public class EntryPoint {
	/**
	 * The folder where test case files are kept
	 */
	public static final String TEST_CASE_FOLDER = "/home/nithin/Study/KRR/SLD/";
	/**
	 * The name of the test case file with extension(preferably in .txt format when running
	 * in GUI mode)
	 */
	public static final String TEST_CASE_FILE = "sld9.txt";
	
	public static void main(String[] args) {
		String testFileStr = TEST_CASE_FOLDER + TEST_CASE_FILE;
		try {
			String imageFileStr = performSLDResolutionFromFile(testFileStr);
			if(imageFileStr != null) {
				try {
					Desktop.getDesktop().open(new File(imageFileStr));
				} catch (Exception e) {
					String msg = "Unable to open file: "+imageFileStr;
					ImageGenException imageGenException = new ImageGenException(msg,e);
					//System.out.println(msg);
					throw imageGenException;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ImproperClauseException e) {
			System.out.println("Clause No "+(e.clauseNo+1)+": \""+e.clauseStr+ "\" is not proper");
		} catch (ImageGenException e) {
			System.out.println(e.getMessage());
			//System.out.println(e.getCause().p);
			e.getCause().printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getErrorIfAny(String contents) throws ImproperClauseException {
		String errorMsg = null;
		String[] split = contents.trim().split(Utils.NEW_LINE);
		List<Clause> clauses = new ArrayList<Clause>();
		int clauseNo = 0;
		for (String clauseStr : split) {
			Clause clause;
			try {
				clause = Clause.getClauseFromString(clauseStr.trim(),null);
				if(!clause.isValidClause()) {
					throw new ImproperClauseException();
				} else
					clauses.add(clause);
			} catch (ImproperClauseException e) {
				e.clauseNo = clauseNo;
				e.clauseStr = clauseStr;
				throw e;
				//e.printStackTrace();
			}
			clauseNo++;
		}
		return errorMsg;
	}
	
	public static String performSLDResolutionFromString(String contents) throws Exception {
		String[] split = contents.trim().split(Utils.NEW_LINE);
		List<Clause> clauses = new ArrayList<Clause>();
		int clauseNo = 0;
		for (String clauseStr : split) {
			Clause clause;
			try {
				clause = Clause.getClauseFromString(clauseStr.trim(),null);
				clauses.add(clause);
			} catch (ImproperClauseException e) {
				e.clauseNo = clauseNo;
				e.clauseStr = clauseStr;
				throw e;
				//e.printStackTrace();
			}
			clauseNo++;
		}
		SLDResnInfo sldResnInfo = new SLDResnInfo();
	    boolean sldResolvable = SLDResn.isSLDResolvable(clauses,sldResnInfo);
	    System.out.println(sldResolvable);
	    //System.out.println(sldResnInfo);
	    if(sldResolvable) {
	    	System.out.println("SLD Resolution successful");
	    	String imageFile = Utils.createImageFile(new File("/tmp/"), sldResnInfo,
	    			"temp"+"_image");
	    	 return imageFile;
	    } else {
	    	System.out.println("SLD Resolution unsuccessful");
	    	//System.out.println(sldResolvable);
	    }
	    return null;
	}
	
	/**
	 * This function is used when we have to perform SLD resolution from a file
	 * having out test case.
	 * @param testFileStr - full path of our test case file as string.
	 * @return - Full path of image file if SLD resolution was successful and image was created, else null
	 * @throws ImproperClauseException - if clauses are not in the correct format,
	 * {@link ImageGenException} - if SLD resolution was successful but image creation failed.
	 * 
	 */
	public static String performSLDResolutionFromFile(String testFileStr) throws Exception {
		File testFile = new File(testFileStr);
		List<Clause> clauses = new ArrayList<Clause>();
		int clauseNo = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(testFile))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       Clause clause;
				try {
					clause = Clause.getClauseFromString(line.trim(),null);
					clauses.add(clause);
				} catch (ImproperClauseException e) {
					e.clauseNo = clauseNo;
					e.clauseStr = line;
					throw e;
					//e.printStackTrace();
				}
				clauseNo++;
		    }
		    SLDResnInfo sldResnInfo = new SLDResnInfo();
		    boolean sldResolvable = SLDResn.isSLDResolvable(clauses,sldResnInfo);
		    System.out.println(sldResolvable);
		    //System.out.println(sldResnInfo);
		    if(sldResolvable) {
		    	System.out.println("SLD Resolution successful");
		    	return Utils.createImageFile(new File(TEST_CASE_FOLDER), sldResnInfo,
		    			Utils.getFileNameWithoutExtension(TEST_CASE_FILE)+"_image");
		    } else {
		    	System.out.println("SLD Resolution unsuccessful");
		    }
		    	//unify(clauses.get(0),clauses.get(1));
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

}
