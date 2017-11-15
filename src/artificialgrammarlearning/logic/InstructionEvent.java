package artificialgrammarlearning.logic;

public class InstructionEvent extends Event {

	String instruction;

	public InstructionEvent(String name, String instruction) {

		super(name, EventType.INSTRUCTION);
		this.instruction = instruction;

	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public void printInstructionOnConsole() {

	}

}
