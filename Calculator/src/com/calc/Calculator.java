package com.calc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Main class containing two inner class
 * 
 * @author anitageorge and tanveenkaur
 *
 */
public class Calculator extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private String lastoperation;
	
	private List<JButton> numberbuttons; 
	private List<JButton> functionbuttons_Basic; 
	private List<JButton> functionbuttons_Sci; 
	private List<String> basicCalc;
	private List<String> sciCalc;
	
	private double tempNumbers1;
	private double tempNumbers2;
	private double res;
	
	private String num1 = "";
	private String num2 = "";
	
	private String display = "";
	private String operation;

	private JTextField resultJText;
	final int WIDTH = 455;
	final int HEIGHT = 350;
	
	/**
	 * In the Constructor, ArrayList for Numbers, Basic Functions and Scientific Functions
	 * Has been created. Two JPanels are created, One for Buttons and Basic Functions and Other 
	 * One for Scientific Functions. These two Panels are placed in a Border Layout of the Frame. 
	 * WEST side of the Border Layout has the Panel (buttonPanel) with Number Buttons and
	 * Basic Functions. EAST side of the Border Layout has the Panel (buttonPanelTwo) with 
	 * Scientific Functions.
	 * 
	 * EXTRA FUNCTIONS which are added ABS, CUBEROOT, LOG 10, 10^x. 
	 * 
	 *  
	 */
	public Calculator() {
		
		numberbuttons = new ArrayList<>();
		functionbuttons_Basic = new ArrayList<>();
		functionbuttons_Sci = new ArrayList<>();
		basicCalc = new ArrayList<>();
		sciCalc = new ArrayList<>();
		lastoperation = "new";
		JPanel buttonPanel = new JPanel(new GridLayout(6, 3));
		JPanel buttonPanelTwo = new JPanel(new GridLayout(0, 3));
		
		basicCalc.add("=");
		basicCalc.add("+");
		basicCalc.add("-");
		basicCalc.add("*");
		basicCalc.add("/");
		basicCalc.add("%");
		basicCalc.add("C");
		
		sciCalc.add("^2");
		sciCalc.add("^3");
		sciCalc.add("SQRT");
		sciCalc.add("1/x");
		sciCalc.add("sinx");
		sciCalc.add("cosx");
		sciCalc.add("tanx");
		sciCalc.add("e^x");
		sciCalc.add("lnx");
		sciCalc.add("10^x");
		sciCalc.add("log10");
		sciCalc.add("3√");
		sciCalc.add("abs");
		
		resultJText = new JTextField();
        resultJText.setPreferredSize(new Dimension(WIDTH-10, 40));
        resultJText.setBackground(Color.WHITE);
        resultJText.setEnabled(false);
        resultJText.setHorizontalAlignment(4);
        resultJText.setDisabledTextColor(Color.BLUE);
        resultJText.setForeground(Color.BLUE);
        resultJText.setBackground(Color.LIGHT_GRAY);
        Font font = new Font("Helvetica", Font.BOLD, 22);
        resultJText.setFont(font);
        add(resultJText, BorderLayout.NORTH);

	    for ( int i = 9; i >= 0; i--) 
	    {
	    	JButton button = new JButton(""+i);
	    	button.setBackground(Color.BLUE);
	    	button.setBorderPainted(true);
	    	button.setOpaque(true);
	    	button.setForeground(Color.RED);
	    	button.setFont(font);
	    	numberbuttons.add(button);
	    }
	    
	    JButton dotbutton = new JButton(".");
	    dotbutton.setBackground(Color.BLUE);
	    dotbutton.setForeground(Color.BLACK);
	    dotbutton.setOpaque(true);
	    dotbutton.setFont(font);
	    numberbuttons.add(dotbutton);
	    
	    
	    for(JButton button:numberbuttons) 
	    {
	    	button.addActionListener(new DigitHandler());
	    	buttonPanel.add(button);
	    	
	    }
	    
	    for(String str:basicCalc) {
	    	functionbuttons_Basic.add(new JButton(str));
	    	
	    }
	    
	    for(String str:sciCalc) {
	    	functionbuttons_Sci.add(new JButton(str));
	    }
	    
	    for(JButton button:functionbuttons_Basic) {
	    	button.addActionListener(new OperationHandler());
	    	button.setBackground(Color.RED);
	    	button.setOpaque(true);
	    	button.setForeground(Color.BLACK);
	    	button.setFont(font);
	    	buttonPanel.add(button);
	    }

	    Font fontsci = new Font("Helvetica", Font.BOLD, 12);
	    for(JButton button:functionbuttons_Sci) {
	    	button.addActionListener(new OperationHandler());
	    	button.setBackground(Color.BLACK);
	    	button.setForeground(Color.BLUE);
	    	button.setOpaque(true);
	    	button.setFont(fontsci);
	    	buttonPanelTwo.add(button);
	    }
	    
	    JButton pibutton = new JButton("π");
	    pibutton.addActionListener(new DigitHandler());
	    pibutton.setBackground(Color.BLUE);
	    pibutton.setOpaque(true);
	    pibutton.setForeground(Color.CYAN);
	    pibutton.setFont(font);
	    buttonPanelTwo.add(pibutton);
	    
	    JButton ebutton = new JButton("e");
	    ebutton.addActionListener(new DigitHandler());
	    ebutton.setBackground(Color.BLUE);
	    ebutton.setForeground(Color.CYAN);
	    ebutton.setOpaque(true);
	    ebutton.setFont(font);
	    buttonPanelTwo.add(ebutton);
	    
	    add(buttonPanel, BorderLayout.WEST);
	    add(buttonPanelTwo, BorderLayout.EAST);
	    setTitle("Basic Scientific Calculator");
	    setSize(WIDTH, HEIGHT);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);

	}

	public static void main(String[] args) {
		new Calculator();
	}
	
	/**
	 * 
	 * @author TanveenKaur and AnitaGeorge
	 * 
	 * Description: DigitHandler is a private class which is handling the two operands (tempNumber1
	 * and tempNumber2). Based on the last operation, it decides if the number should be stored
	 * in tempNumber1 or tempNumber2. After tempNumber2 is been delivered, the result is displayed.
	 * 
	 * The application accepts (digit)π and converts it to (digit)*π e.g 2π = 2*π
	 * 
	 *  According to our calculator, we are not allowing the third operand.
	 */
	
	private class DigitHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			DecimalFormat decimalformat = new DecimalFormat("########.########");
			String action = e.getActionCommand();
			String pie = "πe";
			double num = 1;
			
			if(lastoperation.equals("E")) {
				display = "";
				num1 = "";
				num2 = "";
				tempNumbers1 = 0.0;
				tempNumbers2 = 0.0;
				lastoperation = "new";
			}
			
			if(lastoperation.equals("repeatone")) {
				display = "";
				num1 = "";
				lastoperation = "new";
			}
			
			if(display.equals("0"))
				display="";
			
			if(!pie.contains(action))
				display += action;
			
			if(lastoperation.equals("new")||lastoperation.equals("repeatone")) {
				if(action.equals("π")||action.equals("e")) {
					if(tempNumbers1 != 0) {
						num = tempNumbers1;
						tempNumbers1 = action.equals("e")?Math.E:Math.PI;
						display += "*"+decimalformat.format(tempNumbers1);
						tempNumbers1 *= num;
					} else {
						tempNumbers1 = action.equals("e")?Math.E:Math.PI;
						display += decimalformat.format(tempNumbers1);
					}
					num1 = decimalformat.format(tempNumbers1);
				}
				else if(action.equals(".")) 
					num1 += ".";
				else 
					tempNumbers1 = Double.parseDouble(action); 
				if((!action.equals("."))&&(!pie.contains(action))){
					num1 += decimalformat.format(tempNumbers1);
				}
				lastoperation = "new";
			} else if((lastoperation.equals("O"))||(!lastoperation.equals("E"))||(!lastoperation.equals("N"))) {
				if(action.equals("π")||action.equals("e")) {
					if(tempNumbers2 != 0) {
						num = tempNumbers2;
						tempNumbers2 = action.equals("e")?Math.E:Math.PI;
						display += "*"+decimalformat.format(tempNumbers2);
						tempNumbers2 *= num;
					} else {
						tempNumbers2 = action.equals("e")?Math.E:Math.PI;
						display += decimalformat.format(tempNumbers2);
					}
					num2 = decimalformat.format(tempNumbers2);
				}
				else if(action.equals(".")) 
					num2 += ".";
				else 
					tempNumbers2 = Double.parseDouble(action);
				if((!action.equals("."))&&(!pie.contains(action))) 
					num2 += decimalformat.format(tempNumbers2);
				lastoperation = "N";
			} 
			
			resultJText.setText(display);
		}
		
	}
	/**
	 * 
	 * 
	 * @author TanveenKaur and AnitaGeorge
	 * 
	 * Description: The class OperationHandler is dealing with Basic Operations and Scientific 
	 * Operations as well. 
	 * 
	 * The application is giving a prompt to the user when the user tries to
	 * make calculations with three operands as the operand is not allowed. 
	 *
	 */
	private class OperationHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			String dualoperands = "+-*%/";
			DecimalFormat decimalformat = new DecimalFormat("########.########");
			
			if((lastoperation == "N") && (dualoperands.contains(action)||(sciCalc.contains(action)))) {
				JOptionPane.showMessageDialog(null, "This calculator can process only one operation at a time","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(action.equals("C")){
				tempNumbers1 = 0.0;
				tempNumbers2 = 0.0;
				num1 = "0";
				num2 = "";
				res = 0.0;
				lastoperation = "new";
				display = "0";
			} else if(dualoperands.contains(action)) {
				operation = action;
				if(lastoperation == "E") {
					num1 = decimalformat.format(res);
					num2 = "";
					tempNumbers2 = 0;
					display = num1;
				}
				lastoperation = "O";
				display += operation;
			} else if(action.equals("=")) {
				lastoperation = "E";
				try{
				tempNumbers1 = Double.parseDouble(num1);
				tempNumbers2 = Double.parseDouble(num2);
				} catch (Exception exp) {
					JOptionPane.showMessageDialog(null, "Need Second Operator","Error",JOptionPane.ERROR_MESSAGE);
				}
				
				switch (operation) {
				case "+":
					res = tempNumbers1 + tempNumbers2;
					break;
				case "-":
					res = tempNumbers1 - tempNumbers2;
					break;
				case "*":
					res = tempNumbers1 * tempNumbers2;
					break;
				case "/":
					res = tempNumbers1 / tempNumbers2;
					break;
				case "%":
					res = tempNumbers1 % tempNumbers2;
					break;
				}
				display += action;
				display += decimalformat.format(res);
				tempNumbers2 = 0;
			} else {
				tempNumbers1 = Double.parseDouble(num1);
				if(lastoperation.equals("E")||lastoperation.equals("repeatone"))
					tempNumbers1 = res;
				switch(action) {
				case "^2":
					res = tempNumbers1 * tempNumbers1;
					break;
				case "^3":
					res = tempNumbers1 * tempNumbers1 * tempNumbers1;
					break;
				case "SQRT":
					res = Math.sqrt(tempNumbers1);
					break;
				case "1/x":
					res = 1 / tempNumbers1;
					break;
				case "sinx":
					res = Math.sin(Math.toRadians(tempNumbers1));
					break;
				case "cosx":
					res = Math.cos(Math.toRadians(tempNumbers1));
					break;
				case "tanx":
					res = Math.tan(Math.toRadians(tempNumbers1));
					break;
				case "e^x":
					res = Math.exp(tempNumbers1);
					break;
				case "lnx":
					res = Math.log(tempNumbers1);
					break;
				case "10^x":
					res = Math.pow(10, tempNumbers1);
					break;
				case "log10":
					res = Math.log10(tempNumbers1);
					break;
				case "3√":
					res = Math.cbrt(tempNumbers1);
					break;
				case "abs":
					res = Math.abs(tempNumbers1);
					break;
				}
				display = decimalformat.format(res);
				if(display.equals("-0"))
					display = "0";
				if(res == Double.NaN)
					display = "Infinity";
				num1 = ""+res;
				lastoperation = "repeatone";
			}
			
			resultJText.setText(display);
		}
		
	}

}
