package swingCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Calculator implements ActionListener {

	JButton[] jbtns = new JButton[16];
	JLabel jlab;
	
	boolean first = true;
	long operand1 = 0L, operand2 = 0L; 
	int flag = 0, digits = 0;
	String operator = "";
	
	Calculator() {
		
		JFrame jfrm = new JFrame("Integer Calculator");
		
		jfrm.setLayout(new GridLayout(2, 1));
		
		jfrm.setSize(300, 500);
		
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon image = new ImageIcon("Calculator.png");
		
		jfrm.setIconImage(image.getImage());
		
		JPanel jpn1 = new JPanel();
		JPanel jpn2 = new JPanel();
		
		jpn1.setLayout(new GridLayout(1,0));
		jpn1.setOpaque(true);
		jpn1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		jlab = new JLabel("0", SwingConstants.RIGHT);
		jlab.setVerticalAlignment(SwingConstants.CENTER);
		
		jpn1.add(jlab);
		
		jpn2.setLayout(new GridLayout(4, 4));
		jpn2.setOpaque(true);
		
		String[] strings = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "C", "=", "+"};
		
		for(int i = 0; i < 16; i++) 
			jbtns[i] = new JButton(strings[i]);
		
		jbtns[13].setDisplayedMnemonicIndex(0);
		
		for(int i = 0; i < 16; i++)
			jbtns[i].addActionListener(this);
		
		for(int i = 0; i < 16; i++)
			jpn2.add(jbtns[i]);
		
		jfrm.add(jpn1);
		jfrm.add(jpn2);
		
		jfrm.getRootPane().setDefaultButton(jbtns[14]);
		
		jfrm.setLocationRelativeTo(null);
		jfrm.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		if (ae.getActionCommand().equals("1") || ae.getActionCommand().equals("2") 
				|| ae.getActionCommand().equals("3") || ae.getActionCommand().equals("4") 
				|| ae.getActionCommand().equals("5") || ae.getActionCommand().equals("6")
				|| ae.getActionCommand().equals("7") || ae.getActionCommand().equals("8")
				|| ae.getActionCommand().equals("9") || ae.getActionCommand().equals("0")) {
			
			if (jlab.getText().equals("(c) 2016 Corey Thomason")
					|| jlab.getText().equals("Error: Cannot divide by 0")
					|| jlab.getText().equals("Error: overflow")) {
				flag = -1;
			}
			
			if (flag == 0) {
				operand1 = Long.parseLong(ae.getActionCommand());
				jlab.setText(ae.getActionCommand());
				flag = 1;
				digits++;
			}
			else if (flag == 1 && digits < 10) {
				operand1 = Long.parseLong(jlab.getText() + ae.getActionCommand());
				jlab.setText(jlab.getText() + ae.getActionCommand());
				digits++;
			}
			else if (flag > 1) {
				if (flag == 2) {
					operand2 = Long.parseLong(ae.getActionCommand());
					jlab.setText(ae.getActionCommand());
					flag = 3;
					digits++;
				}
				else if (flag == 3 && digits < 10) {
					operand2 = Long.parseLong(jlab.getText() + ae.getActionCommand());
					jlab.setText(jlab.getText() + ae.getActionCommand());
					digits++;
				}
				else if (flag == 4) {
					operand1 = Long.parseLong(ae.getActionCommand());
					jlab.setText(ae.getActionCommand());
					flag = 5;
					digits++;
				}
				else if (flag == 5 && digits < 10) {
					operand1 = Long.parseLong(jlab.getText() + ae.getActionCommand());
					jlab.setText(jlab.getText() + ae.getActionCommand());
					digits++;
				}
			}
		}
		
		else if (ae.getActionCommand().equals("/") || ae.getActionCommand().equals("*") 
				|| ae.getActionCommand().equals("-") || ae.getActionCommand().equals("+")) {
			operator = ae.getActionCommand();
			if (first == true) {
				flag = 2;
				digits = 0;
			}
			else {
				flag = 4;
				digits = 0;
			}
		}

		else if (ae.getActionCommand().equals("=")) {
		
			if (jlab.getText().equals("(c) 2016 Corey Thomason") 
					|| jlab.getText().equals("Error: Cannot divide by 0")
					|| jlab.getText().equals("Error: overflow")) {
				operator = "";
			}
			
			switch(operator) {
			case "+": {
				operand2 = addition(operand1, operand2);
				if (operand2 > 9999999999L) {
					jlab.setText("Error: overflow");
					break;
				}
				jlab.setText("" + operand2);
				flag = 4;
				first = false;
				digits = 0;
				break;
			}
			case "-":  
				if (flag == 3) {
					operand2 = subtraction(operand1, operand2);
					jlab.setText("" + operand2);
					flag = 4;
					first = false;
					digits = 0;
				}
				else if (flag >= 4) {
					operand2 = subtraction(operand2, operand1);
					jlab.setText("" + operand2);
					digits = 0;
				}
				break;
			case "*": {
				operand2 = multiplication(operand1, operand2);
				if (operand2 > 9999999999L) {
					jlab.setText("Error: overflow");
					break;
				}
				jlab.setText("" + operand2);
				flag = 4;
				first = false;
				digits = 0;
				break;
			}
			case "/":
				if (flag == 3) {
					if (operand2 == 0) {
						jlab.setText("Error: Cannot divide by 0");
					}
					else {
						operand2 = division(operand1, operand2);
						jlab.setText("" + operand2);
						flag = 4;
						first = false;
						digits = 0;
					}
				}
				else if (flag >= 4) {
					if (operand1 == 0) {
						jlab.setText("Error: Cannot divide by 0");
					}
					else {
						operand2 = division(operand2, operand1);
						jlab.setText("" + operand2);
						digits = 0;
					}
				}
				break;
			}
		}
		
		else if (ae.getActionCommand().equals("C")) {
			if (ae.getModifiers() == 18) {
				jlab.setText("(c) 2016 Corey Thomason");
			}
			else {
				jlab.setText("0");
				operand1 = 0;
				operand2 = 0;
				flag = 0;
				first = true;
				digits = 0;
				operator = "";
			}
		}

	}
	
	public long addition(long num1, long num2) {
		long sum = num1 + num2;
		return sum;
	}
	
	public long subtraction(long num1, long num2) {
		long difference = num1 - num2;
		return difference;
	}
	
	public long multiplication(long num1, long num2) {
		long product = num1 * num2;
		return product;
	}
	
	public long division(long num1, long num2) {
		long quotient = num1 / num2;
		return quotient;
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Calculator();
			}
		});
	}
}