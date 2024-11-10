package maze;

import java.awt.*;

public class PinkRoom extends Room {
	public PinkRoom(int roomNum) {
		super(roomNum);
	}

	@Override
	public Color getColor() {
		return Color.PINK;
	}
}
