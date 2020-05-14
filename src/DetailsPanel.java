import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.event.EventListenerList;

public class DetailsPanel extends JPanel implements ActionListener {

    public final static String FIRST_STRING = "First";
    public final static String SECOND_STRING = "Second";
    public final static String THIRD_STRING = "Third";
    public final static String FORTH_STRING = "Forth";
    private static String outputString = "First"; // Setup as the default radio button selection.
    private EventListenerList listenerList = new EventListenerList();

    /**
     * Sets up the details panel on the left, with a lovely wee border.
     */
    public DetailsPanel() {
        Dimension size = getPreferredSize();
        size.width = 250; // Sets up the width of the inside frame
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("Control Panel"));

        ////SETUP THE LABELS ////
        JLabel firstLabel = new JLabel("First Label: ");
        JLabel secondLabel = new JLabel("Second Label: ");
        JLabel thirdLabel = new JLabel("Third Label: ");
        JLabel forthLabel = new JLabel("Forth Label: ");
        JLabel inputBoxLabel = new JLabel("Input Label: ");

        ////SETUP THE TEXT FIELDS ////
        final JTextField userInputField = new JTextField("Default text", 10);
        final JTextArea outputTextArea = new JTextArea();
        final JScrollPane outputScrollArea = new JScrollPane(outputTextArea);
        outputScrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputScrollArea.setPreferredSize(new Dimension(200, 55));
        outputTextArea.setLineWrap(true);
        outputTextArea.setEditable(false);

        //// SETUP THE BUTTON ////
        JButton addBtn = new JButton("Press Me!");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = userInputField.getText().trim();
                String outputText = inputText.toUpperCase() + "\n" + DetailsPanel.outputString + " button selected.";
                fireDetailEvent(new DetailEvent(this, outputText));
            }
        });

        ////SETUP THE OUTPUT BOX////
        addDetailListener(new DetailListener(){
            public void detailEventOccurred(DetailEvent event) {
                String text = event.getText();
                outputTextArea.setText(text);
            }
        });

        //// SETUP RADIO BUTTONS ////
        JRadioButton firstRadioButton = new JRadioButton(FIRST_STRING);
        firstRadioButton.setMnemonic(KeyEvent.VK_A);
        firstRadioButton.setActionCommand(FIRST_STRING);
        firstRadioButton.setSelected(true);

        JRadioButton secondRadioButton = new JRadioButton(SECOND_STRING);
        secondRadioButton.setMnemonic(KeyEvent.VK_B);
        secondRadioButton.setActionCommand(SECOND_STRING);

        JRadioButton thirdRadioButton = new JRadioButton(THIRD_STRING);
        thirdRadioButton.setMnemonic(KeyEvent.VK_C);
        thirdRadioButton.setActionCommand(THIRD_STRING);

        JRadioButton forthRadioButton = new JRadioButton(FORTH_STRING);
        forthRadioButton.setMnemonic(KeyEvent.VK_D);
        forthRadioButton.setActionCommand(FORTH_STRING);

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(firstRadioButton);
        group.add(secondRadioButton);
        group.add(thirdRadioButton);
        group.add(forthRadioButton);

        //Register a listener for the radio buttons.
        firstRadioButton.addActionListener(this);
        secondRadioButton.addActionListener(this);
        thirdRadioButton.addActionListener(this);
        forthRadioButton.addActionListener(this);

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        //// SETUP THE LAYOUT ////
        gc.weightx = 0.5;
        gc.weighty = 0.5;

        // First column
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 0;
        add(firstLabel, gc);
        ++gc.gridy;
        add(secondLabel, gc);
        ++gc.gridy;
        add(thirdLabel, gc);
        ++gc.gridy;
        add(forthLabel, gc);
        ++gc.gridy;
        add(inputBoxLabel, gc);


        // Second column
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 0;
        add(firstRadioButton, gc);
        ++gc.gridy;
        add(secondRadioButton, gc);
        ++gc.gridy;
        add(thirdRadioButton, gc);
        ++gc.gridy;
        add(forthRadioButton, gc);
        ++gc.gridy;
        add(userInputField, gc);

        // Add the text panel where the password is written to
        gc.anchor = GridBagConstraints.BASELINE;
        ++gc.gridy;
        gc.gridx = 0;
        gc.gridwidth = 2;
        add(outputScrollArea, gc);

        // Generate button Row
        gc.weighty = 10;
        ++gc.gridy;
        add(addBtn, gc);
        //
    }

    private void fireDetailEvent(DetailEvent event) { // Clearly this is just some kind of wizardry.
        Object[] listeners = listenerList.getListenerList();

        for(int i = 0; i < listeners.length; i += 2) {
            // I mean really?! We're going through this in double
            // steps to find the DetailListener object or some shit..?
            if(listeners[i] == DetailListener.class){
                ((DetailListener)listeners[i+1]).detailEventOccurred(event);
            }
        }
    }

    private void addDetailListener(DetailListener listener) {
        listenerList.add(DetailListener.class, listener);
    }

    public void removeDetailListener(DetailListener listener) {
        listenerList.remove(DetailListener.class, listener);
        // One day ill do this. If I need to.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DetailsPanel.outputString = e.getActionCommand();
    }
}