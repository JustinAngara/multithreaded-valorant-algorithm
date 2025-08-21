package com.val.main;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

public class Test {
	static boolean isOn1;
	static interface User32 extends Library {
	    public static User32 INSTANCE = (User32) Native.loadLibrary("User32", User32.class);
	    
	    short GetAsyncKeyState(int key);
	    short GetKeyState(int key);

	    IntByReference GetKeyboardLayout(int dwLayout);
	    int MapVirtualKeyExW (int uCode, int nMapType, IntByReference dwhkl);

	    boolean GetKeyboardState(byte[] lpKeyState);

	    int ToUnicodeEx(int wVirtKey, int wScanCode, byte[] lpKeyState, char[] pwszBuff, int cchBuff, int wFlags, IntByReference dwhkl);

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
	public static void main(String[] args)  {   
		run();
	}



	private static boolean isKeyPressed(int key)
	{
	    return User32.INSTANCE.GetAsyncKeyState(key) == -32767;
	}



	private static void getKeyType(int key)
	{
		int keyCode1=MouseEvent.BUTTON1;

	    boolean isButtonDown = (User32.INSTANCE.GetKeyState(keyCode1) & 0x80) == 0x80;
	    


	    byte[] keystate = new byte[256];
	    User32.INSTANCE.GetKeyboardState(keystate); 

	    System.out.println(isButtonDown);



	}
}
