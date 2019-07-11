package CHAlgorithme;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import InterfaceSimulation.Camera;

public class Visualization_Result extends JFrame {
	Visualization panel;

	public Visualization_Result(LinkedList<Camera> Liste_Camera, Map<Integer, LinkedList<Integer>> Clusters) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		panel = new Visualization(Liste_Camera, Clusters);
		content.add(panel, BorderLayout.CENTER);
		setTitle("Visualization of the Result");
		pack();
		setLocationRelativeTo(null);
	}
}

class Visualization extends JPanel {
	LinkedList<double[][]> Liste_Camera_Tabel;
	LinkedList<Camera> Liste_Camera;
	Map<Integer, LinkedList<Integer>> Clusters;
	LinkedList<double[]> subject;

	public Visualization(LinkedList<Camera> Liste_Camera, Map<Integer, LinkedList<Integer>> Clusters) {
		setPreferredSize(new Dimension(1380, 735));
		this.Liste_Camera = Liste_Camera;
		this.Clusters = Clusters;
		Convert(this.Liste_Camera);
	}

	public void Convert(LinkedList<Camera> Liste_Camera) {
		Liste_Camera_Tabel = new LinkedList<>();
		for (int i = 0; i < Liste_Camera.size(); i++) {
			Camera camera = Liste_Camera.get(i);
			double camera_tabel[][] = new double[3][2];
			for (int j = 0; j < camera_tabel.length; j++) {
				camera_tabel[j][0] = camera.getX()[j];
				camera_tabel[j][1] = camera.getY()[j];
			}
			Liste_Camera_Tabel.add(camera_tabel);
		}
	}

	public int ID_Cluster_of_Camera(int indice) {
		int ID_Camera = Liste_Camera.get(indice).getID();
		int ID_Coleur = 0;
		boolean Tr = false;
		Iterator iterator = Clusters.entrySet().iterator();
		while (iterator.hasNext() && Tr == false) {
			Map.Entry mapentry = (Map.Entry) iterator.next();
			LinkedList<Integer> cluster_tempo = (LinkedList<Integer>) mapentry.getValue();
			if (cluster_tempo.contains(ID_Camera)) {
				ID_Coleur = (int) mapentry.getKey();
				Tr = true;
			}
		}
		return ID_Coleur;
	}

	public int Randome(int Max, int Min) {
		Random r = new Random();
		int valeur = Min + (r.nextInt(Max - Min));
		return valeur;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(80, 60);
		g2.setStroke(new BasicStroke(3));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		LinkedList<Color> Liste_Color = new LinkedList<>();

		/** Define the colors of the clusters **/
		for (int i = 0; i < Clusters.size(); i++) {
			Color C = new Color(Randome(255, 0), Randome(255, 0), Randome(255, 0));
			Liste_Color.add(C);
		}

		for (int i = 0; i < Liste_Camera_Tabel.size(); i++) {
			subject = new LinkedList<>(Arrays.asList(Liste_Camera_Tabel.get(i)));
			drawPolygon(g2, subject, Liste_Color.get(ID_Cluster_of_Camera(i)));
		}
	}

	private void drawPolygon(Graphics2D g2, List<double[]> points, Color color) {
		g2.setColor(color);
		int len = points.size();
		Line2D line = new Line2D.Double();
		for (int i = 0; i < len; i++) {
			double[] p1 = points.get(i);
			double[] p2 = points.get((i + 1) % len);
			line.setLine(p1[0], p1[1], p2[0], p2[1]);
			g2.draw(line);
		}
	}
}