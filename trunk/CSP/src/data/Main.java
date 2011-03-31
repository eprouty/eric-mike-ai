package data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import search.Backtrack;

public class Main {
	//this is the entry point for the system, take in the file that will be used for the problem
	public static void main (String [] args) throws IOException{
		if (args.length == 0){
			System.err.println("Please give a text file containing contrainsts for the problem");
			System.exit(-1);
		} 
		FileInputStream fstream = new FileInputStream(args[0]);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		CNet cnet = new CNet();
		
		int section = 0;
		String line = null;
		char name, name2;
		int size;
		String b;
		char[] bags;
		while ((line = br.readLine()) != null){
			if (line.contains("#")){
				if (section == 2){
					cnet.initializeValidBags();
				}
				section++;
				continue;
			} else {
				switch (section){
				//variable enumeration
				case 1:
					name = line.charAt(0);
					size = Integer.parseInt(line.substring(2));
					cnet.addItem(name, size);
					break;
				//bag enumeration
				case 2:
					name = line.charAt(0);
					size = Integer.parseInt(line.substring(2));
					cnet.addBag(name, size);
					break;
				//fitting limits
				case 3:
					int lowerLim = Integer.parseInt(line.substring(0, 1));
					int upperLim = Integer.parseInt(line.substring(2));
					cnet.setLimits(lowerLim, upperLim);
					break;
				//unary inclusive constraints
				case 4:
					name = line.charAt(0);
					b = line.substring(2).replace(" ", ""); 
					bags = b.toCharArray();
					cnet.addUnaryInclusive(name, bags);
					break;
				//unary exclusive constraints
				case 5:
					name = line.charAt(0);
					b = line.substring(2).replace(" ", "");
					bags = b.toCharArray();
					cnet.addUnaryExclusive(name, bags);
					break;
				//binary equals constraints
				case 6:
					name = line.charAt(0);
					name2 = line.charAt(2);
					cnet.addBinaryEquals(name, name2);
					break;
				//binary not equals constraints
				case 7:
					name = line.charAt(0);
					name2 = line.charAt(2);
					cnet.addBinaryNotEquals(name, name2);
					break;
				//mutually exclusive constraints
				case 8:
					name = line.charAt(0);
					name2 = line.charAt(2);
					char b1 = line.charAt(4);
					char b2 = line.charAt(6);
					cnet.addMutualExclusive(name, name2, b1, b2);
					break;
				}
			}
		}
		cnet.finalizeConstraints();
		
		HashMap<Item, Bag> assignments = new HashMap<Item, Bag>();
		if (Backtrack.backtracking(cnet.getBags(), cnet.items, assignments, cnet)){
			System.out.println("Hooray we found a solution");
			for (Item item:assignments.keySet())
			{
				System.out.println("Item "+item.name+" goes in bag "+assignments.get(item).name);
			}
		} else {
			System.err.println("Boooo! no solution");
		}
	}
}
