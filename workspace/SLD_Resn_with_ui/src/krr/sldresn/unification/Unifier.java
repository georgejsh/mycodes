package krr.sldresn.unification;

import static krr.sldresn.model.Clause.CONST_TYPE;
import static krr.sldresn.model.Clause.DEFAULT_TYPE;
import static krr.sldresn.model.Clause.FN_TYPE;
import static krr.sldresn.model.Clause.VAR_TYPE;
import static krr.sldresn.model.Clause.isVariableOccursInClause;

import java.util.HashMap;
import java.util.Map;

import krr.sldresn.model.Clause;

/**
 * Contains methods related to unification like unify,subunify,varunify
 * @author nithin
 *
 */
public class Unifier {
	//public static final String[] VAR_NAMES = {"X","Y","Z","V"};
	/**
	 * contains the information like whether unification was successful or not and the substitution
	 * 
	 * @author nithin
	 *
	 */
	public static class UnifyInfo {
		public Map<String, Clause> subMap = new HashMap<String, Clause>();
		public boolean isUnified = false;
	}
	
	public static UnifyInfo unify(Clause clause1,Clause clause2) {
		Map<String, Clause> subMap = new HashMap<String, Clause>();
		boolean isUnified = subUnify(clause1, clause2, subMap);
		UnifyInfo unifyInfo = new UnifyInfo();
		unifyInfo.subMap = subMap;
		unifyInfo.isUnified = isUnified;
		return unifyInfo;
		//System.out.println(isUnified);
	}
	
	public static boolean subUnify(Clause clause1,Clause clause2,Map<String, Clause> subMap) {
		if(clause1.type == DEFAULT_TYPE && clause2.type == DEFAULT_TYPE) {
			//both are lists
			if(clause1.getSize() == clause2.getSize()) {
				//chance of unifying
				if(clause1.operator == clause2.operator) {
					//chance of unifying
					boolean isChildUnified;
					for(int i=0;i<clause1.getSize();i++) {
						isChildUnified = subUnify(clause1.children.get(i),
								clause2.children.get(i), subMap);
						if(!isChildUnified) {
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
		} else if(clause1.type == CONST_TYPE && clause2.type == CONST_TYPE) {
			//both are constants. They must be same
			if(clause1.name.equals(clause2.name)) {
				return true;
			} else {
				return false;
			}
		} else if(clause1.type == FN_TYPE && clause2.type == FN_TYPE) {
			//both are fns. They must be same
			if(clause1.name.equals(clause2.name)) {
				return true;
			} else {
				return false;
			}
		} else if(clause1.type == VAR_TYPE || clause2.type == VAR_TYPE) {
			//call varunify
			if(clause1.type == VAR_TYPE) {
				return varUnify(clause1, clause2, subMap);
			} else {
				return varUnify(clause2,clause1,subMap);
			}
			
		}
			
		return false;
	}
	
	public static boolean varUnify(Clause varClause,Clause pattern,Map<String, Clause> subMap) {
		String varName = varClause.name;
		//check for variable in the pattern
		if(isVariableOccursInClause(pattern, varName)) {
			return false;
		}
		//check if already varClause has substitution. 
		if(subMap.containsKey(varName)) {
			return subUnify(pattern, subMap.get(varName), subMap);
		}
		subMap.put(varName, pattern);
		return true;
	}
	
	
	
	
}
