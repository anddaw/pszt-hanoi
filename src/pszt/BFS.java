package pszt;

import java.util.ArrayList;

/**
 * impelemntacja interfejsu Algorithm 
 * Przeszukiewanie wszerz (ang. Breadth-first search (BFS))
 * 
 * @param openStates
 *            - kolejka stanow otwartych(jeszcze nierozwinietych)
 * @param closedStates
 *            - lista stanow zamkenietych(rozwinietych)
 * @param courentState
 *            - stan dla ktorego znajdujemy stany nastepne
 * @param newStates
 *            - stany nastpenue wlasnie badanego stanu
 * @param endStateFound
 *            - flaga czy znaleziono stan koncowy
 * 
 * @return solution - lista stanów włącznie z początkowym[0] i końcowym[n] -
 *         pomiędzy
 * @author Marek
 * */

public class BFS implements Algorithm {

	/** Lista stanow otwartych */
	ArrayList<State> openStates = new ArrayList<State>();
	/** Lista ostanow zamknietcy */
	ArrayList<State> closedStates = new ArrayList<State>();

	State courentState;
	/** Znaleziony stan koncowy */
	State endState;
	boolean endStateFound;

	private State startState;
	private State finalState;

	@Override
	public State[] getSolution(State startState, State finalState) {

		init(startState, finalState);

		while (!endStateFound) {

			courentState = openStates.remove(0);
			State[] newStates = courentState.nextStates();
			closedStates.add(courentState);

			if (isEndState(newStates)) {
				State[] solution = prepareSolution(endState);
				endStateFound = true;
				return solution;
			} else {
				for (State state : newStates)
					if (!isOpenState(state) && !isClosedState(state))
						openStates.add(state);
			}
		}
		return null;
	}

	/**
	 * inicjuje listy potrzebne do znalezienia rozwiazania i znienne globane
	 */
	private void init(State startState, State finalState) {
		endStateFound = false;
		this.startState = startState;
		this.finalState = finalState;
		openStates.add(this.startState);
	}

	/**
	 * sprawdza czy wsród nowo znalezionych stanow nie ma stanu koncowego
	 */
	boolean isEndState(State[] newStates) {
		for (State state : newStates) {
			if (state.equals(finalState)) {
				endState = new State(state.disks);
				return true;
			}
		}
		return false;
	}

	/** Metoda przygotowywująca rozwiązanie */
	State[] prepareSolution(State endState) {

		ArrayList<State> solutionList = new ArrayList<State>();
		State tmpState = new State(endState.disks);
		tmpState.parent = endState.parent;

		/*
		 * Odnajduje sciezke od znalezionego stanu konczacego do stanu
		 * poczatkowego od razu zapisuje w poprawnej kolejnosci.
		 */
		while (tmpState.parent != null) {
			solutionList.add(0, tmpState);
			tmpState = tmpState.parent;
		}

		solutionList.add(0, startState);

		State[] solutionArray = new State[solutionList.size()];
		solutionList.toArray(solutionArray);
		return solutionArray;
	}

	boolean isOpenState(State state) {

		return true;
	}

	boolean isClosedState(State state) {
		return true;
	}

}
