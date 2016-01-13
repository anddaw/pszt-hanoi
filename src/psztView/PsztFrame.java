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
		this.setTitle("Temat: PW.P.4. Wieża Hanoi Prowadzący: dr inż. Paweł Wawrzyński");
		this.setSize(660,660);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public void switchPuzzle(int oldTower, int newTower,int puzzle){
		psztPanel.switchPuzzle(oldTower, newTower, puzzle);
	}
	
}
