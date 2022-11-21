
// PROG1415 Test 1 - Hands On
// James Hare

package prog1415;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.event.*;

//A java client program
public class TCPClient extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;

	//Networking Objects
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int port = readPort();
	
	private String login;
	private boolean isLoggedIn = false;
	
	private boolean go = true;
	
	private JTextArea output = new JTextArea("Ready...\nSelected port: " + port + "\n" /* + "Please enter your username:\n" */);
	private JTextField input = new JTextField();
	
	//constructor
	public TCPClient(String login) {
		this.login = login;
		this.isLoggedIn = true;
		
		//build interface
		output.setEditable(false);
		output.setLineWrap(true);
		 
		this.getContentPane().add(input,BorderLayout.NORTH);
		this.getContentPane().add(new JScrollPane(output),BorderLayout.CENTER);
		this.setTitle("Client - " + login);
		this.setBounds(100,100,300,500);
		this.setVisible(true);
		this.addWindowListener(this);
		
		//Connect to the server
		try {
			InetAddress address = InetAddress.getByName("127.0.0.1");	// 127.0.0.1 -- localhost only. To connect to other machines, make this the other machine's IPv4 address
			socket = new Socket(address,port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			out.writeObject(login);
			out.flush();
		} catch (IOException e) {
			new PortConfirmFrame(false);
			this.setVisible(false);
			e.printStackTrace();
		}
		//event to send user's input to the server
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(input.getText().length() > 0) {
					try {
						out.writeObject(input.getText());
						out.flush();
						input.setText("");
						//System.out.println("Login: " +login);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		new ReadMessages().start();
	}
	
	public int readPort() {
		try {
	     String file ="src/prog1415/resources/Requested_Port.txt";
	     
	     BufferedReader reader = new BufferedReader(new FileReader(file));
	     String line = reader.readLine();
	     reader.close();
	     return Integer.parseInt(line);
	     
		}
		catch (Exception e) {
			return 8000;
		}
	}
	
	//thread to read messages coming from the server
	class ReadMessages extends Thread {
		@Override
		public void run() {
			while(go) {
				try {
					Object obj = in.readObject();
					if(obj instanceof String) {
						output.append(obj.toString() + "\n");
					}
				} catch (Exception e) { }
			}
		}
		
	}
	
	public static void main(String[] args) {
		new PortConfirmFrame(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

}
