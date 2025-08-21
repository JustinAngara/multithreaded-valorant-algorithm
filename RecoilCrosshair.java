package com.val.main;


import java.awt.Color;
import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.Timer;

import javax.swing.border.LineBorder;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.val.main.Test.User32;


public class RecoilCrosshair {
	private static Timer t;
	private JFrame frame;
	int screenWidth;
	int screenHeight;
	JPanel panel = new JPanel();
	static boolean isButtonDown;

	static interface User32 extends Library {
	    @SuppressWarnings("deprecation")
		public static User32 INSTANCE = (User32) Native.loadLibrary("User32", User32.class); 
	    
	    short GetAsyncKeyState(int key);
	    short GetKeyState(int key);

	    IntByReference GetKeyboardLayout(int dwLayout);
	    int MapVirtualKeyExW (int uCode, int nMapType, IntByReference dwhkl);

	    boolean GetKeyboardState(byte[] lpKeyState);

	    int ToUnicodeEx(int wVirtKey, int wScanCode, byte[] lpKeyState, char[] pwszBuff, int cchBuff, int wFlags, IntByReference dwhkl);

	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		run();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//					RecoilCrosshair window = new RecoilCrosshair();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RecoilCrosshair() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		
//		frame = new JFrame();
//		frame.getContentPane().setBackground(Color.CYAN);
//	
//		frame.setBounds(0, 0, 2560, 1440);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setUndecorated(true);
//		frame.setAlwaysOnTop(true);
//		frame.setBackground(new Color(0, 0, 0, 0));
//		frame.getContentPane().setLayout(null);
//		
//		
//		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, false));
//		panel.setBackground(Color.CYAN);
//		panel.setBounds(0,0, 20, 20);
//		frame.getContentPane().add(panel); 
//	


	}
	
	public static void run() {
	    long currTime = System.currentTimeMillis();

	    while (System.currentTimeMillis() < currTime + 20000)
	    {
	        for (int key = 1; key < 256; key++)
	            {
	                if (isKeyPressed(key)) 
	                    getKeyType(key);
	                if(!isKeyPressed(key))
	                	getKeyType(key);
	            }
	    }
	}
	
	private static boolean isKeyPressed(int key) {
	    return User32.INSTANCE.GetAsyncKeyState(key) == -32767;
	}



	private static void getKeyType(int key) {
		int keyCode1=MouseEvent.BUTTON1;
		isButtonDown = (User32.INSTANCE.GetKeyState(keyCode1) & 0x80) == 0x80;
	    byte[] keystate = new byte[256];
	    User32.INSTANCE.GetKeyboardState(keystate); 
	    if(isButtonDown) {
	    	System.out.println("test");
	    } 
	}
	
	
	
}
