package client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class MainForm extends javax.swing.JFrame {
	String text;
	String username;
	String host;
	int port;
	Socket socket;
	DataOutputStream dos;
	public boolean attachmentOpen = false;
	private boolean isConnected = false;
	private String selectedUser = null;
	String lectedUser;
	String image1;

	/**
	 * Creates new form MainForm
	 */
	public MainForm() {
		initComponents();
	}

	public void initFrame(String username, String host, int port) {
		this.username = username;
		this.host = host;
		this.port = port;
		setTitle(username);
		/** Connect **/
		connect();
		nameuser.setText( username);
		nameuser.setBackground(new java.awt.Color(150, 37, 37));
		nameuser.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 30));
	}

	public void connect() {
		appendMessage("", " Connecting...", "Status", Color.GREEN, Color.GREEN);
		try {

			socket = new Socket(host, port);
			dos = new DataOutputStream(socket.getOutputStream());
			System.out.println(socket);
			/** Send our username **/
			// gửi xác nhận tài khoản lên seever
			dos.writeUTF("CMD_JOIN " + username);
			appendMessage("", " Connected", "Status", Color.GREEN, Color.GREEN);
			appendMessage("", " type your message now.!", "Status", Color.GREEN, Color.GREEN);

			/** Start Thread **/
			new Thread(new ClientThread(socket, this)).start();
			jButton1.setEnabled(true);
			// were now connected
			isConnected = true;
		} catch (IOException e) {
			isConnected = false;
			JOptionPane.showMessageDialog(this, "Unable to Connect to Server, please try again later.!",
					"Connection Failed", JOptionPane.ERROR_MESSAGE);
			appendMessage("", "[IOException]: " + e.getMessage(), "Error", Color.RED, Color.RED);
		}
	}

	/*
	 * is Connected
	 */
	public boolean isConnected() {
		return this.isConnected;
	}

	/*
	 * System Message
	 */
	public void appendMessage(String type, String msg, String header, Color headerColor, Color contentColor) {
		jTextPane1.setEditable(true);

		// Append type with correct color
		if (type.equals("[CHAT_PRIVATE]:")) {
			gettype(type, Color.RED);
		} else {
			gettype(type, Color.ORANGE);
		}

		// Append the header with color
		getMsgHeader(header, headerColor);

		// Append the message content (handle image or text)
		getMsgContent(msg, contentColor);

		jTextPane1.setEditable(false);
	}

	/*
	 * My Message
	 */
	public void appendMyMessage(String to, String msg, String header) {
		jTextPane1.setEditable(true);

		getMsgHeader(header, Color.ORANGE);
		gettype(to, Color.PINK);

		getMsgContent(msg, Color.LIGHT_GRAY);
		jTextPane1.setEditable(false);
	}

	/*
	 * Message Header
	 */
	public void gettype(String type, Color color) {
		int len = jTextPane1.getDocument().getLength();
		jTextPane1.setCaretPosition(len);
	    jTextPane1.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Verdana", 14), false);
		jTextPane1.replaceSelection(type + " ");
	}

	public void getMsgHeader(String header, Color color) {
		int len = jTextPane1.getDocument().getLength();
		jTextPane1.setCaretPosition(len); // Đảm bảo con trỏ ở cuối
	    jTextPane1.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Verdana", 14), false);
		jTextPane1.replaceSelection(header + ": "); // Thêm dấu ":" vào cuối header
	}

	/*
	 * Message Content
	 */
	// Cover lại màu sắc +font chữ của nội dung tin nhăn
	public void getMsgContent(String msg, Color color) {
		// Kiểm tra xem msg có phải là đường dẫn ảnh không
		if (msg.endsWith(".jpg") || msg.endsWith(".jpeg") || msg.endsWith(".png")) {
			File imgFile = new File(msg);
			if (imgFile.exists()) {
				try {
					ImageIcon icon = new ImageIcon(msg);
					Image scaledImage = getScaledImage(icon.getImage(), 100, 100); // Thay đổi kích thước ảnh
					icon = new ImageIcon(scaledImage);
					insertIcon(icon); // Chèn ảnh vào JTextPane
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				insertText("Image file not found: " + msg, color);
			}
		} else {
			insertText(msg, color); // Nếu không phải ảnh, chèn tin nhắn văn bản
		}
	}

	private void insertText(String msg, Color color) {
		int len = jTextPane1.getDocument().getLength();
		jTextPane1.setCaretPosition(len);
		jTextPane1.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Arial", 12), false);
		jTextPane1.replaceSelection(msg + "\n\n"); // Thêm hai dòng trống để phân cách tin nhắn
	}

	private Image getScaledImage(Image srcImg, int w, int h) {
		Image scaledImg = srcImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return scaledImg;
	}

	private void insertIcon(ImageIcon icon) {
		int len = jTextPane1.getDocument().getLength();
		jTextPane1.setCaretPosition(len);
		jTextPane1.insertIcon(icon); // Chèn icon vào JTextPane
		jTextPane1.replaceSelection(" " + "\n\n"); // Thêm khoảng trắng sau ảnh
	}

	public void appendOnlineList(Vector list) {
		jList1.removeAll();
		jList1.setListData(list);
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
		jTextField1 = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();
		jScrollPane2 = new javax.swing.JScrollPane();
		jList1 = new javax.swing.JList();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextPane1 = new javax.swing.JTextPane();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu3 = new javax.swing.JMenu();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem2 = new javax.swing.JMenuItem();
		jButtonScreenshot = new javax.swing.JButton();
		jbuttonsendimg = new javax.swing.JButton();
setAvatar= new javax.swing.JButton();
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
       

		setBackground(new java.awt.Color(23, 38, 255)); // Use a valid RGB value
		setResizable(false);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				formWindowClosing(evt);
			}
		});
		// Thay đổi màu nền của headerPanel


		// Thay đổi màu sắc của tên người dùng


		// Thay đổi màu của nút gửi tin nhắn
		jButton1.setBackground(new java.awt.Color(255, 69, 0)); // Màu cam đỏ
		jButton1.setForeground(new java.awt.Color(255, 255, 255)); // Màu chữ trắng

		// Thay đổi màu của JTextField
		jTextField1.setBackground(new java.awt.Color(255, 255, 255)); // Màu nền trắng
		jTextField1.setForeground(new java.awt.Color(0, 0, 0)); // Màu chữ đen

		// Thay đổi màu của JList
		jList1.setBackground(new java.awt.Color(240, 248, 255)); // Màu nền sáng
		jList1.setForeground(new java.awt.Color(0, 0, 0)); // Màu chữ đen

		// Thay đổi màu của JScrollPane
		jScrollPane1.getViewport().setBackground(new java.awt.Color(245, 245, 245)); // Màu nền sáng cho chat
		jScrollPane2.getViewport().setBackground(new java.awt.Color(245, 245, 245)); // Màu nền sáng cho danh sách người dùng
		jButton1.setBackground(new java.awt.Color(255, 69, 0)); // Màu cam đỏ
		jButton1.setForeground(new java.awt.Color(255, 255, 255)); // Màu chữ trắng


		getContentPane().setBackground(new java.awt.Color(0, 0, 139)); // Màu nền xanh nước biển đậm cho cửa sổ
		// Tạo JLabel để hiển thị dòng text
		JLabel onlineStatusLabel = new JLabel("Online Users:");
		onlineStatusLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14)); // Định dạng font cho text
		onlineStatusLabel.setForeground(new java.awt.Color(255, 255, 255)); // Màu chữ trắng
		onlineStatusLabel.setBackground(new java.awt.Color(0, 0, 139)); // Màu nền xanh nước biển đậm
		onlineStatusLabel.setOpaque(true); // Đảm bảo màu nền được hiển thị

		// Thêm JLabel vào JScrollPane chứa danh sách người dùng online
		jScrollPane2.setColumnHeaderView(onlineStatusLabel); // Đặt dòng text trên danh sách người dùng online


		jButton1.setBackground(new java.awt.Color(255, 69, 0)); // Màu nền cam cho nút gửi

		jButtonScreenshot.setBackground(new java.awt.Color(255, 215, 0)); // Màu nền vàng cho nút chụp ảnh
		jbuttonsendimg.setBackground(new java.awt.Color(0, 191, 255)); // Màu nền xanh dương cho nút gửi hình ảnh


		jTextField1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField1ActionPerformed(evt);
			}
		});

		image1 = "C:\\Users\\Trung\\Downloads\\hi.jpg";

		ImageIcon userIcon = new ImageIcon(image1); // Đường dẫn tới hình ảnh của bạn
		// Đảm bảo kích thước hình ảnh hợp lý (ví dụ 50x50)
		Image image = userIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Thay đổi kích thước hình
																							// ảnh
		userIcon = new ImageIcon(image);
		jButtonSendFile = new javax.swing.JButton();
		setAvatar.setText("Change Avatar");
		setAvatar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				changeAvatar(evt);
			}
		});

		// Tạo JPanel để chứa tên người dùng và hình ảnh

		nameuser = new JLabel();
		nameuser.setBackground(new java.awt.Color(150, 37, 37));
		// Tạo một JPanel cho Header chứa tên người dùng và ảnh

		headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Sắp xếp từ trái sang phải
		headerPanel.add(new JLabel(userIcon)); // Thêm ảnh vào JPanel
		headerPanel.add(setAvatar);

		headerPanel.add(nameuser); // Thêm tên người dùng vào JPanel
		// Cài đặt vị trí cho JPanel
		headerPanel.setBounds(20, 20, 300, 50); // Điều chỉnh kích thước và vị trí

		jButton1.setText("Send Message");
		jButton1.setEnabled(false);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jList1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
		jList1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jList1MouseClicked(evt);
			}
		});
		// List người dùng online
		jScrollPane2.setViewportView(jList1);
