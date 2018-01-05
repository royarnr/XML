package com.cognizant.XMLCompare;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String string = "Node - TXLife,Version - 2.12.00,";
		String[] keyvalue = string.split(",");
		System.out.println(keyvalue.length);
		for (int i = 0; i < keyvalue.length; i++) {
			System.out.println(keyvalue[i]);
			String[] pairs = keyvalue[i].split("-");
			String key = pairs[0].trim();
			System.out.println(key);
			String value = pairs[1].trim();
			System.out.println(value);
			
		}

	}

}
