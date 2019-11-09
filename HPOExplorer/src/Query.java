import java.io.PrintWriter;

public class Query {

	//constructor class does all the query work
	public Query(Term termParam, boolean firstNode, PrintWriter toResultFile, String header) {
	
		//if we are the first node then print header to file
		if (firstNode) {
			toResultFile.println(header);
		}
		
		//print the current term to the file
		toResultFile.println(termParam.getTermText());
		
		//if the term has a parent, query its parent
		try {
			new Query(termParam.getParentObj(), false, toResultFile, header);
		}
		catch (Exception e) {
			
		}

	}
}
