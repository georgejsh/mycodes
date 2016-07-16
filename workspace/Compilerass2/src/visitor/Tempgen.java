package visitor;
import java.util.*;

public class Tempgen{
	
	public Map<String , Boolean> symtab = new HashMap<String,Boolean>();
	 public Integer temp=-1;
	public String getNext() {
		temp++;
		return "_TC_Java_"+temp.toString();
	}
	public void add(String st,Boolean type) {
		symtab.put(st,type);
		return;
	}
	public String genDeclarations() {
	    Iterator it = symtab.entrySet().iterator();
	    String declare="";
	    while (it.hasNext()) {
		Map.Entry<String, Boolean>pair = (Map.Entry<String, Boolean>)it.next();
		if(pair.getValue()==true)
			declare+="boolean " +pair.getKey() +";\n" ;
		else
			declare+="int " +pair.getKey() +";\n" ;
		it.remove(); // avoids a ConcurrentModificationException
	    }
	    return declare;	
	}
}
