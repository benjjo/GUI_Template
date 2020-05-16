import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
/**
 * GUI template
 * Thanks to https://www.caveofprogramming.com/
 *
 */

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run () {
                JFrame frame = new MainFrame("Title bar");
                frame.setSize(265, 300); // sets the size of the outside frame
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets up JFrame to close when cancelled.
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Sets the JFrame to fullscreen
                frame.setUndecorated(true); // Hides the title bar


                try {
                    URL resource = frame.getClass().getResource("/icon.png");
                    BufferedImage image = ImageIO.read(resource);
                    frame.setIconImage(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                frame.setVisible(true); // Draws the frame to the screen
            }});
    }

}
