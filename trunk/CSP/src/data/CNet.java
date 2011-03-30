package data;

import java.util.ArrayList;
import java.util.Iterator;

public class CNet {
	int lowerLim, upperLim;
	//nodes
	ArrayList<Item> items = new ArrayList<Item>();
	//domain
	ArrayList<Bag> bags = new ArrayList<Bag>();
	//arcs
	ArrayList<ConstraintMatrix> arcs = new ArrayList<ConstraintMatrix>();
	
	public CNet(){
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
		while (icm.hasNext()){
			ConstraintMatrix cm = icm.next();
			char[] item1ValidBags = null, item2ValidBags = null;
			Iterator<Item> ii = items.iterator();
			while (ii.hasNext()){
				Item i = ii.next();
				if (i.name == cm.item1){
					item1ValidBags = i.getValidBags();
				} else if (i.name == cm.item2){
					item2ValidBags = i.getValidBags();
				} else if (item1ValidBags != null && item2ValidBags != null){
					break;
				}
			}
			cm.finalizeConstraints(item1ValidBags, item2ValidBags);
		}
	}
	
	public String getBagString(){
		String bString = "";
		Iterator<Bag> ib = this.bags.iterator();
		while (ib.hasNext()){
			Bag b = ib.next();
			bString += b.name;
		}
		return bString;
	}
	
	private class Item{
		char name;
		int weight;
		char[] validBags;
		
		public Item(char name, int weight){
			this.name = name;
			this.weight = weight;
			validBags = null;
		}
		
		public void setValidBags(char[] validBags){
			this.validBags = validBags;
		}
		
		public char[] getValidBags(){
			if (validBags == null){
				return getBagString().toCharArray();
			} else {
				return validBags;
			}
		}
	}
	
	private class Bag{
		char name;
		int size;
		
		public Bag(char name, int size){
			this.name = name;
			this.size = size;
		}
	}
	
	//matrix considers 0,0 to be the top left
	//item1 goes down the left side of the matrix
	//item2 runs along the top of the matrix
	private class ConstraintMatrix{
		char item1;
		char item2;
		char[][] matrix = new char[bags.size()+1][bags.size()+1];
		
		public ConstraintMatrix(char item1, char item2){
			this.item1 = item1;
			this.item2 = item2;
			matrix[0][0] = '0';
			for (int i=1; i < matrix.length; i++){
				matrix[i][0] = bags.get(i-1).name;
				matrix[0][i] = bags.get(i-1).name;
				for (int j = 1; j < matrix.length; j++){
					matrix[i][j] = 't';
				}
			}
		}
		
		public void setBinaryEquality(){
			for (int i = 1; i < matrix.length; i++){
				for (int j = 1; j < matrix.length; j++){
					if (i == j){
						continue;
					} else {
						matrix[i][j] = 'f';
					}
				}
			}
		}
		
		public void setBinaryInequality(){
			for (int i=1; i < matrix.length; i++){
				for (int j = 1; j < matrix.length; j++){
					if (i != j){
						continue;
					} else {
						matrix[i][j] = 'f';
					}
				}
			}
		}
		
		public void setMutualExclusive(char bag1, char bag2){
			for (int i=1; i < matrix.length; i++){
				for (int j = 1; j < matrix.length; j++){
					if (matrix[i][0] == bag1){
						if (matrix[0][j] == bag2){
							matrix[i][j] = 'f';
						}
					}
				}
			}
		}
		
		public void finalizeConstraints(char[] item1ValidBags, char[] item2ValidBags){
			String v1 = String.valueOf(item1ValidBags);
			String v2 = String.valueOf(item2ValidBags);
			for (int i=1; i < matrix.length; i++){
				for (int j = 1; j < matrix.length; j++){
					if (v1.indexOf(matrix[i][0]) == -1){
						matrix[i][j] = 'f';
					}
					if (v2.indexOf(matrix[0][j]) == -1){
						matrix[i][j] = 'f';
					}
				}
			}
		}
	}
}
