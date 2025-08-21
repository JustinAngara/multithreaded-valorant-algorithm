package com.val.main;

import java.util.ArrayList;
import java.util.Random;

public class ValorantPicker {
	private static ArrayList<String> charecters = new ArrayList<String>();
	public static void main(String[]args) {
		addCharecters();
		System.out.println("Your next chareceter is: "+charecters.get(randomNumber(0,17)));
		
	}
	public static void run() {
		addCharecters();
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
