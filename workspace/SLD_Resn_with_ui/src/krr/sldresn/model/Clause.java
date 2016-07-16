package krr.sldresn.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import krr.sldresn.Utils;
import krr.sldresn.exceptions.ImproperClauseException;

/**
 * Model object similar to list object in charniak and mcdermott.
 * 
 */

public class Clause {
	
	/**
	 * Various types of clauses. LIST IS DEFAULT TYPE
	 */
	public static final int DEFAULT_TYPE = 0;
	public static final int FN_TYPE = 1;
	public static final int VAR_TYPE = 2;
	public static final int CONST_TYPE = 3;
	//Singleton instance of empty clause.
	public static final Clause EMPTY_CLAUSE = new Clause() {
		public String toString() {
			return "[]";
		};
	};
	//counter incremented when an instance of clause is created.
	public static int ID_NUMBER = 1;
	
	/**
	 * parent of the clause.
	 */
	public Clause parent;
	/**
	 * children of the clause.
	 */
	public List<Clause> children = new ArrayList<Clause>();
	/**
	 * AND/OR/NOT or null if operator is not present.
	 */
	public Operator operator = null;
	/**
	 * Name of the function/variable or constant depending on type of the clause.
	 * If type is DEFAULT_TYPE then name is empty string.
	 */
	public String name = "";
	/**
	 * The type of the clause.
	 */
	public int type = DEFAULT_TYPE;
	/**
	 * Unique id used as name of node in dot file.
	 */
	public final int uniqID;
	
	public Clause() {
		uniqID = ID_NUMBER;
		ID_NUMBER++;
	}
	
	public Clause(Clause parent) {
		this.parent = parent;
		uniqID = ID_NUMBER;
		ID_NUMBER++;
	}
	/**
	 * 
	 * @param clause
	 * @return copy of the clause without copying parent.
	 */
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
	 * Checks if two clauses are equal.
	 */
	@Override
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
	
