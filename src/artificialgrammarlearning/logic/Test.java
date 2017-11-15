package artificialgrammarlearning.logic;

public interface Test {

	void start() throws TestCreationException;

	void stop() throws TestCreationException;
	
	Event next() throws TestCreationException;
	
	void loadStimuli(String path) throws TestCreationException;

}
