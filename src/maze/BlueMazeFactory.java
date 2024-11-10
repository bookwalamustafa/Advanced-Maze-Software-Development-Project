package maze;

public class BlueMazeFactory extends MazeFactory {

	@Override
	public Wall makeWall() {
		return new BlueWall();
	}

	@Override
	public Room makeRoom(int roomNum) {
		return new GreenRoom(roomNum);
	}

	@Override
	public Door makeDoor(Room r1, Room r2) {
		return new BrownDoor(r1, r2);
	}
}