//Hiển thị đoạn chat
		jScrollPane1.setViewportView(jTextPane1);

//		jMenu3.setText("Attachment");
		jMenu3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenu3ActionPerformed(evt);
			}
		});

		jMenuItem1.setText("File Sharing");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem1ActionPerformed(evt);
			}
		});
		jMenu3.add(jMenuItem1);

		jMenuBar1.add(jMenu3);

		jMenu1.setText("Account");
		jMenu1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenu1ActionPerformed(evt);
			}
		});

		jMenuItem2.setText("Logout");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem2);

		jMenuBar1.add(jMenu1);
		jButtonSendFile = new javax.swing.JButton();
		jButtonSendFile.setText("Send File");
		jButtonSendFile.setEnabled(true);
		jButtonSendFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonSendFileActionPerformed(evt);
			}
		});
		jButtonScreenshot.setText("TakePicture");
		jButtonScreenshot.setEnabled(true);
		jButtonScreenshot.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonScreenshotActionPerformed(evt);
			}
		});
		jbuttonsendimg.setText("Send Picture");
		jbuttonsendimg.setEnabled(true);
		jbuttonsendimg.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonSendPictureActionPerformed(evt);
			}
		});

		jButtonSendFile.setEnabled(true);
		jButtonSendFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonSendFileActionPerformed(evt);
			}
		});
		setJMenuBar(jMenuBar1);
		// Tạo một đối tượng GroupLayout và áp dụng vào ContentPane của JFrame.
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		// Cấu hình GroupLayout cho phần ngang (Horizontal Group).
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    // Thiết lập phần đầu trang chứa thông tin người dùng và hình ảnh.
		    .addGroup(layout.createSequentialGroup().addContainerGap()
		        .addComponent(headerPanel) // Thêm headerPanel chứa thông tin người dùng.
		        .addContainerGap(180, Short.MAX_VALUE)) // Điều chỉnh khoảng cách và chiều dài cho headerPanel.

		    // Thiết lập phần nhập tin nhắn và các nút chức năng.
		    .addGroup(layout.createSequentialGroup().addContainerGap()
		        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		                // Thêm ô nhập tin nhắn và các nút gửi tin nhắn, gửi ảnh, gửi tệp.
		                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(jButton1) // Nút gửi tin nhắn
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(jButtonScreenshot) // Nút chụp màn hình
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(jbuttonsendimg) // Nút gửi ảnh
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(jButtonSendFile)) // Nút gửi tệp
		            // Thêm vùng cuộn hiển thị tin nhắn.
		            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)) 
		    // Thêm danh sách người dùng trực tuyến nằm bên cạnh khu vực chat.
		    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE) // Danh sách người dùng trực tuyến.
		    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		// Cấu hình GroupLayout cho phần dọc (Vertical Group).
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(layout.createSequentialGroup().addContainerGap()
		        .addComponent(headerPanel) // Thêm headerPanel ở trên cùng.
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        
		        // Hiển thị tin nhắn và danh sách người dùng trực tuyến song song.
		        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE) // Vùng hiển thị tin nhắn (có chiều cao 225).
		            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)) // Danh sách người dùng trực tuyến (có chiều cao 225).
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        
		        // Phần nhập tin nhắn và các nút chức năng dưới vùng hiển thị chat và danh sách người dùng.
		        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE) // Ô nhập tin nhắn.
		            .addComponent(jButton1) // Nút gửi tin nhắn.
		            .addComponent(jButtonScreenshot) // Nút chụp màn hình.
		            .addComponent(jbuttonsendimg) // Nút gửi ảnh.
		            .addComponent(jButtonSendFile)) // Nút gửi tệp.
		        .addContainerGap()));

		// Gọi pack() để tự động điều chỉnh kích thước của cửa sổ sao cho tất cả các thành phần vừa vặn.
		pack();

	}// </editor-fold>//GEN-END:initComponents

	private void jList1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jList1MouseClicked
		selectedUser = (String) jList1.getSelectedValue();
		if (selectedUser != null) {
			System.out.println("PRIVATE" + selectedUser);
			text = jTextPane1.getText();
			System.out.println(text);
			jTextPane1.setText("");

			appendMessage("", "You are now chatting with " + selectedUser, "Status", Color.BLUE, Color.BLUE);

		} else {
			System.out.println(text);
			jTextPane1.setText(text);
			appendMessage("", "You are now chatting ALL ", "Status", Color.GREEN, Color.GREEN);

		}

	}// GEN-LAST:event_jList1MouseClicked

	private void changeAvatar(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSendFileActionPerformed
		// TODO add your handling code here:
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn ảnh để gửi");
		fileChooser.setFileFilter(
				new javax.swing.filechooser.FileNameExtensionFilter("Ảnh JPEG/PNG", "jpg", "jpeg", "png"));
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String addressImage = selectedFile.getAbsolutePath();
			image1 = addressImage;
			System.out.println(image1);
			 image1 = addressImage;
	            System.out.println(image1);
	            
	            ImageIcon userIcon = new ImageIcon(image1); // Đường dẫn tới hình ảnh của bạn
	            // Đảm bảo kích thước hình ảnh hợp lý (ví dụ 100x100)
	            Image image = userIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Thay đổi kích thước hình ảnh
	            userIcon = new ImageIcon(image);

	            headerPanel.removeAll(); // Remove old avatar
	    		headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Sắp xếp từ trái sang phải
	    		headerPanel.add(new JLabel(userIcon)); // Thêm ảnh vào JPanel
	    	    headerPanel.add(setAvatar);

	    		headerPanel.add(nameuser); // Thêm tên người dùng vào JPanel
	    		// Cài đặt vị trí cho JPanel
	    		headerPanel.setBounds(20, 20, 300, 50); // Điều chỉnh kích thước và vị trí

	            headerPanel.revalidate(); // Refresh panel
	            headerPanel.repaint(); // Repaint panel
			
		}
	}// GEN-LAST:event_jButtonSendFileActionPerformed

	private void jButtonSendPictureActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSendFileActionPerformed
		// TODO add your handling code here:
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn ảnh để gửi");
		fileChooser.setFileFilter(
				new javax.swing.filechooser.FileNameExtensionFilter("Ảnh JPEG/PNG", "jpg", "jpeg", "png"));
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String addressImage = selectedFile.getAbsolutePath();

			try {
				FileInputStream fis = new FileInputStream(selectedFile);
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				if (selectedUser != null && !selectedUser.isEmpty()) {
					System.out.println("Send_PIC_PRivate");

					dos.writeUTF("CMD_SENDIMG " + username + " " + selectedUser + " " + selectedFile.getName());
					dos.writeInt(buffer.length);
					dos.write(buffer);
					appendMyMessage(" SEND_FLE To  " + selectedUser, ": " + "File " + selectedFile.getName(), username);

				} else {
					dos.writeUTF("CMD_SENDIMGALL " + username + " " + selectedUser + " " + selectedFile.getName());
					dos.writeInt(buffer.length);
					dos.write(buffer);

					appendMyMessage("", addressImage, username);
				}
				// Send file to server
				if (dos != null) {
					System.out.println("DataOutputStream is still open.");
				} else {
					System.out.println("DataOutputStream is closed or null.");
				}
			} catch (IOException e) {
				appendMessage("", "[IOException]: " + e.getMessage(), "Error", Color.RED, Color.RED);
			}
		}
	}// GEN-LAST:event_jButtonSendFileActionPerformed

	private void jButtonSendFileActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSendFileActionPerformed
		// TODO add your handling code here:
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				FileInputStream fis = new FileInputStream(selectedFile);
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				if (selectedUser != null && !selectedUser.isEmpty()) {
					System.out.println("SendPRivate");

					dos.writeUTF("CMD_SENDFILEPRIAVTE " + username + " " + selectedUser + " " + selectedFile.getName());
					dos.writeInt(buffer.length);
					dos.write(buffer);
					appendMyMessage(" SEND_FLE To  " + selectedUser, ": " + "File " + selectedFile.getName(), username);

				} else {
					dos.writeUTF("CMD_SENDFILEALL " + username + " " + selectedUser + " " + selectedFile.getName());
					dos.writeInt(buffer.length);
					dos.write(buffer);
					appendMessage("",
							"File " + selectedFile.getName() + " sent to "
									+ (selectedUser != null ? selectedUser : "all users"),
							"Status", Color.BLUE, Color.BLUE);
				}
				// Send file to server
				if (dos != null) {
					System.out.println("DataOutputStream is still open.");
				} else {
					System.out.println("DataOutputStream is closed or null.");
				}
			} catch (IOException e) {
				appendMessage("", "[IOException]: " + e.getMessage(), "Error", Color.RED, Color.RED);
			}
		}
	}// GEN-LAST:event_jButtonSendFileActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
