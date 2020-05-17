import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.event.EventListenerList;

public class ControlPanel extends JPanel implements ActionListener {

    /**
     * All of the variables used for the ControlPanel class.
     */
    public final static String FIRST_STRING = "First";
    public final static String SECOND_STRING = "Second";
    public final static String THIRD_STRING = "Third";
    public final static String FOURTH_STRING = "Fourth";
    private static String outputString = "First"; // Setup as the default radio button selection.
    private EventListenerList listenerList = new EventListenerList();

    private JLabel firstLabel;
    private JLabel secondLabel;
    private JLabel thirdLabel;
    private JLabel forthLabel;
    private JLabel inputBoxLabel;

    private JTextField userInputField;
    public static JTextArea outputTextArea;
    private JScrollPane outputScrollArea;

    private JRadioButton firstRadioButton;
    private JRadioButton secondRadioButton;
    private JRadioButton thirdRadioButton;
    private JRadioButton forthRadioButton;

    private JButton actionButton;

    /**
     * Sets up the details panel on the left, with a lovely wee border.
     */
    public ControlPanel(String titleBar) {
        Dimension size = getPreferredSize();
        size.width = 250; // Sets up the width of the inside frame
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder(titleBar));

        this.setupRadioButtons();
        this.setupJLabels();
        this.setupTextField();
        this.setupActionButton();

        //// SETUP THE LAYOUT ////
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 0.5;
        gc.weighty = 0.5;

        // First column
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 0;
        add(this.firstLabel, gc);
        ++gc.gridy;
        add(this.secondLabel, gc);
        ++gc.gridy;
        add(this.thirdLabel, gc);
        ++gc.gridy;
        add(this.forthLabel, gc);
        ++gc.gridy;
        add(this.inputBoxLabel, gc);


        // Second column
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 0;
        add(this.firstRadioButton, gc);
        ++gc.gridy;
        add(this.secondRadioButton, gc);
        ++gc.gridy;
        add(this.thirdRadioButton, gc);
        ++gc.gridy;
        add(this.forthRadioButton, gc);
        ++gc.gridy;
        add(this.userInputField, gc);

        // Add the text panel where the password is written to
        gc.anchor = GridBagConstraints.BASELINE;
        ++gc.gridy;
        gc.gridx = 0;
        gc.gridwidth = 2;
        add(this.outputScrollArea, gc);

        // Generate button Row
        gc.weighty = 10;
        ++gc.gridy;
        add(this.actionButton, gc);
        //
    }

    /**
     * Setup method for the radio buttons.
     */
    private void setupRadioButtons(){
        this.firstRadioButton = new JRadioButton(FIRST_STRING);
        this.firstRadioButton.setMnemonic(KeyEvent.VK_A);
        this.firstRadioButton.setActionCommand(FIRST_STRING);
        this.firstRadioButton.setSelected(true);

        this.secondRadioButton = new JRadioButton(SECOND_STRING);
        this.secondRadioButton.setMnemonic(KeyEvent.VK_B);
        this.secondRadioButton.setActionCommand(SECOND_STRING);

        this.thirdRadioButton = new JRadioButton(THIRD_STRING);
        this.thirdRadioButton.setMnemonic(KeyEvent.VK_C);
        this.thirdRadioButton.setActionCommand(THIRD_STRING);

        this.forthRadioButton = new JRadioButton(FOURTH_STRING);
        this.forthRadioButton.setMnemonic(KeyEvent.VK_D);
        this.forthRadioButton.setActionCommand(FOURTH_STRING);

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(this.firstRadioButton);
        group.add(this.secondRadioButton);
        group.add(this.thirdRadioButton);
        group.add(this.forthRadioButton);

        //Register a listener for the radio buttons.
        this.firstRadioButton.addActionListener(this);
        this.secondRadioButton.addActionListener(this);
        this.thirdRadioButton.addActionListener(this);
        this.forthRadioButton.addActionListener(this);

    }

    /**
     * Setup the labels.
     */
    private void setupJLabels(){
        this.firstLabel = new JLabel("First Label: ");
        this.secondLabel = new JLabel("Second Label: ");
        this.thirdLabel = new JLabel("Third Label: ");
        this.forthLabel = new JLabel("Forth Label: ");
        this.inputBoxLabel = new JLabel("Input Label: ");
    }

    /**
     * Sets up the text field with a scrollable area.
     */
    private void setupTextField(){
        this.userInputField = new JTextField("Default text", 10);
        ControlPanel.outputTextArea = new JTextArea();
        this.outputScrollArea = new JScrollPane(outputTextArea);
        this.outputScrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.outputScrollArea.setPreferredSize(new Dimension(200, 55));
        ControlPanel.outputTextArea.setLineWrap(true);
        ControlPanel.outputTextArea.setEditable(false);
    }

    /**
     * Sets up the action button at the bottom.
     */
    private void setupActionButton(){
        this.actionButton = new JButton("Press Me!");
        this.actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = userInputField.getText().trim();
                String outputText = inputText.toUpperCase() + "\n" + ControlPanel.outputString + " button selected.";
                fireControlPanelEvent(new CPEvent(this, outputText));
            }
        });

        this.addControlPanelListener(new CPListener(){
            public void detailEventOccurred(CPEvent event) {
                ControlPanel.outputTextArea.setText(event.getText());
            }
        });
    }

    /**
     * Clearly this is just some kind of wizardry.
     *
     * I mean really?! We're going through this in double
     * steps to find the CPListener object or some shit..?
     *
     * @param event The event passed from the listener to the actionPerformed method.
     */
    private void fireControlPanelEvent(CPEvent event) {
        Object[] listeners = this.listenerList.getListenerList();

        for(int i = 0; i < listeners.length; i += 2) {
            if(listeners[i] == CPListener.class){
                ((CPListener)listeners[i+1]).detailEventOccurred(event);
            }
        }
    }

    /**
     * Listener for the input box. Updates the listener list with a new event.
     *
     * @param listener Input box messages are detected here from the user input.
     */
    private void addControlPanelListener(CPListener listener) {
        this.listenerList.add(CPListener.class, listener);
    }

    /**
     * Not yet implemented.
     *
     * @param listener Removes a listener object from the list. I guess.
     */
    public void removeControlPanelListener(CPListener listener) {
        this.listenerList.remove(CPListener.class, listener);
        // One day ill do this. If I need to.
    }

    /**
     * Activates the action from the button.
     *
     * @param e Action performed from the listener.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ControlPanel.outputString = e.getActionCommand();
    }
}
