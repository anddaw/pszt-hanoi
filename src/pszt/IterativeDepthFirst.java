/**
 * 
 */
package pszt;

import java.util.LinkedList;

/**
 * @author andrzej Klasa implementujaca przeszukiwanie iteracyjne wzdłuż
 */
public class IterativeDepthFirst implements Algorithm
{
	private State[] prepareSolution(State finalState)
	{
		LinkedList<State> solution = new LinkedList<State>();
		State currentState = finalState;
		while (currentState != null)
		{
			solution.addFirst(currentState);
			currentState = currentState.parent;
		}
		State[] solutionArray = new State[solution.size()];
		solution.toArray(solutionArray);
		return solutionArray;
	}

	/**
	 * 
	 * @param limit
	 *            - poziom do którego przeszukujemy
	 * @return Rozwiązanie lub null jezeli nie uda się znaleźć
	 */
	private State[] getDLSolution(State startState, State finalState, int limit)
	{
		LinkedList<State> openStates = new LinkedList<State>();
		openStates.push(startState);
		State[] solution = null;

		while (openStates.size() > 0)
		{
			State currentState = openStates.pop();

			if (currentState.equals(finalState))
			{
				solution = prepareSolution(currentState);
				break;
			}

			if (currentState.level < limit)
				for (State childState : currentState.nextStates())
					openStates.push(childState);
		}

		return solution;
	}

	@Override
	public State[] getSolution(State startState, State finalState)
	{
		State[] solution;
		for (int level = 0; true; level++)
		{
			solution = getDLSolution(startState, finalState, level);
			if (solution != null)
				return solution;
		}
	}

}
