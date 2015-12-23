package pszt;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * impelemntacja interfejsu Algorithm Przeszukiewanie dwukierunkowe (brak
 * angielskiej nazwy)
 * 
 * @param openStatesTop
 *            - lista stanow otwartych generowanych z startState
 * @param closedStatesTop
 *            - lista stanow zamkenietych generowanych z startState
 * @param openStatesBottom
 *            - Hash stanow otwartych generowanych z finalState
 * @param closedStatesBottom
 *            - hash stanow zamkenietych generowanych z finalState
 * @param courentStateTop
 *            - stan pochodny od startState obecnie rozwijany
 * @param courentStateBottom
 *            - stan pochodny od finaltState obecnie rozwijany
 * @param newStates
 *            - stany nastepne wlasnie badanego stanu
 * @param endStateFound
 *            - flaga czy znaleziono stan koncowy
 * @param dualState
 *            - znaleniony stan spolny
 *
 * @return solution - lista stanów włącznie z początkowym[0] i końcowym[n] -
 *         pomiędzy
 * @author Marek
 * */

public class TwoWay implements Algorithm {

	/** Lista stanow otwartych */
	private ArrayList<State> openStatesTop = new ArrayList<State>();
	private ArrayList<State> openStatesBottom = new ArrayList<State>();
	/** Lista ostanow zamknietcy */
	private HashMap<State, State> closedStatesTop = new HashMap<State, State>();
	private HashMap<State, State> closedStatesBottom = new HashMap<State, State>();

	/** Znaleziony stan koncowy */
	State dualStateTop;
	State dualStateBottom;

	private State startState;
	private State finalState;

	@Override
	public State[] getSolution(State startState, State finalState) {
		init(startState, finalState);

		while (true) {
			expandState(openStatesBottom, closedStatesBottom);
			expandState(openStatesTop, closedStatesTop);
			if (checkDoubleState()) {
				State[] solution = prepareSolution();
				return solution;
			}
		}
	}

	/**
	 * inicjuje listy potrzebne do znalezienia rozwiazania i znienne globalne
	 */
	private void init(State startState, State finalState) {
		this.startState = startState;
		this.finalState = finalState;
		openStatesTop.add(this.startState);
		openStatesBottom.add(this.finalState);
	}

	/**
	 * Funkcja znajduje rozwiniecia pierwszego stanu z listy stanow otawrtych, a
	 * nastepnie wpisuje go na liste stanow zamknietych. Nowo znalezione stany
	 * wpisuje na koniec listy stanow otawrtych, jeżeli nie ma ich jeszcze na
	 * liscie stanow otwartych lub zamknietych
	 * */
	void expandState(ArrayList<State> openStates, HashMap<State, State> closedStates) {

		/* Pobieramy pierwszy otwarty stan */
		State courentState = openStates.remove(0);
		State[] newStates = courentState.nextStates();
		closedStates.put(courentState, courentState);

		/* Nowe unikalne stany dopisujemy do listy otwartych */
		for (State state : newStates)
			if (!isOnStateList(state, openStates)
					&& !closedStates.containsKey(state) )
				openStates.add(openStates.size(), state);
	}

	/** Sprawdza czy podany stan jest na liscie stanow */
	boolean isOnStateList(State state, ArrayList<State> list) {
		for (State checkState : list)
			if (checkState.equals(state))
				return true;

		return false;
	}

	/**
	 * Dla kazdego stanu z openStatesTop sprawdzamy czy nie znalazl sie on na
	 * liscie opsenStatesBottom lub closedStatesBottom
	 */
	boolean checkDoubleState() {

		for (State topState : openStatesTop) {
			for (State bottomState : openStatesBottom) {
				if (topState.equals(bottomState)) {
					dualStateTop = topState;
					dualStateBottom = bottomState;
					return true;
				}
			}
		}

		for (State topState : openStatesTop) {
				if (closedStatesBottom.containsKey(topState)) {
					dualStateTop = topState;
					dualStateBottom = closedStatesBottom.get(topState);
					return true;
				}
		}
		return false;

	}

	State[] prepareSolution() {

		ArrayList<State> solutionList = new ArrayList<State>();
		State tmpStateTop = dualStateTop;
		State tmpStateBottom = dualStateBottom;

		/* Wpisanie na liste rozwiazania stanow poprzedajacych stan wspolny */
		solutionList.add(0, tmpStateTop);

		while (tmpStateTop.parent != null) {
			tmpStateTop = tmpStateTop.parent;
			solutionList.add(0, tmpStateTop);
		}

		/* Wpisanie na liste stanow nastepujacych po stanie wspolnym */
		while (tmpStateBottom.parent != null) {
			tmpStateBottom = tmpStateBottom.parent;
			solutionList.add(solutionList.size(), tmpStateBottom);
		}

		/* Przelisanie listy do tablicy */
		State[] solutionArray = new State[solutionList.size()];
		solutionList.toArray(solutionArray);

		return solutionArray;
	}
}
