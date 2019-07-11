package InterfaceSimulation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import CHAlgorithme.HierarchicalClassification;
import Intersection_Surface_FoV.IntersectionPolygon;

public class Interface extends JFrame {
	int ID_Camera = 0;
	private JPanel contentPane;
	LinkedList<Camera> Liste_Camera;
	JOptionPane jop;
	IntersectionPolygon hodgman;
	double matrice_surface[][];

	public Interface() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Interface interface1 = this;
		setSize(1366, 735);
		setResizable(false);
		setTitle("Clustering Simulation");
		contentPane = new JPanel();
		Liste_Camera = new LinkedList<>();
		getContentPane().add(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Update Camera");
		btnNewButton.setBounds(217, 665, 163, 31);
		contentPane.add(btnNewButton);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainApdate mainApdate = new MainApdate(interface1);
				mainApdate.setVisible(true);
			}
		});

		JButton AddCamera = new JButton("Add Camera");
		AddCamera.setBounds(10, 665, 163, 31);
		contentPane.add(AddCamera);
		AddCamera.setForeground(Color.BLACK);
		AddCamera.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		AddCamera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int X[] = { 760, 704, 816 };
				int Y[] = { 120, 220, 220 };
				Camera camera = new Camera(ID_Camera, X, Y);
				ID_Camera++;
				Liste_Camera.add(camera);
				Design(Liste_Camera);
			}
		});

		JButton btnTraceTheFov = new JButton("Trace the FoV");
		btnTraceTheFov.setForeground(Color.BLACK);
		btnTraceTheFov.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		btnTraceTheFov.setBounds(830, 665, 163, 31);
		btnTraceTheFov.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Liste_Camera.size() > 1) {
					hodgman = new IntersectionPolygon(Liste_Camera);
					hodgman.setVisible(true);
				} else {
					jop = new JOptionPane();
					jop.showMessageDialog(null, "The Number of Cameras is Insufficient", "Attention",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		contentPane.add(btnTraceTheFov);

		JButton btnClusteringAlgorithm = new JButton("Clustering");
		btnClusteringAlgorithm.setForeground(Color.BLACK);
		btnClusteringAlgorithm.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		btnClusteringAlgorithm.setBounds(1030, 665, 147, 31);
		contentPane.add(btnClusteringAlgorithm);
		btnClusteringAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/** Recover the Matrix of Surfaces **/
				FileInputStream fstream;
				LinkedList<String[]> Liste_interface = new LinkedList<>();
				try {
					fstream = new FileInputStream("Matrice de Surfaces.txt");
					BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
					String line;

					while ((line = br.readLine()) != null) {
						String[] StringSerface = line.split(" ");
						Liste_interface.add(StringSerface);
					}
					br.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(
						"Surface matrix size : (" + Liste_interface.size() + " * " + Liste_interface.size() + ")");
				matrice_surface = new double[Liste_interface.size()][Liste_interface.size()];

				for (int i = 0; i < matrice_surface.length; i++) {
					for (int j = 0; j < matrice_surface.length; j++) {

						matrice_surface[i][j] = Double.valueOf(Liste_interface.get(i)[j]);
					}
				}
				long StartTime = System.currentTimeMillis();
				HierarchicalClassification classification = new HierarchicalClassification(matrice_surface,
						Liste_Camera);
				long EndTime = System.currentTimeMillis();
				File file = new File("Matrice de Surfaces.txt");
				file.delete();
				System.out.println("Runtime of the clustering algorithm : " + (EndTime - StartTime) + "/ms");
			}
		});

		JButton btnSaveCameras = new JButton("Save Cameras");
		btnSaveCameras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					PrintWriter Liste_Camera_Txt = new PrintWriter(new FileWriter("Liste_Cameras.txt"));
					for (int i = 0; i < Liste_Camera.size(); i++) {
						Liste_Camera_Txt.print(Liste_Camera.get(i).ID_camera + " ");
						Liste_Camera_Txt.print(Liste_Camera.get(i).X[0] + " ");
						Liste_Camera_Txt.print(Liste_Camera.get(i).X[1] + " ");
						Liste_Camera_Txt.print(Liste_Camera.get(i).X[2] + " ");
						Liste_Camera_Txt.print(Liste_Camera.get(i).Y[0] + " ");
						Liste_Camera_Txt.print(Liste_Camera.get(i).Y[1] + " ");
						Liste_Camera_Txt.print(Liste_Camera.get(i).Y[2] + "\n");
					}
					Liste_Camera_Txt.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnSaveCameras.setForeground(Color.BLACK);
		btnSaveCameras.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		btnSaveCameras.setBounds(420, 665, 163, 31);
		contentPane.add(btnSaveCameras);

		JButton btnObenCameras = new JButton("Open Cameras");
		btnObenCameras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ID_Max = 0;
				try {
					Liste_Camera.clear();
					FileInputStream fstream = new FileInputStream("Liste_Cameras.txt");
					BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
					String line;
					while ((line = br.readLine()) != null) {
						String[] StringCamera = line.split(" ");

						int ID_camera = Integer.valueOf(StringCamera[0]);
						if (ID_Max < ID_camera) {
							ID_Max = ID_camera;
						}
						int X1 = Integer.valueOf(StringCamera[1]);
						int X2 = Integer.valueOf(StringCamera[2]);
						int X3 = Integer.valueOf(StringCamera[3]);
						int[] X = new int[3];
						X[0] = X1;
						X[1] = X2;
						X[2] = X3;

						int Y1 = Integer.valueOf(StringCamera[4]);
						int Y2 = Integer.valueOf(StringCamera[5]);
						int Y3 = Integer.valueOf(StringCamera[6]);
						int[] Y = new int[3];
						Y[0] = Y1;
						Y[1] = Y2;
						Y[2] = Y3;

						Camera camera = new Camera(ID_camera, X, Y);
						Liste_Camera.add(camera);
					}
					br.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Design(Liste_Camera);
				ID_Camera = ID_Max + 1;
			}
		});
		btnObenCameras.setForeground(Color.BLACK);
		btnObenCameras.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		btnObenCameras.setBounds(625, 665, 163, 31);
		contentPane.add(btnObenCameras);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Liste_Camera.clear();
				Design(Liste_Camera);
				repaint();
			}
		});
		btnClear.setForeground(Color.BLACK);
		btnClear.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		btnClear.setBounds(1213, 665, 137, 31);
		contentPane.add(btnClear);
	}

	public void Design(LinkedList<Camera> Liste_Camera) {
		for (int i = 0; i < Liste_Camera.size(); i++) {
			Graphics graphics = contentPane.getGraphics();
			graphics.drawPolygon(Liste_Camera.get(i).X, Liste_Camera.get(i).Y, 3);
			graphics.drawString("C" + Liste_Camera.get(i).ID_camera, Liste_Camera.get(i).X[0],
					Liste_Camera.get(i).Y[0] - 10);
		}
	}
}
