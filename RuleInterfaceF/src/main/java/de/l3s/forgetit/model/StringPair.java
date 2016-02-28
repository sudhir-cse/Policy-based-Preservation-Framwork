package de.l3s.forgetit.model;

import java.io.Serializable;

public class StringPair implements Serializable {
	
	private String left;
	private String right;
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	
	public StringPair() {
	}
	
	public StringPair(String left, String right) {
		super();
		this.left = left;
		this.right = right;
	}
	
	

}
