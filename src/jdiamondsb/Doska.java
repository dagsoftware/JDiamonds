/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdiamondsb;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author jan
 */
public class Doska extends JPanel
{
	final int rozmery = 10;
	final int rozmerDiamantu = 40;
	int aX, aY, bX, bY, pomX, pomY;
	boolean druhyDiamant;
	static Image iRed, iBlue, iGreen, iYellow, iWhite;
	long skore;
	
	int[][] sch = new int[][]
	{
		{0, 1, 2, 3, 4, 3, 2, 3, 2, 0 },
		{3, 1, 1, 4, 4, 1, 3, 2, 1, 1 },
		{0, 4, 2, 1, 2, 4, 4, 1, 2, 4 },
		{0, 2, 0, 2, 0, 0, 3, 1, 3, 3 },
		{4, 4, 3, 0, 2, 1, 4, 2, 3, 2 },
		{4, 2, 4, 2, 1, 0, 4, 3, 4, 2 },
		{3, 4, 0, 2, 3, 0, 0, 2, 4, 1 },
		{0, 1, 2, 0, 1, 4, 4, 3, 0, 2 },
		{4, 4, 2, 0, 2, 0, 3, 1, 4, 0 },
		{2, 1, 4, 4, 0, 0, 4, 3, 4, 0 }
	};
	
	public Doska()
	{
		addMouseListener(new MouseInputAdapter());
		iRed    = Toolkit.getDefaultToolkit().getImage("img/p_red.png");
		iBlue   = Toolkit.getDefaultToolkit().getImage("img/p_blue.png");
		iGreen  = Toolkit.getDefaultToolkit().getImage("img/p_green.png");
		iYellow = Toolkit.getDefaultToolkit().getImage("img/p_yellow.png");
		iWhite  = Toolkit.getDefaultToolkit().getImage("img/p_white.png");
		skore = 0;
		
		//vygenerovanie nahodnej zostavy; zakomentovat pre pouzitie prednastavenej zostavy
		Random randomGenerator = new  Random();
		for (int y = 0; y < rozmery; y++)//riadky od dola
		{
			for (int x = 0; x < rozmery; x++)//stlpce od lava
			{
				sch[x][y] = randomGenerator.nextInt(5);
			}
		}
		pokontroluj();
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		for (int x = 0; x < rozmery; x++)
		{
			for (int y = 0; y < rozmery; y++)
			{
				vykresliStvorec(g, x, y, sch[x][y]);
			}
		}
	}

	private void vykresliStvorec(Graphics g, int x, int y, int i)
	{
		Graphics2D g2d = (Graphics2D) g;
		switch (sch[x][y])
		{
			case 0: //white
				g2d.drawImage(iWhite,  x*40, y*40, this);
				break;
			case 1: //green
				g2d.drawImage(iGreen,  x*40, y*40, this);
				break;
			case 2: //yellow
				g2d.drawImage(iYellow, x*40, y*40, this);
				break;
			case 3: //red
				g2d.drawImage(iRed,    x*40, y*40, this);
				break;
			case 4: //blue
				g2d.drawImage(iBlue,   x*40, y*40, this);
				break;
		}
	}

	class MouseInputAdapter extends MouseAdapter
	{

		@Override
		public void mouseClicked(MouseEvent e)
		{
			vybratDiamanty(e);
		}
	}

	private void vybratDiamanty(MouseEvent e)
	{
		int x = e.getX() / rozmerDiamantu;
		int y = e.getY() / rozmerDiamantu;
		if (!druhyDiamant)//pri prvom kliku
		{
			aX = x;
			aY = y;
			//System.out.println("prvy " + x + "," + y);//DEBUG
			druhyDiamant = true;
		}
		else//pri druhom kliku
		{
			bX = x;
			bY = y;
			//System.out.println("druhy " + x + "," + y);//DEBUG
			if (priSebe())
			{
				//System.out.println("prehodit");//DEBUG
				prehod();
			}
			//System.out.println("nie pri sebe, abo sa uz prehodilo");//DEBUG
			druhyDiamant = false;
		}
	}

	private boolean priSebe()
	{
		return (Math.abs(aX - bX) == 1 && aY == bY) 
			|| (Math.abs(aY - bY) == 1 && aX == bX);
	}

	private void prehod()
	{
		int Pom = sch[aX][aY];
		sch[aX][aY] = sch[bX][bY];
		sch[bX][bY] = Pom;
		
		repaint();
		ries();
	}

