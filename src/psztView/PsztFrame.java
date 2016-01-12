package psztView;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

/**
 * Klasa tworzenia ramki
 * @author JAKUB
 * 
 */
public class PsztFrame extends JFrame {
	
	private PsztPanel psztPanel;
	
	public PsztFrame(){
		psztPanel = new PsztPanel();
		setupPanel();
	}

	/**Stworzenie odpowiedniej ramki*/
	private void setupPanel(){
		this.setContentPane(psztPanel);
		this.setTitle("Temat: PW.P.4. Wieże Hanoi Autorzy: Marek Borkowski Andrzej Dawidziuk Jakub Dudziak Prowadz�cy: dr in�. Pawe� Wawrzynski");
		this.setSize(660,660);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public void switchPuzzle(int oldTower, int newTower,int puzzle){
		psztPanel.switchPuzzle(oldTower, newTower, puzzle);
	}
	
}
