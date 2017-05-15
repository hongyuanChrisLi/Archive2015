package custom;

import java.util.Comparator;

import infa.ObjContainer;

public class CustComparator implements Comparator<ObjContainer> {
	
	@Override
	public int compare(ObjContainer o1, ObjContainer o2) {
		// TODO Auto-generated method stub
		String str1 = o1.getValue("folder") + o1.getValue("type") + o1.getValue("object");
		String str2 = o2.getValue("folder") + o2.getValue("type") + o2.getValue("object");
		return str1.compareTo(str2);
	}
	
}

