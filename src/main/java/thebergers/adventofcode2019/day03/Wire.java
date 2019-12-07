package thebergers.adventofcode2019.day03;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Wire {

	private final UUID id;
	
	private final List<Point> points;
	
	
	public Wire(String pointStr) {
		this.id = UUID.randomUUID();
		this.points = processJumps(pointStr);
	}
	
	public UUID getId() {
		return id;
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	private List<Point> processJumps(String pointStr) {
		Point start = new Point(id, 0,0);
		List<Point> allPoints = new LinkedList<>();
		allPoints.add(start);
		List<String> jumps = Arrays.asList(pointStr.split(","));
		for (String jump: jumps) {
			allPoints.addAll(generatePoints(start, jump));
			start = allPoints.get(allPoints.size() - 1);
		}
		return allPoints;
	}

	enum Direction {
		DOWN,
		LEFT,
		RIGHT,
		UP;
		
		public static Direction getInstance(String direction) {
			if (null == direction) {
				throw new IllegalArgumentException();
			} else if (direction.equals("D")) {
				return DOWN;
			} else if (direction.equals("L")) {
				return LEFT;
			} else if (direction.equals("R")) {
				return RIGHT;
			} else if (direction.equals("U")) {
				return UP;
			}
			throw new IllegalArgumentException();
		}
	}
	private List<Point> generatePoints(Point start, String jump) {
		Move move = getMove(jump, start);
		return move.calculateJump();
	}
	
	Move getMove(String jump, Point start) {
		Direction direction = Direction.getInstance(jump.substring(0, 1));
		Integer distance = Integer.parseInt(jump.substring(1));
		switch (direction) {
		case DOWN:
			return new MoveDown(start, distance);
		case LEFT:
			return new MoveLeft(start, distance);
		case RIGHT:
			return new MoveRight(start, distance);
		case UP:
			return new MoveUp(start, distance);
		}
		throw new IllegalArgumentException();
	}
	
	abstract class Move {
		
		protected final Point start;
		
		private final Direction direction;
		
		private final Integer distance;
		
		public Move(Direction direction, Point start, Integer distance) {
			this.direction = direction;
			this.start = start;
			this.distance = distance;
		}
		
		public Integer getDistance() {
			return distance;
		}
		
		public Direction getDirection() {
			return direction;
		}
		
		abstract Integer calculateX(Integer x);
		
		abstract Integer calculateY(Integer y);
		
		public List<Point> calculateJump() {
			List<Point> jumpPoints = new LinkedList<>();
			for (Integer i = 1; i <= getDistance(); i++) {
				jumpPoints.add(new Point(id, calculateX(i), calculateY(i)));
			}
			return jumpPoints;
		}
	}
	
	class MoveDown extends Move {

		public MoveDown(Point start, Integer distance) {
			super(Direction.DOWN, start, distance);
		}

		@Override
		Integer calculateX(Integer x) {
			return start.getX();
		}

		@Override
		Integer calculateY(Integer y) {
			return start.getY() - y;
		}

	}
	
	class MoveLeft extends Move {

		public MoveLeft(Point start, Integer distance) {
			super(Direction.LEFT, start, distance);
		}

		@Override
		Integer calculateX(Integer x) {
			return start.getX() - x;
		}

		@Override
		Integer calculateY(Integer y) {
			return start.getY();
		}

	}
	
	class MoveRight extends Move {

		public MoveRight(Point start, Integer distance) {
			super(Direction.RIGHT, start, distance);
		}

		@Override
		Integer calculateX(Integer x) {
			return start.getX() + x;
		}

		@Override
		Integer calculateY(Integer y) {
			return start.getY();
		}

	}
	
	class MoveUp extends Move {

		public MoveUp(Point start, Integer distance) {
			super(Direction.UP, start, distance);
		}

		@Override
		Integer calculateX(Integer x) {
			return start.getX();
		}

		@Override
		Integer calculateY(Integer y) {
			return start.getY() + y;
		}
	}
}
