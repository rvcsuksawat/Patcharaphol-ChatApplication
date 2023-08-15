package Client;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Clinetservice implements Runnable {

	Thread thread = null;
	Socket socket = null;
	public ObjectInputStream In;
	public ObjectOutputStream Out;
	private AppClientGUI ui;

	public Clinetservice() {
	}

	public Clinetservice(AppClientGUI ui) {
		super();
		this.ui = ui;
	}

	public void connectServer(String arr, int port) {
		try {
			socket = new Socket(arr, port);
			Out = new ObjectOutputStream(socket.getOutputStream());
			Out.flush();
			In = new ObjectInputStream(socket.getInputStream());
			start();
			ui.lblStatus.setForeground(Color.green);
			ui.lblStatus.setText("Connect Status:ON");
			ui.btnSend.setEnabled(true);
			ui.textField.setEditable(true);
			ui.textArea.append("Success connect server \n");
			ui.textArea.append("Welcome to Group Chat:" + " " + ui.NametextField.getText() + "\n");
			ui.textArea.append("Your ID is: " + socket.getLocalPort() + "\n");
			ui.NametextField.setEditable(false);
			ui.IPtextField.setEditable(false);
			ui.PorttextField.setEditable(false);
			ui.btnConsoc.setEnabled(false);
			File file = new File("user.txt");
			FileWriter fw = new FileWriter(file, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("Username: " + ui.NametextField.getText() + " ID: " + socket.getLocalPort());
			pw.close();
			fw.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Can't connect to server", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Can't connect to server", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void send(MsgClient msg) {
		try {
			Out.writeObject(msg);
			Out.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			ui.textArea.append("Server is close");
			ui.lblStatus.setText("Connect Status:OFF");
			ui.lblStatus.setForeground(Color.red);
			ui.btnConsoc.setEnabled(true);
			ui.NametextField.setEditable(true);
			ui.IPtextField.setEditable(true);
			ui.PorttextField.setEditable(true);
		}
	}

	void start() throws Exception {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				MsgClient msg = (MsgClient) In.readObject();
				ui.textArea.append("\n" + msg.getSender() + " : " + msg.getContent());

			} catch (Exception e) {
				ui.textArea.append("\nServer is close");
				ui.lblStatus.setText("\nConnect Status:OFF");
				ui.lblStatus.setForeground(Color.red);
				ui.btnConsoc.setEnabled(true);
				ui.NametextField.setEditable(true);
				ui.IPtextField.setEditable(true);
				ui.PorttextField.setEditable(true);
				System.err.println("Error in print massage client service");
				break;
			}
		}
	}
}