package gui;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.ImageGenException;
import representation.Sldinfo;

public class Utils {
	private static final String DOT_EXTENSION = ".dot";
	private static final String PNG_EXTENSION = ".png";
	public static final String NEW_LINE = System.getProperty("line.separator");
	public static String NODE_SUFFIX_POS_CLAUSE = "\",color=blue,penwidth = 2.0];";
	public static String NODE_SUFFIX_NEG_CLAUSE = "\",color=red,penwidth = 2.0];";
	public static String EDGE_NOTATION = "->";
	public static <K> List<K> getListFromArray(K[] array) {
		List<K> list = new ArrayList<K>();
		for(K k: array) {
			list.add(k);
		}
		return list;
	}
	
	public static <K> int getIndex(List<K> list,K obj) {
		int i = 0;
		for (K itr:list) {
			if(obj.equals(itr)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	/**
	 * 
	 * @param folder - folder where image file has to be created.
	 * @param sldResnInfo - Model object containing SLD derivation structure.
	 * @param fileName - name of dot file and image file(without extension)
	 * @return - full path of the image file created.
	 * @throws ImageGenException
	 */
	public static String createImageFile(File folder,Sldinfo sldResnInfo,String fileName) throws Exception {
		String imageFilePath = null;
		StringBuilder builder = new StringBuilder();
		builder.append("digraph Output {");
		builder.append("\n");
		//builder.append("edge[arrowhead=none,arrowtail=none]");
		//builder.append("\n");
		appendInfo(builder, sldResnInfo);
		builder.append("\n");
		builder.append("}");
		File dotFile = new File(folder, fileName+DOT_EXTENSION);
		
		//try {
			try {
				dotFile.createNewFile();
			} catch (Exception e) {
				String msg = "Unable to create file "+dotFile.getAbsolutePath();
				ImageGenException imageGenException = new ImageGenException(msg,e);
				//System.out.println(msg);
				throw imageGenException;
			}
			BufferedWriter writer = null;
			try
			{
			    writer = new BufferedWriter( new FileWriter( dotFile));
			    writer.write( builder.toString());

			}
			catch ( IOException e) {
				String msg = "Unable to write contents to file "+dotFile.getAbsolutePath();
				ImageGenException imageGenException = new ImageGenException(msg,e);
				//System.out.println(msg);
				throw imageGenException;
			}
			finally
			{
			    try
			    {
			        if ( writer != null)
			        	writer.close( );
			    }
			    catch ( IOException e)
			    {
			    }
			}
			String imageFileName = fileName + PNG_EXTENSION;
			File imageFile = new File(folder,imageFileName);
			try {
				//execute command to create DOT file.
				Runtime.getRuntime().exec("dot -Tpng "+dotFile.getAbsolutePath()+
						" -o " + imageFile.getAbsolutePath());
			} catch (Exception e) {
				String msg = "Unable to execute dot command to create image file";
				ImageGenException imageGenException = new ImageGenException(msg,e);
				//System.out.println(msg);
				throw imageGenException;
			}
			if(imageFile.exists()) {
				imageFilePath = imageFile.getAbsolutePath();
				/*try {
					Desktop.getDesktop().open(imageFile);
				} catch (Exception e) {
					String msg = "Unable to open file: "+imageFile.getAbsolutePath();
					ImageGenException imageGenException = new ImageGenException(msg,e);
					//System.out.println(msg);
					throw imageGenException;
				}*/
			}
			return imageFilePath;
		
		
	}
	
	/**
	 * 
	 * @param builder - contents of the dot file
	 * @param sldResnInfo - Model object containing SLD derivation structure.
	 */
	public static void appendInfo(StringBuilder builder,Sldinfo sldResnInfo) {
		//posClause node will be created.
		//negClause will be created only for root SLDResnInfo
		//resolvedClause also created and edges added between posClause and negClause
		
		
		if(sldResnInfo.parent == null) {
			//root node.
			//dot file contents for positive clause node.
			StringBuilder negClauseNodeBldr = new StringBuilder();
			negClauseNodeBldr.append(sldResnInfo.negC.uniqID);
			negClauseNodeBldr.append(" ");
			negClauseNodeBldr.append("[label=\"");
			negClauseNodeBldr.append(sldResnInfo.negC.toString());
			negClauseNodeBldr.append(NODE_SUFFIX_NEG_CLAUSE);
			builder.append(negClauseNodeBldr);
			builder.append("\n");
		}
		
		//dot file contents for positive clause node.
		StringBuilder posClauseNodeBldr = new StringBuilder();
		posClauseNodeBldr.append(sldResnInfo.posC.uniqID);
		posClauseNodeBldr.append(" ");
		posClauseNodeBldr.append("[label=\"");
		posClauseNodeBldr.append(sldResnInfo.posC.toString());
		posClauseNodeBldr.append(NODE_SUFFIX_POS_CLAUSE);
		
		builder.append(posClauseNodeBldr);
		builder.append("\n");
		
		//dot file contents for resolved clause node.
		StringBuilder resClauseNodeBldr = new StringBuilder();
		resClauseNodeBldr.append(sldResnInfo.resC.uniqID);
		resClauseNodeBldr.append(" ");
		resClauseNodeBldr.append("[label=\"");
		resClauseNodeBldr.append(sldResnInfo.resC.toString());
		resClauseNodeBldr.append(NODE_SUFFIX_NEG_CLAUSE);
		
		builder.append(resClauseNodeBldr);
		builder.append("\n");
		
		//add edges
		StringBuilder edgePosToResBldr = new StringBuilder();
		edgePosToResBldr.append(sldResnInfo.posC.uniqID);
		edgePosToResBldr.append(EDGE_NOTATION);
		edgePosToResBldr.append(sldResnInfo.resC.uniqID);
		if(sldResnInfo.subMapPos.size() > 0) {
			edgePosToResBldr.append("[label=\"");
			edgePosToResBldr.append(sldResnInfo.subMapPos.toString());
			edgePosToResBldr.append("\"]");
		}
		
		builder.append(edgePosToResBldr);
		builder.append("\n");
		
		StringBuilder edgeNegToResBldr = new StringBuilder();
		edgeNegToResBldr.append(sldResnInfo.negC.uniqID);
		edgeNegToResBldr.append(EDGE_NOTATION);
		edgeNegToResBldr.append(sldResnInfo.resC.uniqID);
		if(sldResnInfo.subMapNeg.size() > 0) {
			edgeNegToResBldr.append("[label=\"");
			edgeNegToResBldr.append(sldResnInfo.subMapNeg.toString());
			edgeNegToResBldr.append("\"]");
		}
		
		builder.append(edgeNegToResBldr);
		builder.append("\n");
		
		builder.append("\n");
		if(sldResnInfo.child != null) {
			appendInfo(builder, sldResnInfo.child);
		}
	}
	
	public static void removeSpaceAtEndOf(StringBuilder builder) {
		int length = builder.length();
		if(length > 0) {
			char ch = builder.charAt(length-1);
			if(ch == ' ') {
				builder.deleteCharAt(length-1);
			}
		}
	}
	
	public static String getFileNameWithoutExtension(String fileName) {
		int lastIndexOf = fileName.lastIndexOf(".");
		if(lastIndexOf != -1) {
			return fileName.substring(0, lastIndexOf);
		}
		return fileName;
	}
	
	public static String getFileContents(File file) {
		StringBuilder builder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
				builder.append(line);
				builder.append(NEW_LINE);
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}


}
