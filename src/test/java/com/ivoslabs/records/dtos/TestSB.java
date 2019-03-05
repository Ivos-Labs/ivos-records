package com.ivoslabs.records.dtos;

public class TestSB {

    public static void main(String[] args) {
	StringBuilder sb = new StringBuilder(10);
	for (int i = 0; i < 10; i++) {
	    sb.append(i);
	}

	String a = "AAAAB";
	a = a.substring(0, 4);

	sb.replace(2, 3, a);

	System.out.println("'" + sb.toString() + "'");
    }

}
