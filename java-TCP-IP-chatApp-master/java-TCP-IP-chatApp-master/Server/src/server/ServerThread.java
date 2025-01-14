
package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {

	ServerSocket server;
	MainForm main;
	boolean keepGoing = true;

	public ServerThread(int port, MainForm main) {
		main.appendMessage("[Server]: Starting server in port " + port);
		try {
			this.main = main;
			server = new ServerSocket(port);
			main.appendMessage("[Server]: Server started.!");
		} catch (IOException e) {
			main.appendMessage("[IOException]: " + e.getMessage());
		} catch (Exception e) {
			main.appendMessage("[Exception]: " + e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			while (keepGoing) {
				Socket socket = server.accept();
				
				// main.appendMessage("[Socket]: "+ socket);
				/** SOcket thread **/
				new Thread(new SocketThread(socket, main)).start();
			}
		} catch (IOException e) {
			main.appendMessage("[ServerThreadIOException]: " + e.getMessage());
		}
	}

	public void stop() {
		try {
			server.close();
			keepGoing = false;
			System.out.println("Server is now closed..!");
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
//    public void sendMessageToClient(Socket socket, String message) {
//        try {
//            // Create output stream to send message to client
//            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//            dos.writeUTF(message);  // Send message
//            dos.flush();
//        } catch (IOException e) {
//            main.appendMessage("[IOException in sendMessageToClient]: " + e.getMessage());
//        }
//    }

}
