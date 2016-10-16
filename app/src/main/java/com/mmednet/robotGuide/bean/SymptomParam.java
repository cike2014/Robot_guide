package com.mmednet.robotGuide.bean;

import java.io.Serializable;
import java.util.Comparator;

public class SymptomParam implements Serializable, Comparator<SymptomParam> {

	/** serialVersionUID **/
	private static final long serialVersionUID = -9104001045711648351L;

	/** 计数器 **/
	private int counter;

	/** name **/
	private String name;

	public SymptomParam() {

	}

	public SymptomParam(int counter, String name) {
		super();
		this.counter = counter;
		this.name = name;
	}

	public int getCounter() {
		return counter;
	}

	public String getName() {
		return name;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SymptomParam [counter=" + counter + ", name=" + name + "]";
	}

	@Override
	public int compare(SymptomParam o1, SymptomParam o2) {
		int num = o1.getCounter() - o2.getCounter();
		return num == 0 ? o1.getName().compareTo(o2.getName()) : num;
	}

}
