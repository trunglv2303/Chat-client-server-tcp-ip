package client;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

public class CheckLogin implements Runnable {

	Socket socket;
	DataInputStream dis;
	LoginForm loginForm;
	StringTokenizer st;
	String host, username, pass;
	int port;

	public CheckLogin(Socket socket, int port, String host, String username, String pass, LoginForm loginForm) {
		this.loginForm = loginForm;
		this.port = port;
		this.username = username;
		this.host = host;
		this.pass = pass;
		this.socket = socket;
		try {
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {

		try {
			while (true) {

				String data = dis.readUTF();
				st = new StringTokenizer(data);

				String CMD = st.nextToken();
				switch (CMD) {
				case "CMD_LOGIN_SUCCES":
					JOptionPane.showMessageDialog(loginForm, "LOGIN ACCOUNT SUCCESS");
					MainForm main = new MainForm();
//				        main.initFrame(txtUsername.getText(), txtHost.getText(), Integer.parseInt(txtPort.getText()));
					main.initFrame(username, host, port);

					main.setVisible(true);
					loginForm.setVisible(false);

					break;
				case "CMD_LOGIN_FAILL":
					JOptionPane.showMessageDialog(loginForm, "LOGIN ACCOUNT FAILL");

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
