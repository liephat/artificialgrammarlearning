package artificialgrammarlearning.logic;

import java.util.HashMap;

public abstract class Block {

	private BlockType type;

	private HashMap<Integer, Block> blocks;
	private HashMap<Integer, Event> elements;

	// If block has subblocks/subelements, this ID refers to the actual
	// block/element
	private int actualBlockId;
	private int actualElementId;

	public Block() {

		// Default type: abstract
		this.type = BlockType.ABSTRACT;

		// Set initial block and element ID to 0
		this.actualBlockId = 0;
		this.actualElementId = 0;

		blocks = new HashMap<Integer, Block>();
		elements = new HashMap<Integer, Event>();

	}

	public Block(BlockType type) {

		this();
		this.type = type;

	}

	protected Event getNextEvent() throws TestCreationException {

		if (blocks.size() == 0 && elements.size() == 0) {
			throw new TestCreationException(
					"There is no next element because the block is empty.");
			// TODO: Better return null?
		}

		if (this.type == BlockType.ABSTRACT) {

			// Check if actual block has a next element: If not go to next block
			if (!blocks.get(actualBlockId).nextBlock()
					&& !blocks.get(actualBlockId).nextElement() && nextBlock()) {
				actualBlockId++;

				return blocks.get(actualBlockId).getNextEvent();

			} else if (!blocks.get(actualBlockId).nextBlock()
					&& !blocks.get(actualBlockId).nextElement() && !nextBlock()) {
				return null;
			} else {
				return blocks.get(actualBlockId).getNextEvent();
			}

		} else {
			if (nextElement()) {
				actualElementId++;
				Event element = elements.get(actualElementId);
				return element;
			} else {
				return null;
			}

		}

	}

	protected Event getPreviousElement() throws TestCreationException {

		if (blocks.size() == 0 && elements.size() == 0) {
			throw new TestCreationException(
					"There is no previous element because the block is empty.");
			// TODO: Better return null?
		}

		if (this.type == BlockType.ABSTRACT) {

			// Check if actual block has a previous block or element: If not go
			// to next block
			if (!blocks.get(actualBlockId).previousBlock()
					&& !blocks.get(actualBlockId).previousElement()
					&& previousBlock()) {
				actualBlockId--;

				return blocks.get(actualBlockId).getPreviousElement();

			} else if (!blocks.get(actualBlockId).previousBlock()
					&& !blocks.get(actualBlockId).previousElement()
					&& !previousBlock()) {
				return null;
			} else {
				return blocks.get(actualBlockId).getPreviousElement();
			}

		} else {
			if (previousElement()) {
				actualElementId--;
				Event element = elements.get(actualElementId);
				return element;
			} else {
				return null;
			}

		}
	}

	public void addBlock(Block block) throws TestCreationException {

		if (this.type == BlockType.ABSTRACT) {
			int nextBlockId = blocks.size() + 1;
			blocks.put((nextBlockId), block);
		} else {
			throw new TestCreationException(
					"It's not possible to add a block because the type is not abstract.");
		}

	}

	public void removeBlock(int id) throws TestCreationException {

		blocks.remove(id);

		while (id <= blocks.size()) {

			blocks.put((id), blocks.get(id + 1));
			blocks.remove(id + 1);

			id++;
		}

	}

	public void addElement(Event element) throws TestCreationException {

		if (this.type == BlockType.ELEMENTAL) {
			int nextElementId = elements.size() + 1;
			elements.put((nextElementId), element);
		} else {
			throw new TestCreationException(
					"It's not possible to add an element because the type is not elemental.");
		}

	}

	public boolean nextElement() {
		if (elements.size() <= actualElementId) {
			return false;
		} else {
			return true;
		}
	}

	public boolean nextBlock() {
		if (blocks.size() < actualBlockId) {
			return false;
		} else {
			return true;
		}
	}

	public boolean previousElement() {
		if (actualElementId < 1) {
			return false;
		} else {
			return true;
		}
	}

	public boolean previousBlock() {
		if (actualBlockId <= 1) {
			return false;
		} else {
			return true;
		}
	}

	public BlockType getType() {
		return type;
	}

	public void setType(BlockType type) {
		this.type = type;
	}

}
