package artificialgrammarlearning.gui.controller;

import java.util.ArrayList;

import artificialgrammarlearning.gui.model.AGLTestModel;
import artificialgrammarlearning.gui.model.AGLTestModelException;
import artificialgrammarlearning.gui.view.TestFrame;
import artificialgrammarlearning.logic.ArtificialGrammarLearningTest;
import artificialgrammarlearning.logic.TestCreationException;

public class TestController {

	private AGLTestModel model;

	private TestFrame testFrame;

	private ArrayList<String> response;

	public TestController(String pathToStimuli, String participantName) {
		testFrame = new TestFrame(this);

		response = new ArrayList<String>();

		try {
			model = new AGLTestModel(new ArtificialGrammarLearningTest(
					pathToStimuli, participantName, "results"));

			model.addObserver(testFrame);

			testFrame.setTestModel(model);
			
			model.start();
			next();
		} catch (TestCreationException e) {
			testFrame.showClosingMessageToUser(e.getMessage());
		} catch (AGLTestModelException e) {
			testFrame.showClosingMessageToUser(e.getMessage());
		}

	}
	
	public void stop() {
		try {
			model.stop();
		} catch (AGLTestModelException e) {
			testFrame.showClosingMessageToUser(e.getMessage());
		}
	}

	public void next() {

		try {
			model.next();
		} catch (AGLTestModelException e) {
			testFrame.showClosingMessageToUser(e.getMessage());
		}

	}

	public void setResponse(String response) {
		if (this.response.size() == 3) {
			this.response.add(response);
			model.setResponse(this.response);
			this.response = new ArrayList<String>();
			next();
		} else {
			this.response.add(response);
		}
	}

}
