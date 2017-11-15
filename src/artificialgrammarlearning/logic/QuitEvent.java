package artificialgrammarlearning.logic;

public class QuitEvent extends Event {
	
	private String quitMessage;
	
	public QuitEvent(String name, String quitMessage) {
		super(name, EventType.QUIT);
		this.quitMessage = quitMessage;
	}
	
	public String getQuitMessage() {
		return quitMessage;
	}

	public void setQuitMessage(String quitMessage) {
		this.quitMessage = quitMessage;
	}

}
