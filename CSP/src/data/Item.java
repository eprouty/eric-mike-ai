package data;

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
	
	public boolean checkValidBag(Bag b){
		for (char c : validBags){
			if (c == b.name){
				return true;
			}
		}
		return false;
	}
}