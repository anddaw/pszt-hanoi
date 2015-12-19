package pszt;

/**
 * Klasa reprezentująca stan modelu
 * Użycie:
 * Model model = new Model();
 * model.findSolution(liczba_dyskow, algorytm);
 * Potem można znaleźć kolejne kroki rozwiązania w tablicy model.solution
 * model.solution[0] - stan poczatkowy
 * model.solution[model.solution.size()-1] - stan końcowy
 * a pomiedzy nimi rozwiazanie
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
	 * @param disks - liczba krążków do ustawienia
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
		 * @param disks - ilość krążków
		 * @param algorithm - algorytm rozwiązujący problem
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
		BFS algorithmBFS = new BFS();
		model.findSolution(6, algorithmBFS);
		
		//wyswietlanie rozwiazania
		for(State state : model.solution){
			state.print();
		}
		/*-------------------------------------------------*/
		
/*testy Andrzeja*/		
//		int[] ar1 = {0,0,0,0};
//		State state= new State(ar1);
//	
//		
//		for(int i =0; i<1000000; i++)
//		{
//			state=state.nextStates()[0];
//		}
//		state.print();
	}
}
