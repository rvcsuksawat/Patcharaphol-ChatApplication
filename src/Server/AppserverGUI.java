package Server;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AppserverGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextArea Logtext;
	private JPanel contentPane;
	public JTextField textField;
	public JButton btnStart;
	public JButton btnStop;
	private ServerSoclet serverSocket = new ServerSoclet(this);
	public JLabel lblStatus;
	public boolean serverstatus = false;
	public JButton btnUser;
	public JButton btnDisconnectUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppserverGUI frame = new AppserverGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AppserverGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 34, 416, 342);
		contentPane.add(scrollPane);

		Logtext = new JTextArea();
		Logtext.setEditable(false);
		scrollPane.setViewportView(Logtext);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText() != null && !textField.getText().equals("")) {
					StartServer(e);

				} else {
					JOptionPane.showMessageDialog(null, "Enter port", "Alert", JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		btnStart.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStart.setBounds(10, 432, 80, 50);
		contentPane.add(btnStart);

		btnStop = new JButton("Stop");
		btnStop.setEnabled(false);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					StopServer(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnStop.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStop.setBounds(120, 432, 80, 50);
		contentPane.add(btnStop);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setBounds(125, 392, 225, 30);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Port:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(92, 391, 35, 30);
		contentPane.add(lblNewLabel);

		lblStatus = new JLabel("Server Status: ...");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStatus.setBounds(10, 10, 132, 20);
		contentPane.add(lblStatus);

		lblStatus.setText("Server Status: Offline");
		lblStatus.setForeground(Color.RED);

		JButton btnClear = new JButton("Clear Log");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Logtext.setText("");
			}
		});
		btnClear.setBounds(326, 7, 100, 21);
		contentPane.add(btnClear);

		btnUser = new JButton("user");

		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkuser(e);
			}
		});
		btnUser.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnUser.setEnabled(false);
		btnUser.setBounds(230, 432, 80, 50);
		contentPane.add(btnUser);

		btnDisconnectUser = new JButton("kick");
		btnDisconnectUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kickuser(e);
			}
		});
		btnDisconnectUser.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDisconnectUser.setEnabled(false);
		btnDisconnectUser.setBounds(340, 432, 80, 50);
		contentPane.add(btnDisconnectUser);
	}

	private void StartServer(ActionEvent evt) {
		int port = checkAndParsePort(textField.getText());
		serverSocket.startServer(port);

	}

	private int checkAndParsePort(String port) {
		int result = Integer.parseInt(port);
		return result;
	}

	private void checkuser(ActionEvent evt) {
		serverSocket.checkuser();
	}

	private void StopServer(ActionEvent evt) throws IOException {
		serverSocket.close();
		serverSocket.Removealluser();
	}

	private void kickuser(ActionEvent awt) {
		try {
			int id = Integer
					.parseInt(JOptionPane.showInputDialog(null, "Enter ID user you want to kick", "Enter ID user"));
			serverSocket.remove(id);
		} catch (Exception e) {
			System.out.println("\nNo user get kick");
		}
	}
}
