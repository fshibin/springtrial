package com.thefans.nzshop;

public class Parent extends Child {

	public void foo() {
		System.out.println("parent.foo");
	}
	public static void main(String[] args) {
		String l = "ta";
		char[] ca = new char[] {'t', 'a'};
		String p = new String(ca);
		System.out.print(l == p.intern());
		Integer a = 5;
		Integer b = 10;
		Integer c = a + b;
    }
}
