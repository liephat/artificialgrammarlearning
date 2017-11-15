package artificialgrammarlearning.logic;

import java.util.ArrayList;
import java.util.Observer;

public class TextResponseEvent extends Event {

	private ArrayList<String> response;

	public TextResponseEvent(String name, Observer observer) {
		super(name, EventType.RESPONSE);
		this.addObserver(observer);
	}

	public ArrayList<String> getResponse() {
		return response;
	}

	public void setResponse(ArrayList<String> response) {
		this.response = response;
	}

	public void validateResponse() {
		setChanged();
		notifyObservers(response);
	}

}
