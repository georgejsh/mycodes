package krr.sldresn.model;

public enum Operator {
	AND("and"),
	OR("or"),
	NOT("not");
	
	public String name;

	private Operator(String name) {
		this.name = name;
	}
	
	public static Operator getOperatorByName(String name) {
		if("and".equals(name)) {
			return AND;
		} else if("or".equals(name)) {
			return OR;
		} else if("not".equals(name)) {
			return NOT;
		} else {
			return null;
		}
	}
}
