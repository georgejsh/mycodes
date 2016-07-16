package representation;

import java.util.HashMap;
import java.util.Map;

public class Sldinfo {
	public Sldinfo child;
	public Sldinfo parent;
	public Clause posC;
	public Map<String, Clause> subMapPos = new HashMap<String, Clause>();
	public Clause negC;
	public Map<String, Clause> subMapNeg = new HashMap<String, Clause>();
	public Clause resC;

	public  void clearAttributes() {
		child = null;
		posC = null;
		negC = null;
		resC = null;
		subMapNeg.clear();
		subMapPos.clear();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("pos: ");
		builder.append(posC.toString());
		builder.append(",");
		builder.append("neg: ");
		builder.append(negC.toString());
		builder.append(" --> ");
		builder.append(resC.toString());
		if(child != null) {
			builder.append("\n");
			builder.append(child.toString());
			//builder.append(")");
		}
		return builder.toString();
	}
}
