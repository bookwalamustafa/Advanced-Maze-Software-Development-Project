package maze;

import java.util.Scanner;

import maze.ui.MazeViewer;

public class MazeGameDriver {

	public static final String filePath = "/Users/mustafabookwala/Desktop/Lab5_Part2/large.maze";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Choose maze type: red or blue");
		String choice = scanner.nextLine().toLowerCase();

		MazeFactory factory;

		switch (choice) {
		case "red":
			factory = new RedMazeFactory();
			break;
		case "blue":
			factory = new BlueMazeFactory();
			break;
		default:
			System.out.println("Invalid choice. Please restart the program and choose either 'red' or 'blue'.");
			scanner.close();
			return;
		}

		MazeGameDriver driver = new MazeGameDriver();

		Maze maze = driver.loadMaze(filePath, factory);
		MazeViewer viewer = new MazeViewer(maze);
		viewer.run();

		scanner.close();
	}

	public Maze loadMaze(String path, MazeFactory factory) {
		return factory.loadMaze(path);
	}

}