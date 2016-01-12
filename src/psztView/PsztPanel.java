package psztView;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pszt.Algorithm;
import pszt.BFS;
import pszt.IterativeDepthFirst;
import pszt.Model;
import pszt.State;
import pszt.TwoWay;

/*
 * klasa reprezentujaca widok, odpowiada za zmiane stanu i dzialanie calego programu
 * @author JD
 */
//UWAGA brakuje odwolania do guzika URUCHOM - musi byc odwolanie do kontroler/algorytmu - zeby to sie w sensownym tempie dzialo
public class PsztPanel extends JPanel
{

	/** Pole wyboru algorytmu */
	private JComboBox<String> comboBoxAlgorithm;
	/** Guzik restartu */
	private JButton buttonRestart;
	/** Guzik Akceptacji algorytmu i liczby krazkow */
	private JButton buttonAccept;
	/** Guzik uruchomienia danego algorytmu (po nacisnieciu nowy stan */
	private JButton buttonRun;
	/** Pole tekstowe do wpisania liczby krazkow */
	private JTextField fieldTextAmountOfPuzzle;
	/** Guzik Informacyjny */
	private JButton buttonInfo;
	/** Lista krazkow w postaci kwadratow z AWT */
	private ArrayList<Canvas> Tower;
	/** Zmienna przechowuje ile jest w sumie krazkow */
	private int amountPuzzle;
	/** Zmienna przechowuje liczbe krazkow na poszczegolnych palikach */
	private int[] amountInTower;
	/** Rozwiązanie **/
	private State[] solution;

	public PsztPanel()
	{

		comboBoxAlgorithm = new JComboBox<String>();
		buttonRestart = new JButton("RESTART");
		buttonAccept = new JButton("AKCEPTUJ");
		buttonRun = new JButton("URUCHOM");
		buttonInfo = new JButton("WSPARCIE");
		fieldTextAmountOfPuzzle = new JTextField();
		fieldTextAmountOfPuzzle = new JTextField();
		amountInTower = new int[3];
		Tower = new ArrayList<Canvas>();
		UstawPanel();
		UstawAkcje();
	}

	/** Rozmiesczenie guzików i pol swingowych w odpowiednich miejscach */
	private void UstawPanel()
	{
		// Ustawienie tla
		setBackground(new Color(255, 204, 153));
		setLayout(null);

		// Ustawienie listy rozwijalnej z algorytmami
		comboBoxAlgorithm.setBounds(10, 10, 200, 70);
		comboBoxAlgorithm.addItem("Przeszukiwanie wszerz");
		comboBoxAlgorithm
				.addItem("Przeszukiwanie w głąb  z iteracyjnym pogłębianiem");
		comboBoxAlgorithm.addItem("Przeszukiwanie dwukierunkowe");
		add(comboBoxAlgorithm);

		// Ustawienie guzika restart - ponownie mo�emy wybrac algorytm i zaczac
		// od stanu poczatkowego
		buttonRestart.setBounds(374, 10, 100, 40);
		add(buttonRestart);
		buttonRestart.setEnabled(false);

		// Ustawienie guzika akceptuj - akceptacja algorytmu i sposobu
		// przetwarzania (sposoby jak ma si� wykonywac)
		buttonAccept.setBounds(374, 65, 100, 40);
		add(buttonAccept);

		// Ustawienie guzika uruchom -
		buttonRun.setBounds(374, 120, 100, 40);
		add(buttonRun);
		buttonRun.setEnabled(false);

		// ustawienie liczby krążków - pole tekstowe
		fieldTextAmountOfPuzzle.setBounds(10, 105, 200, 70);
		add(fieldTextAmountOfPuzzle);
		fieldTextAmountOfPuzzle.setColumns(10);

		// Ustawienie guzika informacyjnego
		buttonInfo.setBounds(514, 10, 126, 150);
		add(buttonInfo);

	}

