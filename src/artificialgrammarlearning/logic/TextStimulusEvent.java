package artificialgrammarlearning.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

public class TextStimulusEvent extends Event implements Observer {

	private ArrayList<String> textStimulus;

	public TextStimulusEvent(String name, ArrayList<String> textStimulus) {

		// Initialization
		super(name, EventType.STIMULUS);
		this.textStimulus = textStimulus;
		setResponseCorrect(false);
	}

	public ArrayList<String> getTextStimulus() {
		return textStimulus;
	}

	public void setTextStimulus(ArrayList<String> textStimulus) {
		this.textStimulus = textStimulus;
	}

	public String toString() {
		String tmpString = "Name: " + getName() + ", DisplayTime: "
				+ getDisplayTime();

		int counter = 1;
		for (String stimulus : textStimulus) {
			tmpString = tmpString + "\n-> Stimulus " + counter + ": "
					+ stimulus;
			counter++;
		}

		return tmpString;
	}

	public boolean checkIfResponseIsCorrect(ArrayList<String> response) {
		// Convert into sets for easier comparison
		HashSet<String> setStimulus = new HashSet<String>(textStimulus);
		HashSet<String> setResponse = new HashSet<String>(response);

		// Check if response has same text strings as stimulus
		if (setResponse.equals(setStimulus)) {
			setResponseCorrect(true);
		}

		return isResponseCorrect();
	}

	@Override
	public void update(Observable responseEvent, Object obj) {
		@SuppressWarnings("unchecked")
		ArrayList<String> response = (ArrayList<String>) obj;
		
		checkIfResponseIsCorrect(response);
	}

}
