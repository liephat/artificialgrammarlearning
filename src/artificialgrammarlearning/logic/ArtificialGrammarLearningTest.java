package artificialgrammarlearning.logic;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArtificialGrammarLearningTest extends Block implements Test {

	private boolean running;
	private String pathToStimuli;

	private ArrayList<ArrayList<String>> cardContents;

	private ArrayList<Event> testHistory;

	private Result result;

	public ArtificialGrammarLearningTest(String pathToStimuli,
			String participantName, String pathToResultsFolder)
			throws TestCreationException {

		// Initialization
		this.pathToStimuli = pathToStimuli;

		result = new Result(participantName, pathToResultsFolder);
	}

	private void prepareAndAddEvents() throws TestCreationException {

		InstructionEvent instructionBeginning = new InstructionEvent(
				"Instruction_0",
				"Bitte lesen Sie die Instruktionen und wenden sich an den Versuchsleiter, sobald Sie fertig sind.");

		addElement(instructionBeginning);

		loadStimuli();

		// Add prepared stimuli as stimulus elements to the test
		int counter = 1;
		for (ArrayList<String> cardContent : cardContents) {
			TextStimulusEvent textStimulus = new TextStimulusEvent(
					String.valueOf(counter), cardContent);

			// Set display time to 10 s
			textStimulus.setDisplayTime(10000);
			addElement(textStimulus);

			// Add response element after each stimulus element
			addElement(new TextResponseEvent("Response_for_Card_" + counter,
					textStimulus));
			counter++;
		}

		QuitEvent end = new QuitEvent("Farewell",
				"Die Lernaufgabe ist beendet. Vielen Dank für Ihre Teilnahme!\n"
						+ "Bitte wenden Sie sich wieder an den Versuchsleiter.");
		addElement(end);

	}

	public void loadStimuli(String pathToStimuli) throws TestCreationException {
		this.pathToStimuli = pathToStimuli;

		loadStimuli();
	}

	/**
	 * Reads all strings from file (lines in file must be a multiple of four)
	 * and writes them to an <code>ArrayList</code> which is used to create the
	 * contents for the cards that will be presented during the test.
	 * 
	 * @throws TestCreationException
	 */

	private void loadStimuli() throws TestCreationException {

		ArrayList<String> stimuliRaw = new ArrayList<String>();

		// Load text stimuli
		Path path = Paths.get(pathToStimuli);
		if (Files.notExists(path)) {
			throw new TestCreationException(
					"File with text stimuli doesn't exist!");
		}
		try {
			List<String> lines = Files.readAllLines(path,
					Charset.defaultCharset());
			for (String line : lines) {
				stimuliRaw.add(line);
			}
		} catch (IOException e) {
			throw new TestCreationException("Couldn't read file!");
		}

		// Give stimuli structure for presentation cards (4 strings/card)
		if (stimuliRaw.size() % 4 == 0) {
			int counter = 0;

			while (counter < stimuliRaw.size()) {
				ArrayList<String> tmpArrayList = new ArrayList<String>();

				for (int i = 0; i < 4; i++) {
					tmpArrayList.add(stimuliRaw.get(counter));
					counter++;
				}

				cardContents.add(tmpArrayList);
			}
		} else {
			throw new TestCreationException(
					"Wrong stimuli file. Lines must be a multiple of 4!");
		}

	}

	public Event next() throws TestCreationException {

		Event nextEvent;

		if (testHistory.size() >= 2) {
			Event lastEvent = testHistory.get(testHistory.size() - 1);
			Event secondLastEvent = testHistory.get(testHistory.size() - 2);

			// If last event was response, check if response was correct
			if (lastEvent.getType() == EventType.RESPONSE) {
				TextResponseEvent response = (TextResponseEvent) lastEvent;
				response.validateResponse();

				if (!secondLastEvent.isResponseCorrect()) {
					nextEvent = getPreviousElement();
					testHistory.add(nextEvent);
					return nextEvent;
				}

			} else if (lastEvent.getType() == EventType.STIMULUS) {
				result.addPresentation(lastEvent.getName());
			}

		}

		nextEvent = getNextEvent();

		if (nextEvent != null) {
			testHistory.add(nextEvent);
		}

		return nextEvent;

	}

	@Override
	public void start() throws TestCreationException {
		running = true;

		cardContents = new ArrayList<ArrayList<String>>();

		testHistory = new ArrayList<Event>();

		// Set type to elemental
		setType(BlockType.ELEMENTAL);

		prepareAndAddEvents();

		result.setDateBegin(new Date());
	}

	/**
	 * Test method. Not useful.
	 * 
	 * @throws TestCreationException
	 */
	public void testRun() throws TestCreationException {

		Event nextElement = this.getNextEvent();

		while (nextElement != null) {
			System.out.println("#### Next Element: ####");
			System.out.println(nextElement);
			nextElement = this.getNextEvent();
		}

		Event previousElement = this.getPreviousElement();

		while (previousElement != null) {
			System.out.println("#### Previous Element: ####");
			System.out.println(previousElement);
			previousElement = this.getPreviousElement();
		}
	}

	/**
	 * Stops the test and writes all results to a file.
	 */
	@Override
	public void stop() throws TestCreationException {
		if (running) {
			running = false;

			result.setDateEnd(new Date());
			result.writeResultsToFile();
		} else {
			throw new TestCreationException("Test is not running");
		}
	}

	public boolean isRunning() {
		return running;
	}

}