//        // TODO add your handling code here:
		try {
			String content = jTextField1.getText();
			if (selectedUser != null && !selectedUser.isEmpty()) {
				// Gửi tin nhắn riêng tư
				
				System.out.println("PRIVATE" + selectedUser);
				dos.writeUTF("CMD_CHAT " + username + " " + selectedUser + " " + content);
				
				appendMyMessage(" SEND_To " + selectedUser, ": " + content, username);
			} else {
				System.out.println("ALL");
				
				// Gửi tin nhắn chung
				dos.writeUTF("CMD_CHATALL " + username+ " " + content);
				appendMyMessage("", content, username);
			}
			jTextField1.setText("");
		} catch (IOException e) {
			appendMessage("", "[IOException]: " + e.getMessage(), "Error", Color.RED, Color.RED);
		}

	}// GEN-LAST:event_jButton1ActionPerformed

	private void jButtonScreenshotActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
//      // TODO add your handling code here:
		// Tạo và hiển thị CameraCapture
		CameraCapture cameraCapture = new CameraCapture();
		cameraCapture.setVisible(true);

	}// GEN-LAST:event_jButton1ActionPerformed

	private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenu3ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jMenu3ActionPerformed

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed
		// TODO add your handling code here:
		if (!attachmentOpen) {
			AttachmentForm attachment = new AttachmentForm();
			attachment.setVisible(true);
			attachment.setFocusableWindowState(true);
			attachment.initFrame(username, MainForm.this, host, port);
			attachmentOpen = true;
		}
	}// GEN-LAST:event_jMenuItem1ActionPerformed

	private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
		// TODO add your handling code here:
		int confirm = JOptionPane.showConfirmDialog(this, "Close this Application.?");
		if (confirm == 0) {
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			this.dispose();
			System.exit(0);
		}
	}// GEN-LAST:event_formWindowClosing

	private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenu1ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jMenu1ActionPerformed

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem2ActionPerformed
		// TODO add your handling code here:
		int confirm = JOptionPane.showConfirmDialog(null, "Logout your Account.?");
		if (confirm == 0) {
			try {
				socket.close();
				setVisible(false);
				/** Login Form **/
				new LoginForm().setVisible(true);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}// GEN-LAST:event_jMenuItem2ActionPerformed

	private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField1ActionPerformed
		// TODO add your handling code here:
		try {
			String content = username + " " + evt.getActionCommand();
			dos.writeUTF("CMD_CHATALL " + content);
			appendMyMessage("", " " + evt.getActionCommand(), username);
			jTextField1.setText("");
		} catch (IOException e) {
			appendMessage("", "[IOException]: " + e.getMessage(), "Error", Color.RED, Color.RED);
		}
	}// GEN-LAST:event_jTextField1ActionPerformed

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
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainForm().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JList jList1;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JLabel nameuser;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JButton setAvatar;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextPane jTextPane1;
	private javax.swing.JButton jButtonSendFile; // Add this line
	private javax.swing.JPanel headerPanel;
	private javax.swing.JButton jButtonScreenshot;
	private javax.swing.JButton jbuttonsendimg;

	// End of variables declaration//GEN-END:variables
}