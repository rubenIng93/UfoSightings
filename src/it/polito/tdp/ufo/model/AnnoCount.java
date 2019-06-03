package it.polito.tdp.ufo.model;

import java.time.Year;

public class AnnoCount {
	
	private Year year;
	private int count;
	
	public AnnoCount(Year year, int count) {
		super();
		this.year = year;
		this.count = count;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return year + "(" + count + ")";
	}
	
	
	
	
	
	

}
