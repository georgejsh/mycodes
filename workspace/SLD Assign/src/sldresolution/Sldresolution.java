package sldresolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import representation.Clause;
import representation.Sldinfo;
import unification.Unification;
import unification.Unification.UnifyInfo;

public class Sldresolution {

	public static boolean isResolvable(List<Clause> input, Sldinfo sl) {
		List<Clause> posClause = new ArrayList<Clause>();
		List<Clause> negClause = new ArrayList<Clause>();
		boolean isGoalNegative = false;
		for (int i = 0; i < input.size(); i++) {
			Clause c = input.get(i);
			if (c.ispositive()) {
				if (i == input.size() - 1) {
					isGoalNegative = false;
					posClause.add(0, c);
				} else
					posClause.add(c);
			} else if (c.isnegative()) {
				if (i == input.size() - 1) {
					isGoalNegative = true;
					//System.out.println(isGoalNegative);
					negClause.add(0, c);
				} else
					negClause.add(c);
			}
		}
		//System.out.println(posClause.size());
	 boolean flag=isSLDResolvable(posClause, negClause, isGoalNegative, sl);
	 //System.out.println(flag);
	 return flag;
	}

	public static boolean isSLDResolvable(List<Clause> pos, List<Clause> neg,
			boolean isGoalNegative, Sldinfo sl) {
		//System.out.println(isGoalNegative);
		if (isGoalNegative) {
			boolean flag = false;
			System.out.println(neg.size());
			for (Clause nc : neg) {
				//System.out.println(nc.type);
				flag = isSLDres(pos, nc, sl);
				//System.out.println(flag);
				if (flag)
					break;
			}
			//System.out.println(flag);
			return flag;
		} else {
			for (Clause pClause : pos) {
				Clause posLiteral = Clause.getPosLiteral(pClause);
				for (Clause nClause : neg) {
					Clause negativeLiteral = Clause.getMatchNegLiteral(posLiteral, nClause);
					if (negativeLiteral == null)
						continue;
					UnifyInfo ui = Unification.unify(posLiteral,
							negativeLiteral);
					if (ui.isUnified) {
						Clause resolved = GetResolvedClause(nClause, sl,
								pClause, ui);
						if (resolved == Clause.EMPTY_CLAUSE)
							return true;
						else {
							Sldinfo childResnInfo = new Sldinfo();
							childResnInfo.parent = sl;
							sl.child = childResnInfo;
							boolean isResolved = isSLDres(pos, resolved,
									childResnInfo);
							if (isResolved) {
								return true;
							} else
								sl.clearAttributes();
						}
					}
				}
			}
			return false;
		}
	}

	public static boolean isSLDres(List<Clause> pos, Clause neg, Sldinfo sl) {
		Clause posC = null;
		for (int i = 0; i < pos.size(); i++) {
			posC = pos.get(i);
			Clause posLiteral = Clause.getPosLiteral(posC);
			if (posLiteral != null) {
				//System.out.print(posLiteral.children.size());
				Clause negLiteral = Clause.getMatchNegLiteral(posLiteral, neg);
				if (negLiteral == null)
					continue;
				//System.out.println(negLiteral.children.size());
				UnifyInfo ui = Unification.unify(posLiteral, negLiteral);
				//System.out.print(posLiteral.type);
				//System.out.println(negLiteral.type);
				//System.out.println(ui.isUnified);
				if (ui.isUnified) {
					//System.out.println(ui.isUnified);
					Clause resolved = GetResolvedClause(neg, sl, posC, ui);
					//System.out.println(resolved.type);
					if (resolved == Clause.EMPTY_CLAUSE)
						return true;
					else {
						Sldinfo childResnInfo = new Sldinfo();
						childResnInfo.parent = sl;
						sl.child = childResnInfo;
						boolean isResolved = isSLDres(pos, resolved,childResnInfo);
						System.out.println(isResolved);
						if (isResolved) {
							return true;
						} else
							sl.clearAttributes();
					}
				} else
					sl.clearAttributes();
			}
		}
		return false;
	}

	public static Clause GetResolvedClause(Clause neg, Sldinfo sl, Clause pos,
			UnifyInfo ui) {
		Clause posCSub = Clause.applySub(pos, ui.subMap);
		Clause negCSub = Clause.applySub(neg, ui.subMap);
		Clause resClause = Clause.getResolvedClause(posCSub, negCSub);
		sl.posC = pos;
		sl.negC = neg;
		sl.resC = resClause;
		if (!ui.subMap.isEmpty()) {
			for (Map.Entry<String, Clause> entry : ui.subMap.entrySet()) {
				String key = entry.getKey();
				if (Clause.isVarOccurInCla(pos, key)) {
					sl.subMapPos.put(key, entry.getValue());
				}
			}
			for (Map.Entry<String, Clause> entry : ui.subMap.entrySet()) {
				String key = entry.getKey();
				if (Clause.isVarOccurInCla(neg, key)) {
					sl.subMapNeg.put(key, entry.getValue());
				}
			}
		}
		return resClause;
	}
}

