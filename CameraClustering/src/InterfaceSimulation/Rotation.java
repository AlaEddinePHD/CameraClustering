package InterfaceSimulation;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Rotation extends JFrame {

	private JPanel contentPane;
	private JTextField angle;
	JOptionPane jop;

	public Rotation(Interface interface1) {
		setTitle("Update Camera \" Rotation \"");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("Select the ID of the camera you want to edit :");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		label.setBounds(10, 24, 369, 34);
		contentPane.add(label);

		JComboBox comboBox = new JComboBox();
		comboBox.setForeground(Color.BLACK);
		comboBox.setFont(new Font("Times New Roman", Font.BOLD, 15));
		comboBox.setBounds(423, 24, 122, 39);
		contentPane.add(comboBox);
		for (int i = 0; i < interface1.Liste_Camera.size(); i++) {
			comboBox.addItem(interface1.Liste_Camera.get(interface1.Liste_Camera.size() - i - 1).ID_camera);
		}

		JButton Save = new JButton("Save");
		Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (angle.getText().isEmpty() == false) {
					double angle_retation = Double.valueOf(angle.getText());
					int iD_Camera_relever = (int) comboBox.getSelectedItem();

					Camera cameraReleve = null;
					boolean tr = false;
					int k = 0;
					while (tr == false) {
						if (interface1.Liste_Camera.get(k).ID_camera == iD_Camera_relever) {
							tr = true;
							cameraReleve = interface1.Liste_Camera.get(k);
						} else {
							k++;
						}
					}
					if (tr) {
						interface1.Liste_Camera.remove(k);
						int oldX[] = cameraReleve.X;
						int oldY[] = cameraReleve.Y;

						/**
						 * New coordinates, source :
						 * https://homeomath2.imingo.net/rotation.htm
						 **/
						int newX[] = new int[oldX.length];
						int newY[] = new int[oldY.length];
						for (int i = 0; i < oldY.length; i++) {
							newX[i] = (int) (((oldX[i] - oldX[0]) * Math.cos(Math.toRadians(angle_retation)))
									- ((oldY[i] - oldY[0]) * Math.sin(Math.toRadians(angle_retation))) + oldX[0]);
							newY[i] = (int) (((oldX[i] - oldX[0]) * Math.sin(Math.toRadians(angle_retation)))
									+ ((oldY[i] - oldY[0]) * Math.cos(Math.toRadians(angle_retation))) + oldY[0]);
						}
						Camera newCamera = new Camera(iD_Camera_relever, newX, newY);
						interface1.Liste_Camera.add(newCamera);
						interface1.repaint();
						angle.setText("");
					}

				} else {
					jop = new JOptionPane();
					jop.showMessageDialog(null, "Fill in the Fields Please", "Attention", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		Save.setForeground(Color.BLACK);
		Save.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 25));
		Save.setBounds(10, 197, 166, 51);
		contentPane.add(Save);

		JButton Refreshe = new JButton("Refreshe");
		Refreshe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				interface1.Design(interface1.Liste_Camera);
				angle.setText("");
				comboBox.removeAllItems();
				for (int i = 0; i < interface1.Liste_Camera.size(); i++) {
					comboBox.addItem(interface1.Liste_Camera.get(interface1.Liste_Camera.size() - i - 1).ID_camera);
				}
			}
		});
		Refreshe.setForeground(Color.BLACK);
		Refreshe.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 25));
		Refreshe.setBounds(216, 197, 149, 51);
		contentPane.add(Refreshe);

		JButton Exit = new JButton("Exit");
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interface1.Design(interface1.Liste_Camera);
				MainApdate mainApdate = new MainApdate(interface1);
				mainApdate.setVisible(true);
				dispose();
			}
		});
		Exit.setForeground(Color.BLACK);
		Exit.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 25));
		Exit.setBounds(417, 197, 149, 51);
		contentPane.add(Exit);

		JLabel lblGiveTheAngle = new JLabel("Give the Angle of Rotation (\u03B8\u00B0)");
		lblGiveTheAngle.setForeground(Color.BLACK);
		lblGiveTheAngle.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblGiveTheAngle.setBounds(10, 92, 256, 34);
		contentPane.add(lblGiveTheAngle);

		JLabel label_1 = new JLabel("\u03B8\u00B0 (+ || -) =");
		label_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label_1.setBounds(226, 137, 91, 28);
		contentPane.add(label_1);

		angle = new JTextField();
		angle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		angle.setColumns(10);
		angle.setBounds(325, 137, 90, 34);
		contentPane.add(angle);
	}
}
