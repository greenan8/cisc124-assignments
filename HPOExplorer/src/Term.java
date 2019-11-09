import java.util.ArrayList;

public class Term {

	//the terms id
	String identity;
	//the term's parent name
	String parent;
	//reference to term's parent's object
	Term parentObj;
	//full data of the term
	String termText;
	//arraylist of the children
	ArrayList<Term> children = new ArrayList<Term>();
	//if a leaf, how long the path from the root node
	int termCount;

		//constructor setting initial vars
		public Term(String idParam, String parentParam, String termParam) {
			
			identity = idParam;
			parent = parentParam;
			termText = termParam;
		}
	
	
		//to receive child
		public void addChild(Term childParam) {
			children.add(childParam);
		}
		
		//to receive parant object
		public void addParentObject(Term parentObjParam) {
			parentObj = parentObjParam;
		}
		
		//that relays its text parents text
		public String getParent() {
			return parent;
		}
		
		//to return parent object reference
		public Term getParentObj(){
			return parentObj;
		}
				
		//to return term data
		public String getTermText(){
			return termText;
		}
		
		//to return list of children
		public ArrayList<Term> getChildren(){
			return children;
		}
		
		//to set the length from root node
		public void setCount(int countParam) {
			termCount = countParam;
		}
		
		//to return the length to root node
		public int getTermCount() {
			return termCount;
		}
		
		
}
