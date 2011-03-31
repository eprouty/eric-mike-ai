package search;

import data.CNet;
import data.Bag;
import data.Item;
import java.util.ArrayList;
import java.util.HashMap;

public class Backtrack 
{
	public static boolean backtracking(ArrayList<Bag> bags, ArrayList<Item> items, HashMap<Item,Bag> assignment, CNet net)
	{
		if (items.size()==0) return true;
		for (int i=0; i<bags.size(); i++)
		{
			for (int j=0; j<items.size(); j++)
			{
				if (consistent(bags.get(bags.size()-1), items.get(j), assignment, net))
				{
					assignment.put(items.get(j), bags.get(bags.size()-1));
					bags.get(bags.size() - 1).addItem(items.get(j));
					ArrayList<Item> itemsCopy = deepCopy(items);
					ArrayList<Bag> bagsCopy = deepCopyBag(bags);
					itemsCopy.remove(j);
					if (backtracking(bagsCopy,itemsCopy,assignment,net))
					{
						return true;
					}
				}
				assignment.remove(items.get(j));
			}
			bags.remove(0);
		}
		return false;
	}
	public static ArrayList<Item> deepCopy(ArrayList<Item> list)
	{
		ArrayList<Item> copy=new ArrayList<Item>();
		for(Item item:list)
		{
			Item copyItem=new Item(item.name, item.weight);
			copy.add(copyItem);
		}
		return copy;
	}
	public static ArrayList<Bag> deepCopyBag(ArrayList<Bag> list)
	{
		ArrayList<Bag> copy=new ArrayList<Bag>();
		for(Bag bag:list)
		{
			Bag copyItem=new Bag(bag.name, bag.size);
			copy.add(copyItem);
		}
		return copy;
	}
	public static boolean consistent(Bag bag, Item item, HashMap<Item, Bag> assignments, CNet net)
	{
		//TODO: Implement this method, which checks if a 'value is consistent with an assignment'
		return net.checkConsistency(item, bag, assignments);
	}
}
