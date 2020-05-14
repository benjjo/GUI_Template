import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private DetailsPanel detailsPanel;

    public MainFrame(String title){
        super(title);

        // Set the layout manager
        setLayout(new BorderLayout()); // BorderLayout just lets you add components to top, left and centre of the frame.

        detailsPanel = new DetailsPanel();

        // Add Swing components to the content pane
        Container c = getContentPane();
        c.add(detailsPanel, BorderLayout.WEST);
    }
}