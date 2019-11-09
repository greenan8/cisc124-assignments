/*--------------------------------------------------------------*/
// Author: 		Andrew Greenan
///Student #:   20004588
// Description: This program is designed to read in a list of data
//				in the data structure of a tree and create objects
//				for each term. It then reads a text file and outputs
//				the quereis requested from said text file into a 
//				results text file. Finally it find the max height 
//				of the tree and all possible paths of this height
//				int a text file.
//NOTES:		--I found multiple paths of length 16 for the max path
//				so showed all solutions in the file
//				--My files do not format properly in notepad But
//				do format in notepad++
/*--------------------------------------------------------------*/

import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.FileOutputStream;


public class HPOExplorer {
	
	//create scanner variable for reading files
	static Scanner reader;
	
	//create hashmap to hold all Term Objects created when reading hpo file
	static HashMap<String, Term> allNodes = new HashMap<String, Term>();
	
	//create maxCount variable to keep track of what the longest path in the tree is equal to
	static int maxCount;
	
	//main method used to envoke all other methods in order
	public static void main(String[] args) {
		
		// read HPO file and create terms
		createTerms();

		
		//open query file and make queries
		queryFile();
		
		//assign path length to each node then print all the path to all nodes with length equal to max length
		maxPathCountAdd(allNodes.get("HP:0000001"), 1);
		maxPathPrint();
		
		System.out.println("HPOexplorer: Success");
	}
	
	
	//function is used to open a file
	public static void openFile(String fileName){
		
		//if file found then create a scanner of that fileName given
		try {
			reader = new Scanner(new File(fileName));
		}
		catch(Exception e) {
			System.out.println("File not found: " + fileName);
			System.exit(0);
		}
			
	}
	
	public static void createTerms(){
		//open file with scanner
		openFile("HPO.txt");
		
		//declaring variables needed while looping through every line of file
		
		boolean skippedStart = false; //check if we have made it to the terms
		String identity = ""; //identity of the current term
		String parent = ""; //parent of the current term
		String fullTerm = ""; //current terms full info
		boolean notFirstParent = false; //boolean to stop collecting parent after first parent
		boolean obsolete = false; //record if term is obsolete

		//loop through every line
		while (reader.hasNextLine()) {  
			   String line = reader.nextLine();
			   
			   //check if we have made it to the first term
			   if (!skippedStart && line.equals("[Term]")) {
				   skippedStart = true;
			   }
			   
			   //if haven't skipped the start yet continue without doing anything else
			   else if (!skippedStart) {
				   continue;
			   }
			   
			   //check if we are on an id line, if so then split the line and obtain the id
			   else if (line.contains("id:")) {
				   String[] parts = line.split(" ");
				   if (parts[0].equals("id:")){
					   identity = parts[1];
				   }
			   }
			   
			   //check if we are on a parent line and split the line saving the parent id
			   	if (!notFirstParent && line.contains("is_a")) {
			   		String[] parts = line.split(" ");
			   		if (parts[0].equals("is_a:")){
			   			parent = parts[1];
			   			notFirstParent = true;
				   	}
			   	}
			   
			   	//if we find is_obsolete in the term then set boolean to true
			   	if (line.equals("is_obsolete: true")) {
			   		obsolete = true;
				   	}
				
			   	//saving the full term to a string variable
				if (fullTerm == "") {
					fullTerm = (fullTerm + line);
				}
				else {
					fullTerm = (fullTerm + "\n" + line);	
				}
			   
				//if not obsolete and have reach the empty line between terms then we must save term
				if (!obsolete && line.equals("")) {
					
					if (parent.equals("")) {parent = null;}
					//create new term with info recieved and put this term on hashmap of all nodes
					Term newTerm = new Term(identity, parent, fullTerm);
					allNodes.put(identity, newTerm);
		
					//reset vars
					identity = "";
					parent = "";
					fullTerm = "";
					notFirstParent = false;
					obsolete = false;
		
				//if obsolete at the end of term we need to reset all vars without saving this term	
				} else if (obsolete && line.equals("")) {
		
					//reset vars
					identity = "";
					parent = "";
					fullTerm = "";
					notFirstParent = false;
					obsolete = false;
				}
				
		}
		
		//close reader as no longer in use
		reader.close();
		
		//after recording all nodes we must not go through each object and add their parent and child to the object for easier querying
		for (Term node : allNodes.values()) {
			//adding a reference to parents object into a nodes attributes
		    node.addParentObject(allNodes.get(node.getParent()));
		    
		    //adding children into a parent object and catching exception of no children
		    try {
		    	allNodes.get(node.getParent()).addChild(node);
		    }
		    catch (Exception e){
		    	
		    }
		}
   }
	
	//function that reads query file and queries all requests
	public static void queryFile() {
		//open file with scanner
		openFile("queries.txt");
		
		// Declare a data stream object to send data to a text file.
		PrintWriter toResultFile = null;
		
		// Open the result file to write to it
		// Check for file I/O exceptions.
		try {
			toResultFile = new PrintWriter(new FileOutputStream("results.txt"));
		}
		catch (Exception e) {	
			System.out.println("Error opening the file 'results.txt'");
			System.exit(0);
		}
		
		
		while (reader.hasNextLine()) {
			//define a empty string to add queryID to
			String queryIdentity = "";
			
			//save line to string var
			String line = reader.nextLine();
			
			//split line and add query id to a var
			String[] parts = line.split(" ");
		   	queryIdentity = parts[1];
		   		
		   	//call query
			new Query(allNodes.get(queryIdentity), true, toResultFile, "[query_answer]");
			
		}
		//close as both are no longer in use
		reader.close();
		toResultFile.close();
	}
	
	
	//function to assign a length variable to every object or node
	public static void maxPathCountAdd(Term currentNode, int count) {
		
		//if getchildren is empty then we have reached a leaf
		if (currentNode.getChildren().isEmpty()) {
			
			//set the nodes attribute to its current count or length to get to it
			currentNode.setCount(count);
			
			//if this length is currently longer then anything else seen, set our max count to it
			if (count > maxCount) {
				maxCount = count;
				return;
			}
		}
		else {
			//for every child of this node check if it is a leaf and add its length or check its children
			for (Term child : currentNode.getChildren()) {
				maxPathCountAdd(child, count + 1);
			}
		}
	
	}
	
	//function to print the max path to a file
	public static void maxPathPrint() {
		
		//declare printwriter var
		PrintWriter toMaxPathFile = null;
		
		//try to open maxpath file to write to it, catch if file can not be found
		try {
			toMaxPathFile = new PrintWriter(new FileOutputStream("maxpath.txt"));
		}
		catch (Exception e) {	
			System.out.println("Error opening the file 'maxpath.txt'");
			System.exit(0);
		}
		
		//if a nodes count is equal to the max count seen then print its path using query to the max path file
		for (Term node : allNodes.values()){
			if (node.getTermCount() == maxCount) {
				new Query(node, true, toMaxPathFile, "[max_path=" + maxCount + "]");
			}
		}
	}
	

}
