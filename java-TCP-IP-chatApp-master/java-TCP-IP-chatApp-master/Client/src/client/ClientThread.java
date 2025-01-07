package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ClientThread implements Runnable {
	private boolean isConnected = false;

	Socket socket;
	DataInputStream dis;
	MainForm main;
	StringTokenizer st;
	LoginForm loginForm;

	public ClientThread(Socket socket, MainForm main) {
		this.main = main;
		this.socket = socket;
		try {
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			main.appendMessage("", "[IOException]: " + e.getMessage(), "Error", Color.RED, Color.RED);
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				String data = dis.readUTF();
				st = new StringTokenizer(data);
				/** Get Message CMD **/
				String CMD = st.nextToken();

				switch (CMD) {
				case "CMD_CHAT_VIDEO":
				    try {
				        Video g = new Video();
				        while (true) {
				            // Đọc kích thước và dữ liệu hình ảnh từ luồng vào
				            int length = dis.readInt();
				            if (length > 0) {
				                byte[] imageBytes = new byte[length];
				                dis.readFully(imageBytes, 0, imageBytes.length);
				                
				                // Chuyển byte array thành BufferedImage
				                ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
				                BufferedImage image = ImageIO.read(bais);
				                if (image != null) {
				                    g.setScreenView(null, image);  // Cập nhật giao diện với hình ảnh mới
				                }
				            }
				        }
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
				    break;



				case "CMD_RECIVE_FILE_PRIVATE":
				    // Đọc kích thước của file (số byte cần đọc)
				    int fileSize_private = dis.readInt();
				    
				    // Tạo mảng byte có kích thước tương ứng với kích thước file để lưu trữ dữ liệu
				    byte[] fileBytes_private = new byte[fileSize_private];
				    
				    // Đọc toàn bộ dữ liệu của file vào mảng byte
				    dis.readFully(fileBytes_private);

				    // Đọc tên người gửi file từ chuỗi phân tách
				    String usersend_private = st.nextToken();
				    
				    // Đọc tên file từ chuỗi phân tách
				    String namefile_private = st.nextToken();
				    
				    // Đọc loại file hoặc thông tin bổ sung từ chuỗi phân tách
				    String type2_private = st.nextToken();
				    
				    // Hiển thị hộp thoại yêu cầu người dùng xác nhận có muốn nhận file không
				    int choice_private_private = JOptionPane.showConfirmDialog(main, 
				        "Bạn có muốn nhận file từ " + usersend_private + " không?", 
				        "Nhận File", JOptionPane.YES_NO_OPTION);
				    
				    // Nếu người dùng chọn "Yes" (chấp nhận nhận file)
				    if (choice_private_private == JOptionPane.YES_OPTION) {
				        // Đảm bảo thư mục chứa file đã tồn tại, nếu chưa thì tạo thư mục
				        File file = new File("D:/" + namefile_private);
				        
				        // Sử dụng FileOutputStream để ghi dữ liệu vào file
				        try (FileOutputStream fos = new FileOutputStream(file)) {
				            // Ghi mảng byte chứa dữ liệu file vào file đích
				            fos.write(fileBytes_private);
				            
				            // Gửi thông báo về việc file đã được lưu thành công với đường dẫn tuyệt đối của file
				            main.appendMessage(type2_private, 
				                "File đã được lưu thành công tại " + file.getAbsolutePath(),
				                usersend_private, Color.MAGENTA, Color.BLUE);
				        } catch (IOException e) {
				            // Nếu có lỗi trong quá trình ghi file, in ra lỗi
				            e.printStackTrace();
				        }
				    }
				    // Kết thúc case
				    break;

				case "CMD_RECIVE":
					int fileSize = dis.readInt();
					byte[] fileBytes = new byte[fileSize];
					dis.readFully(fileBytes);

					String usersend = st.nextToken();
					String namefile = st.nextToken();
					String type2 = st.nextToken();
					int choice = JOptionPane.showConfirmDialog(main, "Bạn có muốn nhận file từ " + usersend + " không?",
							"Nhận File", JOptionPane.YES_NO_OPTION);
					if (choice == JOptionPane.YES_OPTION) {
						// Đảm bảo thư mục chứa tệp đã tồn tại
						File file = new File("D:/" + namefile);
						try (FileOutputStream fos = new FileOutputStream(file)) { // Dùng FileOutputStream để ghi byte
																					// vào tệp
							fos.write(fileBytes); // Ghi mảng byte vào tệp
							main.appendMessage(type2,file.getAbsolutePath(),
									usersend, Color.MAGENTA, Color.BLUE);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
					break;
					//nhận thông báo từ server
				case "CMD_MESSAGE":
					// phát ra âm thanh
					SoundEffect.MessageReceive.play(); // Play Audio clip
					String msg = "";
					//loại chat
					String type = st.nextToken();
					System.out.println("type" + type);
					String frm = st.nextToken();
					System.out.println("frm" + frm);
					// nội dung đoạn mess
					while (st.hasMoreTokens()) {
						msg = msg + " " + st.nextToken();
					}
					// hiện lên giao diện
					main.appendMessage(type, msg, frm, Color.MAGENTA, Color.BLUE);
					break;
				case "CMD_ONLINE":
					// Tạo vector chứa danh sách người dùng
					Vector online = new Vector();
					//kiểm tra dữ liệu bên tring 
					while (st.hasMoreTokens()) {
						// chuỗi tiếp theo
						String list = st.nextToken();
						// nếu phần list khác tên với người đang dùng
						if (!list.equalsIgnoreCase(main.username)) {
							// thêm người dùng vào vector
							online.add(list);
						}
					}
					// sau khi duyệt xong sẽ add dữ liệu online vào giao diện
					main.appendOnlineList(online);
					break;

				default:
					main.appendMessage("", "[CMDException]: Unknown Command " + CMD, "CMDException", Color.RED,
							Color.RED);
					break;
				}
			}
		} catch (IOException e) {
			main.appendMessage("", "[IOException]: " + e.getMessage(), "Error", Color.RED, Color.RED);
		}
	}
}
