package InterfaceSimulation;

import java.awt.Color;
import java.awt.Graphics;

public class Camera {
	int ID_camera;
	int X[];
	int Y[];

	public Camera(int ID_camera, int X[], int Y[]) {
		this.ID_camera = ID_camera;
		this.X = X;
		this.Y = Y;
	}

	public void designe(Graphics g) {
		g.setColor(Color.red);
		g.drawPolygon(Y, X, 3);
	}

	public int[] getX() {
		return X;
	}

	public int[] getY() {
		return Y;
	}

	public int getID() {
		return ID_camera;
	}
}
