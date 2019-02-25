package jdiamondsb;

import java.awt.*;
import java.util.logging.*;
import javax.swing.*;

/**
 *
 * @author jan
 */
public class JDiamondsB extends JFrame
{

	public JDiamondsB()
	{
		Doska doska = new Doska();
		getContentPane().add(doska);
		getContentPane().add(new infoLista(), BorderLayout.SOUTH);
		
		setTitle("JDiamonds");
		//setSize(400+16, 400+38);//zohladnene okraje windows 7 - bez infolisty
		setSize(400+16, 400+62);//zohladnene okraje windows 7 - s infolistou
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
		{
			Logger.getLogger(JDiamondsB.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		JDiamondsB hra = new JDiamondsB();
		hra.setVisible(true);
	}
	
}