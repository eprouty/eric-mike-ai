package data;

import java.util.ArrayList;

public class Item{
	public char name;
	public int weight;
	public char[] validBags;
	
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
			return CNet.getBagString().toCharArray();
		} else {
			return validBags;
		}
	}
	
	public ArrayList<Bag> getBags(CNet cnet){
		ArrayList<Bag> bags = new ArrayList<Bag>();
		for (char c : validBags){
			bags.add(cnet.getBag(c));
		}
		return bags;
	}
	
	public boolean checkValidBag(Bag b){
		for (char c : validBags){
			if (c == b.name){
				return true;
			}
		}
		return false;
	}
}