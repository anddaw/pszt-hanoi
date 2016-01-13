package psztView;

/**
 * Klasa startowa
 * Inicjuje cały program w niej main
 * @author JD
 *
 */
public class PsztViewControler
{
	PsztFrame psztFrame;

	public PsztViewControler()
	{
		psztFrame = new PsztFrame();
	}

	public static void main(String args[])
	{
		PsztViewControler psztViewControler = new PsztViewControler();
	}
}
