package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Massage.Massage;

public class ClientHandler extends Thread {

	public Integer id = -1;
	private Socket socket;
	private ServerSoclet serverService;
	private ObjectInputStream streamIn = null;
	private ObjectOutputStream streamOut = null;

	public ClientHandler() {
	}

	public ClientHandler(Socket socket, ServerSoclet serverService) throws Exception {
		super();
		this.socket = socket;
		this.serverService = serverService;
		id = socket.getPort();

		try {
			openStream();
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void openStream() throws Exception {
		try {
			streamOut = new ObjectOutputStream(socket.getOutputStream());
			streamOut.flush();
			streamIn = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			throw e;
		}
	}

	public void send(Massage msg) {
		try {
			streamOut.writeObject(msg);
			streamOut.flush();
		} catch (IOException ex) {
			System.out.println("Exception [SocketClient : send(...)]");
		}
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
		if (streamOut != null)
			streamOut.close();
	}

	public void run() {
		while (true) {
			try {
				Massage msg = (Massage) streamIn.readObject();
				serverService.handle(id, msg);
			} catch (Exception ioe) {
				System.out.println("ERROR reading: " + ioe.getMessage());
				serverService.remove(id);
				try {
					close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
