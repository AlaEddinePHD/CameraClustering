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
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Withdrawal extends JFrame {
	JOptionPane jop;
	private JPanel contentPane;
	private JTextField NewX;
	private JTextField NewY;

	/**
	 * Create the frame.
	 */
	public Withdrawal(Interface interface1) {
		setTitle("Update Camera \" Withdrawal \"");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSelectTheId = new JLabel("Select the ID of the camera you want to edit :");
		lblSelectTheId.setForeground(Color.BLACK);
		lblSelectTheId.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSelectTheId.setBounds(10, 26, 369, 34);
		contentPane.add(lblSelectTheId);

		JComboBox comboBox = new JComboBox();
		comboBox.setForeground(Color.BLACK);
		comboBox.setFont(new Font("Times New Roman", Font.BOLD, 15));
		comboBox.setBounds(423, 26, 122, 39);
		contentPane.add(comboBox);
		for (int i = 0; i < interface1.Liste_Camera.size(); i++) {
			comboBox.addItem(interface1.Liste_Camera.get(interface1.Liste_Camera.size() - i - 1).ID_camera);
		}

		JLabel lblNewLabel = new JLabel("The Added Distance for Camera Coordinates");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 88, 392, 28);
		contentPane.add(lblNewLabel);

		JLabel lblX = new JLabel("X (+ || -) =");
		lblX.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblX.setBounds(43, 138, 82, 21);
		contentPane.add(lblX);

		NewX = new JTextField();
		NewX.setFont(new Font("Times New Roman", Font.BOLD, 20));
		NewX.setBounds(144, 132, 90, 34);
		contentPane.add(NewX);
		NewX.setColumns(10);

		JLabel lblY = new JLabel("Y (+ || -) =");
		lblY.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblY.setBounds(308, 138, 82, 21);
		contentPane.add(lblY);

		NewY = new JTextField();
		NewY.setFont(new Font("Times New Roman", Font.BOLD, 20));
		NewY.setColumns(10);
		NewY.setBounds(409, 132, 90, 34);
		contentPane.add(NewY);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (NewX.getText().isEmpty() == false && NewY.getText().isEmpty() == false) {

					int valueX = Integer.valueOf(NewX.getText());
					int valueY = Integer.valueOf(NewY.getText());
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

						for (int i = 0; i < oldY.length; i++) {
							oldX[i] = oldX[i] + valueX;
							oldY[i] = oldY[i] + valueY;
						}
						Camera newCamera = new Camera(iD_Camera_relever, oldX, oldY);
						interface1.Liste_Camera.add(newCamera);
						interface1.repaint();
						NewX.setText("");
						NewY.setText("");
					}
				} else {
					jop = new JOptionPane();
					jop.showMessageDialog(null, "Fill in the Fields Please", "Attention", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnSave.setForeground(Color.BLACK);
		btnSave.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 25));
		btnSave.setBounds(10, 199, 166, 51);
		contentPane.add(btnSave);

		JButton button_1 = new JButton("Refreshe");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interface1.Design(interface1.Liste_Camera);
				NewX.setText("");
				NewY.setText("");
				comboBox.removeAllItems();
				for (int i = 0; i < interface1.Liste_Camera.size(); i++) {
					comboBox.addItem(interface1.Liste_Camera.get(interface1.Liste_Camera.size() - i - 1).ID_camera);
				}
			}
		});
		button_1.setForeground(Color.BLACK);
		button_1.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 25));
		button_1.setBounds(216, 199, 149, 51);
		contentPane.add(button_1);

		JButton button_2 = new JButton("Exit");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interface1.Design(interface1.Liste_Camera);
				MainApdate mainApdate = new MainApdate(interface1);
				mainApdate.setVisible(true);
				dispose();
			}
		});
		button_2.setForeground(Color.BLACK);
		button_2.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 25));
		button_2.setBounds(417, 199, 149, 51);
		contentPane.add(button_2);
	}
}