	private void ries()
	{
		//System.out.println("riesi sa");//TODO	
		
		if (sch[aX][aY] == sch[bX][bY])
			return;
		else
		{
			//v stlpcoch stredne sa menilo
			if (aY+1 < rozmery && aY-1 >= 0 && sch[aX][aY] == sch[aX][aY+1] && sch[aX][aY] == sch[aX][aY-1])
			{
				skore += 100;
				infoLista.getLabel().setText(Long.toString(skore));
				System.out.println("skore: "+ skore);
				if (aY+2 < rozmery && sch[aX][aY] == sch[aX][aY+2])
				{
					skore += 100;
					infoLista.getLabel().setText(Long.toString(skore));
					System.out.println("skore: "+ skore);
					sch[aX][aY+2] = -1;
				}
				if (aY-2 >= 0 && sch[aX][aY] == sch[aX][aY-2])
				{
					skore += 100;
					infoLista.getLabel().setText(Long.toString(skore));
					System.out.println("skore: "+ skore);
					sch[aX][aY-2] = -1;
				}
				sch[aX][aY] = sch[aX][aY+1] = sch[aX][aY-1] = -1;
				popadaj();
			}
			if (bY+1 < rozmery && bY-1 >= 0 && sch[bX][bY] == sch[bX][bY+1] && sch[bX][bY] == sch[bX][bY-1])
			{
				skore += 100;
				infoLista.getLabel().setText(Long.toString(skore));
				System.out.println("skore: "+ skore);
				if (bY+2 < rozmery && sch[bX][bY] == sch[bX][bY+2])
				{
					skore += 100;
					infoLista.getLabel().setText(Long.toString(skore));
					System.out.println("skore: "+ skore);
					sch[bX][bY+2] = -1;
				}
				if (bY-2 >= 0 && sch[bX][bY] == sch[bX][bY-2])
				{
					skore += 100;
					infoLista.getLabel().setText(Long.toString(skore));
					System.out.println("skore: "+ skore);
					sch[bX][bY-2] = -1;
				}
				sch[bX][bY] = sch[bX][bY+1] = sch[bX][bY-1] = -1;
				popadaj();
			}
			
			//v stplcoch krajne sa menilo
			if (aY+2 < rozmery && sch[aX][aY] == sch[aX][aY+1] && sch[aX][aY+1] == sch[aX][aY+2])
			{
				skore += 100;
				infoLista.getLabel().setText(Long.toString(skore));
				System.out.println("skore: "+ skore);
				sch[aX][aY] = sch[aX][aY+1] = sch[aX][aY+2] = -1;
				popadaj();
			}
			else
			{
				if (aY-2 >= 0 && sch[aX][aY] == sch[aX][aY-1] && sch[aX][aY-1] == sch[aX][aY-2])
				{
					skore += 100;
					infoLista.getLabel().setText(Long.toString(skore));
					System.out.println("skore: "+ skore);
					sch[aX][aY] = sch[aX][aY-1] = sch[aX][aY-2] = -1;
					popadaj();
				}
			}
			if (bY+2 < rozmery && sch[bX][bY] == sch[bX][bY+1] && sch[bX][bY+1] == sch[bX][bY+2])
			{
				skore += 100;
				infoLista.getLabel().setText(Long.toString(skore));
				System.out.println("skore: "+ skore);
				sch[bX][bY] = sch[bX][bY+1] = sch[bX][bY+2] = -1;
				popadaj();
				return;
			}
			else
			{
				if (bY-2 >= 0 && sch[bX][bY] == sch[bX][bY-1] && sch[bX][bY-1] == sch[bX][bY-2])
				{
					skore += 100;
					infoLista.getLabel().setText(Long.toString(skore));
					System.out.println("skore: "+ skore);
					sch[bX][bY] = sch[bX][bY-1] = sch[bX][bY-2] = -1;
					popadaj();
					return;
				}
			}
			
			//////////////////////////////////////////////////////////////////////////
			//v stlpcoch stredne sa menilo
			if (aX+1 < rozmery && aX-1 >= 0 && sch[aX][aY] == sch[aX+1][aY] && sch[aX][aY] == sch[aX-1][aY])
			{
				skore += 100;
				infoLista.getLabel().setText(Long.toString(skore));
				System.out.println("skore: "+ skore);
				if (aX+2 < rozmery && sch[aX][aY] == sch[aX+2][aY])
				{
					skore += 100;
					infoLista.getLabel().setText(Long.toString(skore));
					System.out.println("skore: "+ skore);
					sch[aX+2][aY] = -1;
				}
				if (aX-2 >= 0 && sch[aX][aY] == sch[aX-2][aY])
				{
					skore += 100;
					infoLista.getLabel().setText(Long.toString(skore));
					System.out.println("skore: "+ skore);
					sch[aX-2][aY] = -1;
				}
				sch[aX][aY] = sch[aX+1][aY] = sch[aX-1][aY] = -1;
				popadaj();
			}
			if (bX+1 < rozmery && bX-1 >= 0 && sch[bX][bY] == sch[bX+1][bY] && sch[bX][bY] == sch[bX-1][bY])
			{
				skore += 100;
				infoLista.getLabel().setText(Long.toString(skore));
				System.out.println("skore: "+ skore);
				if (bX+2 < rozmery && sch[bX][bY] == sch[bX+2][bY])
				{
					skore += 100;
					infoLista.getLabel().setText(Long.toString(skore));
					System.out.println("skore: "+ skore);
					sch[bX+2][bY] = -1;
				}
				if (bX-2 >= 0 && sch[bX][bY] == sch[bX-2][bY])
				{
					skore += 100;
					infoLista.getLabel().setText(Long.toString(skore));
					System.out.println("skore: "+ skore);
					sch[bX-2][bY] = -1;
				}
				sch[bX][bY] = sch[bX+1][bY] = sch[bX-1][bY] = -1;
				popadaj();
			}
			
			//v riadkoch krajne sa menilo
			if (aX+2 < rozmery && sch[aX][aY] == sch[aX+1][aY] && sch[aX+1][aY] == sch[aX+2][aY])
			{
				skore += 100;
				infoLista.getLabel().setText(Long.toString(skore));
				System.out.println("skore: "+ skore);
				sch[aX][aY] = sch[aX+1][aY] = sch[aX+2][aY] = -1;
				popadaj();
			}
			else
			{
				if (aX-2 >= 0 && sch[aX][aY] == sch[aX-1][aY] && sch[aX-1][aY] == sch[aX-2][aY])
				{
					skore += 100;
					infoLista.getLabel().setText(Long.toString(skore));
					System.out.println("skore: "+ skore);
					sch[aX][aY] = sch[aX-1][aY] = sch[aX-2][aY] = -1;
					popadaj();
				}
			}
			if (bX+2 < rozmery && sch[bX][bY] == sch[bX+1][bY] && sch[bX+1][bY] == sch[bX+2][bY])
			{
				skore += 100;
				infoLista.getLabel().setText(Long.toString(skore));
				System.out.println("skore: "+ skore);
				sch[bX][bY] = sch[bX+1][bY] = sch[bX+2][bY] = -1;
				popadaj();
				return;
			}
			else
			{
				if (bX-2 >= 0 && sch[bX][bY] == sch[bX-1][bY] && sch[bX-1][bY] == sch[bX-2][bY])
				{
					skore += 100;
					infoLista.getLabel().setText(Long.toString(skore));
					System.out.println("skore: "+ skore);
					sch[bX][bY] = sch[bX-1][bY] = sch[bX-2][bY] = -1;
					popadaj();
					return;
				}
			}
			
			//neriesilo sa, tak sa prehodi naspat
			int Pom = sch[aX][aY];
			sch[aX][aY] = sch[bX][bY];
			sch[bX][bY] = Pom;
		}
	}

