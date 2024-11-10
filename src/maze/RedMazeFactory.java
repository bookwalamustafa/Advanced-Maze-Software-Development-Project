package maze;

public class RedMazeFactory extends MazeFactory {

	@Override
	public Wall makeWall() {
		return new RedWall();
	}

	@Override
	public Room makeRoom(int roomNum) {
		return new PinkRoom(roomNum);
	}

	@Override
	public Door makeDoor(Room r1, Room r2) {
		return new Door(r1, r2);
	}
}
