/**
 * 
 */
package currencyConverter.local.ui.gui.objects;

import static java.awt.GridBagConstraints.CENTER;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DocumentFilter;

import currencyConverter.local.math.Calculation;
import currencyConverter.local.valueobjects.CurrencyObject;

/**
 * @author mortlust
 *
 */
public class AddGUIObjects extends DocumentFilter implements ActionListener, KeyListener, ItemListener {
	
	private int itemChanged = 0;
	private int selectedIndexFrom, selectedIndexTo;
	private int defaultValue;
	
//	private DataAdministration da;
		
//	private int jFrameWidth;
//	private int jFrameHeight;
	private int fromIndex;
	private int toIndex;
	
	private String[] choice;
	private String[] choiceFrom;
	private String[] choiceTo;
	
	private JLabel fromLabel;
	private JLabel toLabel;
	private JLabel dataSource;
	private JLabel date;
	
	private JTextField fromJTextField;
	private JTextField toJTextField;
//	use instead of regular JTextField??
//	private JFormattedTextField ftf = new JFormattedTextField(NumberFormat.getIntegerInstance());
	
	private DocumentFilter filter;
	
	private JComboBox<String> fromJComboBox = new JComboBox<>();
	private JComboBox<String> toJComboBox = new JComboBox<>();
	
	private DefaultComboBoxModel modelFrom;
	private DefaultComboBoxModel modelTo;
	
	List<CurrencyObject> co;
	
	private JButton fromButton;
	private JButton toButton;
	
	private Calculation c = new Calculation();
	
	/**
	 * add objects to JFrame
	 * @param cubeTime 
	 * @param da 
	 */
	public void addToJFrame(JFrame jframe, List<CurrencyObject>  co, String senderName, String cubeTime) {

		this.co = new Vector<CurrencyObject>();
		this.co = co;
		
		defaultValue = 1;
		
		dataSource = new JLabel(senderName);
		date = new JLabel(cubeTime);
		
		fromJTextField = new JTextField();
		toJTextField = new JTextField();
		
//		disable paste
		fromJTextField.setTransferHandler(null);
		toJTextField.setTransferHandler(null);
		
		fromJTextField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toJTextField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
//		fromButton = new JButton("test");
		
		choice = new String[co.size()];
		choiceFrom = new String[co.size()-1];
		choiceTo = new String[co.size()-1];
 		
		//////////////////////////////////////////////////
		for(int i = 0;i<co.size();i++) {
			choice[i] = co.get(i).getSymbol();			
		}
		
//		initializing combo boxes
		initializeComboBox(co);
		
//		fromJComboBox = new JComboBox<>(choiceFrom);
//		toJComboBox = new JComboBox<>(choiceTo);
		
		//////////////////////////////////////////////////
		
//		add events
		fromJTextField.addKeyListener(this);
		toJTextField.addKeyListener(this);
		
		fromJComboBox.addItemListener(this);
		toJComboBox.addItemListener(this);
		
		fromJTextField.setText("" + defaultValue);
		toJTextField.setText("" + c.calc(co.get(fromIndex), co.get(toIndex), Integer.parseInt(fromJTextField.getText())));
		
//		jFrameWidth = jframe.getWidth();
//		jFrameHeight = jframe.getHeight();
		
		jframe.setLayout(new GridBagLayout());
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		
		p1.setBackground(Color.green);
		p2.setBackground(Color.red);
		
		addGB(jframe,fromLabel,0,0,3,0,0);
		
		addGB(jframe,fromJTextField,0,1,2,0,0);
		addGB(jframe,fromJComboBox,2,1,1,0,0);
//		addGB(jframe,p1,2,1,1,0,0);
//		addGB(jframe,fromButton,2,1,1,0,0);
		
		addGB(jframe,toLabel,0,2,3,0,0);
		
		addGB(jframe,toJTextField,0,3,2,0,0);
		addGB(jframe,toJComboBox,2,3,1,0,0);
//		addGB(jframe,p2,2,3,1,0,0);
		
		addGB(jframe,dataSource,0,4,2,0,0);
		addGB(jframe,date,2,4,1,0,0);
		
	}

