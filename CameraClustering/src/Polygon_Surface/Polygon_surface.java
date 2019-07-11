package Polygon_Surface;

import java.util.LinkedList;

public class Polygon_surface {

	public static void main(String[] args) {
		double total = 0;
		LinkedList<double[]> points_plygone = new LinkedList<>();

		double xA = 0;
		double yA = 0;
		double A[] = new double[2];
		A[0] = xA;
		A[1] = yA;
		points_plygone.add(A);

		double xB = 8;
		double yB = -4;
		double B[] = new double[2];
		B[0] = xB;
		B[1] = yB;
		points_plygone.add(B);

		double xC = 12;
		double yC = 0;
		double C[] = new double[2];
		C[0] = xC;
		C[1] = yC;
		points_plygone.add(C);

		double xD = 9;
		double yD = 6;
		double D[] = new double[2];
		D[0] = xD;
		D[1] = yD;
		points_plygone.add(D);

		Calcule_Surface(points_plygone);
	}

	public static void Calcule_Surface(LinkedList<double[]> points_plygone) {
		double somme = 0;
		int siz = points_plygone.size();

		for (int i = 0; i < siz; i++) {
			somme = somme + (points_plygone.get(i)[0] * points_plygone.get((i + 1) % siz)[1])
					- (points_plygone.get(i)[0] * points_plygone.get((i - 1 + siz) % siz)[1]);
		}
		System.out.println("2 " + Math.abs(0.5 * somme));
	}
}
