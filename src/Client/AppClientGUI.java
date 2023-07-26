package Client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AppClientGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextArea textArea;
	public JLabel lblStatus;
	private JPanel contentPane;
	public JTextField textField;
	public JButton btnConsoc;
	public JButton btnSend;
	public JTextField NametextField;
	public JTextField IPtextField;
	public JTextField PorttextField;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private Clinetservice cs = new Clinetservice(this);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppClientGUI frame = new AppClientGUI();
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
	public AppClientGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 91, 466, 513);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		lblStatus = new JLabel("Connect Status:OFF");
		lblStatus.setForeground(Color.RED);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStatus.setBounds(10, 30, 163, 30);
		contentPane.add(lblStatus);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 614, 340, 40);
		contentPane.add(textField);
		textField.setColumns(10);

		btnSend = new JButton("SEND ->");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				sendMassage(e);

			}
		});
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSend.setBounds(360, 614, 116, 40);
		btnSend.setEnabled(false);
		contentPane.add(btnSend);

		btnConsoc = new JButton("Connect");
		btnConsoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				if (NametextField.getText() != null && !NametextField.getText().equals("")
						&& IPtextField.getText() != null && !IPtextField.getText().equals("")
						&& PorttextField.getText() != null && !PorttextField.getText().equals("")) {
					String IP = IPtextField.getText();
					int port = Integer.parseInt(PorttextField.getText());

					cs.connectServer(IP, port);
				}
			}
		});
		btnConsoc.setBounds(377, 65, 85, 21);
		contentPane.add(btnConsoc);

		NametextField = new JTextField();
		NametextField.setBounds(155, 38, 96, 19);
		contentPane.add(NametextField);
		NametextField.setColumns(10);

		IPtextField = new JTextField();
		IPtextField.setBounds(261, 38, 96, 19);
		contentPane.add(IPtextField);
		IPtextField.setColumns(10);

		PorttextField = new JTextField();
		PorttextField.setBounds(367, 38, 96, 19);
		contentPane.add(PorttextField);
		PorttextField.setColumns(10);

		lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(155, 15, 45, 13);
		contentPane.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("IP");
		lblNewLabel_1.setBounds(261, 15, 45, 13);
		contentPane.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("PORT");
		lblNewLabel_2.setBounds(367, 15, 45, 13);
		contentPane.add(lblNewLabel_2);
	}

	private void sendMassage(ActionEvent evt) {
		if (textField.getText() != null && !textField.getText().isEmpty()) {
			cs.send(new MsgClient("message", NametextField.getText(), textField.getText(), "All"));

			textField.setText("");
		}
	}
}