	//initializes comboBox and JLabel at start
	private void initializeComboBox(List<CurrencyObject>  co) {
		
		int j = 0;
		
		for(int i = 0;i<choice.length;i++) {
			if(!choice[i].equals(choice[1])) {
				choiceFrom[j] = choice[i];
				j++;
			} else
				toIndex = i;
		}
		
		j = 0;
		
		for(int i = 0;i<choice.length;i++) {
			if(!choice[i].equals(choice[0])) {
				choiceTo[j] = choice[i];
				j++;
			}else
				fromIndex = i;
		}
		
		setJComboBoxItems();
		
		setJLabelText(co,fromIndex, toIndex);
		
	}
	
//	update content of comboBox and JLabel during runtime
	private void initializeComboBox(ItemEvent ie, List<CurrencyObject>  co, String selectedFrom, String selectedTo) {
		
		int j = 0;
		
		for(int i = 0;i<choice.length;i++) {
			if(!choice[i].equals(selectedTo)) {
				choiceFrom[j] = choice[i];
				j++;
			} else
				toIndex = i;
		}
				
		j = 0;
		
		for(int i = 0;i<choice.length;i++) {
			if(!choice[i].equals(selectedFrom)) {
				choiceTo[j] = choice[i];
				j++;
			} else
				fromIndex = i;
		}
		
		updateJComboBox();
		
//		fromJComboBox.setSelectedIndex(fromIndex);
		
		setJLabelText(co,fromIndex, toIndex);
		
//		jframe.renew();
		
	}
	
//	public int returnIndex(String s) {
//		
//		for(int i = 0;i<choiceFrom.length;i++) {
//			if(fromJComboBox.getItemAt(i).equals(s)) {
//				return i;
//			}
//		}
//		
//		return 0;
//	}
	
	public void setJComboBoxItems() {
		
		fromJComboBox.removeAllItems();
		toJComboBox.removeAllItems();
				
		modelFrom = new DefaultComboBoxModel(choiceFrom);
		modelTo = new DefaultComboBoxModel(choiceTo);
		
		fromJComboBox.setModel(modelFrom);
		toJComboBox.setModel(modelTo);
	}
	
	public void updateJComboBox() {
		setJComboBoxItems();
	}
	
	private void setJLabelText(List<CurrencyObject>  co, int fromIndex, int toIndex) {
		
		for(int i = 0;i<co.size();i++) {
			if(choiceFrom[i].equals(co.get(fromIndex).getSymbol())) {
				fromLabel = new JLabel(co.get(fromIndex).getDisplayName());
				break;
			}
		}
		
		for(int i = 0;i<co.size();i++) {
			if(choiceTo[i].equals(co.get(toIndex).getSymbol())) {
				toLabel = new JLabel(co.get(toIndex).getDisplayName());
				break;
			}
		}
	}

