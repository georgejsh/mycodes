package start;


import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.ClauseException;
import exceptions.ImageGenException;
import gui.Utils;
import representation.Clause;
import representation.Sldinfo;
import sldresolution.Sldresolution;

public class Solution {
	public static final String TEST_CASE_FOLDER = "/home/georgejsh/Downloads/KRRBahu/testcases/";
	public static final String TEST_CASE_FILE = "sld8_brackman.txt";

	public static void main(String[] args) {
		String filein = TEST_CASE_FOLDER + TEST_CASE_FILE;
		try {
			String imageFileStr = SLD(filein);
			if (imageFileStr != null) {
				try {
					Desktop.getDesktop().open(new File(imageFileStr));
				} catch (Exception e) {
					String msg = "Unable to open file: " + imageFileStr;
					ImageGenException imageGenException = new ImageGenException(msg, e);
					// System.out.println(msg);
					throw imageGenException;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClauseException e) {
			System.out.println("Clause No " + (e.cNo + 1) + ": \""
					+ e.cStr + "\" is not proper");
		} catch (ImageGenException e) {
			System.out.println(e.getMessage());
			// System.out.println(e.getCause().p);
			e.getCause().printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String SLD(String filein) throws Exception {
		File tfile = new File(filein);
		List<Clause> input = new ArrayList<Clause>();
		int Cno = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(tfile))) {
			String line;
			while ((line = br.readLine()) != null) {
				Clause c;
				try {
					//System.out.println(line);
					c = Clause.clauseToString(line.trim(), null);
					//System.out.println(c.children.size());
					input.add(c);
				} catch (ClauseException e) {
					e.cNo = Cno;
					e.cStr = line;
					throw e;
				}
				Cno++;
			}
			Sldinfo sl = new Sldinfo();
			boolean isresolvable = Sldresolution.isResolvable(input, sl);
			if (isresolvable) {
				System.out.println("SLD resolution successful");
				String imageFile = Utils.createImageFile(new File("/tmp/"), sl,
		    			"temp"+"_image");
		    	 return imageFile;
			} else {
				System.out.println("SLD resolution unsuccessful");
			}
		} catch (Exception e) {
			throw e;
		}
		return null;
	}
}
