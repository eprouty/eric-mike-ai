package heuristics;

import java.util.ArrayList;

import data.CNet;
import data.ConstraintMatrix;
import data.Item;

public class MRV {
	public static Item getNextItem(CNet cnet, ArrayList<Item> itemList){
		int minimum = 10000000, temp;
		Item min = null;
		//check the constraint matrixes... whichever has the fewest true values in it has MRV
		for (ConstraintMatrix cm : cnet.getArcs()){
			if ((temp = cm.getMinimum()) < minimum){
				if (itemList.contains(cnet.getItem(cm.getItem(1)))){
					minimum = temp;
					min = cnet.getItem(cm.getItem(1));
				} else if (itemList.contains(cnet.getItem(cm.getItem(2)))){
					minimum = temp;
					min = cnet.getItem(cm.getItem(2));
				}
			}
		}
		//Not all items are bounded within constraint matrixes, this will pull out any other items that have not been found yet in the proper MRV order
		if (min == null){
			for (Item i : itemList){
				if (i.getValidBags().length < minimum){
					minimum = i.getValidBags().length;
					min = i;
				}
			}
		}
		itemList.remove(min);
		return min;
	}
}
