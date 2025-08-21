 package com.val.main;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

public class AutoAfk {

	private JFrame frame;
	Timer t;
	static boolean isOn;
	// 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//AutoAFKKeyboardListener.run();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutoAfk window = new AutoAfk();
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
	public AutoAfk() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t = new Timer(500,(ActionEvent e)->{
			
			try {
				mouseWheel(-1);
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		t.start();
	}
	public void mouseWheel(int wheelAmt) throws AWTException {
		Robot robot = new Robot();  

		robot.mouseWheel(wheelAmt);
	}

}
