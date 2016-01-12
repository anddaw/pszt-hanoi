package pszt;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Klasa reprezentująca stan wieży
 * @author andrzej
 *
 */
public class State
{
	/**
	 * Tablica dysków - każde pole tablicy zawiera numer słupka na którym znjaduje się
	 * dany krążek, np.
	 * {0,0,0} - trzy krążki, wszystkie na słupku 0,
	 * {2,2,2,2} - 4, krążki wszystkie na pliku nr 2
	 */
	protected int disks[];
	
	/**
	 * Stan poprzedni.
	 * Konstruktor domyślnie ustawia go na NULL, chyba że stan został 
	 * wygenerowny za pomocą funkcji nextStates().
	 */
	public State parent;
	
	/**
	 * Poziom - na potrzby algorytmów preszukiwania,
	 * automatycznie większy o 1 niż poziom rodzica
	 * początkowo 0
	 */
	public int level;

	public class BadRodException extends RuntimeException
	{
		/**
		 * Wyjątek - zła inicjalizacja tablicy
		 */
		private static final long serialVersionUID = 1L;
	}
	

	/**
	 * Uzycie:
	 * int[] ar1 = {0,1,2};
	 * state = new State(ar1);
	 * @param disks - tablica intów, z numerami słupków dla kolejnych krążków
	 * @throws BadRodException - rzuca jeżeli zły numer krążka, np 3
	 * 
	 */
	public State(int disks[]) throws BadRodException
	{
		parent = null;
		level = 0;
		for (int rod : disks)
		{
			if (rod < 0 || rod > 2)
			{
				throw new BadRodException();
			} else
			{
				this.disks = disks;
			}
		}
	}

	/**
	 * Funkcja pomocnicza do liczenia stanów
     * @return Zwraca stan z przeniosionym jednym dyskiem 
     * @param disk - dysk do przeniesienia 
	 * @param rod - słupek, na który przeniosiony zostanie dysk
	 */
	private State nextState(int disk, int rod) throws BadRodException
	{
		
		int tmpDisks[] = disks.clone();

		if (rod < 0 || rod > 2)
		{
			throw new BadRodException();
		}
		
		tmpDisks[disk]=rod;
		State tmpState = new State(tmpDisks);
		tmpState.parent = this;
		tmpState.level = this.level + 1;
		return tmpState;
	}

		/**
		 * Wyślwietla na stdout stan. Na potrzeby testów
		 */
	public void print()
	{
		for (int rodNb = 0; rodNb <= 2; rodNb++)
		{
			System.out.print("[ ");
			for (int i = 0; i < disks.length; i++)
			{
				if (disks[i] == rodNb)
				{
					System.out.print(i + " ");
				}
			}
			System.out.print("]");
		}
		System.out.print("\n");
	}

	/** 
	* @return ilość dysków w stanie
	*/
	public int size()
	{
		return disks.length;
	}

	
	/**
	 * Sprawdza czy stany są zgodne
	 * @param another - drugi stan
	 * @return true jeżeli zgodne
	 */
	public boolean equals(State another)
	{
		return Arrays.equals(this.disks, another.disks);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof State))
			return false;
		else
		{
			State state = (State) obj;
			return equals(state);
		}

	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(disks);
	}

	/**
	 * @return Lista następnych możliwych stanów wieży
	 */
	public State[] nextStates()
	{
		
		ArrayList<State> states = new ArrayList<State>();

		int max[] = { -1, -1, -1 };
		//Znajdź numery najwyższych krążków na każdym paliku
		for(int i=0; i<disks.length; i++)
		{
			if(i>max[disks[i]])
			{
				max[disks[i]]=i;
			}
		}
		
		//Policz możliwe kombinacje
		for (int fromRod=0; fromRod<=2; fromRod++)
		{
			for(int toRod=0; toRod<=2; toRod++)
			{
				if(toRod!=fromRod && max[toRod]<max[fromRod])
				{
					states.add(nextState(max[fromRod], toRod));
				}
			}
		}

		State statesArray[] = new State[states.size()];
		states.toArray(statesArray);
		return statesArray;
	}

	public int whereIsChange() {
		for(int change = 0; change < disks.length; change++)
			if(disks[change]!=parent.disks[change])
				return change;
		// TODO
		return 0;
	}
}
