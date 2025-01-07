/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Video extends JFrame {
	public JLabel screen;
	JLabel videoReceived;

	public Video() {
		setLayout(null);
		setSize(new Dimension(640, 560));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		screen = new JLabel();
		screen.setBounds(0, 0, 640, 480);
		add(screen);
		videoReceived = new JLabel("Client Not Started");
		videoReceived.setBounds(440, 450, 100, 50);
		add(videoReceived);
	}

	public void setScreenView(JLabel j, BufferedImage b) {
	    if (b != null) {
	        // Set the image as the icon for the JLabel
	        j.setIcon(new ImageIcon(b));
	    } else {
	        // In case no image is received, clear the icon (or set a placeholder image)
	        j.setIcon(new ImageIcon());
	    }

	    // Optionally update text (if you have a status label like videoReceived)
	    // videoReceived.setText((b != null) ? "Video Receiving" : "Video Turned Off");
	}


}
