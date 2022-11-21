
// PROG1415 Test 1 - Hands On
// James Hare

package prog1415;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class LoginFrame extends JFrame {
	
	private String login;
	
	private JFrame frame;
	JTextField input = new JTextField(15);
	//JButton submit = new JButton("Login");
	JButton exit = new JButton("Exit");
	JTextArea output = new JTextArea(200,200);

	JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));

	//int accountNum = txtAccount.getText();

	public LoginFrame ()
	{
		this.setTitle("Login");
		this.setBounds(100, 100, 300, 150);
		Container con = this.getContentPane();
		south.add(exit);
		north.add(input);
		//north.add(submit);
		con.add(north,BorderLayout.NORTH);
		con.add(output);
		output.setLineWrap(true);
		output.setText("Please enter your username and hit enter.");
		output.setEditable(false);
		con.add(south, BorderLayout.SOUTH);
		
		frame = this;

		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(input.getText().length() > 0 && findWhiteSpace(input.getText()) == false){
					//login = input.getText();
					frame.setVisible(false);
					new TCPClient(input.getText());
				}
				else {
					output.setText("Please enter your username and hit enter to continue... \nNOTE: Whitespaces are not allowed.");
				}
			}
		});
		exit.addActionListener(new Exit());


		this.setVisible(true);
	}
	
	public boolean findWhiteSpace(String line) {
		boolean found = false;
		for (char c : line.toCharArray())
		{
			if (Character.isWhitespace(c) == true) {
				return true;
			}
		}
		return found;
	}
}

class Exit implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		System.exit(0);
	}
}
