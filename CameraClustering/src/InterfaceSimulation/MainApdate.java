package InterfaceSimulation;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainApdate extends JFrame {

	private JPanel contentPane;
	Interface interface1;

	public MainApdate(Interface interface1) {
		this.interface1 = interface1;
		setTitle("Update Camera Coordinates");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Translation");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Withdrawal withdrawal = new Withdrawal(interface1);
				withdrawal.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Century", Font.BOLD, 25));
		btnNewButton.setBounds(10, 11, 414, 71);
		contentPane.add(btnNewButton);

		JButton btnRotation = new JButton("Rotation");
		btnRotation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rotation rotation = new Rotation(interface1);
				rotation.setVisible(true);
				dispose();
			}
		});
		btnRotation.setFont(new Font("Century", Font.BOLD, 25));
		btnRotation.setBounds(10, 93, 414, 71);
		contentPane.add(btnRotation);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Remove_camera remove_camera = new Remove_camera(interface1);
				remove_camera.setVisible(true);
				dispose();
			}
		});
		btnRemove.setIcon(null);
		btnRemove.setFont(new Font("Century", Font.BOLD, 25));
		btnRemove.setBounds(10, 179, 414, 71);
		contentPane.add(btnRemove);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnExit.setFont(new Font("Century", Font.BOLD, 25));
		btnExit.setBounds(10, 268, 414, 71);
		contentPane.add(btnExit);
	}
}
