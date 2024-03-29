package data;

//matrix considers 0,0 to be the top left
//item1 goes down the left side of the matrix
//item2 runs along the top of the matrix
public class ConstraintMatrix{
	char item1;
	char item2;
	char[][] matrix = new char[CNet.bags.size()+1][CNet.bags.size()+1];
	
	public ConstraintMatrix(char item1, char item2){
		this.item1 = item1;
		this.item2 = item2;
		matrix[0][0] = '0';
		//initialize the sides to contain the names of the related bags
		for (int i=1; i < matrix.length; i++){
			matrix[i][0] = CNet.bags.get(i-1).name;
			matrix[0][i] = CNet.bags.get(i-1).name;
			for (int j = 1; j < matrix.length; j++){
				matrix[i][j] = 't';
			}
		}
	}
	public int countIntersectingBags(Bag bag)
	{
		int intersections=0;
		for (int i=1; i<matrix.length; i++)
		{
			if (matrix[i][0]==bag.name)
			{
				for (int j=1; j<matrix[i].length; j++)
				{
					if (matrix[i][j]=='f') intersections++;
				}
			}
		}
		return intersections;
	}
	public char getItem(int num){
		if (num == 1){
			return item1;
		} else {
			return item2;
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
	
	//go through and ensure that all of the constraints are set based upon the unary as well as binary constraints
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
	
	//checks to see if it is possible to put the first or second item in the selected bag
	public boolean checkValidity(char possibleBag, int itemNum){
		boolean valid = false;
		if (itemNum == 1){
			for (int i=1; i < matrix.length; i++){
				if (matrix[i][0] == possibleBag){
					for (int j=1; j < matrix.length; j++){
						if (matrix[i][j] == 't'){
							valid = true;
						}
					}
				}
			}
		} else {
			for (int j=1; j < matrix.length; j++){
				if (matrix[0][j] == possibleBag){
					for (int i=1; i < matrix.length; i++){
						if (matrix[i][j] == 't'){
							valid = true;
						}
					}
				}
			}
		}
		return valid;
	}
	
	//ensure that it is consistent with the constraints to put item1 in bag1 and item2 in bag2
	public boolean checkConsistency(char bag1, char bag2){
		for (int i=1; i < matrix.length; i++){
			for (int j = 1; j < matrix.length; j++){
				if (matrix[i][0] == bag1){
					if (matrix[0][j] == bag2){
						if (matrix[i][j] == 't'){
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
		return false;
	}
	
	public int getMinimum(){
		int min = 0;
		for (int i=1; i < matrix.length; i++){
			for (int j = 1; j < matrix.length; j++){
				if (matrix[i][j] == 't'){
					min++;
				}
			}
		}
		return min;
	}
}
