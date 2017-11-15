package artificialgrammarlearning.logic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.HashMap;

public class Result {

	private String participantName;
	private HashMap<String, Integer> frequenciesOfCardPresentations;
	private Date dateBegin;
	private Date dateEnd;
	private String pathToResultFile;
	private String result;

	public Result(String participantName, String pathToResultsFolder) {

		this.participantName = participantName;
		this.pathToResultFile = pathToResultsFolder + "\\" + participantName
				+ ".txt";
		frequenciesOfCardPresentations = new HashMap<String, Integer>();

	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date actualDate) {
		this.dateBegin = actualDate;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getResult() {
		return result;
	}

	public void addPresentation(String card) {
		if (frequenciesOfCardPresentations.containsKey(card)) {
			int frequency = frequenciesOfCardPresentations.get(card);
			frequency++;
			frequenciesOfCardPresentations.put(card, frequency);
		} else {
			frequenciesOfCardPresentations.put(card, 1);
		}
	}

	private void prepareResults() {

		result = "VP-Code: " + participantName;
		result = result + "\n\nBeginn: " + dateBegin.toString();
		result = result + "\nEnde: " + dateEnd.toString();
		
		// Berechnung der Dauer
		long duration = dateEnd.getTime() - dateBegin.getTime();
		result = result + "\nGesamtdauer (ms): " + duration;

		result = result + "\n\nKarte\tHäufigkeit";

		int totalFrequency = 0;

		for (String key : frequenciesOfCardPresentations.keySet()) {
			result = result + "\n" + key + "\t"
					+ frequenciesOfCardPresentations.get(key);

			totalFrequency = totalFrequency
					+ frequenciesOfCardPresentations.get(key);
		}

		result = result + "\n\nInsgesamt: " + totalFrequency + "\n\n";

	}

	public void writeResultsToFile() {

		prepareResults();

		Path resultFile = Paths.get(pathToResultFile);
		if (!resultFile.toFile().exists()) {
			resultFile.toFile().getParentFile().mkdirs();
		}

		try {
			BufferedWriter writer = Files.newBufferedWriter(resultFile,
					Charset.defaultCharset(), StandardOpenOption.CREATE,
					StandardOpenOption.APPEND);
			writer.write(result);
			writer.flush();
			System.out.println(result);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
