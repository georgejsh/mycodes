package krr.sldresn.sld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import krr.sldresn.model.Clause;
import krr.sldresn.model.SLDResnInfo;
import krr.sldresn.unification.Unifier;
import krr.sldresn.unification.Unifier.UnifyInfo;

public class SLDResn {
	/**
	 * 
	 * @param clauses - last clause is the negated goal clause.
	 * 
	 * @return
	 */
	public static boolean isSLDResolvable(List<Clause> clauses,SLDResnInfo sldResnInfo) {
		//seperate the clauses into positive and negative clauses.
		List<Clause> posClauses = new ArrayList<Clause>();
		List<Clause> negClauses = new ArrayList<Clause>();
		boolean isNegGoalNegative = false;
		for (int i=0;i<clauses.size();i++) {
			Clause clause = clauses.get(i);
			if(clause.isPositiveClause()) {
				if(i  == (clauses.size()-1)) {
					//goal clause added at start
					posClauses.add(0,clause);
					isNegGoalNegative = false;
				} else {
					posClauses.add(clause);
				}
			} else if(clause.isNegativeClause()) {
				if(i  == (clauses.size()-1)) {
					//negative goal clause added at start
					negClauses.add(0,clause);
					isNegGoalNegative = false;
				} else {
					negClauses.add(clause);
				}
			}
		}
		return isSLDResolvable(posClauses,negClauses,isNegGoalNegative,sldResnInfo);
	}
	/**
	 * Vacuously true cases(where SLD resolution may not require the negated goal) is also handled.
	 * it is require
	 * @param posClauses - list of positive clauses.
	 * @param negClauses - list of negative clauses
	 * @param isNegGoalNegative - whether the negated goal is positive or negative clause.
	 * @param sldResnInfo - information regarding SLD derivation
	 * @return
	 */
	private static boolean isSLDResolvable(List<Clause> posClauses,
			List<Clause> negClauses,boolean isNegGoalNegative, SLDResnInfo sldResnInfo) {
		if(isNegGoalNegative) {
			//goal is a negative clause.
			boolean isSLDResolved = false;
			//negative goal clause occurs at the start of the list and SLD resolution is applied
			//first to the negated goal 
			for (Clause negClause : negClauses) {
				isSLDResolved = isSLDResolvable(posClauses, negClause,sldResnInfo);
				if(isSLDResolved)
					break;
			}
			return isSLDResolved;
		} else {
			//goal is a +ve clause
			//positive goal clause occurs at the start of the list and SLD resolution is applied
			//first to the negated goal 
			for (Clause posClause : posClauses) {
				Clause posLiteral = Clause.getPosLiteralInPosClause(posClause);
				for (Clause negClause : negClauses) {
					Clause negativeLiteral = Clause.getMatchLiteralInNegClause(posLiteral, negClause);
					if(negativeLiteral == null) {
						continue;
					}
					//unify the literals which should get cancelled.
					UnifyInfo unifyInfo = Unifier.unify(posLiteral, negativeLiteral);
					if(unifyInfo.isUnified) {
						//get resolved clause if unification was successful.
						Clause resolvedClause = getResolvedCauseAfterUpdatingSLdResolutionInfo(
								negClause, sldResnInfo, posClause, unifyInfo);
						if(resolvedClause == Clause.EMPTY_CLAUSE) {
							return true;
						} else {
							//continue with the new resolved clause.
							SLDResnInfo childResnInfo = new SLDResnInfo();
							childResnInfo.parent = sldResnInfo;
							sldResnInfo.child = childResnInfo;
							
							boolean isResolved = isSLDResolvable(posClauses, resolvedClause,childResnInfo);
							if(isResolved) {
								return true;
							} else {
								//clear the goal tree as resolution was unsuccessful.
								sldResnInfo.clearAttributes();
							}
						}
					}
				}
				
			}
			return false;
		}
	}
	
	/**
	 * Returns true is the negative clause was successfully resolved using the positive clauses. 
	 * The negative clause will be applied to a positive clause resulting in a resolved clause which
	 * is again negative and this method is recursively called again.
	 * 
	 * @param posClauses - list of positive clauses.
	 * @param negClause - negative clause
	 * @param sldResnInfo - information regarding SLD derivation
	 * @return
	 */
	private static boolean isSLDResolvable(List<Clause> posClauses,	Clause negClause,SLDResnInfo sldResnInfo) {
		Clause posClause = null;
		for(int i=0;i<posClauses.size();i++) {
			posClause = posClauses.get(i);
			//search for a matching literal in positive clause which 
			//must exist as negative literal in the negative clause.
			Clause posLiteral = Clause.getPosLiteralInPosClause(posClause);
			if(posLiteral != null) {
				Clause negativeLiteral = Clause.getMatchLiteralInNegClause(posLiteral, negClause);
				if(negativeLiteral == null) {
					continue;
				}
				UnifyInfo unifyInfo = Unifier.unify(posLiteral, negativeLiteral);
				if(unifyInfo.isUnified) {
					Clause resolvedClause = getResolvedCauseAfterUpdatingSLdResolutionInfo(
							negClause, sldResnInfo, posClause, unifyInfo);
					if(resolvedClause == Clause.EMPTY_CLAUSE) {
						//empty clause derived. So return true;
						return true;
					} else {
						//continue with the new resolved clause.
						SLDResnInfo childResnInfo = new SLDResnInfo();
						childResnInfo.parent = sldResnInfo;
						sldResnInfo.child = childResnInfo;
						
						//negClauses.add(0, resolvedClause);
						boolean isResolved = isSLDResolvable(posClauses, resolvedClause,childResnInfo);
						if(isResolved) {
							return true;
						} else {
							sldResnInfo.clearAttributes();
						}
					}
				} else {
					//continue with the remaining + clauses.
					//So initialize info
					sldResnInfo.clearAttributes();
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the resolved clause after applying substitution on the negative clause 
	 * and the positive clause if unification was successful
	 * 
	 * @param negClause - negative clause
	 * @param sldResnInfo - information regarding SLD derivation
	 * @param posClause - positive clause
	 * @param unifyInfo - contains substitution info
	 * @return
	 */
	private static Clause getResolvedCauseAfterUpdatingSLdResolutionInfo(
			Clause negClause, SLDResnInfo sldResnInfo, Clause posClause,
			UnifyInfo unifyInfo) {
		//unification successful.
		//now apply substitution
		Clause posClauseSub = Clause.applySubstitution(posClause, unifyInfo.subMap);
		Clause negClauseSub = Clause.applySubstitution(negClause, unifyInfo.subMap);
		//Resolve them to get a new neg clause.
		Clause resolvedClause = Clause.getResolvedClause(posClauseSub, negClauseSub);
		
		//update sldResnInfo
		sldResnInfo.posClause = posClause;
		sldResnInfo.negClause = negClause;
		sldResnInfo.resClause = resolvedClause;
		
		//update substitution information 
		if(!unifyInfo.subMap.isEmpty()) {
			for (Map.Entry<String, Clause> entry : unifyInfo.subMap.entrySet()) {
				String key = entry.getKey();
				if(Clause.isVariableOccursInClause(posClause, key)) {
					sldResnInfo.subMapPosClause.put(key, entry.getValue());
				}
			}
			for (Map.Entry<String, Clause> entry : unifyInfo.subMap.entrySet()) {
				String key = entry.getKey();
				if(Clause.isVariableOccursInClause(negClause, key)) {
					sldResnInfo.subMapNegClause.put(key, entry.getValue());
				}
			}
		}
		return resolvedClause;
	}
}
