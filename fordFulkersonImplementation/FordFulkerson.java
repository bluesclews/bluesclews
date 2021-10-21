package fordFulkersonImplementation;
import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		ArrayList<Integer> epath = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE*/
		
		
		
		int length = graph.getNbNodes();
		ArrayList<ArrayList<Edge>> adjMatrix = new ArrayList<ArrayList<Edge>>();
		for(int i = 0; i < graph.getNbNodes(); i++) {
			ArrayList<Edge> empt = new ArrayList<Edge>();
			adjMatrix.add(empt);
		}
		
		for(Integer i = 0; i < length; i ++) {
			for(Integer y =0; y < length; y++) {
				if(graph.getEdge(i, y) != null) {
					adjMatrix.get(i).add(graph.getEdge(i, y));
				}
				
			}
		}
		
		
		Integer check = source;
		
		boolean success = DFS(source,  graph, path, adjMatrix);
		
		for(int i = 0 ; i < path.size(); i++) {
			System.out.print(path.get(i));
		}
		
		System.out.print("\n");
		if(success)return path;
		else return epath;
/*
		while(check.equals(destination) != true) {
			
			Integer test = check;
			
			for(int i = 0; i < adjMatrix.get(check).size(); i++) {
				boolean not_visited = true;
				
				//make sure that the node being visited isn't already in the path
				for(int v = 0; v < path.size(); v++) {
					if (adjMatrix.get(check).get(i).nodes[1] == path.get(v)) {
						not_visited = false;
						break;
					}
				}
				if( adjMatrix.get(check).get(i).weight > 0 && not_visited) {
					
					path.add(adjMatrix.get(check).get(i).nodes[1]);
					
					check = adjMatrix.get(check).get(i).nodes[1];
					
				}
				
				
			}
			
			// if the loop could not find another node to progress to return empty path
			if( check == test) return epath;
		}
		*/

		
	}
	
	public static boolean  DFS(Integer next, WGraph graph, ArrayList<Integer> path, ArrayList<ArrayList<Edge>> adjMatrix) {
		//System.out.print(" ");
		//System.out.print(next);
		path.add(next);
		if(next ==graph.getDestination()) {
			
			return true;
		}
		
		else {
			for(int i = 0; i < adjMatrix.get(next).size(); i++) {
				boolean not_visited = true;
				
				//make sure that the node being visited isn't already in the path
				for(int v = 0; v < path.size(); v++) {
					if (adjMatrix.get(next).get(i).nodes[1] == path.get(v)) {
						not_visited = false;
						break;
					}
				}
				if( adjMatrix.get(next).get(i).weight > 0 && not_visited) {
					
					boolean check_nxt = DFS(adjMatrix.get(next).get(i).nodes[1], graph, path, adjMatrix);
					
					System.out.print(check_nxt);
					
					if(check_nxt) {
						
						return true;
					}
					
					
					
					
					
				}
				
				
				
			}
			
			path.remove(next);
			
			return false;
		}
	}



	public static String fordfulkerson( WGraph graph){
		String answer="";
		int maxFlow = 0;
		
		
		
		/* YOUR CODE GOES HERE		*/
		
		WGraph Gf = new WGraph(graph);
		
		ArrayList<Edge> edges = graph.getEdges();
		
		//adds all backwards edges
		for(Edge e:edges) {
			Edge newe = new Edge(e.nodes[1], e.nodes[0], 0);
			Gf.addEdge(newe);
		}
		//calls DFS
		ArrayList<Integer> path = pathDFS(Gf.getSource(), Gf.getDestination(), Gf);
		
		
		while(path.size() > 0) {
			
		
			int bottleneck = Gf.getEdge(path.get(0),  path.get(1)).weight;
			for(Integer e = 1; e < path.size(); e++) {
				if(path.get(e) == Gf.getDestination()) break;
				if (Gf.getEdge(path.get(e), path.get(e+1)).weight < bottleneck) bottleneck = e;
			}
			System.out.print(bottleneck);
			for(int e = 0; e < path.size(); e++) {
				
				if(path.get(e) == Gf.getDestination()) break;
				Edge ftemp = Gf.getEdge(path.get(e), path.get(e+1));
				if ((ftemp.weight -= bottleneck) >= 0) {
					ftemp.weight -= bottleneck;
					Edge btemp = Gf.getEdge(path.get(e+1), path.get(e));
					btemp.weight += bottleneck;
				}
				
				
				
			}
			
			path = pathDFS(Gf.getSource(), Gf.getDestination(), Gf);
		}
	
		for(Integer i = 0; i< graph.getNbNodes();i++) {
			if(graph.getEdge( i, graph.getDestination()) != null) {
				maxFlow += graph.getEdge(i, graph.getDestination()).weight;
			}
		}
		
		
		

		answer += maxFlow + "\n" + Gf.toString();	
		return answer;
	}
	

	 public static void main(String[] args){
		 
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 
		  
		 System.out.println(fordfulkerson(g));
	 }
}

