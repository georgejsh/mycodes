package exceptions;

public class ClauseException extends Exception {
	// clause index in the list of clauses.
	public int cNo = 0;
	// clause string contents as given.
	public String cStr = "";
}
