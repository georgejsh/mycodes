package representation;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import exceptions.ClauseException;
import gui.Utils;

public class Clause {
	public static final int DEFAULT_TYPE = 0;
	public static final int FN_TYPE = 1;
	public static final int VAR_TYPE = 2;
	public static final int CONST_TYPE = 3;
	public static int ID_NUMBER = 1;
	public String name = "";
	public int type = DEFAULT_TYPE;
	public Clause parent;
	public List<Clause> children = new ArrayList<Clause>();
	public Operator operator = null;
	public final int uniqID;
	public static final Clause EMPTY_CLAUSE = new Clause() {
		public String toString() {
			return "[]";
	};
	};

	public Clause() {
		uniqID = ID_NUMBER;
		ID_NUMBER++;
	}

	public Clause(Clause parent) {
		this.parent = parent;
		uniqID = ID_NUMBER;
		ID_NUMBER++;
	}

	public void addC(Clause c) {
		children.add(c);
		c.parent = this;
	}

	public int getSize() {
		return children.size();
	}

	public Clause getCopy() {
		Clause copy = new Clause();
		copy.operator = operator;
		copy.name = name;
		copy.type = type;
		for (Clause childClause : children) {
			copy.children.add(childClause.getCopy());
		}
		return copy;
	}
	/**
	 * return string represenation of clause. This is used as label of the node in
	 * the goal tree image created.
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		switch (type) {
		case VAR_TYPE:
			builder.append("?");
		case CONST_TYPE:
		case FN_TYPE:
			builder.append(name);
			//builder.append(" ");
			return builder.toString();
		default:
			break;
		}
		builder.append("(");
		if(operator != null) {
			builder.append(operator.name);
			builder.append(" ");
		}
		for (Clause clause : children) {
			builder.append(clause.toString());
			builder.append(" ");
		}
		Utils.removeSpaceAtEndOf(builder);
		builder.append(")");
		if(operator != null) {
			//builder.append(")");
		}
		return builder.toString();
	}
	public static Clause clauseToString(String str, Clause parent)
			throws ClauseException {
		//System.out.println(str);
		Clause c = new Clause(parent);
		int frmindex=0;
		int ind = str.indexOf("(", frmindex);
		if (ind == 0) {
			char last = str.charAt(str.length() - 1);
			if (last != ')')
				throw new ClauseException();
			else
				str = str.substring(1, str.length() - 1);
			return clauseToString(str, parent);
		} else {
			StringTokenizer st = new StringTokenizer(str);
			String token = null;
			int ns = 0;
			int cbindex;
			int lbindex = -1;
			String temp;
			while (st.hasMoreTokens()) {
				token = st.nextToken();
				if (token == "(" || token.startsWith("(")) {
					cbindex = str.indexOf("(", lbindex + 1);
					int mbindex = getMatchingBracket(str, cbindex);
					if (mbindex == -1)
						throw new ClauseException();
					else {
						temp = str.substring(cbindex+1, mbindex).trim();
						Clause child = clauseToString(temp, c);
						c.addC(child);
						lbindex = cbindex;
						if (mbindex >= str.length() - 1)
							return c;
						else {
							str = str.substring(mbindex + 1).trim();
							lbindex = -1;
							st = new StringTokenizer(str);
						}
					}
				} else if (token == ")") {

				} else {
					ns++;
					if (ns == 1) {
						Operator OperatorName = Operator.getOperator(token);
						if (OperatorName != null) {
							c.operator = OperatorName;
						} else {
							Clause fnC = new Clause(c);
							fnC.name = token;
							fnC.type = FN_TYPE;
							c.addC(fnC);
						}
					} else {
						boolean flag = false;
						if (token.startsWith("?")) {
							token = token.substring(1);
							flag = true;
						}
						if (flag) {
							// its a variable
							Clause varC = new Clause(c);
							varC.name = token;
							varC.type = VAR_TYPE;
							c.addC(varC);
						} else {
							// its a constant
							Clause constC = new Clause(c);
							constC.name = token;
							constC.type = CONST_TYPE;
							c.addC(constC);
						}
					}
				}
			}
		}
		return c;
	}

	public static boolean isVarOccurInCla(Clause c, String var) {
		boolean flag = false;
		if (c.type == VAR_TYPE) {
			if (var.equals(c.name)) {
				return true;
			}

		} else if (c.type == DEFAULT_TYPE) {
			for (Clause child : c.children) {
				flag = flag || isVarOccurInCla(child, var);
			}
		}
		return flag;
	}

	public static int getMatchingBracket(String str, int pos) {
		char ch = str.charAt(pos);
		if (ch == '(') {
			int counter = 1;
			for (int i = pos + 1; i < str.length(); i++) {
				ch = str.charAt(i);
				if (ch == '(') {
					counter++;
				} else if (ch == ')') {
					counter--;
				}
				if (counter == 0)
					return i;
			}
		}
		return -1;
	}

	public boolean ispositive() {
		if (type == DEFAULT_TYPE) {
			if (operator == Operator.NOT) {
				return false;
			} else if (operator == Operator.OR) {
				// exactly one positive
				int noOfPosClauses = 0;
				for (Clause child : children) {
					Operator childOperator = child.operator;
					if (childOperator == Operator.NOT) {

					} else if (childOperator == Operator.AND) {
						// should not happen
					} else if (childOperator == null) {
						// No Operator
						noOfPosClauses++;
					}
				}
				if (noOfPosClauses == 1) {
					return true;
				} else {
					return false;
				}
			} else if (operator == null) {
				// handle +ve atomic literals
				Clause child1 = children.get(0);
				if (child1.type == FN_TYPE) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isnegative() {
		if (type == DEFAULT_TYPE) {
			if (operator == Operator.NOT) {
				return true;
			} else if (operator == Operator.OR) {
				// all negative
				int noOfPosClauses = 0;
				for (Clause child : children) {
					Operator childOperator = child.operator;
					if (childOperator == Operator.NOT) {

					} else if (childOperator == Operator.AND) {
						// should not happen
					} else if (operator == null) {
						// No Operator
						noOfPosClauses++;
					}
				}
				if (noOfPosClauses == 0) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public static Clause getPosLiteral(Clause posC) {
		Clause posLiteral = null;
		if (posC.operator == Operator.OR) {
			for (Clause child : posC.children) {
				if (child.operator == Operator.NOT) {

				} else {
					posLiteral = child;
					break;
				}
			}
		} else {
			return posC;
		}
		return posLiteral;
	}

	public static Clause getMatchNegLiteral(Clause posLiteral, Clause negClause) {
		Clause matchingLiteral = null;
		String posLitFnName = posLiteral.children.get(0).name;
		Operator operator = negClause.operator;
		if (operator == Operator.NOT) {
			Clause negPartOfClause = negClause.children.get(0);
			String negLiteralFnName = negPartOfClause.children.get(0).name;
			if (negLiteralFnName.equals(posLitFnName)) {
				if (negPartOfClause.getSize() == posLiteral.getSize()) {
					matchingLiteral = negClause;
					return matchingLiteral.children.get(0);
				}
			}
		} else if (operator == Operator.OR) {
			String childFnName;
			for (Clause child : negClause.children) {
				// child is a neg literal,check it can be matched.
				Clause negPartOfClause = child.children.get(0);
				childFnName = negPartOfClause.children.get(0).name;
				if (childFnName.equals(posLitFnName)) {
					// both function names are same.now check for arity
					if (negPartOfClause.getSize() == posLiteral.getSize()) {
						// literals matched.
						matchingLiteral = child;
						return matchingLiteral.children.get(0);
					}
				}
			}
		}
		return matchingLiteral;
	}

	public static Clause applySub(Clause c, Map<String, Clause> Submap) {
		Clause sub = c.getCopy();
		int type = sub.type;
		switch (type) {
		case DEFAULT_TYPE:
			List<Clause> subChilds = new ArrayList<Clause>();
			for (Clause child : sub.children) {
				Clause subChild = applySub(child, Submap);
				subChild.parent = sub;
				subChilds.add(subChild);
			}
			sub.children = subChilds;
			break;
		case FN_TYPE:
			// no need to apply
			break;
		case VAR_TYPE:
			String varName = sub.name;
			if (Submap.containsKey(varName)) {
				Clause value = Submap.get(varName);
				value = applySub(value, Submap);
				sub = value;
			}
			break;
		case CONST_TYPE:
			// no need to apply
			break;
		default:
			break;
		}
		return sub;
	}

	public static Clause getResolvedClause(Clause posClause, Clause negClause) {
		Clause resolvedClause = new Clause();
		if (posClause.operator == null && negClause.operator == Operator.NOT) {
			Clause literalInNegClause = negClause.children.get(0);
			if (literalInNegClause.equals(posClause)) {
				// sending empty clause
				return EMPTY_CLAUSE;
			} else {
				System.err.println("Error in case 1");
			}
			// case 1
		} else if (posClause.operator == Operator.OR
				&& negClause.operator == Operator.NOT) {
			// case 2
			Clause literalInNegClause = negClause.children.get(0);
			for (Iterator<Clause> iterator = posClause.children.iterator(); iterator
					.hasNext();) {
				Clause posClauseChild = iterator.next();
				if (posClauseChild.operator == Operator.NOT) {
					// negative literal
				} else {
					// positive literal
					if (posClauseChild.equals(literalInNegClause)) {
						iterator.remove();
					} else {
						System.err.println("Error in case 2");
					}
				}
			}
			if (posClause.children.size() == 1) {
				// resolvent should be of size 1
				if (posClause.children.size() == 1) {
					resolvedClause = posClause.children.get(0);
					return resolvedClause;
				} else {
					System.err.println("Error in case 2");
				}
			} else {
				// size >= 2
				resolvedClause.operator = Operator.OR;
				for (Clause posClauseChild : posClause.children) {
					resolvedClause.children.add(posClauseChild);
				}
				return resolvedClause;
				// resolvent should be of size >= 2
			}
		} else if (posClause.operator == null
				&& negClause.operator == Operator.OR) {
			// case 3
			for (Iterator<Clause> iterator = negClause.children.iterator(); iterator
					.hasNext();) {
				Clause negClauseChild = iterator.next();
				Clause literalInNegClauseChild = negClauseChild.children.get(0);
				if (literalInNegClauseChild.equals(posClause)) {
					iterator.remove();
				}
			}
			if (negClause.children.size() == 1) {
				// resolvent of size 1
				resolvedClause = negClause.children.get(0);
				return resolvedClause;
			} else {
				// resolvent of size 2
				resolvedClause = negClause;
				return resolvedClause;
			}
		} else {
			// case 4 where both operators are OR
			resolvedClause.operator = Operator.OR;
			for (Clause childInPosClause : posClause.children) {
				Operator operator = childInPosClause.operator;
				if (operator == Operator.NOT) {
					resolvedClause.children.add(childInPosClause);
					childInPosClause.parent = resolvedClause;
				} else {
					// positive literal. Its match should be there
					for (Iterator<Clause> iterator = negClause.children
							.iterator(); iterator.hasNext();) {
						Clause negClauseChild = (Clause) iterator.next();
						Clause literalInNegClauseChild = negClauseChild.children
								.get(0);
						if (literalInNegClauseChild.equals(childInPosClause)) {
							// remove from negClause.children
							iterator.remove();
						}

					}
				}
			}
			// add the remaining clauses in negClause.children() to
			// resolvedClause
			for (Clause clause : negClause.children) {
				resolvedClause.children.add(clause);
			}
		}
		return resolvedClause;
	}
	public boolean equals(Object obj) {
		if(obj instanceof Clause) {
			Clause clause = (Clause) obj;
			if(clause.operator == operator) {
				if(clause.name.equals(name)) {
					if(clause.type == type) {
						List<Clause> clauseChilds = clause.children;
						if(clauseChilds.size() == children.size()) {
							for(int i=0;i<clauseChilds.size();i++) {
								Clause child = children.get(i);
								Clause clauseChild = clauseChilds.get(i);
								if(child.equals(clauseChild)) {
									
								} else {
									return false;
								}
							}
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return false;
	}
}
