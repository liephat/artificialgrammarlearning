package artificialgrammarlearning.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import artificialgrammarlearning.gui.controller.TestController;
import artificialgrammarlearning.gui.model.AGLTestModel;
import artificialgrammarlearning.logic.EventType;

public class TestFrame extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TestController testController;
	private JPanel contentPane;
	private JTextField tfResponse;
	private JTextArea areaGeneralInstruction;

	private Timer stimulusTimer;

	private Font standardFont;
	private AGLTestModel testModel;

	public TestFrame(TestController testController) {
		this.testController = testController;

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(800, 600));
		this.setTitle("Artificial Grammar Learning");
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				reallyClosingDialog();
			}
		});

		initializeComponents();
		initializeTimer();

	}

	private void initializeTimer() {
		stimulusTimer = new Timer(10000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stimulusTimer.stop();
				testController.next();
			}
		});
	}

	private void reallyClosingDialog() {
		if (JOptionPane.showConfirmDialog(this, "Wollen Sie wirklich beenden?",
				"Schließen", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			System.gc(); // google search solution, preventing
							// interruptException
			this.dispose();
		}
	}

	private JTextPane createInstructionPane() {

		Font instructionFont = new Font("Arial", 0, 32);

		JTextPane paneInstruction = new JTextPane();
		paneInstruction.setEditable(false);
		paneInstruction.setFont(instructionFont);
		paneInstruction.setBackground(this.getBackground());

		// StyledDocument doc = paneInstruction.getStyledDocument();
		// SimpleAttributeSet center = new SimpleAttributeSet();
		// StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
		// doc.setParagraphAttributes(0, doc.getLength(), center, false);

		paneInstruction.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_SPACE)
					testController.next();
			}
		});

		return paneInstruction;
	}

	private JTextPane createStimulusPane() {
		JTextPane paneStimulus = new JTextPane();
		paneStimulus.setEditable(false);
		paneStimulus.setFont(standardFont);
		paneStimulus.setBackground(this.getBackground());

		StyledDocument doc = paneStimulus.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		return paneStimulus;
	}

	private void initializeResponseComponents() {
		tfResponse = new JTextField(8);
		tfResponse.setFont(standardFont);
		tfResponse.setHorizontalAlignment(JTextField.CENTER);
		tfResponse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				testController.setResponse(tfResponse.getText());
				tfResponse.setText("");

			}
		});

		// Convert small letters in to big letters
		tfResponse.setDocument(new PlainDocument() {
			@Override
			public void replace(int offset, int length, String text,
					AttributeSet attrs) throws BadLocationException {
				super.replace(offset, length, text.toUpperCase(), attrs);
			}
		});
	}

	private void initializeComponents() {

		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		contentPane.setBackground(Color.WHITE);

		this.add(contentPane);
		this.setBackground(Color.WHITE);

		standardFont = new Font("Arial", 0, 46);

		areaGeneralInstruction = new JTextArea("(Weiter mit der Leertaste)");
		areaGeneralInstruction.setFont(new Font("Arial", 0, 20));
		areaGeneralInstruction.setEditable(false);
		areaGeneralInstruction.setBackground(this.getBackground());

		initializeResponseComponents();
	}

	private JButton createSaveAndQuitButton() {
		JButton buttonQuit = new JButton();
		buttonQuit.setText("Beenden");

		buttonQuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testController.stop();
				
				System.gc(); // google search solution, preventing
				// interruptException
				dispose();
				return;
			}

		});
		
		return buttonQuit;
	}

	private void showInstruction(String instruction) {

		contentPane.removeAll();

		JTextPane paneInstruction = createInstructionPane();
		paneInstruction.setText(instruction);

		GridBagConstraints constraints = createStandardGridBagConstraints();

		contentPane.add(paneInstruction, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;

		contentPane.add(areaGeneralInstruction, constraints);

	}

	private void showStimulus(ArrayList<String> stimulus) {

		contentPane.removeAll();

		JTextPane paneStimulus = createStimulusPane();
		for (String line : stimulus) {
			paneStimulus.setText(paneStimulus.getText() + line + "\n");
		}

		GridBagConstraints constraints = createStandardGridBagConstraints();

		contentPane.add(paneStimulus, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;

		stimulusTimer.start();

	}

	private void showResponse() {

		contentPane.removeAll();

		GridBagConstraints constraints = createStandardGridBagConstraints();

		contentPane.add(tfResponse, constraints);

		tfResponse.requestFocus();

	}
	
	private void showFarewell(String quitMessage) {

		contentPane.removeAll();

		JTextPane paneInstruction = createInstructionPane();
		paneInstruction.setText(quitMessage);

		GridBagConstraints constraints = createStandardGridBagConstraints();

		contentPane.add(paneInstruction, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;

		contentPane.add(createSaveAndQuitButton(), constraints);

	}

	private GridBagConstraints createStandardGridBagConstraints() {

		GridBagConstraints constraints = new GridBagConstraints();

		constraints.insets = new Insets(50, 0, 50, 0);
		constraints.gridx = 0;
		constraints.gridy = 0;

		return constraints;
	}

	@Override
	public void update(Observable obs, Object obj) {

		EventType actualEventType = testModel.getEventType();

		if (actualEventType == EventType.INSTRUCTION) {

			showInstruction(testModel.getInstruction());

		} else if (actualEventType == EventType.STIMULUS) {

			showStimulus(testModel.getStimulus());

		} else if (actualEventType == EventType.RESPONSE) {

			showResponse();

		} else if (actualEventType == EventType.QUIT) {
			
			showFarewell(testModel.getFarewell());

		}

		this.repaint();
		this.setVisible(true);

	}

	public void showClosingMessageToUser(String message) {

		JOptionPane
				.showConfirmDialog(
						this,
						"Sorry, ein unerwarteter Fehler ist aufgetreten. \n"
								+ "Bitte wende dich an den Programmierer! Das Programm schließt sich automatisch.\n"
								+ "Der Grund für den Fehler:\n" + message,
						"Fehler", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
		System.gc();
		this.dispose();
	}

	public void setTestModel(AGLTestModel model) {
		testModel = model;
	}

}
