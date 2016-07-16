package krr.sldresn.exceptions;

/**
 * Instance of this exception occur when an input clause is in improper format
 * @author nithin
 *
 */
public class ImproperClauseException extends Exception {

	private static final long serialVersionUID = 1L;
	//clause index in the list of clauses.
	public int clauseNo = 0;
	//clause string contents as given.
	public String clauseStr = "";

}
