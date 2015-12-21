package pszt;

import java.util.ArrayList;

/**
 * impelemntacja interfejsu Algorithm Przeszukiewanie wszerz (ang. Breadth-first
 * search (BFS))
 * 
 * @param openStates
 *            - lista stanow otwartych(jeszcze nierozwinietych)
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

		init(startState, finalState); // krok 0

		while (true) {
			courentState = openStates.remove(0); // pobieramy pierwszy z listy
													// otwartych
			State[] newStates = courentState.nextStates();
			closedStates.add(courentState);

			if (isEndState(newStates)) {
				State[] solution = prepareSolution(endState);
				endStateFound = true;
				return solution;
			} else {
				for (State state : newStates)
					if (!isOpenState(state) && !isClosedState(state))
						openStates.add(openStates.size(), state); // nowo
																	// znalezione
																	// dodajemy
																	// na koniec
			}
		}
	}

	/**
	 * inicjuje listy potrzebne do znalezienia rozwiazania i znienne globane
	 */
	private void init(State startState, State finalState) {
		// endStateFound = false;
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
				endState = state;
				return true;
			}
		}
		return false;
	}

	/**
	 * Metoda przygotowywująca rozwiązanie Dostaje na wejscie stan odnaleziony
	 * stan konczowy Zwraca tablice stanów włącznie z początkowym[0] i
	 * końcowym[n] - pomiędzy
	 */
	State[] prepareSolution(State endState) {

		ArrayList<State> solutionList = new ArrayList<State>();
		State tmpState;
		tmpState = endState;

		/*
		 * Odnajduje sciezke od znalezionego stanu konczacego do stanu
		 * poczatkowego od razu zapisuje w poprawnej kolejnosci.
		 */
		solutionList.add(0, tmpState);
		while (tmpState.parent != null) {
			tmpState = tmpState.parent;
			solutionList.add(0, tmpState);
		}

		State[] solutionArray = new State[solutionList.size()];
		solutionList.toArray(solutionArray);
		return solutionArray;
	}

	/** Sprawdza czy podany stan jest na liscie stanow otwartych */
	boolean isOpenState(State state) {
		for (State checkState : openStates)
			if (checkState.equals(state))
				return true;

		return false;
	}

	/** Sprawdza czy podany stan jest na liscie stanow zamknietych */
	boolean isClosedState(State state) {
		for (State checkState : closedStates)
			if (checkState.equals(state))
				return true;
		return false;
	}

}
