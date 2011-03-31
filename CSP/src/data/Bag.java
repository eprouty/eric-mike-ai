package data;

import java.util.ArrayList;

public class Bag{
	public char name;
	public int size;
	public ArrayList<Item> items;
	
	public Bag(char name, int size){
		this.name = name;
		this.size = size;
	}
	
	public void addItem(Item i){
		items.add(i);
		size = size - i.weight;
	}
	
	public int getRemainingSize(){
		return size;
	}
	
	public Item[] getItemsInBag(){
		Item[] i = new Item[items.size()];
		i = items.toArray(i);
		return i;
	}
}