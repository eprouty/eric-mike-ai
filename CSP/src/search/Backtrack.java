package search;

import data.CNet;
import data.Bag;
import data.Item;
import heuristics.MRV;

import java.util.ArrayList;
import java.util.HashMap;

public class Backtrack 
{
	public static boolean backtracking(ArrayList<Bag> bags, ArrayList<Item> items, HashMap<Item,Bag> assignment, CNet net)
	{
		if (items.size()==0)
		{
			int itemCount=0;
			float weight=0;
			for (int i=0; i<bags.size(); i++)
			{
				itemCount=bags.get(i).items.size();
				if (itemCount<net.lowerLim || itemCount>net.upperLim)
				{
					return false;
				}
				weight=0;
				for (int j=0; j<bags.get(i).items.size(); j++)
				{
					weight+=bags.get(i).items.get(j).weight;
				}
				if (weight/(weight+bags.get(i).size)<0.9) return false;
			}
			return true;
		}
		Item i = null;
		while ((i = MRV.getNextItem(net, items)) != null)
		{
			bags = i.getBags(net);
			sortByLCV(bags,net);
			for (int j=0; j<bags.size(); j++)
			{
				
				boolean consistency=consistent(bags.get(j), i, assignment, net);
				//boolean weightCheck=bags.get(j).addItem(items.get(i));
				if (consistency)
				{
					bags.get(j).addItem(i);
					assignment.put(i, bags.get(j));
					//System.out.println("Assigned " + i.name + " " + bags.get(j).name);
					ArrayList<Item> itemsCopy = deepCopy(items);
					//ArrayList<Bag> bagsCopy = deepCopyBag(bags);
					itemsCopy.remove(i);
					if (backtracking(bags,itemsCopy,assignment,net))
					{
						if (net.lowerLimMet()){
							return true;
						} else {
							return false;
						}
					}
					bags.get(j).removeItem(i);
					//System.out.println("Removed " + i.name + " " + bags.get(j).name);
				}
				assignment.remove(i);
			}
			return false;
		}
		return false;
	}
	public static void sortByLCV(ArrayList<Bag> bags, CNet cnet)
	{
		int minValue=1000, minPos=-1, value;
		for (int i=0; i<bags.size(); i++)
		{
			minValue=1000; minPos=-1;
			for (int j=i; j<bags.size()-1; j++)
			{
				value=cnet.countIntersections(bags.get(j));
				if (value<minValue)
				{
					minPos=j;
					minValue=value;
				}
			}
			if (minPos!=-1)
			{
				swap(bags,minPos,i);
			}
		}
	}
	public static void swap(ArrayList<Bag> bags, int pos1, int pos2)
	{
		Bag swapBag=bags.get(pos1);
		bags.remove(pos1);
		bags.add(pos1,bags.get(pos2));
		bags.remove(pos2);
		bags.add(pos2,swapBag);
	}
	public static ArrayList<Item> deepCopy(ArrayList<Item> list)
	{
		ArrayList<Item> copy=new ArrayList<Item>();
		for(Item item:list)
		{
			Item copyItem=new Item(item.name, item.weight);
			copyItem.setValidBags(item.validBags);
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
			copyItem.setItems(bag.items);
			copy.add(copyItem);
		}
		return copy;
	}
	public static boolean consistent(Bag bag, Item item, HashMap<Item, Bag> assignments, CNet net)
	{
		return net.checkConsistency(item, bag, assignments);
	}
}
