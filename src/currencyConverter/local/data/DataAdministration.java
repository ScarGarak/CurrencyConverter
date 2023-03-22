package currencyConverter.local.data;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import java.util.Currency;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import currencyConverter.local.valueobjects.CurrencyObject;
import currencyConverter.local.data.persistence.DataPersistenceManager;
import currencyConverter.local.data.persistence.ObjectDataPersistenceManager;

public class DataAdministration {
	
	private URL ecbURL;
	private Document doc;
	private SAXBuilder sb;
	
	private Element root;
	private Element envelopeSubject;
	private Element elSender;
	private Element elCube;
	private Element senderName;
	private Element cubeTime;
	
	private List<Element> rootChildren;
	private List<Element> senderChild;
	private List<Element> cubeChild;
	private List<Element> listCubeChildren;
	private List<CurrencyObject> lco = new Vector<CurrencyObject>();
	
//	persistence-interface, managing details of data access
	private DataPersistenceManager pm = new ObjectDataPersistenceManager();
	
	public List<CurrencyObject> readDataXML() {
		
//		int i = 0;
		
//		read XML from web link
		try {
			
			ecbURL = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml?f70b7eeaef6f54bf8b8aae4be6655034");
			doc = null;
			sb = new SAXBuilder();
			
			try {
					
				doc = sb.build(ecbURL);
					
				} catch(JDOMException e) {
					e.printStackTrace();
				}
				
//			add XML rootElement to Element root and print root.getName
			root = doc.getRootElement();
			
//			add XML root children to rootChildren
//			add element 0 to envelopeSubject, element 1 (Sender) to elSender and 2 (Cube) to elCube
			rootChildren = root.getChildren();
			envelopeSubject = rootChildren.get(0);
			elSender = rootChildren.get(1);
			elCube = rootChildren.get(2);
			
//			add XML Sender child to senderName
			senderChild = elSender.getChildren();
			senderName = senderChild.get(0);
			
//			add XML elCube children to list cubeChild and adds element 0 (Cube) to cubeTime				
			cubeChild = elCube.getChildren();
			cubeTime = cubeChild.get(0);
			
//			add XML cubeChild to listCubeChildren
			listCubeChildren = cubeTime.getChildren();
//			test
				
		} catch(IOException e) {
			e.printStackTrace();
		}
		
//		add CurrencyObject items to lco vector
		for(Element a : listCubeChildren) {
			
			Currency c = Currency.getInstance(a.getAttributeValue("currency"));
//			create currencyObjects
			CurrencyObject co = new CurrencyObject(c.getSymbol(),a.getAttributeValue("rate"),c.getDisplayName(),c.getNumericCode());			
			lco.add(co);
			
		}
		
		Currency c = Currency.getInstance("EUR");
		CurrencyObject co = new CurrencyObject(c.getSymbol(),"1",c.getDisplayName(),c.getNumericCode());
//		System.out.println(co);
		lco.add(co);
		
		return lco;
		
	}
	
//	read XML from web link
	
//	print XML currency objects
	public void printCurrencyXML(List<CurrencyObject> co) {
		
//		print root element
		System.out.println("Root: " + root.getName());
		System.out.println("----------------------------------------");
		
//		print content of rootChildren by name
		for(Element a : rootChildren) {
			System.out.println("Root children: " + a.getName());
		}
		
		System.out.println("----------------------------------------");
		
//		print subject of Envelope
		System.out.println("envelopeSubject: " + envelopeSubject.getTextNormalize());
		
//		print name of sender
		System.out.println("senderName: " + senderName.getTextNormalize());
		
//		print attribute time from cubeTime
		System.out.println("cubeTime: " + cubeTime.getAttributeValue("time"));
		
		System.out.println(co.size() + " -> Objete wurden erstellt!");
		
		for(int j=0;j<co.size();j++) {
			
			System.out.println("----------------------------------------");
			System.out.println("ID: " + j);
			System.out.println(co.get(j).toString());
		}
		
	}
	
	public String getStringSenderName(List<CurrencyObject> co) {
		return senderName.getTextNormalize();
	}
	
	public String getStringCubeTime(List<CurrencyObject> co) {
		return cubeTime.getAttributeValue("time");
	}
	
	public String getRate() {
		
		return null;
	}
	
}
