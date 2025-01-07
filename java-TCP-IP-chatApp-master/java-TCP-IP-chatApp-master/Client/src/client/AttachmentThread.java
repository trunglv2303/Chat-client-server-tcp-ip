package client;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DecimalFormat;


public class AttachmentThread implements Runnable{
    
    private Socket socket;
    private DataOutputStream dos;
    private File filename;
    private String recipient, myusername;
    private AttachmentForm form;
    private DecimalFormat df = new DecimalFormat("##,#00");
    
    public AttachmentThread(Socket socket, File filename, String recipient, String myusername, AttachmentForm form){
        this.socket = socket;
        this.filename = filename;
        this.recipient = recipient;
        this.myusername = myusername;
        this.form = form;
    }

    @Override
    public void run() {
        try {
            System.out.println("Sending File..!");
            dos = new DataOutputStream(socket.getOutputStream());
            
            // Format: CMD_SENDFILE [Filename] [Recipient] [Sender]
            String clean_filename = filename.getName();
            dos.writeUTF("CMD_SENDFILE " + clean_filename.replace(" ", "_") + " " + recipient + " " + myusername);

            // Create an OutputStream to send data to server
            OutputStream output = socket.getOutputStream();
            
            // Read the file into a BufferedInputStream
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename));
            
            // Create a buffer for reading the file in chunks
            byte[] buffer = new byte[1024];
            
            // Get the total size of the file
            long totalBytes = filename.length();
            long bytesSent = 0;
            
            // Variable to hold the number of bytes read from the file
            int count;
            
            // Read the file in chunks and send it over the socket
            while ((count = bis.read(buffer)) > 0) {
                bytesSent += count;
                
                // Calculate the percentage of file sent
                int percent = (int) ((bytesSent * 100) / totalBytes);  // Calculate progress percentage
                
                // Update GUI progress
                form.setMyTitle(df.format(percent) + "% Sending File...");
                
                // Send the data to the server
                output.write(buffer, 0, count);
            }
            
            // Update the GUI when the file is completely sent
            form.disableMyGUI(false);
            
            // Close streams
            output.flush();
            output.close();
            bis.close();
            
            System.out.println("File was sent..!");
            
        } catch (IOException e) {
            System.out.println("[SendFile]: " + e.getMessage());
        }
    }
}