	/** Ustawiane akcje dla poszególnych guzików */
	private void UstawAkcje()
	{

		/* Akcja dla guzika AKCEPTUJ */
		buttonAccept.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (isNumeric(fieldTextAmountOfPuzzle.getText()) == true)
				{
					buttonAccept.setEnabled(false);
					comboBoxAlgorithm.setEnabled(false);
					fieldTextAmountOfPuzzle.setEnabled(false);
					buttonRestart.setEnabled(true);
					buttonRun.setEnabled(true);
					CreateTower(amountPuzzle);
					/* Ustawienie licznikow krazkow na palikach */
					amountInTower[0] = amountPuzzle;
					amountInTower[1] = 0;
					amountInTower[2] = 0;
					/*
					 * Tutaj będzie wys�anie do modelu co zosta�o wybrane
					 * (algorytm, rodzaj operacji).
					 * model(comboBoxAlgorithm.getSelectedItem
					 * (),comboBoxType.getSelectedItem());
					 */

				} else
				{
					JOptionPane
							.showMessageDialog(
									null,
									"Podano błedne dane - w polu do wpisywania ilości krązków podano nie liczbę",
									"Błąd", JOptionPane.ERROR_MESSAGE);
					fieldTextAmountOfPuzzle.setText("");
				}
				
				int selectedAlgorithm = comboBoxAlgorithm.getSelectedIndex();

				Algorithm algorithm;

				switch (selectedAlgorithm)
				{
				case 0:
					algorithm = new BFS();
					break;
				case 1:
					algorithm = new IterativeDepthFirst();
				default:
					algorithm = new TwoWay();
					break;
				}
				
				Model model = new Model();
				solution = model.findSolution(amountPuzzle, algorithm);

			}
		});

		/* Akcja dla guzika RESTART */
		buttonRestart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonAccept.setEnabled(true);
				comboBoxAlgorithm.setEnabled(true);
				fieldTextAmountOfPuzzle.setEnabled(true);
				fieldTextAmountOfPuzzle.setText("");
				JOptionPane
						.showMessageDialog(
								null,
								"Zrestarowano dotychczasowe ustawienia - wpisz ponownie liczbę krążków i wybierz algorytm",
								"Wsparcie", JOptionPane.INFORMATION_MESSAGE);
				for (Canvas canvas : Tower)
				{
					remove(canvas);
				}
				Tower.removeAll(Tower);
				buttonRestart.setEnabled(false);
			}
		});

		/* Akcja dla guzika URUCHOM */
		buttonRun.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});

		/* Akcja dla guzika WSPARCIE */
		buttonInfo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane
						.showMessageDialog(
								null,
								"Wybierz algorytm, wpisz liczbę krążków(od 3 do 20)\nZaakceputuj kliknięciem w guzik AKCEPTUJ\nAby zacząć wykonywać wybrany algorytm naciśnij guzik URUCHOM\nAby zacząć wybieranie od nowa naciśnij guzik RESTART",
								"Wsparcie", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	/**
	 * Funkcja sprawdzaj�ca czy liczba kr��k�w jest z podanego zakresu i jest to
	 * napewno liczba
	 * 
	 * @param str
	 *            - wartosc z fieldTextAmountOfPuzzle (pola tekstowego ktore
	 *            oznacza ile jest krazkow)
	 */
	private boolean isNumeric(String str)
	{
		int onlyInteger;
		try
		{
			onlyInteger = Integer.parseInt(str);
			if (onlyInteger < 3 || onlyInteger > 20)
			{
				return false;
			}

		} catch (NumberFormatException nfe)
		{
			return false;
		}
		amountPuzzle = onlyInteger;
		return true;
	}

	/**
	 * Tworzenie wiezy Hanoi w stanie poczatkowym
	 * 
	 * @param heightOfTower
	 *            - wysokosc wiezy
	 */
	private void CreateTower(int heightOfTower)
	{
		int x = 15, y = 610, width = 200; // odpowiednie parametry do setBounds
		for (int i = 0; i < heightOfTower; i++, x += 5, y -= 20, width -= 10)
		{
			Canvas canvas = new Canvas();
			canvas.setBackground(Color.BLACK);
			canvas.setBounds(x, y, width, 15);
			add(canvas);
			Tower.add(canvas);
		}
	}

	/**
	 * Funkcja zmieniajaca polozenie krazkow
	 * 
	 * @param oldTower
	 *            - palik z ktorego chcemy przeniesc krazek
	 * @param newTower
	 *            - palik na ktory chcemy przeniesc krazek
	 * @param puzzle
	 *            - numer krazka ktory przenosimy
	 */
	void switchPuzzle(int oldTower, int newTower, int puzzle)
	{
		int x, y = 610;
		amountInTower[oldTower]--; // odejmuje jeden krazek od ilosci krazkow na
									// starym paliku
		y = y - 20 * amountInTower[newTower]; // Oblicza wspolrzedna Y, patrze
												// ile jest krazkow juz na nowym
												// paliku, zeby wiedziec jak
												// wysoko nowy umiescic

		// Obliczam wsporzedna X, w zaleznosci od palika rozni sie x
		if (oldTower == 0)
		{
			x = Tower.get(puzzle).getX() + 215 * newTower;
		} else if (oldTower == 1)
		{
			if (newTower == 0)
				x = Tower.get(puzzle).getX() - 215;
			else
				x = Tower.get(puzzle).getX() + 215;
		} else
		{
			if (newTower == 1)
				x = Tower.get(puzzle).getX() - 215;
			else
				x = Tower.get(puzzle).getX() - 430;
		}
		Tower.get(puzzle).setBounds(x, y, Tower.get(puzzle).getWidth(), 15);// trzeci
																			// parametr
																			// to
																			// szerokosc
																			// (width)
																			// pozostaje
																			// taki
																			// sam
		amountInTower[newTower]++; // dodaje jeden krazek do krazkow na nowym
									// paliku
	}

}
