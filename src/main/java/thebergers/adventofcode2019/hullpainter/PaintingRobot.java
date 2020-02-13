package thebergers.adventofcode2019.hullpainter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerInterface;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerResult;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerRunner;

public class PaintingRobot implements IntcodeComputerRunner, PropertyChangeListener {

	private static final Logger LOG = LoggerFactory.getLogger(PaintingRobot.class);
	private final Hull hull;

	private final IntcodeComputerInterface computer;

	private final Colour startingColour;
	
	private BlockingQueue<Long> results;

	private List<Long> allResults;

	private boolean terminate;

	private Panel currentPanel;

	private FacingDirection facing;

	private ExecutorService pool;

	public PaintingRobot(IntcodeComputerInterface computer) {
		this.hull = new Hull();
		this.computer = computer;
		this.terminate = false;
		this.startingColour = Colour.BLACK;
		initialise();
	}

	public PaintingRobot(IntcodeComputerInterface computer, Colour startingColour) {
		this.hull = new Hull();
		this.computer = computer;
		this.terminate = false;
		this.startingColour = startingColour;
		initialise();
	}

	@Override
	public void initialise() {
		results = new LinkedBlockingQueue<>();
		currentPanel = hull.addPanel(0, 0, startingColour);
		facing = FacingDirection.UP;
		computer.addPropertyChangeListener(this);
		allResults = new ArrayList<>();
	}

	@Override
	public void start() {
		pool = Executors.newSingleThreadExecutor();
		pool.execute(computer);
	}

	@Override
	public IntcodeComputerResult doProcessing() throws InterruptedException, ExecutionException {

		computer.addInput(currentPanel.getColour().getCode());
		Integer counter = 0;
		while (true) {
			LOG.info("Waiting for output...");
			Long result = results.take();
			allResults.add(result);
			counter++;
			LOG.info("doProcessing: Counter={}, Received value {}", counter, result);
			Long resultAtIndex = result;
			InstructionType action = InstructionType.getInstructionType(counter);
			if (action.equals(InstructionType.GET_COLOUR_TO_PAINT)) {
				Colour colourToUse = Colour.getInstance(resultAtIndex);
				LOG.info("Painting panel {} as {}", currentPanel, colourToUse.getDescription());
				hull.paintPanel(currentPanel, colourToUse);
			} else if (action.equals(InstructionType.MOVE)) {
				TurnDirection direction = TurnDirection.getInstance(resultAtIndex);
				LOG.info("Turning to the {}", direction.getDirection());
				navigate(direction);
				LOG.info("Moved to {}", currentPanel);
				computer.addInput(currentPanel.getColour().getCode());
			}
			if (terminate) {
				break;
			}
		}
		pool.shutdown();
		return new IntcodeComputerResult(1, computer.getName(),
			allResults.toString(), allResults, true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		IntcodeComputerResult result = (IntcodeComputerResult)evt.getNewValue();
		LOG.info("propertyChange: Received result {}", result.getAllOutput());
		if (result.isTerminated()) {
			terminate = true;
			LOG.info("Received Termination instruction...");
		}
		List<Long> resultsList = result.getOutputAsList();
		Long value = resultsList.get(resultsList.size() -1);
		results.add(value);
		LOG.info("Added value {} to queue...", value);

	}

	public long getPaintedPanels() {
		return hull.countPaintedPanels();
	}

	private void navigate(TurnDirection turnDirection) {
		facing = makeTurn(turnDirection);
		currentPanel = move(facing);
	}

	private FacingDirection makeTurn(TurnDirection turnDirection) {
		switch (facing) {
			case DOWN:
				switch (turnDirection) {
					case LEFT:
						return FacingDirection.RIGHT;
					case RIGHT:
						return FacingDirection.LEFT;
				}
			case LEFT:
				switch (turnDirection) {
					case LEFT:
						return FacingDirection.DOWN;
					case RIGHT:
						return FacingDirection.UP;
				}
			case RIGHT:
				switch (turnDirection) {
					case LEFT:
						return FacingDirection.UP;
					case RIGHT:
						return FacingDirection.DOWN;
				}
			case UP:
				switch (turnDirection) {
					case LEFT:
						return FacingDirection.LEFT;
					case RIGHT:
						return FacingDirection.RIGHT;
				}
		}
		throw new IllegalStateException(String.format("FacingDirection: %s, TurnDirection: %s",
			facing, turnDirection));
	}

	private Panel move(FacingDirection facing) {
		int currX = currentPanel.getX();
		int currY = currentPanel.getY();
		int newX = currX;
		int newY = currY;
		switch (facing) {
			case DOWN:
				newY--;
				break;
			case LEFT:
				newX--;
				break;
			case RIGHT:
				newX++;
				break;
			case UP:
				newY++;
				break;
		}
		return hull.goToPanel(newX, newY);
	}

	public Hull getHull() {
		return hull;
	}

	enum InstructionType {
		GET_COLOUR_TO_PAINT,
		MOVE;

		public static InstructionType getInstructionType(int resultSize) {
			if (resultSize % 2 == 1) {
				return GET_COLOUR_TO_PAINT;
			}
			return MOVE;
		}
	}

	enum FacingDirection {
		DOWN("down"),
		LEFT("left"),
		RIGHT("right"),
		UP("up");

		private String direction;

		FacingDirection(String direction) {
			this.direction = direction;
		}

		public String getDirection() {
			return direction;
		}
	}

	enum TurnDirection {
		LEFT("Left"),
		RIGHT("Right");

		private String direction;

		TurnDirection(String direction) {
			this.direction = direction;
		}

		public String getDirection() {
			return direction;
		}

		public static TurnDirection getInstance(Long direction) {
			if (direction.equals(0L)) {
				return LEFT;
			}
			if (direction.equals(1L)) {
				return RIGHT;
			}
			return null;
		}
	}
}
