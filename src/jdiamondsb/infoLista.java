/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdiamondsb;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author jan
 */
public class infoLista extends JPanel
{
	private JLabel skoreLabel = new JLabel("Skóre: ");
	private static JLabel skoreLabel2 = new JLabel("");
	//private JLabel casLabel = new JLabel("  Čas: ");
	//private JLabel casLabel2 = new JLabel("");
	
	public infoLista()
	{
		add(skoreLabel);
		add(skoreLabel2);
		//add(casLabel);
		//add(casLabel2);
		
		Font f = skoreLabel2.getFont();
		skoreLabel2.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
	}
	
	public static JLabel getLabel() 
	{
		return skoreLabel2;
	}
}
