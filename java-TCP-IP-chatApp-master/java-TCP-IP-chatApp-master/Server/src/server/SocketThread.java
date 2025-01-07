/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import client.registration;

public class SocketThread implements Runnable {

	Socket socket;
	MainForm main;
	registration registration;
	DataInputStream dis;
	StringTokenizer st;
	String client, filesharing_username;
	ServerThread serverThread;

	public SocketThread(Socket socket, MainForm main) {
		this.main = main;
		this.socket = socket;

		try {
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			main.appendMessage("[SocketThreadIOException]: " + e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				/** Get Client Data **/
				String data = dis.readUTF();
				System.out.println(data);
				st = new StringTokenizer(data);
				String CMD = st.nextToken();
//                System.out.println(st);
//                if (data.startsWith("!@#$")) {
//                }
				/** Check CMD **/
				switch (CMD) {
				case "CMD_CHECKJOIN":
					// gán địa chỉ file
					String DIRECTORY_PATH1 = "D:/user_data";

					/** CMD_JOIN [clientUsername] **/
					// lấy phần username
					String clientUsername = st.nextToken();
					// tạo 1 file có tên người dùng
					File clientfile1 = new File(DIRECTORY_PATH1 + "/" + clientUsername + ".txt");
					// kiểm tra nếu tệp tồn tại thì duyệt
					if (clientfile1.exists()) {
					// server gửi mess tới các client khác
						DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
						// gửi CMD đã đăng n
						dos.writeUTF("CMD_LOGIN_SUCCES");
						dos.flush();
						client = clientUsername;
//						main.setClientList(clientUsername);
//						main.setSocketList(socket);
						main.appendMessage("[Client]: " + clientUsername + " joins the chatroom.!");
					} else {
						DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
						dos.writeUTF("CMD_LOGIN_FAILL");
						dos.flush();
						main.appendMessage("[Client]: " + clientUsername + " joins faill chatroom.!");

					}

					break;
					
				case "CMD_JOIN":
					/** CMD_JOIN [clientUsername] **/
					String clientUsername0 = st.nextToken();
					client = clientUsername0;
					// thêm tên người dùng vào client List
					main.setClientList(clientUsername0);
					System.out.println(socket);
					main.setSocketList(socket);
					main.appendMessage("[Client]: " + clientUsername0 + " joins the chatroom.!");
					break;
				case "CMD_CREAT":

					String clientusername = st.nextToken();
					String clientpass = st.nextToken();
					client = clientusername;

					String DIRECTORY_PATH = "D:/user_data";
					File directory = new File(DIRECTORY_PATH);
					if (!directory.exists()) {
						directory.mkdirs();
					}
					// Tạo file với tên là username người đăng kí
					File clientfile = new File(DIRECTORY_PATH + "/" + clientusername + ".txt");

					try {
						if (clientfile.exists()) {
							try {
								DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
								dos.writeUTF("CMD_CREATE_FAILL");
								dos.flush(); // Ensure data is sent out

							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							BufferedWriter writer = new BufferedWriter(new FileWriter(clientfile, true));
							// Ghi dữ liệu vào file
							writer.write("Username:" + clientusername + " Password:" + clientpass);
							writer.newLine(); // Thêm dòng mới sau khi ghi

							main.appendMessage("[ClientDK]: " + clientusername + " REGISTRATION SUCCESS.!");

							try {
								DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
								dos.writeUTF("CMD_CREATE_SUCCESS");
								dos.flush(); // Ensure data is sent out

							} catch (IOException e) {
								e.printStackTrace();
							}
						}

					} catch (IOException e) {
						e.printStackTrace();
					}

					break;
				case "CMD_CHAT":
					/** CMD_CHAT [from] [sendTo] [message] **/
					String from = st.nextToken(); // Lấy tên người gửi
					String sendTo = st.nextToken(); // Lấy tên người nhận
					String msg = ""; // Khởi tạo nội dung tin nhắn
					System.out.println(sendTo);
					String type = "[CHAT_PRIVATE]:";

					// Ghép các từ còn lại thành nội dung tin nhắn
					while (st.hasMoreTokens()) {
						msg = msg + " " + st.nextToken();
					}

					// Lấy socket của người nhận từ danh sách client
					Socket tsoc = main.getClientList(sendTo);
					try {
						// Tạo DataOutputStream để gửi dữ liệu qua socket
						DataOutputStream dos = new DataOutputStream(tsoc.getOutputStream());

						/** CMD_MESSAGE **/
						// Tạo nội dung tin nhắn với định dạng "người gửi: nội dung"

						String content = type + " " + from + ": " + msg;
						// Gửi lệnh CMD_MESSAGE cùng với nội dung tin nhắn đến người nhận
						dos.writeUTF("CMD_MESSAGE " + content);

						// Ghi lại log tin nhắn
						main.appendMessage("[Message]: From " + from + " To " + sendTo + " : " + msg);
					} catch (IOException e) {
						// Ghi lại log lỗi nếu không thể gửi tin nhắn
						main.appendMessage("[IOException]: Unable to send message to " + sendTo);
					}
					break;

				case "CMD_CHATALL":
					/** CMD_CHATALL [from] [message] **/
					// người gửi
					String chatall_from = st.nextToken();
					// content
					String chatall_msg = "";
					String type1 = "[CHAT_TONG]:";
					// Kiem tra xem con content nao con dư không khong
					while (st.hasMoreTokens()) {
						chatall_msg = chatall_msg + " " + st.nextToken();
					}
					// tong hop lai messager cua nguoi gui
					String chatall_content = type1 + " " + chatall_from + " " + chatall_msg;
					for (int x = 0; x < main.clientList.size(); x++) {
						// kiểm tra xem nếu vị trí thứ x là người dùng gửi thì sẽ không chạy qua if
						if (!main.clientList.elementAt(x).equals(chatall_from)) {
							try {
								
								// lấy socket của từng cái ép về dạng socket
								Socket tsoc2 = (Socket) main.socketList.elementAt(x);
								DataOutputStream dos2 = new DataOutputStream(tsoc2.getOutputStream());
								dos2.writeUTF("CMD_MESSAGE " + chatall_content);
								main.appendMessage("[CMD_MESSAGE]: " + chatall_from + " " + chatall_msg);

							} catch (IOException e) {
								main.appendMessage("[CMD_CHATALL]: " + e.getMessage());
							}
						}
					}
					main.appendMessage("[CMD_CHATALL]: " + chatall_content);
					break;
//gửi file riêng tư
				case "CMD_SENDFILEPRIAVTE":
					//tên người gửi
					String usersendp = st.nextToken();
					// tên người nhận
					String selectUsersend = st.nextToken();
					String namefile = st.nextToken();
					int fileSize3 = dis.readInt();
					String type3 = "[CHAT_PRIVATE]:";
					
					byte[] fileBytes3 = new byte[fileSize3];
					dis.readFully(fileBytes3);
					String DIRECTORYFile = "D:/mail_client";
					File directoryFile = new File(DIRECTORYFile);
					File clienFile = new File(DIRECTORYFile + "/" + namefile + ".txt");
					if (!directoryFile.exists()) {
						directoryFile.mkdirs(); // Tạo thư mục nếu chưa tồn tại
					}
					if (!clienFile.exists()) {
						clienFile.mkdirs(); // Tạo thư mục nếu chưa tồn tại
					}
					
					try (FileOutputStream fos = new FileOutputStream(clienFile)) {
						fos.write(fileBytes3); // Ghi mảng byte vào tệp
						fos.flush(); // Đảm bảo dữ liệu được ghi vào tệp
						System.out.println("File đã được lưu thành công vào: " + clienFile.getAbsolutePath());
					} catch (IOException e) {
						e.printStackTrace(); // In ra lỗi nếu không thể lưu tệp
					}
					
					
					Socket tsocSocket = main.getClientList(selectUsersend);

					try {
						// Tạo DataOutputStream để gửi dữ liệu qua socket
						DataOutputStream dos2 = new DataOutputStream(tsocSocket.getOutputStream());

						dos2.writeUTF("CMD_RECIVE_FILE_PRIVATE " + usersendp + " " + namefile + " " + type3);

						dos2.writeInt(fileBytes3.length);
						dos2.write(fileBytes3);
						dos2.flush();
						main.appendMessage(
								"[CMD_RECIVE_FILE_PRIVATE]: " + usersendp + " SEND TO " + selectUsersend + namefile);

					} catch (IOException e) {
						// Ghi lại log lỗi nếu không thể gửi tin nhắn
						main.appendMessage("[IOException]: Unable to send message to " + selectUsersend);
					}

					break;
				case "CMD_SENDIMGALL":
					String usersend_pic = st.nextToken();
					String selectUser_pic = st.nextToken();
					String addmail_pic = st.nextToken();
					int fileSize_pic = dis.readInt();
					String type2_pic = "[CHAT_TONG]:";
					// Đọc ảnh vào mảng byte

					byte[] fileBytes_pic = new byte[fileSize_pic];
					dis.readFully(fileBytes_pic);
					String DIRECTORY_PATH11_pic = "D:/image_client";
					File directory1_pic = new File(DIRECTORY_PATH11_pic);
					File imageFile = new File(DIRECTORY_PATH11_pic + "/" + addmail_pic);

					if (!directory1_pic.exists()) {
						directory1_pic.mkdirs(); // Tạo thư mục nếu chưa tồn tại
					}

					try (FileOutputStream fos = new FileOutputStream(imageFile)) {
						// Ghi mảng byte (ảnh) vào tệp
						fos.write(fileBytes_pic);
						fos.flush(); // Đảm bảo dữ liệu đã được ghi đầy đủ vào tệp
						System.out.println("Ảnh đã được lưu thành công tại: " + imageFile.getAbsolutePath());
					} catch (IOException e) {
						e.printStackTrace(); // In lỗi nếu có vấn đề trong quá trình lưu ảnh
					}
					for (int x = 0; x < main.clientList.size(); x++) {
						if (!main.clientList.elementAt(x).equals(usersend_pic)) {
							try {
								// lấy socket của từng client
								Socket tsoc2 = (Socket) main.socketList.elementAt(x);
								DataOutputStream dos2 = new DataOutputStream(tsoc2.getOutputStream());
								dos2.writeUTF("CMD_RECIVE " + usersend_pic + " " + addmail_pic + " " + type2_pic);

								// Gửi dữ liệu file đến client
								dos2.writeInt(fileBytes_pic.length);
								dos2.write(fileBytes_pic);
								dos2.flush();
								main.appendMessage(
										"[CMD_SENDIMGALL]: " + usersend_pic + " SEND FILE  TO ALL  " + addmail_pic);

							} catch (IOException e) {
								main.appendMessage("[CMD_CHATALL]: " + e.getMessage());
							}
						}
					}

					break;
					///Send maill nhóm
				case "CMD_SENDFILEALL":
					String usersend = st.nextToken();
					String selectUser = st.nextToken();
					String addmail = st.nextToken();
					int fileSize = dis.readInt();
					String type2 = "[CHAT_TONG]:";

					byte[] fileBytes = new byte[fileSize];
					dis.readFully(fileBytes);
					String DIRECTORY_PATH11 = "D:/mail_client";
					File directory1 = new File(DIRECTORY_PATH11);
					File clientfile2 = new File(DIRECTORY_PATH11 + "/" + addmail );

					if (!directory1.exists()) {
						directory1.mkdirs(); // Tạo thư mục nếu chưa tồn tại
					}

					// Tạo tệp và ghi dữ liệu vào tệp
					try (FileOutputStream fos = new FileOutputStream(clientfile2)) {
						fos.write(fileBytes); // Ghi mảng byte vào tệp
						fos.flush(); // Đảm bảo dữ liệu được ghi vào tệp
						System.out.println("File đã được lưu thành công vào: " + clientfile2.getAbsolutePath());
					} catch (IOException e) {
						e.printStackTrace(); // In ra lỗi nếu không thể lưu tệp
					}

					for (int x = 0; x < main.clientList.size(); x++) {
						if (!main.clientList.elementAt(x).equals(usersend)) {
							try {
								// lấy socket của từng client
								Socket tsoc2 = (Socket) main.socketList.elementAt(x);
								DataOutputStream dos2 = new DataOutputStream(tsoc2.getOutputStream());
								dos2.writeUTF("CMD_RECIVE " + usersend + " " + addmail + " " + type2);

								// Gửi dữ liệu file đến client
								dos2.writeInt(fileBytes.length);
								dos2.write(fileBytes);
								dos2.flush();
								main.appendMessage("[CMD_SENDFILEALL]: " + usersend + " SEND FILE  TO ALL  " + addmail);

							} catch (IOException e) {
								main.appendMessage("[CMD_CHATALL]: " + e.getMessage());
							}
						}
					}

					break;

				case "CMD_SHARINGSOCKET":
					main.appendMessage("CMD_SHARINGSOCKET : Client stablish a socket connection for file sharing...");
					String file_sharing_username = st.nextToken();
					filesharing_username = file_sharing_username;
					main.setClientFileSharingUsername(file_sharing_username);
					main.setClientFileSharingSocket(socket);
					main.appendMessage("CMD_SHARINGSOCKET : File sharing connected...");
					break;

				case "CMD_SENDFILE":
					main.appendMessage("CMD_SENDFILE : Client sending a file...");
					/*
					 * Format: CMD_SENDFILE [Filename] [Recipient] [Consignee]
					 */
					String file_name = st.nextToken();
					String sendto = st.nextToken();
					String consignee = st.nextToken();
					System.out.println(file_name);
					System.out.println(sendto);

					System.out.println(consignee);

					/** Get the client Socket **/
					main.appendMessage("CMD_SENDFILE : preparing connections..");
					Socket cSock = main.getClientFileSharingSocket(sendto); /* Consignee Socket */
					/* Now Check if the consignee socket was exists. */
					if (cSock != null) { /* Exists */
						try {
							main.appendMessage("CMD_SENDFILE : Connected..!");
							/** First Write the filename.. **/
							main.appendMessage("CMD_SENDFILE : Sending file to client...");
							DataOutputStream cDos = new DataOutputStream(cSock.getOutputStream());
							cDos.writeUTF("CMD_SENDFILE " + file_name);
							/** Second send now the file content **/
							InputStream input = socket.getInputStream();
							OutputStream sendFile = cSock.getOutputStream();
							byte[] buffer = new byte[1024];
							int cnt;
							while ((cnt = input.read(buffer)) > 0) {
								sendFile.write(buffer, 0, cnt);
							}
							sendFile.flush();
							sendFile.close();
							/** Remove client list **/
							main.removeClientFileSharing(sendto);
							main.removeClientFileSharing(consignee);
							main.appendMessage("CMD_SENDFILE : File was send to client...");
						} catch (IOException e) {
							main.appendMessage("[CMD_SENDFILE]: " + e.getMessage());
						}
					} else { /* Not exists, return error */
						/* FORMAT: CMD_SENDFILEERROR */
						main.removeClientFileSharing(consignee);
						main.appendMessage("CMD_SENDFILE : Client '" + sendto + "' was not found.!");
						DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
						dos.writeUTF("CMD_SENDFILEERROR " + "Client '" + sendto
								+ "' was not found, File Sharing will exit.");
					}
					break;

				default:
					main.appendMessage("[CMDException]: Unknown Command " + CMD);
					break;
				}
			}
		} catch (IOException e) {
			/* this is for chatting client, remove if it is exists.. */
			System.out.println(client);
			System.out.println("File Sharing: " + filesharing_username);
			main.removeFromTheList(client);
			if (filesharing_username != null) {
				main.removeClientFileSharing(filesharing_username);
			}
			main.appendMessage("[SocketThread]: Client connection closed..!");
		}
	}

}