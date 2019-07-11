package InterfaceSimulation;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Remove_camera extends JFrame {

	private JPanel contentPane;
	Interface interface1;

	/**
	 * Create the frame.
	 */
	public Remove_camera(Interface interface1) {
		setTitle("Delete Camera");
		this.interface1 = interface1;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox comboBox = new JComboBox();
		comboBox.setForeground(Color.BLACK);
		comboBox.setFont(new Font("Times New Roman", Font.BOLD, 15));
		comboBox.setBounds(423, 21, 122, 39);
		contentPane.add(comboBox);
		for (int i = 0; i < interface1.Liste_Camera.size(); i++) {
			comboBox.addItem(interface1.Liste_Camera.get(interface1.Liste_Camera.size() - i - 1).ID_camera);
		}

		JLabel lblNewLabel = new JLabel("Select ID of the camera you want to delete : ");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 21, 369, 34);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Remove");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ID_Camera_relever = (int) comboBox.getSelectedItem();
				boolean tr = false;
				int k = 0;
				while (tr == false) {
					if (interface1.Liste_Camera.get(k).ID_camera == ID_Camera_relever) {
						tr = true;
					} else {
						k++;
					}
				}
				if (tr) {
					interface1.Liste_Camera.remove(k);
					interface1.repaint();
				}

				comboBox.removeAllItems();
				for (int i = 0; i < interface1.Liste_Camera.size(); i++) {
					comboBox.addItem(interface1.Liste_Camera.get(interface1.Liste_Camera.size() - i - 1).ID_camera);
				}

			}
		});
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 25));
		btnNewButton.setIcon(null);
		btnNewButton.setBounds(10, 111, 166, 51);
		contentPane.add(btnNewButton);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				interface1.Design(interface1.Liste_Camera);
				MainApdate mainApdate = new MainApdate(interface1);
				mainApdate.setVisible(true);
				dispose();
			}
		});
		btnExit.setForeground(Color.BLACK);
		btnExit.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 25));
		btnExit.setBounds(417, 111, 149, 51);
		contentPane.add(btnExit);

		JButton btnRefreshe = new JButton("Refreshe");
		btnRefreshe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interface1.Design(interface1.Liste_Camera);
				for (int i = 0; i < interface1.Liste_Camera.size(); i++) {
					comboBox.addItem(interface1.Liste_Camera.get(interface1.Liste_Camera.size() - i - 1).ID_camera);
				}
			}
		});
		btnRefreshe.setForeground(Color.BLACK);
		btnRefreshe.setFont(new Font("Century", Font.BOLD | Font.ITALIC, 25));
		btnRefreshe.setBounds(216, 111, 149, 51);
		contentPane.add(btnRefreshe);
	}
}
