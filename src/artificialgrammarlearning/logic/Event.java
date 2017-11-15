package artificialgrammarlearning.logic;

import java.util.Observable;

public abstract class Event extends Observable {

	private int displayTime;
	private String name;
	private boolean responseCorrect;
	private EventType type;

	public Event(String name, EventType type) {
		this.name = name;
		this.type = type;
		responseCorrect = true;
	}

	public int getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(int displayTime) {
		this.displayTime = displayTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isResponseCorrect() {
		return responseCorrect;
	}

	public void setResponseCorrect(boolean responseCorrect) {
		this.responseCorrect = responseCorrect;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public String toString() {
		return "Name: " + name + ", DisplayTime: " + displayTime;
	}

}
