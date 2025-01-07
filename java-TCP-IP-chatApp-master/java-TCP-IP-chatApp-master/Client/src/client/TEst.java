package client;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class TEst {

	 

	    public static void main(String[] args) {
	        try {
	 
	            File file = new File("D:\\newfile.txt");
	 
	            if (file.createNewFile()) {
	            	FileOutputStream fos = new FileOutputStream(file);
                    String data = "dssadasdsad";
                    fos.write(data.getBytes()); // Chuyển chuỗi thành mảng byte và ghi vào tệp
	            } else {
	                System.out.println("File already exists.");
	            }
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    
	}
}
