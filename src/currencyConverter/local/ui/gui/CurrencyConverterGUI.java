package currencyConverter.local.ui.gui;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import currencyConverter.local.data.DataAdministration;
import currencyConverter.local.math.Calculation;
import currencyConverter.local.ui.gui.objects.AddGUIObjects;
import currencyConverter.local.valueobjects.CurrencyObject;

@SuppressWarnings("serial")
public class CurrencyConverterGUI extends JFrame {
	
//	needed variables
	private static List<CurrencyObject>  co;
	private static DataAdministration da;
	private static AddGUIObjects aGO;
	
	public CurrencyConverterGUI() throws IOException {
		super("Bla_blub - Currency Converter");
//		create logic object from class
		initialize();
	}
	
	private void initialize() {
		setTitle("CurrencyConverter");
		setResizable(false);
		setSize(new Dimension(300,200));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		paintCurrencyConverter();
		
//		add events
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void paintCurrencyConverter() {
//		add objects to JFrame
		AddGUIObjects aGO = new AddGUIObjects();
		aGO.addToJFrame(this, co, da.getStringSenderName(co), da.getStringCubeTime(co));
//		add objects/layouts to objects
	}
	
	public static void main(String[] args) {
//		create a new thread, which creates an instance of CurrencyConverterGUI
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				da = new DataAdministration();
				co = new Vector<CurrencyObject>(da.readDataXML());
				Calculation c = new Calculation();
								
//				da.printCurrencyXML(co);
				
				try {
					new CurrencyConverterGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int i = 10;
				System.out.println("----------Currency-Converter------------");
				System.out.println("----------------------------------------");
				System.out.println("------" + i + " " + co.get(0).getSymbol() + " sind " + c.calc(co.get(0),co.get(29),i) + " " + co.get(29).getSymbol() + "--------");
				System.out.println("----------------------------------------");
				System.out.println("----------------------------------------");
				
			}
		});
	}
}
