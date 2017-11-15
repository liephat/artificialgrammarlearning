package artificialgrammarlearning.logic.tests;

import java.util.ArrayList;

import artificialgrammarlearning.logic.ArtificialGrammarLearningTest;
import artificialgrammarlearning.logic.TestCreationException;
import artificialgrammarlearning.logic.TextResponseEvent;

public class TestingResultWriter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		Result testResult = new Result("Mike", "results");
//		
//		testResult.setDateBegin(new Date());
//		
//		testResult.setDateEnd(new Date());
//		
//		testResult.addPresentation("Card_01");
//		testResult.addPresentation("Card_01");
//		testResult.addPresentation("Card_01");
//		testResult.addPresentation("Card_01");
//		
//		testResult.addPresentation("Card_02");
//		testResult.addPresentation("Card_02");
//		
//		testResult.addPresentation("Card_03");
//		testResult.addPresentation("Card_03");
//		testResult.addPresentation("Card_03");
//		
//		testResult.writeResultsToFile();
		
		
		// Vorbereitung fürs Testen
		
		ArrayList<String> response_one = new ArrayList<String>();
		response_one.add("TSSXXVPS");
		response_one.add("TXXTTTVV");
		response_one.add("PVPXVPS");
		response_one.add("TXS");
		
		ArrayList<String> response_two = new ArrayList<String>();
		response_two.add("TSSSXXVV");
		response_two.add("PTTTVPS");
		response_two.add("PTVPXVV");
		response_two.add("PVV");
		
		ArrayList<String> response_three = new ArrayList<String>();
		response_three.add("PVPXTVPS");
		response_three.add("TSXXTVV");
		response_three.add("PVPXVV");
		response_three.add("PTVPS");
		
		ArrayList<String> response_four = new ArrayList<String>();
		response_four.add("TSXXTVPS");
		response_four.add("TSSXXVV");
		response_four.add("TSSSXS");
		response_four.add("PTTVV");
		
		ArrayList<String> response_five = new ArrayList<String>();
		response_five.add("TXXVPXVV");
		response_five.add("TXXTVPS");
		response_five.add("TXTVPS");
		response_five.add("TSXS");
		
		try {
			ArtificialGrammarLearningTest test = new ArtificialGrammarLearningTest(
					"stimuli//strings_regular.txt", "Klaus", "results");
			test.start();
			
			System.out.println(test.next());
			System.out.println(test.next());
			
			TextResponseEvent response = (TextResponseEvent) test.next();
			response.setResponse(response_one);
			
			System.out.println(test.next());
			
			response = (TextResponseEvent) test.next();
			response.setResponse(response_two);
			
			test.next();
			
			response = (TextResponseEvent) test.next();
			response.setResponse(response_three);
			
			test.next();
			
			response = (TextResponseEvent) test.next();
			response.setResponse(response_four);
			
			test.next();
			
			response = (TextResponseEvent) test.next();
			response.setResponse(response_five);
			
			test.next();

			test.stop();
			
			System.out.println(test.isRunning());
		} catch (TestCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
