package com.val.main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ValorantAgent {

	private JFrame frame;
	private static ArrayList<String> charecters = new ArrayList<String>();
	JLabel lblNewLabel_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ValorantAgent window = new ValorantAgent();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ValorantAgent() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		addCharecters();
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Your Valorant Agent: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 41));
		lblNewLabel.setBounds(0, 0, 584, 53);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel_1 = new JLabel("Agent: ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(0, 64, 584, 90);
		frame.getContentPane().add(lblNewLabel_1);
		JButton btnNewButton = new JButton("Generate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_1.setText("Agent: "+charecters.get(randomNumber(0,17))
				+" Win Prediction: "+(int)(Math.random()*100)+"%");
			}
		});
		btnNewButton.setBounds(241, 282, 90, 90);
		frame.getContentPane().add(btnNewButton);
		
		

		
		
	}
	
	public static int randomNumber(int min, int max) {
		Random random = new Random();
		int rn = random.nextInt(max + 1 - min) + min;
		return rn;
	}
	public static void addCharecters() {
		charecters.add("Astra");
		charecters.add("Breach");
		charecters.add("Brimstone");
		charecters.add("Chamber");
		charecters.add("Cypher");
		charecters.add("Jett");
		charecters.add("KAY/O");
		charecters.add("Kill Joy");
		charecters.add("Neon");
		charecters.add("Omen");
		charecters.add("Phoenix");
		charecters.add("Raze");
		charecters.add("Sage");
		charecters.add("Yoru");
		charecters.add("Skye");
		charecters.add("Sova");
		charecters.add("Viper");
		charecters.add("Reyna");
	}
}
