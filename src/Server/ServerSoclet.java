package Server;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import Massage.Massage;

public class ServerSoclet extends Thread {

	private AppserverGUI ui;
	public HashMap<Integer, ClientHandler> clientHandlerList = new HashMap<Integer, ClientHandler>();
	private ServerSocket serverSocket = null;
	public Thread thread = null;

	public ServerSoclet() {

	}

	public ServerSoclet(AppserverGUI ui) {
		super();
		this.ui = ui;
	}

	public void startServer(int port) {
		try {
			this.serverSocket = new ServerSocket(port);
			ui.serverstatus = true;
			ui.lblStatus.setText("\nServer Status: Online");
			ui.lblStatus.setForeground(Color.green);
			ui.textField.setEditable(false);
			ui.btnStart.setEnabled(false);
			ui.btnStop.setEnabled(true);
			ui.btnUser.setEnabled(true);
			ui.btnDisconnectUser.setEnabled(true);
			System.out.println(serverSocket);
			ui.Logtext.append(
					"Server is started... \n" + InetAddress.getLocalHost() + "\nPort: " + serverSocket.getLocalPort());

			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {

							ui.Logtext.append("\nWaiting for client connect...");
							addClientHandler(serverSocket.accept());
							ui.Logtext.append("");
						} catch (Exception e) {
							e.printStackTrace();
							System.err.println("Server Down");
						}
						if (serverSocket.isClosed()) {
							break;
						}
					}

				}
			}).start();

			clientReciept();

		} catch (IOException e) {
			System.err.println(e.getStackTrace());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void clientReciept() throws Exception {
		if (thread == null) {
			this.thread = new Thread(this);
			thread.start();
		}
	}

	private void addClientHandler(Socket socket) {
		try {
			Integer id = socket.getPort();
			clientHandlerList.put(id, new ClientHandler(socket, this));
			ui.Logtext.append("\nSuccess for regis client : " + socket);
			ui.Logtext.append("\nclient id: " + socket.getPort());
			ui.Logtext.append("\nnumber of client connect: " + clientHandlerList.size());
			clientHandlerList.forEach((key, value) -> System.out.println(key + " " + value));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public synchronized void handle(Integer id, Massage msg) {
		Iterator<Integer> allClientMap = clientHandlerList.keySet().iterator();
		while (allClientMap.hasNext()) {
			Integer key = allClientMap.next(); // Key
			ClientHandler serverThread = clientHandlerList.get(key);
			serverThread.send(msg);
		}
	}

	public synchronized void remove(Integer id) {
		try {
			ClientHandler serverThread = clientHandlerList.get(id);
			String filePath = "user.txt";
			deleteLinesWithWord(filePath, id.toString());
			serverThread.close();
			ui.Logtext.append("\nClient: " + id + " left ");
			int n = clientHandlerList.size() - 1;
			ui.Logtext.append("\nnumber of client connect: " + n);

		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		} finally {
			clientHandlerList.remove(id);

		}
	}

	public static void deleteLinesWithWord(String filePath, String wordToDelete) {
		try {
			// Step 1: Read the file and exclude lines containing the word to delete
			File inputFile = new File(filePath);
			File tempFile = new File("temp.txt");
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while ((currentLine = reader.readLine()) != null) {
				// Check if the line contains the word to delete
				if (currentLine.contains(wordToDelete)) {
					continue; // Skip the line containing the word to delete
				}
				writer.write(currentLine + System.getProperty("line.separator"));
			}

			writer.close();
			reader.close();

			// Step 2: Replace the original file with the updated content
			if (!inputFile.delete()) {
				System.err.println("Could not delete the original file.");
				return;
			}

			if (!tempFile.renameTo(inputFile)) {
				System.err.println("Could not rename the temporary file to the original file name.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Removealluser() {
		String filePath = "user.txt";
		try {
			clientHandlerList.forEach((key, value) -> remove(key));
			clientHandlerList.forEach((key, value) -> deleteLinesWithWord(filePath, key.toString()));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		} finally

		{
			ui.lblStatus.setText("Server Status: Offline");
			ui.lblStatus.setForeground(Color.red);
			ui.btnStart.setEnabled(true);
			ui.btnStop.setEnabled(false);
			ui.textField.setEditable(true);
			ui.Logtext.append("\nServer Close\n");
		}

	}

	public void readTextFile(String filePath) {
		try {
			File file = new File(filePath);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;

			while ((line = reader.readLine()) != null) {
				ui.Logtext.append("\n" + line);
				System.out.println(line);

			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void checkuser() {

		if (!clientHandlerList.isEmpty()) {
			String path = "user.txt";
			readTextFile(path);
		} else {
			ui.Logtext.append("\nNo user connect");
		}
	}

	@SuppressWarnings("deprecation")
	public void close() {
		if (serverSocket != null) {

			try {

				serverSocket.close();
				ui.serverstatus = false;
				thread.stop();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
