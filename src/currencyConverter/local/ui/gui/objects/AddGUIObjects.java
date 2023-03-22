/**
 * 
 */
package currencyConverter.local.ui.gui.objects;

import static java.awt.GridBagConstraints.CENTER;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import currencyConverter.local.math.Calculation;
import currencyConverter.local.valueobjects.CurrencyObject;

/**
 * @author mortlust
 *
 */
public class AddGUIObjects implements KeyListener, ItemListener {
	
	private int itemChanged = 0;
	private int switchFromTo = 0;
	private String selectedItemFrom, selectedItemTo;
	private int defaultValue;
		
	private int fromIndex;
	private int toIndex;
	
	private String[] choice;
	private String[] choiceFrom;
	private String[] choiceTo;
	
	private JLabel fromLabel = new JLabel();
	private JLabel toLabel = new JLabel();
	private JLabel dataSource;
	private JLabel date;
	
	private JTextField fromJTextField;
	private JTextField toJTextField;
		
	private JComboBox<String> fromJComboBox = new JComboBox<>();
	private JComboBox<String> toJComboBox = new JComboBox<>();
	
	private DefaultComboBoxModel<String> modelFrom;
	private DefaultComboBoxModel<String> modelTo;
	
	List<CurrencyObject> co;
	
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
		
//		component orientation LEFT_TO_RIGHT
		fromJTextField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toJTextField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				
		choice = new String[co.size()];
		choiceFrom = new String[co.size()-1];
		choiceTo = new String[co.size()-1];
 		
		//////////////////////////////////////////////////
		for(int i = 0;i<co.size();i++) {
			choice[i] = co.get(i).getSymbol();			
		}
		
//		initializing combo boxes
		initializeComboBox(co);
		
		//////////////////////////////////////////////////
		
//		add events
		fromJTextField.addKeyListener(this);
		toJTextField.addKeyListener(this);
		
		fromJComboBox.addItemListener(this);
		toJComboBox.addItemListener(this);
		
//		set default value for JTextField
		fromJTextField.setText("" + defaultValue);
		toJTextField.setText("" + c.calc(co.get(fromIndex), co.get(toIndex), new BigDecimal(fromJTextField.getText())));
		
//		set GridBagLayout for Jframe
		jframe.setLayout(new GridBagLayout());
		
		
		
		addGB(jframe,fromLabel,0,0,3,0,0);
		
		addGB(jframe,fromJTextField,0,1,2,0,0);
		addGB(jframe,fromJComboBox,2,1,1,0,0);
		
		addGB(jframe,toLabel,0,2,3,0,0);
		
		addGB(jframe,toJTextField,0,3,2,0,0);
		addGB(jframe,toJComboBox,2,3,1,0,0);
		
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
		
		updateJComboBox();
		
		setJLabelText(co,fromIndex, toIndex);
		
	}
	
//	update content of comboBox and JLabel during runtime
	private void itemHasChanged(ItemEvent ie, List<CurrencyObject>  co, String selectedFrom, String selectedTo) {
		
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
		
		updateJTextField(ie);
				
		setJLabelText(co,fromIndex, toIndex);
				
	}
	
//	update JTextField text
	private void updateJTextField(ItemEvent ie) {
		if(ie.getSource() == fromJComboBox) {
			System.out.println("source -> fromJComboBox");
			toJTextField.setText("" + c.calc(co.get(fromIndex), co.get(toIndex), new BigDecimal(fromJTextField.getText())));
		}
		if(ie.getSource() == toJComboBox) {
			System.out.println("source -> toJComboBox");
			fromJTextField.setText("" + c.calc(co.get(toIndex), co.get(fromIndex), new BigDecimal(toJTextField.getText())));
		}
	}
	
//	set/update JComboBox items	
	public void updateJComboBox() {
//		setJComboBoxItems();
		fromJComboBox.removeAllItems();
		toJComboBox.removeAllItems();
				
		modelFrom = new DefaultComboBoxModel<String>(choiceFrom);
		modelTo = new DefaultComboBoxModel<String>(choiceTo);
		
		fromJComboBox.setModel(modelFrom);
		toJComboBox.setModel(modelTo);
	}
	
//	set/update JLabel text
	private void setJLabelText(List<CurrencyObject>  co, int fromIndex, int toIndex) {
		
		for(int i = 0;i<co.size();i++) {
			if(choiceFrom[i].equals(co.get(fromIndex).getSymbol())) {
				fromLabel.setText(co.get(fromIndex).getDisplayName());
				break;
			}
		}
		
		for(int i = 0;i<co.size();i++) {
			if(choiceTo[i].equals(co.get(toIndex).getSymbol())) {
				toLabel.setText(co.get(toIndex).getDisplayName());
				break;
			}
		}
	}
	
//	check if user entry matches numbers
	private void checkEntry(KeyEvent ke) {	
		String s = fromJTextField.getText();
		fromJTextField.setText(s.replaceAll("[^0-9.]", ""));
		
	}
	
//	update JTextField text
	private void valueHasChanged(KeyEvent ke) {
//		.toPlainString() changes BigDecimal String from E+1 to 10
		if(ke.getSource() == fromJTextField) {
			toJTextField.setText((c.calc(co.get(fromIndex), co.get(toIndex), new BigDecimal(fromJTextField.getText()))).toPlainString());
		}
		if(ke.getSource() == toJTextField) {
			fromJTextField.setText((c.calc(co.get(toIndex), co.get(fromIndex), new BigDecimal(toJTextField.getText()))).toPlainString());
		}
	}
	
//	add objects to GridBagLayout
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

//	add objects to GridBagLayout
	private void addGB(JFrame jframe, Component component, int gridx, int gridy, int width, int ipadx, int ipady) {
        addGB(jframe, component, gridx, gridy, width, 1, GridBagConstraints.BOTH, 0.0, 0.0, CENTER, new Insets(5, 5, 5, 5), ipadx, ipady);
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
	public void itemStateChanged(ItemEvent ie) {
		// TODO Auto-generated method stub
		if(ie.getStateChange() == 1) {
			if(itemChanged==0) {
				if(switchFromTo == 0) {
//					System.out.println("if(itemChanged==1) -> " + itemChanged);
					selectedItemFrom = "" + fromJComboBox.getSelectedItem();
//					System.out.println("selectedItemFrom -> " + selectedItemFrom);
					selectedItemTo = "" + toJComboBox.getSelectedItem();
					itemHasChanged(ie,co,selectedItemFrom,selectedItemTo);
//					itemHasChanged(ie, selectedItemFrom, selectedItemTo);
					switchFromTo++;
					System.out.println("itemChanged nach fromJComboBox.setSelectedIndex -> " + itemChanged);
					fromJComboBox.setSelectedItem(selectedItemFrom);
				}
				if(switchFromTo == 1) {
					itemChanged++;
					switchFromTo--;
					System.out.println("itemChanged nach toComboBox.setSelectedIndex -> " + itemChanged);
					toJComboBox.setSelectedItem(selectedItemTo);
				}
			} else if(itemChanged==1) {
				System.out.println("else(itemChanged==1) -> " + itemChanged);
				itemChanged--;
			}
		} 
	}
	
}