	/**
	 * 
	 * @return true if the clause is a positive clause,else false.
	 */
	public boolean isPositiveClause() {
		if(type == DEFAULT_TYPE ) {
			if(operator == Operator.NOT) {
				return false;
			} else if(operator == Operator.OR) {
				//exactly one positive
				int noOfPosClauses = 0;
				for (Clause child : children) {
					Operator childOperator = child.operator;
					if(childOperator == Operator.NOT) {
						
					} else if(childOperator == Operator.AND) {
						//should not happen
					} else if(childOperator == null){
						//No Operator
						noOfPosClauses++;
					}
				}
				if(noOfPosClauses == 1) {
					return true;
				} else {
					return false;
				}
			} else if(operator == null) {
				//handle +ve atomic literals
				Clause child1 = children.get(0);
				if(child1.type == FN_TYPE) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return true if clause is of correct syntax for SLD resolution.
	 */
	public boolean isValidClause() {
		return isValidClause(false);
	}
	
	/**
	 * 
	 * @param isOrSeen - whether OR operator has already seen in its ancestor clauses.
	 * @return true if clause is of correct syntax for SLD resolution.
	 */
	private boolean isValidClause(boolean isOrSeen) {
		if(type == DEFAULT_TYPE) {
			if(operator == Operator.OR) {
				if(isOrSeen) {
					return false;
				}
				boolean isValid = true;
				for (Clause child : children) {
					isValid = isValid && child.isValidClause(true);
				}
				return isValid;
			} else if(operator == Operator.NOT) {
				Clause clause = children.get(0);
				return clause.isValidClause(isOrSeen);
			} else if(operator == null) {
				boolean isValid = true;
				for (Clause child : children) {
					isValid = isValid && child.isValidClause(isOrSeen);
				}
				return isValid;
			} else if(operator == Operator.AND) {
				return false;
			}
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @return true if the clause is a positive clause,else false.
	 */
	public boolean isNegativeClause() {
		if(type == DEFAULT_TYPE ) {
			if(operator == Operator.NOT) {
				return true;
			} else if(operator == Operator.OR) {
				//all negative
				int noOfPosClauses = 0;
				for (Clause child : children) {
					Operator childOperator = child.operator;
					if(childOperator == Operator.NOT) {
						
					} else if(childOperator == Operator.AND) {
						//should not happen
					} else if(operator == null) {
						//No Operator
						noOfPosClauses++;
					}
				}
				if(noOfPosClauses == 0) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;	
	}
	
	/**
	 * 
	 * @return no of child clauses.
	 */
	public int getSize() {
		return children.size();
	}
	
	/**
	 * add child clause.
	 * @param clause
	 */
	public void addClause(Clause clause) {
		children.add(clause);
		clause.parent = this;
	}
	
	public static boolean isVariableOccursInClause(Clause clause,String varName) {
		boolean isVarInClause = false;
		if(clause.type == VAR_TYPE) {
			if(varName.equals(clause.name)) {
				return true;
			}
			
		} else if(clause.type == DEFAULT_TYPE) {
			for (Clause child : clause.children) {
				isVarInClause = isVarInClause || isVariableOccursInClause(child, varName);
			}
		}
		return isVarInClause;
	}
	/**
	 * 
	 * @param str - String to parse to form the clause
	 * @param parent - Parent of the clause to be formed.
	 * @return - clause formed by parsing the string
	 * @throws ImproperClauseException - if syntax of the clause is incorrect.
	 */
	public static Clause getClauseFromString(String str,Clause parent) throws ImproperClauseException{
		
		Clause clause = new Clause(parent);
		int fromIndex = 0;
		int indexOf = str.indexOf("(", fromIndex);
		//if(indexOf != -1) {
			if(indexOf == 0) {
				//starting is ( so end should be )
				char lastChar = str.charAt(str.length()-1);
				if(lastChar != ')')
					throw new ImproperClauseException();
				str = str.substring(1, str.length()-1);
				return getClauseFromString(str,parent);
			} else {
				//need to process the string
				StringTokenizer st = new StringTokenizer(str);
				
				String token = null;
				int noOfStrings = 0;
				int lastSeenIndexStartingBrackets = -1;
				int curIdxStartingBrackets;
				String subClauseStr;
				while (st.hasMoreTokens()) {
					//form child clauses.
					token = st.nextToken();
					if(token == "(" ||token.startsWith("(")) {
						curIdxStartingBrackets = str.indexOf("(", lastSeenIndexStartingBrackets+1);
						int matchingBracket = Utils.getMatchingBracket(str, curIdxStartingBrackets);
						if(matchingBracket == -1) {
							throw new ImproperClauseException();
						}
						subClauseStr = str.substring(curIdxStartingBrackets+1,matchingBracket).trim();
						//child clause
						Clause clauseFromString = getClauseFromString(subClauseStr, clause);
						clause.addClause(clauseFromString);
						lastSeenIndexStartingBrackets = curIdxStartingBrackets;
						//update string tokenizer
						if(matchingBracket >= str.length()-1) {
							return clause;
						} else {
							str = str.substring(matchingBracket +1).trim();
							lastSeenIndexStartingBrackets = -1;
							st = new StringTokenizer(str);
						}
					} else if(token == ")") {;
						
					} else {
						//it is a string
						noOfStrings++;
						if(noOfStrings == 1) {
							Operator operatorByName = Operator.getOperatorByName(token);
							if(operatorByName != null) {
								clause.operator = operatorByName;
							} else {
								//its a fn
								Clause fnClause = new Clause(clause);
								fnClause.name = token;
								fnClause.type = FN_TYPE;
								clause.addClause(fnClause);
							}
							
						} else {
							//either a variable or a constant
							boolean isVar = false; 
							if(token.startsWith("?")) {
								token=token.substring(1);
								isVar = true;
							}
							if(isVar) {
								//its a variable
								Clause varClause = new Clause(clause);
								varClause.name = token;
								varClause.type = VAR_TYPE;
								clause.addClause(varClause);
							} else {
								//its a constant
								Clause constClause = new Clause(clause);
								constClause.name = token;
								constClause.type = CONST_TYPE;
								clause.addClause(constClause);
							}
							
						}
					}
				}
			}
		return clause;
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
	/**
	 * 
	 * @param posClause
	 * @return Only 1 pos literal shud be there
	 */
	public static Clause getPosLiteralInPosClause(Clause posClause) {
		Clause posLiteral = null;
		if(posClause.operator == Operator.OR) {
			for (Clause child : posClause.children) {
				if(child.operator == Operator.NOT) {
					
				} else {
					posLiteral = child;
					break;
				}
			}
		} else {
			//handle standalone terminals
			return posClause;
		}
		return posLiteral;
	}
	
	/**
	 * 
	 * @param posLiteral (p x)
	 * @param negClause (not (p raju)) can be of length 1(NOT operator) or more(OR operator)
	 * @return matchingLiteral with not operator not included
	 */
	public static Clause getMatchLiteralInNegClause(Clause posLiteral,Clause negClause) {
		Clause matchingLiteral = null;
		String posLitFnName = posLiteral.children.get(0).name;
		Operator operator = negClause.operator;
		if(operator == Operator.NOT) {
			Clause negPartOfClause = negClause.children.get(0);
			String negLiteralFnName = negPartOfClause.children.get(0).name;
			if(negLiteralFnName.equals(posLitFnName)) {
				if(negPartOfClause.getSize() == posLiteral.getSize()) {
					//literals matched.
					matchingLiteral = negClause;
					return matchingLiteral.children.get(0);
				}
			}
		} else if(operator == Operator.OR) {
			String childFnName;
			for (Clause child : negClause.children) {
				//child is a neg literal,check it can be matched.
				Clause negPartOfClause = child.children.get(0);
				childFnName = negPartOfClause.children.get(0).name;;
				if(childFnName.equals(posLitFnName)) {
					//both function names are same.now check for arity
					if(negPartOfClause.getSize() == posLiteral.getSize()) {
						//literals matched.
						matchingLiteral = child;
						return matchingLiteral.children.get(0);
					}
				}
			}
		}
		return matchingLiteral;
	}
	
	/**
	 * Applies substitution to a clause
	 * @param clause - clause to apply substitution
	 * @param subMap - variables and their substitutable terms
	 * @return - clause after applying substitution
	 */
	public static Clause applySubstitution(Clause clause,Map<String, Clause> subMap) {
		Clause sub = clause.getCopy();
		int type = sub.type;
		switch (type) {
		case VAR_TYPE:
			String varName = sub.name;
			if(subMap.containsKey(varName)) {
				//sub exists
				Clause value = subMap.get(varName);
				//recursive substitution
				value = applySubstitution(value, subMap);
				//copy value back to sub
				sub = value;
			}
			break;
		case CONST_TYPE:
			//no need to apply sub
			break;
		case DEFAULT_TYPE:
			//apply substitution to its children
			List<Clause> subChilds = new ArrayList<Clause>();
			for (Clause child : sub.children) {
				Clause subChild = applySubstitution(child, subMap);
				subChild.parent = sub;
				subChilds.add(subChild);
			}
			sub.children = subChilds;
			break;
		case FN_TYPE:
			//no need to apply sub
			break;
		default:
			break;
		}
		return sub;
	}
	/**
	 * Takes a positive clause and a negative clause and returns the resolved clause.
	 * 
	 * Many cases arise
	 * 1) positive clause is of length 1,negative clause is of length 1 - checked working
	 * 2) positive clause is of length > 1,negative clause is of length 1 - checked working
	 * 3) positive clause is of length 1,negative clause is of length > 1 - checked working
	 * 4) positive clause is of length > 1,negative clause is of length > 1 - checked working
	 * @param posClause- Positive clause
	 * @param negClause - Negative clause
	 * @return - Resolved clause
	 */
	public static Clause getResolvedClause(Clause posClause,Clause negClause) {
		Clause resolvedClause = new Clause();
		if(posClause.operator == null && negClause.operator == Operator.NOT ) {
			Clause literalInNegClause = negClause.children.get(0);
			if(literalInNegClause.equals(posClause)) {
				//sending empty clause
				return EMPTY_CLAUSE;
			} else {
				System.err.println("Error in case 1");
			}
			//case 1
		} else if(posClause.operator == Operator.OR && negClause.operator == Operator.NOT) {
			//case 2
			Clause literalInNegClause = negClause.children.get(0);
			for (Iterator<Clause> iterator = posClause.children.iterator(); iterator
					.hasNext();) {
				Clause posClauseChild = iterator.next();
				if(posClauseChild.operator == Operator.NOT) {
					//negative literal
				} else {
					//positive literal
					if(posClauseChild.equals(literalInNegClause)) {
						iterator.remove();
					} else {
						System.err.println("Error in case 2");
					}
				}
			}
			if(posClause.children.size() == 1) {
				//resolvent should be of size 1
				if(posClause.children.size() == 1) {
					resolvedClause = posClause.children.get(0);
					return resolvedClause;
				} else {
					System.err.println("Error in case 2");
				}
			} else {
				//size >= 2
				resolvedClause.operator = Operator.OR;
				for (Clause posClauseChild : posClause.children) {
					resolvedClause.children.add(posClauseChild);
				}
				return resolvedClause;
				//resolvent should be of size >= 2
			}
		} else if(posClause.operator == null && negClause.operator == Operator.OR) {
			//case 3
			for (Iterator<Clause> iterator = negClause.children.iterator(); iterator
					.hasNext();) {
				Clause negClauseChild = iterator.next();
				Clause literalInNegClauseChild = negClauseChild.children.get(0);
				if(literalInNegClauseChild.equals(posClause)) {
					iterator.remove();
				}
			}
			if(negClause.children.size() == 1) {
				//resolvent of size 1
				resolvedClause = negClause.children.get(0);
				return resolvedClause;
			} else {
				//resolvent of size 2
				resolvedClause = negClause;
				return resolvedClause;
			}
		} else {
			//case 4 where both operators are OR
			resolvedClause.operator = Operator.OR;
			for (Clause childInPosClause : posClause.children) {
				Operator operator = childInPosClause.operator;
				if(operator == Operator.NOT) {
					resolvedClause.children.add(childInPosClause);
					childInPosClause.parent = resolvedClause;
				} else {
					//positive literal. Its match should be there
					for (Iterator<Clause> iterator = negClause.children.iterator(); iterator
							.hasNext();) {
						Clause negClauseChild = (Clause) iterator.next();
						Clause literalInNegClauseChild = negClauseChild.children.get(0);
						if(literalInNegClauseChild.equals(childInPosClause)) {
							//remove from negClause.children
							iterator.remove();
						}
						
					}
				}
			}
			//add the remaining clauses in negClause.children() to resolvedClause
			for (Clause clause : negClause.children) {
				resolvedClause.children.add(clause);
			}
		}
		return resolvedClause;
	}
}
