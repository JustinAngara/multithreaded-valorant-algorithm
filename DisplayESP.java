
package com.val.main;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;



public class DisplayESP extends JFrame {

	private JPanel contentPane;
	Timer t;
	

	Graphics2D g2;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DisplayESP frame = new DisplayESP();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public DisplayESP() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 2560, 1440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	    setUndecorated(true);
	    setBackground(new Color(0, 0, 0, 0));
		setAlwaysOnTop(true);
	
		setDefaultLookAndFeelDecorated(true);
		
		
 
		
		t = new Timer(60,(ActionEvent e)->{
			repaint();
		});


		

	}
	


	public void paint(Graphics g) { 
		int upperBound = (2560/2) - 45;
		int y = 705;
		g.drawRect(upperBound, y, 90, 30);

    }
	// 1325 - 1235 =
	

}
