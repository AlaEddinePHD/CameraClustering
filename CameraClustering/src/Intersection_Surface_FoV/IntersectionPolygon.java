package Intersection_Surface_FoV;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

import InterfaceSimulation.Camera;

public class IntersectionPolygon extends JFrame {

	IntersectionpolygonPanel panel;

	public IntersectionPolygon(LinkedList<Camera> Liste_Camera) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		panel = new IntersectionpolygonPanel(Liste_Camera);
		content.add(panel, BorderLayout.CENTER);
		setTitle("IntersectionPolygon");
		pack();
		setLocationRelativeTo(null);
	}
}

class IntersectionpolygonPanel extends JPanel {
	LinkedList<double[]> subject, clipper, intersection_Polygon_Summits;
	LinkedList<Camera> Liste_Camera;
	LinkedList<double[][]> Liste_Camera_Tabel;
	private int EquationCamera[][] = new int[3][2];
	private int Cam_tempo[][] = new int[3][2];
	LinkedList<LinkedList<double[]>> Liste_Arcs_Scheduling;
	LinkedList<LinkedList<double[]>> Liste_Arcs;
	LinkedList<double[]> points_plygone;
	LinkedList<LinkedList<double[]>> Liste_polygons;
	double matrice_surface[][];

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

	public double Calculate_Area(LinkedList<double[]> points_plygone) {
		double somme = 0;
		int siz = points_plygone.size();

		for (int i = 0; i < siz; i++) {
			somme = somme + (points_plygone.get(i)[0] * points_plygone.get((i + 1) % siz)[1])
					- (points_plygone.get(i)[0] * points_plygone.get((i - 1 + siz) % siz)[1]);
		}
		return Math.abs(0.5 * somme);
	}

