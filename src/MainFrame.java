import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private ControlPanel controlPanel;

    public MainFrame(String title){
        super(title);

        // Set the layout manager
        setLayout(new BorderLayout()); // BorderLayout just lets you add components to top, left and centre of the frame.

        controlPanel = new ControlPanel("Control Panel");

        // Add Swing components to the content pane
        Container c = getContentPane();
        c.add(controlPanel, BorderLayout.WEST);
    }
}