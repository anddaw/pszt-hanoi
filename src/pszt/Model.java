package pszt;

//import java.util.HashMap;

/**
 * Klasa reprezentująca stan modelu Użycie: Model model = new Model();
 * model.findSolution(liczba_dyskow, algorytm); Potem można znaleźć kolejne
 * kroki rozwiązania w tablicy model.solution model.solution[0] - stan
 * poczatkowy model.solution[model.solution.size()-1] - stan końcowy a pomiedzy
 * nimi rozwiazanie
 * 
 * @author andrzej
 *
 */
public class Model
{
	/** Stan początkowy */
	private State startState;
	/** Stan końcowy */
	private State finalState;
	/** Algorytm szukania rozwiązań */
	Algorithm algorithm;
	/** Rozwiązanie */
	State solution[];

	/**
	 * Ustawia podaną liczbę krążków na pierwszym paliku
	 * 
	 * @param disks
	 *            - liczba krążków do ustawienia
	 */
	private void setDisksNumber(int disks)
	{

		// ustaw liczbę krążków
		int startArray[] = new int[disks];
		int finalArray[] = new int[disks];

		// zainicjalizuj tablice
		for (int i = 0; i < disks; i++)
		{
			startArray[i] = 0;
			finalArray[i] = 2;
		}
		// zainicjalizuj stany
		startState = new State(startArray);
		finalState = new State(finalArray);
	}

	/**
	 * Znajduje rozwiązanie dla podanych parametrów
	 * 
	 * @param disks
	 *            - ilość krążków
	 * @param algorithm
	 *            - algorytm rozwiązujący problem
	 * 
	 */
	public void findSolution(int disks, Algorithm algorithm)
	{
		setDisksNumber(disks);

		if (algorithm == null)
		{
			throw new NoAlgorihmException();
		} else
		{
			solution = algorithm.getSolution(startState, finalState);
		}
	}

	public class NoAlgorihmException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;

	}

	public static void main(String args[])
	{
		/*-------------------------------------
		 * testy Marka*/
		Model model = new Model();

		Algorithm[] algorithms = new Algorithm[3];
		algorithms[0] = new BFS();
		algorithms[1] = new TwoWay();
		algorithms[2] = new IterativeDepthFirst();

		for (Algorithm algorithm : algorithms)
		{
			long time = System.currentTimeMillis();
			model.findSolution(4, algorithm);

			System.out.print(algorithm.getClass() + " czas: ");
			System.out.print(System.currentTimeMillis() - time);
			System.out.println(" kroki: " + model.solution.length);

		}

		/*-------------------------------------------------*/

		/* testy Andrzejka */
		// int[] disks1 = {0,0,0};
		// int[] disks2 = {0,0,1};
		// State state1 = new State(disks1);
		// State state2 = new State(disks2);
		// State state3 = state1.nextStates()[0];
		//
		//
		// HashMap<State,State> map = new HashMap<State,State>();
		// map.put(state2, state2);
		// System.out.println(state2.equals(state3));
		// System.out.println(state2.hashCode()==state3.hashCode());
		// System.out.println(map.containsKey(state3));
	}
}
