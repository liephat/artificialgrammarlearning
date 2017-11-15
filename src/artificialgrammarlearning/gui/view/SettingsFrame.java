package artificialgrammarlearning.gui.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import artificialgrammarlearning.gui.controller.TestController;

public class SettingsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7385034545930167701L;

	private JTextField tfName;
	private JTextField tfStimuli;
	private JTextField tfResults;

	public SettingsFrame() {

		this.setTitle("Artificial Grammar Learning Settings");
		this.setResizable(false);
		this.setLocation(400, 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		initializeComponents();

		this.setVisible(true);
		this.pack();
	}

	private void initNameLabelAndTextField() {
		JLabel labelName = new JLabel();
		labelName.setText("VP-Code:");

		this.add(labelName);

		tfName = new JTextField();

		this.add(tfName);

	}

	private void initStimuliLabelAndFileChooser() {
		JLabel labelStimuli = new JLabel();
		labelStimuli.setText("Stimuli:");

		this.add(labelStimuli);

		tfStimuli = new JTextField(20);

		JButton buttonStimuli = new JButton("Suche");
		buttonStimuli.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				initOpenStimuliFileDialog();

			}
		});
		
		JPanel fileChooserStimuli = createFileChooser(tfStimuli, buttonStimuli);
		this.add(fileChooserStimuli);
	}
	
	private JPanel createFileChooser(JTextField tf, JButton button) {
		
		JPanel fileChooser = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		fileChooser.add(tf, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 1;
		fileChooser.add(button, c);
		
		return fileChooser;
	}

	private void initOpenStimuliFileDialog() {

		JFileChooser openStimuliFileDialog = new JFileChooser();
		// openFileDialog.setDialogType(JFileChooser.OPEN_DIALOG);

		openStimuliFileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openStimuliFileDialog.setCurrentDirectory(new File("stimuli"));

		int state = openStimuliFileDialog.showOpenDialog(null);

		if (state == JFileChooser.APPROVE_OPTION) {

			tfStimuli
					.setText(openStimuliFileDialog.getSelectedFile().getPath());

		}
	}
	
	/**
	 * Method is not used yet.
	 */
	private void initResultsLabelAndFileChooser() {
		JLabel labelResults = new JLabel();
		labelResults.setText("Results:");

		this.add(labelResults);

		tfResults = new JTextField(20);

		JButton buttonResults = new JButton("Suche");
		buttonResults.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				initOpenResultsPathDialog();

			}
		});
		
		JPanel fileChooserStimuli = createFileChooser(tfResults, buttonResults);
		this.add(fileChooserStimuli);
	}
	
	/**
	 * Method is not used yet.
	 */
	private void initOpenResultsPathDialog() {

		JFileChooser openResultsPathDialog = new JFileChooser();
		// openFileDialog.setDialogType(JFileChooser.OPEN_DIALOG);

		openResultsPathDialog
				.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// TODO: Aktueller Ordner ändern
		openResultsPathDialog.setCurrentDirectory(new File("results"));

		int state = openResultsPathDialog.showOpenDialog(null);

		if (state == JFileChooser.APPROVE_OPTION) {

			tfResults
					.setText(openResultsPathDialog.getSelectedFile().getPath());

		}
	}

	private void initButtons() {

		initCancelButton();

		initStartButton();

	}

	private void initStartButton() {
		JButton buttonStart = new JButton();
		buttonStart.setText("Start");

		buttonStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TestController testController = new TestController(tfStimuli
						.getText(), tfName.getText());
				dispose();
			}

		});

		this.add(buttonStart);

	}

	private void initCancelButton() {
		JButton buttonCancel = new JButton();
		buttonCancel.setText("Abbrechen");

		buttonCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		this.add(buttonCancel);
	}

	private void initializeComponents() {

		GridLayout layout = new GridLayout();
		layout.setColumns(2);
		layout.setRows(3);
		layout.setHgap(10);
		layout.setVgap(10);

		this.setLayout(layout);

		initNameLabelAndTextField();
		initStimuliLabelAndFileChooser();
		initButtons();

	}

	public void showErrorMessage(String message) {

		JOptionPane.showConfirmDialog(this, message, "Fehler",
				JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);

	}
}