	private void addGB(JFrame jframe, Component component, int gridx, int gridy, int gridwidth, int gridheight,int fill, double weightx, double weighty, int anchor, Insets insets,int ipadx, int ipady) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = fill;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.anchor = anchor;
        constraints.insets = insets;
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        jframe.add(component, constraints);
    }
	
	private void addGB(JFrame jframe, Component component, int gridx, int gridy, int width, int ipadx, int ipady) {
        addGB(jframe, component, gridx, gridy, width, 1, GridBagConstraints.BOTH, 0.0, 0.0, CENTER, new Insets(5, 5, 5, 5), ipadx, ipady);
	}
	
	private void addGB(JPanel p, Component component, int gridx, int gridy, int gridwidth, int gridheight,int fill, double weightx, double weighty, int anchor, Insets insets,int ipadx, int ipady) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = fill;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.anchor = anchor;
        constraints.insets = insets;
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        p.add(component, constraints);
    }
	
	private void addGB(JPanel p, Component component, int gridx, int gridy, int width, int ipadx, int ipady) {
        addGB(p, component, gridx, gridy, width, 1, GridBagConstraints.BOTH, 0.0, 0.0, CENTER, new Insets(5, 5, 5, 5), ipadx, ipady);
	}
	
	private void checkEntry(ActionEvent ke) {
		
		String s = fromJTextField.getText();
		fromJTextField.setText(s.replaceAll("[^0-9.]", ""));

	}
	
	private void checkEntry(KeyEvent ke) {
		
		String s = fromJTextField.getText();
		fromJTextField.setText(s.replaceAll("[^0-9.]", ""));
		
	}
	
	private void valueHasChanged(KeyEvent ke) {
//		.toPlainString() changes BigDecimal String from E+1 to 10
		if(ke.getSource() == fromJTextField) {
			toJTextField.setText((c.calc(co.get(fromIndex), co.get(toIndex), Integer.parseInt(fromJTextField.getText()))).toPlainString());
		}
		if(ke.getSource() == toJTextField) {
			fromJTextField.setText((c.calc(co.get(toIndex), co.get(fromIndex), Integer.parseInt(toJTextField.getText()))).toPlainString());
		}
	}
	
	private void itemHasChanged(ItemEvent ie) {
//		System.out.println("itemHasChanged");
		String fromSelectedItem, toSelectedItem;
//		fromSelectedItem = "" + ie.getItem();
		fromSelectedItem = "" + fromJComboBox.getSelectedItem();
//		System.out.println("fromSelectedItem -> " + fromSelectedItem);
		toSelectedItem = "" + toJComboBox.getSelectedItem();
		initializeComboBox(ie, co, fromSelectedItem, toSelectedItem);
//		fromSelectedItem = "" + fromJComboBox.getSelectedItem();
//		toSelectedItem = "" + toJComboBox.getSelectedItem();
////		System.out.println("fromSelectedItem" + fromSelectedItem);
//		initializeComboBox(co, fromSelectedItem, toSelectedItem);
		
//		if(ie.getSource() == fromJComboBox) {
//			System.out.println("source is fromJComboBox");
//			toSelectedItem = "" + toJComboBox.getSelectedItem();
//			initializeComboBox(co, fromSelectedItem, toSelectedItem);
//		}
		
////		something went wrong..
//		if(ie.getSource() == toJComboBox) {
//			System.out.println("source is toComboBox");
////			System.out.println("toJComboBox: ie.getItem() -> " + ie.getItem());
//			toSelectedItem = "" + toJComboBox.getSelectedItem();
//			initializeComboBox(co, fromSelectedItem, toSelectedItem);
//		}
	}
	
	@Override
	public void keyPressed(KeyEvent ke) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		// TODO Auto-generated method stub
		checkEntry(ke);
		if (!fromJTextField.getText().equals("") || !toJTextField.getText().equals("")) {
			valueHasChanged(ke);
		}
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		checkEntry(ae);
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		// TODO Auto-generated method stub
		if(ie.getStateChange() == 1) {
			if(itemChanged==0) {
				System.out.println("if(itemChanged==1) -> " + itemChanged);
				selectedIndexFrom = fromJComboBox.getSelectedIndex();
				selectedIndexTo = toJComboBox.getSelectedIndex();
				itemHasChanged(ie);
				itemChanged++;
				fromJComboBox.setSelectedIndex(selectedIndexFrom);
				itemChanged++;
				toJComboBox.setSelectedIndex(selectedIndexTo);
//				itemChanged++;
			} else if(itemChanged==1) {
				System.out.println("if(itemChanged==1) -> " + itemChanged);
				itemChanged--;
			}
		} 
	}
	
}
