package client;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

public class RegistionThread implements Runnable {

	Socket socket;
	DataInputStream dis;
	registration registration;
	StringTokenizer st;

	public RegistionThread(Socket socket) {
		this.registration = registration;
		this.socket = socket;
		try {
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
		}
	}

	@Override
	public void run() {

		try {
			while (true) {

				String data = dis.readUTF();
				st = new StringTokenizer(data);

				String CMD = st.nextToken();
				switch (CMD) {
				case "CMD_CREATE_SUCCESS":
					JOptionPane.showMessageDialog(registration, "REGISTRATION SUCCESS");

					System.out.println("REGISTRATION SUCCESS");
					break;
				case "CMD_CREATE_FAILL":
					JOptionPane.showMessageDialog(registration, "REGISTRATION FAILL");

					System.out.println("REGISTRATION FAILL");
					break;
//                        
//                    case "CMD_ONLINE":
//                        Vector online = new Vector();
//                        while(st.hasMoreTokens()){
//                            String list = st.nextToken();
//                            if(!list.equalsIgnoreCase(main.username)){
//                                online.add(list);
//                            }
//                        }
//                        registration.appendOnlineList(online);
//                        break;

				default:
//                        main.appendMessage("[CMDException]: Unknown Command "+ CMD, "CMDException", Color.RED, Color.RED);
					break;
				}
			}
		} catch (IOException e) {
//            main.appendMessage("[IOException]: "+ e.getMessage(), "Error", Color.RED, Color.RED);
		}
	}
}
