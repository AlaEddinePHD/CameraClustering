package CHAlgorithme;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import InterfaceSimulation.Camera;

public class HierarchicalClassification {
	double Intersection = 560;
	double Matrice[][];
	LinkedList<Camera> Liste_Camera;
	Map<Integer, LinkedList<Integer>> Clusters;
	int id_cluster;
	boolean State_Camera[];
	float somme_numbre_cluster = 0;
	int Max_cluster = 0;
	float somme_size;
	float somme_moyenne_size_cluster = 0;
	float max_size_cluster = 0;

	public HierarchicalClassification(double Matrice[][], LinkedList<Camera> Liste_Camera) {
		this.Matrice = Matrice;
		this.Liste_Camera = Liste_Camera;

		Algorithme_CH();

		float moy = (somme_numbre_cluster);
		System.out.println("nombre de cluster moyenn " + moy);
	}

	public void Algorithme_CH() {
		somme_size = 0;
		id_cluster = 0;
		Clusters = new HashMap<>();
		State_Camera = new boolean[Matrice.length];
		for (int i = 0; i < State_Camera.length; i++) {
			State_Camera[i] = true;
		}
		Isolated_Elements();
		LinkedList Result_Max = Max_Element();

		while ((double) Result_Max.get(2) != 0) {
			/**
			 * if the two cameras do not belong to any group, in this case,
			 * these cameras constitute a new cluster
			 **/
			if (State_Camera[(int) Result_Max.get(0)] == false && State_Camera[(int) Result_Max.get(1)] == false) {
				LinkedList<Integer> new_cluster = new LinkedList<>();
				new_cluster.add((int) Result_Max.get(0));
				new_cluster.add((int) Result_Max.get(1));
				Clusters.put(id_cluster, new_cluster);
				id_cluster++;
				State_Camera[(int) Result_Max.get(1)] = true;
				State_Camera[(int) Result_Max.get(0)] = true;
				/**
				 * The camera already belongs to a cluster, in this case, the
				 * other camera will add to the first camera cluster
				 **/
			} else if (State_Camera[(int) Result_Max.get(0)] == true) {
				Iterator iterator = Clusters.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry mapentry = (Map.Entry) iterator.next();
					LinkedList<Integer> cluster_tempo = (LinkedList<Integer>) mapentry.getValue();
					if (cluster_tempo.contains((int) Result_Max.get(0))) {
						cluster_tempo.add((int) Result_Max.get(1));
						Clusters.put((Integer) mapentry.getKey(), cluster_tempo);
						State_Camera[(int) Result_Max.get(1)] = true;
					}
				}
			} else {
				Iterator iterator = Clusters.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry mapentry = (Map.Entry) iterator.next();
					LinkedList<Integer> cluster_tempo = (LinkedList<Integer>) mapentry.getValue();
					if (cluster_tempo.contains((int) Result_Max.get(1))) {
						cluster_tempo.add((int) Result_Max.get(0));
						Clusters.put((Integer) mapentry.getKey(), cluster_tempo);
						State_Camera[(int) Result_Max.get(0)] = true;
					}
				}
			}
			Result_Max = Max_Element();
		}

		System.out.println("Resulting camera clusters " + Clusters);
		somme_numbre_cluster += Clusters.size();
		if (Clusters.size() > Max_cluster) {
			Max_cluster = Clusters.size();
		}
		Iterator iterator = Clusters.entrySet().iterator();
		int max_local = 0;
		while (iterator.hasNext()) {
			Map.Entry mapentry = (Map.Entry) iterator.next();
			LinkedList<Integer> cluster_tempo = (LinkedList<Integer>) mapentry.getValue();
			somme_size += cluster_tempo.size();
			if (cluster_tempo.size() > max_local) {
				max_local = cluster_tempo.size();
			}
		}
		somme_moyenne_size_cluster += (somme_size / Clusters.size());
		if (max_local > max_size_cluster) {
			max_size_cluster = max_local;
		}
		System.out.println("Moyenne de size : " + somme_size / Clusters.size());
		System.out.println("Max size cluster : " + max_size_cluster);

		Visualization_Result visualization_Result = new Visualization_Result(Liste_Camera, Clusters);
	}

	public LinkedList Max_Element() {
		double FoV_Max = Matrice[0][0];
		LinkedList triple_Max = new LinkedList();
		triple_Max.add(0);
		triple_Max.add(0);
		triple_Max.add(FoV_Max);
		for (int i = 0; i < Matrice.length; i++) {
			for (int j = 0; j < Matrice.length; j++) {
				if (State_Camera[j] == false || State_Camera[i] == false) {
					// The two cameras can not have been together before
					if (FoV_Max < Matrice[i][j] && Matrice[i][j] != 0 && Matrice[i][j] > Intersection) {
						FoV_Max = Matrice[i][j];
						triple_Max.clear();
						triple_Max.add(i);
						triple_Max.add(j);
						triple_Max.add(FoV_Max);
					}
				}
			}
		}
		return triple_Max;
	}

	public void Isolated_Elements() {
		for (int i = 0; i < Matrice.length; i++) {
			for (int j = 0; j < Matrice.length; j++) {
				if (Matrice[i][j] != 0 && Matrice[i][j] > Intersection) {
					State_Camera[i] = false;
				}
			}
		}

		for (int i = 0; i < State_Camera.length; i++) {
			if (State_Camera[i] == true) {
				LinkedList<Integer> cluster = new LinkedList<>();
				cluster.add(i);
				Clusters.put(id_cluster, cluster);
				id_cluster++;
			}
		}
	}

	public int Randome(int Max, int Min) {
		Random r = new Random();
		int valeur = Min + (r.nextInt(Max - Min));
		return valeur;
	}

}
