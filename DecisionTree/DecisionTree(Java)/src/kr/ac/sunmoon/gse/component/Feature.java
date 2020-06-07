package kr.ac.sunmoon.gse.component;

public class Feature 
{
	public static final int NUMERIC = 0;
	public static final int STRING = 1;
	
	private int index;
	private String name;
	private int type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
