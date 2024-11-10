/*
 * SimpleMazeGame.java
 * Copyright (c) 2008, Drexel University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Drexel University nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DREXEL UNIVERSITY ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DREXEL UNIVERSITY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import maze.ui.MazeViewer;

/**
 * 
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class SimpleMazeGame {
	/**
	 * Creates a small maze.
	 */
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

	public static Maze loadMaze(final String path) throws ScriptException {
		Maze maze = new Maze();

		// Code for reading input from file taken from StudentRepo -> VehicleCityVersion
		// > CodeProject > src > utils > FileUtils.java
		List<String> lines = new ArrayList<>();
		BufferedReader br = null;
		try {
			br = Files.newBufferedReader(Paths.get(path));
			String line = br.readLine();
			while (line != null) {
				lines.add(line);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.err.println("Failed to finalize readLineByLine");
				}
			}
		}

		List<Room> rooms = new ArrayList<>();
		List<Door> doors = new ArrayList<>();
		String[] directions = { "North", "South", "East", "West" };

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.startsWith("room")) {
				String[] splitLine = line.split(" ");
				int roomNumber = Integer.parseInt(splitLine[1]);
				Room room = new Room(roomNumber);
				rooms.add(room);
				maze.addRoom(room);
			}
		}

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.startsWith("door")) {
				String[] splitLine = line.split(" ");
				int room1 = Integer.parseInt(splitLine[2]);
				int room2 = Integer.parseInt(splitLine[3]);
				Door door = new Door(rooms.get(room1), rooms.get(room2));
				doors.add(door);
			}
		}

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.startsWith("room")) {
				String[] splitLine = line.split(" ");
				int roomNumber = Integer.parseInt(splitLine[1]);
				Room room = rooms.get(roomNumber);
				for (int j = 2; j < 6; j++) {
					String ele = splitLine[j];
					if (ele.equals("wall")) {
						room.setSide(Direction.valueOf(directions[j - 2]), new Wall());
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

	public static void main(String[] args) throws ScriptException {
		// Maze maze = createMaze();
		Maze maze = loadMaze("/Users/mustafabookwala/Desktop/Drexel/Pre-Junior/Fall 2024/SE 310/Week 2/HW1/large.maze");
		MazeViewer viewer = new MazeViewer(maze);
		viewer.run();
	}
}