	public IntersectionpolygonPanel(LinkedList<Camera> Liste_Camera) {
		this.Liste_Camera = Liste_Camera;
		Convert(this.Liste_Camera);
		setPreferredSize(new Dimension(1366, 735));

		Liste_polygons = new LinkedList<>();
		matrice_surface = new double[Liste_Camera_Tabel.size()][Liste_Camera_Tabel.size()];
		for (int i = 0; i < Liste_Camera_Tabel.size(); i++) {
			for (int j = i + 1; j < Liste_Camera_Tabel.size(); j++) {
				double[][] subjPoints = Liste_Camera_Tabel.get(i);
				double[][] clipPoints = Liste_Camera_Tabel.get((j) % Liste_Camera_Tabel.size());
				subject = new LinkedList<>(Arrays.asList(subjPoints));
				clipper = new LinkedList<>(Arrays.asList(clipPoints));
				LinkedList<double[]> polygon_result = clipPolygon();

				Liste_polygons.add(polygon_result);
				double Surface = Calculate_Area(polygon_result);

				/**
				 * Put the surfaces in a triangular matrix for Clustering
				 * Algorithm
				 **/
				matrice_surface[Liste_Camera.get(i).getID()][Liste_Camera.get((j) % Liste_Camera_Tabel.size())
						.getID()] = Surface;
				matrice_surface[Liste_Camera.get((j) % Liste_Camera_Tabel.size()).getID()][Liste_Camera.get(i)
						.getID()] = Surface;
			}
		}

		/** Surface matrix display **/
		/*** Save the Surface Matrix to a TXT file ***/
		try {
			PrintWriter matrice_text = new PrintWriter(new FileWriter("Surface Matrix.txt", true));
			for (int i = 0; i < matrice_surface.length; i++) {
				for (int j = 0; j < matrice_surface.length; j++) {
					matrice_text.print(matrice_surface[i][j] + " ");
				}
				matrice_text.print("\n");
			}
			matrice_text.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<double[]> inTraingle(LinkedList<double[]> clipper1, LinkedList<double[]> subject1,
			LinkedList<double[]> result) {
		int X[] = new int[3];
		int Y[] = new int[3];

		X[0] = (int) clipper1.get(0)[0];
		Y[0] = (int) clipper1.get(0)[1];
		X[1] = (int) clipper1.get(1)[0];
		Y[1] = (int) clipper1.get(1)[1];
		X[2] = (int) clipper1.get(2)[0];
		Y[2] = (int) clipper1.get(2)[1];

		EquationCamera = SetCordCamera(X, Y, EquationCamera);
		List<double[]> input = subject1;
		for (int j = 0; j < input.size(); j++) {
			double[] Q = input.get(j);
			Cam_tempo = SetCordonnerObjet(Cam_tempo, (int) Q[0], (int) Q[1]);
			if (Resolution(Cam_tempo) == true) {
				result.add(Q);
			}
		}
		return result;
	}

	public double[] lineLineIntersection(double[] A, double[] B, double[] C, double[] D) {
		double point_intersection[] = null;
		// Line AB represented as a1x + b1y = c1
		double a1 = B[1] - A[1];
		double b1 = A[0] - B[0];
		double c1 = a1 * (A[0]) + b1 * (A[1]);

		// Line CD represented as a2x + b2y = c2
		double a2 = D[1] - C[1];
		double b2 = C[0] - D[0];
		double c2 = a2 * (C[0]) + b2 * (C[1]);

		double determinant = a1 * b2 - a2 * b1;

		if (determinant == 0) {
			// The lines are parallel. This is simplified
			// by returning a pair of FLT_MAX
		} else {
			point_intersection = new double[2];
			double x = (b2 * c1 - b1 * c2) / determinant;
			double y = (a1 * c2 - a2 * c1) / determinant;

			point_intersection[0] = x;
			point_intersection[1] = y;

		}

		if (point_intersection == null
				|| (point_intersection[0] == Double.MAX_VALUE && point_intersection[1] == Double.MAX_VALUE)) {
			return null;
		} else {
			if ((A[0] <= point_intersection[0] && point_intersection[0] <= B[0])
					&& (C[0] <= point_intersection[0] && point_intersection[0] <= D[0])) {
				return point_intersection;
			} else if ((A[0] <= point_intersection[0] && point_intersection[0] <= B[0])
					&& (C[0] >= point_intersection[0] && point_intersection[0] >= D[0])) {
				return point_intersection;
			} else if ((A[0] >= point_intersection[0] && point_intersection[0] >= B[0])
					&& (C[0] >= point_intersection[0] && point_intersection[0] >= D[0])) {
				return point_intersection;
			} else if ((A[0] >= point_intersection[0] && point_intersection[0] >= B[0])
					&& (C[0] <= point_intersection[0] && point_intersection[0] <= D[0])) {
				return point_intersection;
			} else {
				return null;
			}
		}
	}

	/** Cette methode retourne la liste des sommets de polygone ordonnée **/
	private LinkedList<double[]> clipPolygon() {
		intersection_Polygon_Summits = new LinkedList<>();

		/** Extraction of the points inside the triangles **/
		intersection_Polygon_Summits = (LinkedList<double[]>) inTraingle(subject, clipper,
				intersection_Polygon_Summits);
		intersection_Polygon_Summits = (LinkedList<double[]>) inTraingle(clipper, subject,
				intersection_Polygon_Summits);

		/** Extract intersection points **/
		for (int i = 0; i < subject.size(); i++) {
			double[] B = subject.get((i + 1) % subject.size());
			double[] A = subject.get(i);
			for (int j = 0; j < clipper.size(); j++) {
				double[] Q = clipper.get((j + 1) % clipper.size());
				double[] P = clipper.get(j);
				double[] point_intersection = lineLineIntersection(A, B, P, Q);
				if (point_intersection != null) {
					point_intersection[0] = RoundPoint(point_intersection[0], 0);
					point_intersection[1] = RoundPoint(point_intersection[1], 0);
					intersection_Polygon_Summits.add(point_intersection);
				}
			}
		}

		/** Find Acceptable Ribs **/
		Liste_Arcs = new LinkedList<>();
		for (int i = 0; i < intersection_Polygon_Summits.size(); i++) {
			double A[] = intersection_Polygon_Summits.get(i);
			for (int j = 0; j < intersection_Polygon_Summits.size(); j++) {
				if (i != j) {
					double B[] = intersection_Polygon_Summits.get(j);
					boolean Accpter = true;
					int k = 0;
					while (k < intersection_Polygon_Summits.size() && Accpter) {
						double C[] = intersection_Polygon_Summits.get(k);
						int l = 0;
						while (l < intersection_Polygon_Summits.size() && Accpter) {
							if (k != l) {
								if ((k != i && j != l) || (k != j && i != l)) {
									double D[] = intersection_Polygon_Summits.get(l);
									double intersection[] = lineLineIntersection(A, B, C, D);
									if (intersection != null) {
										if (Accepte_point(intersection) == false) {
											Accpter = false;

										} // accept
									} // no intersection
								}
							}
							l++;
						}
						k++;
					}
					if (Accpter == true) {
						LinkedList<double[]> Line = new LinkedList<>();
						Line.add(A);
						Line.add(B);

						if (Existe_Inverted_arc(Liste_Arcs, Line) == false) {
							Liste_Arcs.add(Line);
						}
					}
				}
			}
		}

		points_plygone = new LinkedList<>();
		if (Liste_Arcs.isEmpty() == false) {
			Scheduling();
			points_plygone.add(Liste_Arcs_Scheduling.get(0).get(0));
			for (int i = 0; i < Liste_Arcs_Scheduling.size(); i++) {
				LinkedList<double[]> line = Liste_Arcs_Scheduling.get(i);
				if (Existe_point_polygon(points_plygone, line.get(0))) {
					points_plygone.add(line.get(1));
				} else {
					points_plygone.add(line.get(0));
				}
			}
		}
		return points_plygone;
	}

	public boolean Existe_point_polygon(LinkedList<double[]> points_plygone, double[] point) {
		boolean tr = false;
		int i = 0;
		while (i < points_plygone.size() && tr == false) {
			if (Same_point(points_plygone.get(i), point)) {
				tr = true;
			}
			i++;
		}
		return tr;
	}

	public void Scheduling() {
		Liste_Arcs_Scheduling = new LinkedList<>();
		Liste_Arcs_Scheduling.add(Liste_Arcs.get(0));
		Liste_Arcs.remove(0);
		double[] point = Liste_Arcs_Scheduling.getLast().get(1);
		LinkedList<double[]> Line = new LinkedList<>();
		Line = Cherche_Arc(point);

		while (Liste_Arcs.isEmpty() == false) {
			if (Line != null) {
				Liste_Arcs_Scheduling.add(Line);
				point = Liste_Arcs_Scheduling.getLast().get(1);
				Line = Cherche_Arc(point);
			} else {
				point = Liste_Arcs_Scheduling.getLast().get(0);
				Line = Cherche_Arc(point);
			}
		}
	}

	public LinkedList<double[]> Cherche_Arc(double[] point) {
		boolean tr = false;
		int i = 0;
		LinkedList<double[]> lineExacte = null;
		while (i < Liste_Arcs.size() && tr == false) {
			LinkedList<double[]> line = new LinkedList<>();
			line = Liste_Arcs.get(i);
			if (Same_point(line.get(0), point) || Same_point(line.get(1), point)) {
				tr = true;
				lineExacte = line;
				Liste_Arcs.remove(i);
			}
			i++;
		}
		return lineExacte;
	}

	// if the same point return true else return false
	public boolean Same_point(double[] A, double[] B) {
		if (A[0] == B[0] && A[1] == B[1]) {
			return true;
		} else {
			return false;
		}
	}

	// if the arc exists the method return true else return false
	public boolean Existe_Inverted_arc(LinkedList<LinkedList<double[]>> Liste_arcs, LinkedList<double[]> Line) {
		boolean tr = false;
		int i = 0;
		while (i < Liste_arcs.size() && tr == false) {
			LinkedList<double[]> Line_Tempo = Liste_arcs.get(i);
			if (Same_point(Line.get(0), Line_Tempo.get(0)) && Same_point(Line.get(1), Line_Tempo.get(1))) {
				tr = true;
			} else if (Same_point(Line.get(0), Line_Tempo.get(1)) && Same_point(Line.get(1), Line_Tempo.get(0))) {
				tr = true;
			}
			i++;
		}
		return tr;
	}

	public double RoundPoint(double value, int places) {
		double scale = Math.pow(10, places);
		return Math.round(value * scale) / scale;
	}

	public boolean Accepte_point(double[] point) {
		boolean tr = false;
		int i = 0;
		while (i < intersection_Polygon_Summits.size() && tr == false) {
			double A[] = intersection_Polygon_Summits.get(i);
			if (A[0] == point[0] && A[1] == point[1]) {
				tr = true;
			}
			i++;
		}
		return tr;
	}

	public int[][] SetCordCamera(int x[], int y[], int Cord_Cam[][]) {
		Cord_Cam[0][0] = x[1] - x[0];
		Cord_Cam[0][1] = y[1] - y[0];
		Cord_Cam[1][0] = x[2] - x[0];
		Cord_Cam[1][1] = y[2] - y[0];
		Cord_Cam[2][0] = x[0];
		Cord_Cam[2][1] = y[0];
		return Cord_Cam;
	}

	public int[][] SetCordonnerObjet(int Cord_Cam[][], int cordX, int cordY) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				Cord_Cam[i][j] = EquationCamera[i][j];
			}
		}
		Cord_Cam[2][0] = cordX - Cord_Cam[2][0];
		Cord_Cam[2][1] = cordY - Cord_Cam[2][1];
		return Cord_Cam;
	}

	public double[][] Converst(int e1[][]) {
		double[][] M = new double[3][2];
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j < 2; j++) {
				M[i][j] = e1[i][j];
			}
		}
		return M;
	}

	public boolean Resolution(int e1[][]) {
		double temp;
		double[] s = new double[2];
		double[][] e = Converst(e1);
		int a, b;
		for (int k = 0; k < 1; k++) {
			for (a = 1 + k; a < 2; a++) {
				temp = e[k][a];
				for (b = k; b < 3; b++) {
					e[b][a] = e[b][a] * e[k][k] - e[b][k] * temp;
				}
			}
		}
		s[1] = e[2][1] / e[1][1];

		for (int i = 1; i < 2; i++) {
			for (int j = 2; j <= 2; j++) {
				e[2 - i][2 - j] *= s[2 - i];
				e[2][2 - j] -= e[2 - i][2 - j];
				e[2 - i][2 - j] = 0;
			}
			s[2 - (i + 1)] = e[2][2 - (i + 1)] / e[2 - (i + 1)][2 - (i + 1)];
		}
		double som = 0;
		for (int i = 0; i < 2; i++) {
			som = som + s[i];
		}
		if (som > 1 || s[0] < 0 || s[1] < 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(80, 60);
		g2.setStroke(new BasicStroke(3));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < Liste_Camera_Tabel.size(); i++) {
			subject = new LinkedList<>(Arrays.asList(Liste_Camera_Tabel.get(i)));
			drawPolygon(g2, subject, Color.black);
		}
		for (int i = 0; i < Liste_polygons.size(); i++) {
			drawPolygon(g2, Liste_polygons.get(i), new Color(Randome(255, 0), Randome(255, 0), Randome(255, 0)));
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

	public int Randome(int Max, int Min) {
		Random r = new Random();
		int valeur = Min + (r.nextInt(Max - Min));
		return valeur;
	}
}
