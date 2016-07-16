package representation;

public enum Operator {
	AND("and"), OR("or"), NOT("not");
	public String name;

	private Operator(String str) {
		this.name = str;
	}

	public static Operator getOperator(String str) {
		if ("and".equals(str)) {
			return AND;
		} else if ("or".equals(str)) {
			return OR;
		} else if ("not".equals(str)) {
			return NOT;
		} else {
			return null;
		}
	}
}
