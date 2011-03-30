package data;

public class Item{
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
			return CNet.getBagString().toCharArray();
		} else {
			return validBags;
		}
	}
}