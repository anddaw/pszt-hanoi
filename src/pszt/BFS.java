package pszt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * impelemntacja interfejsu Algorithm
 * Jest to przeszukiewanie wszerz (ang. Breadth-first search (BFS))
 * 
 * @param 	openStates 	- kolejka stanow otwartych(jeszcze nierozwinietych)
 * 			closedStates - lista stanow zamkenietych(rozwinietych)
 * 			courentState - stan dla ktorego znajdujemy stany nastepne
 * 			newStates	- stany nastpenue wlasnie badanego stanu
 * 			endStateFound - flaga czy znaleziono stan koncowy
 * 
 * @return 	solution 	- lista stanów włącznie z początkowym[0] i końcowym[n] - pomiędzy
 * @author Marek
 * */

public class BFS implements Algorithm {
	
	/**Kolejka ostanow otwartych*/
	Queue<State> openStates = new LinkedList<State>();
	/**Lista ostanow zamknietcy*/
	ArrayList<State> closedStates = new ArrayList<State>();
	/**Lista stanow z rozwiazaniem*/
	ArrayList<State> solution = new ArrayList<State>();
	State courentState;
	/**Znalezion stan koncowy, posiada informacje o rodzicu*/
	State endState;
	boolean endStateFound = false;
	
	private State startState;
	private State finalState;

	@Override
	public State[] getSolution(State startState, State finalState) {
		
		init(startState, finalState);
		
		while(!endStateFound){
			courentState = openStates.poll();
			State [] newStates = courentState.nextStates();
			if(isEndState(newStates)){
				//prepareSolution();
				break;
			}
			
			
		}
		
		
		return null;
	}
	
	
	/**
	 * inicjuje listy potrzebne do znalezienia rozwiazania i znienne globane
	 */
	private void init(State startState, State finalState){
		this.startState = startState;
		this.finalState = finalState;
		openStates.add(this.startState);
		
	}
	
	/**
	 * sprawdza czy wsród nowo znalezionych stanow nie ma stanu koncowego*/
	boolean isEndState(State[] newStates){
		for(State state : newStates){
			if(state.equals(finalState)) {
				endState = state;
				return true;
			}
		}
		return false;
	}
	
	
	/**Metoda przygotowywująca rozwiązanie*/
	/*State[] prepareSolution(){
		
	}*/

}
