package client;

import java.awt.Color;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

public class registration extends javax.swing.JFrame {
	private javax.swing.JButton jButton1;
	private javax.swing.JButton back;

	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JPasswordField txtPas;
	private javax.swing.JTextField txtHost;
	private javax.swing.JTextField txtPort;
	private javax.swing.JTextField txtUsername;
	private javax.swing.JTextPane jTextPane1;
	Socket socket;
	DataOutputStream dos;

	private boolean isConnected = false;

	public registration() {
		initComponents();
	}

	private void initComponents() {
		jLabel1 = new javax.swing.JLabel();
		txtUsername = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		txtHost = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		txtPort = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		txtPas = new javax.swing.JPasswordField();
		jLabel5 = new javax.swing.JLabel();
		back = new javax.swing.JButton();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("REGISTRATION ACCOUNT");
		setResizable(false);

		jLabel1.setText("Username:");

		txtUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

		jLabel5.setText("Password:");

		txtPas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

		jButton1.setBackground(new java.awt.Color(150, 37, 37));
		jButton1.setFont(new java.awt.Font("Lucida Console", 2, 18)); // NOI18N
		jButton1.setForeground(new java.awt.Color(255, 255, 255));
		jButton1.setText("Sign Up");
		back.setBackground(new java.awt.Color(150, 37, 37));
		back.setFont(new java.awt.Font("Lucida Console", 2, 18)); // NOI18N
		back.setForeground(new java.awt.Color(255, 255, 255));
		back.setText("Back");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);

			}
		});
		back.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbuttonback(evt);

			}
		});

		jLabel2.setText("IP Address:");

		txtHost.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		txtHost.setText("127.0.0.1");

		jLabel3.setText("Port:");

		txtPort.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		txtPort.setText("4444");

		jLabel4.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
		jLabel4.setText("REGISTATION ACCOUNT");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(70, 70, 70).addComponent(jLabel4))
						.addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 300,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 300,
										javax.swing.GroupLayout.PREFERRED_SIZE) // Thêm nút Back vào đây
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel1).addComponent(jLabel5).addComponent(jLabel2)
												.addComponent(jLabel3))
										.addGap(18, 18, 18)
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(txtUsername).addComponent(txtPas).addComponent(txtHost)
												.addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
														javax.swing.GroupLayout.PREFERRED_SIZE))))))
				.addContainerGap(37, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(37, 37, 37).addComponent(jLabel4).addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel1))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(txtPas, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel5))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(txtHost, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel2))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel3))
						.addGap(30, 30, 30)
						.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
								javax.swing.GroupLayout.PREFERRED_SIZE) // Thêm nút Back vào đây
						.addContainerGap(46, Short.MAX_VALUE)));
		pack();
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		// Handle button click event here
		String username = txtUsername.getText();
		String password = new String(txtPas.getPassword());
		String host = txtHost.getText();
		int port = Integer.parseInt(txtPort.getText());

		connection(port, host, username, password);
		// Implement your login logic here
//		JOptionPane.showMessageDialog(this,
//				"Username: " + username + "\nPassword: " + password + "\nHost: " + host + "\nPort: " + port);
	}

	private void jbuttonback(java.awt.event.ActionEvent evt) {
		// Handle button click event here
		LoginForm form = new LoginForm();
		form.setVisible(true);
		this.setVisible(false);

	}

	public boolean isConnected() {
		return this.isConnected;
	}

	public void connection(int port, String host, String username, String pass) {
//        appendMessage(" Connecting...", "Status", Color.GREEN, Color.GREEN);
		try {
			socket = new Socket(host, port);
			if (socket.isConnected()) {
				System.out.println("Kết nối thành công đến " + host + " trên cổng " + port);
			} else {
				System.out.println("Kết nối thất bại đến " + host + " trên cổng " + port);
				return;
			}
			dos = new DataOutputStream(socket.getOutputStream());
			/** Send our username **/
			dos.writeUTF("CMD_CREAT " + username + " " + pass);

//            appendMessage(" Connected", "Status", Color.GREEN, Color.GREEN);
//            appendMessage(" type your message now.!", "Status", Color.GREEN, Color.GREEN);
//            

			/** Start Thread **/
			new Thread(new RegistionThread(socket)).start();

			jButton1.setEnabled(true);
			// were now connected
			isConnected = true;
		} catch (IOException e) {
			isConnected = false;
			JOptionPane.showMessageDialog(this, "Unable to Connect to Server, please try again later.!",
					"Connection Failed", JOptionPane.ERROR_MESSAGE);
//            appendMessage("[IOException]: "+ e.getMessage(), "Error", Color.RED, Color.RED);
		}
	}

	public static void message(String mess) {
		System.out.println(mess);
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new registration().setVisible(true);
			}
		});
	}

}
