package artificialgrammarlearning.gui.model;

import java.util.ArrayList;
import java.util.Observable;

import artificialgrammarlearning.logic.Event;
import artificialgrammarlearning.logic.EventType;
import artificialgrammarlearning.logic.InstructionEvent;
import artificialgrammarlearning.logic.QuitEvent;
import artificialgrammarlearning.logic.Test;
import artificialgrammarlearning.logic.TestCreationException;
import artificialgrammarlearning.logic.TextResponseEvent;
import artificialgrammarlearning.logic.TextStimulusEvent;

public class AGLTestModel extends Observable {

	private Test artificialGrammarLearningTest;
	private Event actualEvent;

	public AGLTestModel(Test artificialGrammarLearningTest) {
		this.artificialGrammarLearningTest = artificialGrammarLearningTest;
	}

	public void start() throws AGLTestModelException {
		try {
			artificialGrammarLearningTest.start();
		} catch (TestCreationException e) {
			throw new AGLTestModelException(e.getMessage());
		}
	}
	
	public void next() throws AGLTestModelException {
		
		try {
			actualEvent = artificialGrammarLearningTest.next();
		} catch (TestCreationException e) {
			throw new AGLTestModelException(e.getMessage());
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void stop() throws AGLTestModelException {
		try {
			artificialGrammarLearningTest.stop();
		} catch (TestCreationException e) {
			throw new AGLTestModelException(e.getMessage());
		}
	}
	
	public void setResponse(ArrayList<String> response) {
		TextResponseEvent responseEvent = (TextResponseEvent) actualEvent;
		
		responseEvent.setResponse(response);
	}

	public Event getActualEvent() {
		return actualEvent;
	}
	
	public EventType getEventType() {
		return actualEvent.getType();
	}
	
	public int getDisplayTime() {
		return actualEvent.getDisplayTime();
	}
	
	public String getInstruction() {
		InstructionEvent instructionEvent = (InstructionEvent) actualEvent;
		
		return instructionEvent.getInstruction();
	}
	
	public String getFarewell() {
		QuitEvent quitEvent = (QuitEvent) actualEvent;
		
		return quitEvent.getQuitMessage();
	}
	
	public ArrayList<String> getStimulus() {
		TextStimulusEvent stimulusEvent = (TextStimulusEvent) actualEvent;
		
		return stimulusEvent.getTextStimulus();
	}

}
