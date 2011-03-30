package search;

import data.ConstraintMatrix;
import data.Item;

public class AC3 {
	public AC3(){
	}
	
	public static void reduceArc(Item item1, Item item2, ConstraintMatrix cm){
		char[] valid = item1.getValidBags();
		String validDomain = "";
		for (char c : valid){
			if (cm.checkValidity(c, 1)){
				validDomain += c;
			}
		}
		
		if (validDomain.isEmpty()){
			System.err.println("An item exists that cannot be placed into any bags!");
			System.exit(-1);
		}
		item1.setValidBags(validDomain.toCharArray());
		
		valid = item2.getValidBags();
		validDomain = "";
		
		for (char c : valid){
			if (cm.checkValidity(c, 2)){
				validDomain += c;
			}
		}
		
		if (validDomain.isEmpty()){
			System.err.println("An item exists that cannot be placed into any bags!");
			System.exit(-1);
		}
		item2.setValidBags(validDomain.toCharArray());
	}
}
