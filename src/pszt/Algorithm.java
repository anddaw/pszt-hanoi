package pszt;

/**
 * 
 * Interfejs do iplementacji algorytmów przeszukiwania
 * implementecję klas zostawiam kol. Markowi
 *
 */
public interface Algorithm
{
	/**
	 * 
	 * @param startState - stan początkowy
	 * @param finalState - stan końcowy
	 * @return Lista stanów włącznie z początkowym[0] i końcowym[n] - pomiędzy 
	 * 0 i n - stany należące do znalezionej ścieżki która jest rozwiązaniem
	 */
	public State[] getSolution(final State startState, final State finalState);
}