	private void popadaj()
	{
		for (int y = rozmery-1; y >= 0; y--)//riadky od dola
		{
			for (int x = 0; x < rozmery; x++)//stlpce od lava
			{
				if (sch[x][y] == -1)
				{
					//if (sch[x][y-1])
					for (int i = y-1; i >=0; i--)//hladanie prveho diamantu nad
					{
						if (sch[x][i] != -1)
						{
							//posun
							sch[x][y] = sch[x][i];
							sch[x][i] = -1;
							break;
						}
					}
				}
			}
		}
		pridaj();
	}
	
	private void pridaj()
	{
		Random randomGenerator = new  Random();
		for (int y = rozmery-1; y >= 0; y--)//riadky od dola
		{
			for (int x = 0; x < rozmery; x++)//stlpce od lava
			{
				if (sch[x][y] == -1)
				{
					sch[x][y] = randomGenerator.nextInt(5);
				}
			}
		}
		//System.out.println("sem este pride");
		pokontroluj();
	}
	
	private void pokontroluj()
	{
		for (int y = rozmery-1; y >= 0; y--)//riadky od dola
		{
			for (int x = 0; x < rozmery; x++)//stlpce od lava
			{
				if (y+1 < rozmery && y-1 >= 0 && sch[x][y] == sch[x][y+1] && sch[x][y] == sch[x][y-1])
				{
					sch[x][y] = sch[x][y+1] = sch[x][y-1] = -1;
					popadaj();
				}
				else
				{
					if (x+1 < rozmery && x-1 >= 0 && sch[x][y] == sch[x+1][y] && sch[x][y] == sch[x-1][y])
					{
						sch[x][y] = sch[x+1][y] = sch[x-1][y] = -1;
						popadaj();
					}
				}
			}
		}
	}
	
}
