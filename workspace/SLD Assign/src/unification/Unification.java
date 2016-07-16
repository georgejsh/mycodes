package unification;
import java.util.HashMap;
import java.util.Map;
import representation.Clause;

public class Unification {
	public static class UnifyInfo {
		public Map<String, Clause> subMap = new HashMap<String, Clause>();
		public boolean isUnified = false;
	}

	public static UnifyInfo unify(Clause clause1, Clause clause2) {
		Map<String, Clause> subMap = new HashMap<String, Clause>();
		boolean flag = subunify(clause1, clause2, subMap);
		//System.out.println(clause1.type);
		//System.out.println(clause2.type);
		//System.out.println(flag);
		UnifyInfo unifyInfo = new UnifyInfo();
		unifyInfo.subMap = subMap;
		unifyInfo.isUnified = flag;
		return unifyInfo;
		// System.out.println(isUnified);
	}

	public static boolean subunify(Clause c1, Clause c2, Map<String, Clause> m) {
		//System.out.print(c1.type);
		//System.out.println(c2.type);
		// default=0,function=1,var=2,cnst=3
		if (c1.type == 0 && c2.type == 0) {
			if (c1.getSize() == c2.getSize()) {
				if (c1.operator == c2.operator) {
					boolean flag;
					//System.out.println(c1.getSize());
					for (int i = 0; i < c1.getSize(); i++) {
						flag = subunify(c1.children.get(i), c2.children.get(i),
								m);
						if (!flag)
							return false;
					}
					return true;
				} else
					return false;
			} else
				return false;
		} else if (c1.type == 3 && c2.type == 3) {
			//System.out.println(c1.name+c2.name);
			if (c1.name.equals(c2.name))
				return true;
			else
				return false;
		} else if (c1.type == 1 && c2.type == 1) {
			//System.out.println(c1.name);
			//System.out.println(c2.name);
			if (c1.name.equals(c2.name))
			{
				//System.out.println(true);
				return true;
			}
			else
				return false;
		} else if (c1.type == 2 || c2.type == 2) {
			if (c1.type == 2)
			{
				//System.out.println(c1.name+c2.name);
				return varUnify(c1, c2, m);
			}
			else
			{
			//System.out.println(c2.type);
			   return varUnify(c2, c1, m);
			}
		}
		return false;
	}

	public static boolean varUnify(Clause var, Clause pattern,
			Map<String, Clause> m) {
		String varname=var.name;
		//System.out.println(varname);
		if (isVarInClause(pattern, varname))
			return false;
		if (m.containsKey(varname))
		{
			System.out.println(var.name);
			return subunify(pattern,m.get(varname), m);
		}
		m.put(varname, pattern);
		return true;
	}

	public static boolean isVarInClause(Clause clause, String varName) {
		boolean temp = false;
		if (clause.type == 2) {
			if (varName.equals(clause.name)) {
				return true;
			}
		} else if (clause.type == 0) {
			for (Clause child : clause.children) {
				temp = temp || isVarInClause(child, varName);
			}
		}
		return temp;
	}
}
