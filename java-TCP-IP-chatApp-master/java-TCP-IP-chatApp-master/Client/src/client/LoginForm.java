/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class LoginForm extends javax.swing.JFrame {
	Socket socket;
	DataOutputStream dos;
	private static LoginForm loginForm;

	public void Main() {
		// Khởi tạo loginForm
		loginForm = new LoginForm();
	}

	/**
	 * Creates new form LoginForm
	 */
	public LoginForm() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
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
		signup = new javax.swing.JButton();
//        registation=new java.swing.
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Please Login");
		setResizable(false);

		jLabel1.setText("Username:");

		txtUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel5.setText("Password:");
		txtPas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

		jButton1.setBackground(new java.awt.Color(150, 37, 37));
		jButton1.setFont(new java.awt.Font("Lucida Console", 2, 18)); // NOI18N
		jButton1.setForeground(new java.awt.Color(255, 255, 255));
		jButton1.setText("Sign In");
		signup.setBackground(new java.awt.Color(150, 37, 37));
		signup.setFont(new java.awt.Font("Lucida Console", 2, 18)); // NOI18N
		signup.setForeground(new java.awt.Color(255, 255, 255));
		signup.setText("Sign Up");
		//button đăng kí tài khoản
		signup.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// tạo  giao diện đăng kí
				registration registration = new registration();
				// hiển thị giao diện
				registration.setVisible(true);
				// ẩn giao diện login
				setVisible(false);

			}
		});
// button đăng nhập người dùng
		jButton1.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jLabel2.setText("IP Address:");

		txtHost.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		txtHost.setText("127.0.0.1");

		jLabel3.setText("Port:");

		txtPort.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		txtPort.setText("4444");

		jLabel4.setFont(new java.awt.Font("SansSerif", 3, 14)); // NOI18N
		jLabel4.setText("Please login here.");
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
								.addComponent(signup, javax.swing.GroupLayout.PREFERRED_SIZE, 300,
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
						.addComponent(signup, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
								javax.swing.GroupLayout.PREFERRED_SIZE) // Thêm nút Back vào đây
						.addContainerGap(46, Short.MAX_VALUE)));
		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		// kiểm tra xem các rằng buộc nhập có đúng không
		// TODO add your handling code here:
		if (txtHost.getText().length() > 0 && txtPort.getText().length() > 0 && txtUsername.getText().length() > 0) {
			String username = txtUsername.getText();
			String password = new String(txtPas.getPassword());
			String host = txtHost.getText();
			int port = Integer.parseInt(txtPort.getText());
			//gọi hàm connect truyền vào đó là port user và giao diện đăng nhập
			connection(port, host, username, password, loginForm);

		} else {
//            JOptionPane.showMessageDialog(this, "Incomplete Form.!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
		}
	}// GEN-LAST:event_jButton1ActionPerformed

	public void connection(int port, String host, String username, String pass, LoginForm loginForm) {
//      appendMessage(" Connecting...", "Status", Color.GREEN, Color.GREEN);
		try {
			//tạo ra socket kết nối tới server
			socket = new Socket(host, port);
			//kiểm tra kết nối đến server được thiết lập có thành công hay không
			if (socket.isConnected()) {
				System.out.println("Kết nối thành công đến " + host + " trên cổng " + port);
			} else {
				System.out.println("Kết nối thất bại đến " + host + " trên cổng " + port);
				return;
			}
			// tạo dataoutput với socket người dùng 
			dos = new DataOutputStream(socket.getOutputStream());
			/** Send our username **/
			// gửi CMD tên người dùng lên server
			dos.writeUTF("CMD_CHECKJOIN " + username);
			/** Start Thread **/
			// tạo thread để kiểm tra dữ liệu từ serevr gửi về
			new Thread(new CheckLogin(socket, port, host, username, pass, loginForm)).start();
			jButton1.setEnabled(true);

		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Unable to Connect to Server, please try again later.!",
					"Connection Failed", JOptionPane.ERROR_MESSAGE);
//          appendMessage("[IOException]: "+ e.getMessage(), "Error", Color.RED, Color.RED);
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				loginForm = new LoginForm(); // Khởi tạo đối tượng LoginForm
				loginForm.setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JButton signup;

	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JPasswordField txtPas;
	private javax.swing.JTextField txtHost;
	private javax.swing.JTextField txtPort;
	private javax.swing.JTextField txtUsername;
	// End of variables declaration//GEN-END:variables
}
