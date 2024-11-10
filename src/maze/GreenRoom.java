package maze;

import java.awt.*;

public class GreenRoom extends Room {
	public GreenRoom(int roomNum) {
		super(roomNum);
	}

	@Override
	public Color getColor() {
		return Color.GREEN;
	}
}
