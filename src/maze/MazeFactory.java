package maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MazeFactory {

	public static Maze createMaze() {

		Maze maze = new Maze();
		Room r0 = new Room(0);
		Room r1 = new Room(1);
		Door d0 = new Door(r0, r1);

		r0.setSide(Direction.North, new Wall());
		r0.setSide(Direction.South, new Wall());
		r0.setSide(Direction.East, d0);
		r0.setSide(Direction.West, new Wall());

		r1.setSide(Direction.North, new Wall());
		r1.setSide(Direction.South, new Wall());
		r1.setSide(Direction.East, new Wall());
		r1.setSide(Direction.West, d0);

		maze.addRoom(r0);
		maze.addRoom(r1);

		maze.setCurrentRoom(r0);

		// System.out.println("The maze does not have any rooms yet!");
		return maze;

	}

	public Wall makeWall() {
		return new Wall();
	}

	public Room makeRoom(int roomNum) {
		return new Room(roomNum);
	}

	public Door makeDoor(Room r1, Room r2) {
		return new Door(r1, r2);
	}

	public Maze loadMaze(String path) {
		Maze maze = new Maze();

		List<String> lines = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			System.err.println("Error reading the maze file: " + e.getMessage());
			return maze;
		}

		List<Room> rooms = new ArrayList<>();
		List<Door> doors = new ArrayList<>();
		String[] directions = { "North", "South", "East", "West" };

		for (String line : lines) {
			if (line.startsWith("room")) {
				String[] splitLine = line.split(" ");
				int roomNumber = Integer.parseInt(splitLine[1]);
				Room room = makeRoom(roomNumber);
				rooms.add(room);
				maze.addRoom(room);
			}
		}

		for (String line : lines) {
			if (line.startsWith("door")) {
				String[] splitLine = line.split(" ");
				int room1 = Integer.parseInt(splitLine[2]);
				int room2 = Integer.parseInt(splitLine[3]);
				Door door = makeDoor(rooms.get(room1), rooms.get(room2));
				doors.add(door);
			}
		}

		for (String line : lines) {
			if (line.startsWith("room")) {
				String[] splitLine = line.split(" ");
				int roomNumber = Integer.parseInt(splitLine[1]);
				Room room = rooms.get(roomNumber);
				for (int j = 2; j < 6; j++) {
					String ele = splitLine[j];
					if (ele.equals("wall")) {
						room.setSide(Direction.valueOf(directions[j - 2]), makeWall());
					} else if (ele.matches("\\d+")) {
						int adjacentRoomIndex = Integer.parseInt(ele);
						room.setSide(Direction.valueOf(directions[j - 2]), rooms.get(adjacentRoomIndex));
					} else {
						int doorIndex = Integer.parseInt(ele.substring(1));
						room.setSide(Direction.valueOf(directions[j - 2]), doors.get(doorIndex));
					}
				}
			}
		}

		maze.setCurrentRoom(rooms.get(0));
		return maze;
	}
}
