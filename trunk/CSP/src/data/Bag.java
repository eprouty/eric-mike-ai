package data;

import java.util.ArrayList;

public class Bag{
	public char name;
	public int size;
	public ArrayList<Item> items = new ArrayList<Item>();
	
	public Bag(char name, int size){
		this.name = name;
		this.size = size;
	}
	
	public boolean addItem(Item i){
		if (i.weight>size)
		{
			return false;
		}
		items.add(i);
		size = size - i.weight;
		return true;
	}
	
	public int getRemainingSize(){
		return size;
	}
	
	public void setRemainingSize(int rsize){
		size = rsize;
	}
	
	public Item[] getItemsInBag(){
		Item[] i = new Item[items.size()];
		i = items.toArray(i);
		return i;
	}
}