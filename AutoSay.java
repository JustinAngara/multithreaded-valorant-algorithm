package com.val.main;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;

public class AutoSay {
	Timer t;
	private JFrame frame;
	static boolean isOn;
	List <String> text = new ArrayList<String>();
	String test;
	
	/**
	 * Launch the application.
	 * @throws AWTException 
	 */
	public static void main(String[] args) throws AWTException {
		JNativeHook.main(null);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutoSay window = new AutoSay();
					window.frame.setVisible(true);
				} catch (Exception e) {
					
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AutoSay() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		text.add("I MISS THE RAGE :interrobang:");

		frame = new JFrame();

		frame.setBounds(100, 100, 451, 334);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setAlwaysOnTop(true);
		
		JLabel lblNewLabel = new JLabel("Status: "+isOn);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 435, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("On/off");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				isOn=!isOn;
			}
		});
		btnNewButton.setBounds(176, 120, 89, 58);
		frame.getContentPane().add(btnNewButton);
		
		t =  new Timer(5,(ActionEvent e)-> {
			AutoSay as = new AutoSay();
			if (isOn) {
				try {
					try {
						as.say();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						
					}
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					
				}	
			
			}
			lblNewLabel.setText("Status: "+isOn);
		});
		t.start();
		
	}

	
	public void say() throws AWTException, InterruptedException {
		

		
		StringSelection stringSelection = new StringSelection(text.get(text.size()-1)); Clipboard
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);
			 
		
		
		Robot robot = new Robot();
		

		robot.keyRelease(KeyEvent.VK_SLASH);
		delay(10);
		robot.keyRelease(KeyEvent.VK_SLASH);
		delay(10);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		delay(10);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
		delay(40);
		robot.keyPress(KeyEvent.VK_ENTER);
		
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		delay(1000);
	}
	public void delay(int sleepDelay) throws InterruptedException {
		Thread.sleep(sleepDelay);
	}
}
