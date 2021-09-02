package com.thefans.nzshop;

public class Child {
	private void foo() {
		System.out.println("child.foo");
	}
	public void test() {
		System.out.println("child.test");
		foo();
	}
}
