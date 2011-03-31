package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import search.AC3;

public class CNet {
	public int lowerLim, upperLim;
	//nodes
	ArrayList<Item> items = new ArrayList<Item>();
	static //domain
	ArrayList<Bag> bags = new ArrayList<Bag>();
	//arcs
	ArrayList<ConstraintMatrix> arcs = new ArrayList<ConstraintMatrix>();
	
	public CNet(){
	}
	
	public ArrayList<Bag> getBags(){
		return bags;
	}
	
	public ArrayList<ConstraintMatrix> getArcs(){
		return arcs;
	}
	
	public Item getItem(char name){
		for (Item i : items){
			if (i.name == name){
				return i;
			}
		}
		return null;
	}
	
	//sets information about how many items must be in a bag
	public void setLimits(int lowerLim, int upperLim){
		this.lowerLim = lowerLim;
		this.upperLim = upperLim;
	}
	
	//adds an item to the CNET
	public void addItem(char name, int weight){
		items.add(new Item(name, weight));
	}
	
	//adds a bag to the CNET
	public void addBag(char name, int size){
		bags.add(new Bag(name, size));
	}
	
	//this adds information about a unary inclusive constraint to an item
	public void addUnaryInclusive(char item, char[] bags){
		for (int i=0; i < items.size(); i++){
			if (items.get(i).name == item){
				items.get(i).setValidBags(bags);
				break;
			}
		}
	}
	
	//this add information about a unary exclusive constraint to an item
	public void addUnaryExclusive(char item, char[] bags){
		for (int i=0; i < items.size(); i++){
			if (items.get(i).name == item){
				String bString = getBagString();
				for (char c : bags){
					bString = bString.replace(String.valueOf(c), "");
				}
				items.get(i).setValidBags(bString.toCharArray());
				break;
			}
		}
	}
	
	//modifies or creates a ConstraintMatrix (arc in terms of a constraint net) to represent the binary equality constraint of two items
	public void addBinaryEquals(char item1, char item2){
		for (int i=0; i < arcs.size(); i++){
			if (arcs.get(i).item1 == item1 && arcs.get(i).item2 == item2){
				ConstraintMatrix cm = new ConstraintMatrix(item1, item2);
				cm.setBinaryEquality();
				arcs.add(cm);
				return;
			} else if (arcs.get(i).item1 == item2 && arcs.get(i).item2 == item1){
				ConstraintMatrix cm = new ConstraintMatrix(item2, item1);
				cm.setBinaryEquality();
				arcs.add(cm);
				return;
			}
		}
		ConstraintMatrix cm = new ConstraintMatrix(item1, item2);
		cm.setBinaryEquality();
		arcs.add(cm);
	}
	
	//modifies or creates a ConstraintMatrix (arc in terms of a constraint net) to represent the binary inequality constraint of two items
	public void addBinaryNotEquals(char item1, char item2){
		for (int i=0; i < arcs.size(); i++){
			if (arcs.get(i).item1 == item1 && arcs.get(i).item2 == item2){
				ConstraintMatrix cm = new ConstraintMatrix(item1, item2);
				cm.setBinaryInequality();
				arcs.add(cm);
				return;
			} else if (arcs.get(i).item1 == item2 && arcs.get(i).item2 == item1){
				ConstraintMatrix cm = new ConstraintMatrix(item2, item1);
				cm.setBinaryInequality();
				arcs.add(cm);
				return;
			}
		}
		ConstraintMatrix cm = new ConstraintMatrix(item1, item2);
		cm.setBinaryInequality();
		arcs.add(cm);
	}
	
	//modifies or creates a ConstraintMatrix (arc in terms of a constraint net) to represent the fact that two items are mutually exclusive
	//to their respective bags
	public void addMutualExclusive(char item1, char item2, char bag1, char bag2){
		for (int i=0; i < arcs.size(); i++){
			if (arcs.get(i).item1 == item1 && arcs.get(i).item2 == item2){
				ConstraintMatrix cm = new ConstraintMatrix(item1, item2);
				cm.setMutualExclusive(bag1, bag2);
				arcs.add(cm);
				return;
			} else if (arcs.get(i).item1 == item2 && arcs.get(i).item2 == item1){
				ConstraintMatrix cm = new ConstraintMatrix(item2, item1);
				cm.setMutualExclusive(bag2, bag1);
				arcs.add(cm);
				return;
			}
		}
		ConstraintMatrix cm = new ConstraintMatrix(item1, item2);
		cm.setMutualExclusive(bag1, bag2);
		arcs.add(cm);
	}
	
	//this function should be called after all the constraints have been read in from a file
	//it goes through the constraint matrix and propogates the valid bags as defined by the unary inclusive and exclusive constraints
	public void finalizeConstraints(){
		Iterator<ConstraintMatrix> icm = arcs.iterator();
		Item i1 = null,i2 = null;
		while (icm.hasNext()){
			ConstraintMatrix cm = icm.next();
			char[] item1ValidBags = null, item2ValidBags = null;
			Iterator<Item> ii = items.iterator();
			while (ii.hasNext()){
				Item i = ii.next();
				if (i.name == cm.item1){
					item1ValidBags = i.getValidBags();
					i1 = i;
				} else if (i.name == cm.item2){
					item2ValidBags = i.getValidBags();
					i2 = i;
				} else if (item1ValidBags != null && item2ValidBags != null){
					break;
				}
			}
			cm.finalizeConstraints(item1ValidBags, item2ValidBags);
			AC3.reduceArc(i1, i2, cm);
		}
	}
	
	public static String getBagString(){
		String bString = "";
		Iterator<Bag> ib = bags.iterator();
		while (ib.hasNext()){
			Bag b = ib.next();
			bString += b.name;
		}
		return bString;
	}
	
	public void initializeValidBags(){
		String bagString = getBagString();
		char[] bags = bagString.toCharArray();
		for (Item i : items){
			i.setValidBags(bags);
		}
	}
	
	public boolean lowerLimMet(){
		for (Bag b : bags){
			if (b.getNumberOfItems() < lowerLim){
				return false;
			}
		}
		return true;
	}
	
	public boolean checkConsistency(Item item, Bag bag, HashMap<Item, Bag> assignments){
		boolean valid = true;
		if (item.checkValidBag(bag)){
			
			if (bag.getRemainingSize() >= item.weight){
				//System.out.println("Bag "+bag.name+" has "+bag.getRemainingSize()+" size left, which means item "+item.name+" of size "+item.weight+" fits.");
				if (bag.getNumberOfItems() + 1 <= upperLim){
					Iterator<Item> ii = assignments.keySet().iterator();
					while (ii.hasNext()){
						if (!valid){
							return false;
						}
						Item i = ii.next();
						Bag b = assignments.get(i);
						for (ConstraintMatrix cm : arcs){
							if (!valid){
								return false;
							}else if (cm.item1 == item.name && cm.item2 == i.name){
								valid = cm.checkConsistency(bag.name, b.name);
							} else if (cm.item1 == i.name && cm.item2 == item.name){
								valid = cm.checkConsistency(b.name, bag.name);
							}
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public int countIntersections(Bag bag)
	{
		int intersections=0;
		for (ConstraintMatrix cm:arcs)
		{
			intersections+=cm.countIntersectingBags(bag);
		}
		return intersections;
	}
}
