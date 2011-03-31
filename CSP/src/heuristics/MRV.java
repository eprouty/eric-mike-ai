package heuristics;

import java.util.ArrayList;

import data.CNet;
import data.ConstraintMatrix;
import data.Item;

public class MRV {
	public static Item getNextItem(CNet cnet, ArrayList<Item> itemList){
		int minimum = 10000000, temp;
		Item min = null;
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
