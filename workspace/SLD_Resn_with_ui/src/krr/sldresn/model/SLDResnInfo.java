package krr.sldresn.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The information of how goal was reached is stored in this class. 
 * @author nithin
 *
 */
public class SLDResnInfo {
	public Clause posClause;
	/**
	 * substitution for the positive clause.
	 */
	public Map<String, Clause> subMapPosClause = new HashMap<String, Clause>();
	public Clause negClause;
	/**
	 * substitution for the negative clause.
	 */
	public Map<String, Clause> subMapNegClause = new HashMap<String, Clause>();
	public Clause resClause;
	//child only if resolvedClause != EMPTY_CLAUSE
	public SLDResnInfo child;
	public SLDResnInfo parent;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("pos: ");
		builder.append(posClause.toString());
		builder.append(",");
		builder.append("neg: ");
		builder.append(negClause.toString());
		builder.append(" --> ");
		builder.append(resClause.toString());
		if(child != null) {
			builder.append("\n");
			builder.append(child.toString());
			//builder.append(")");
		}
		return builder.toString();
	}
	
	public void clearAttributes() {
		posClause = null;
		subMapPosClause.clear();
		negClause = null;
		subMapNegClause.clear();
		resClause = null;
		child = null;
	}
	
}
