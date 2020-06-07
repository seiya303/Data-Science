package kr.ac.sunmoon.gse.component;

import java.util.ArrayList;

public class Group 
{
	private String key;
	private String operator;
	private String operand;
	private ArrayList<Instance> instances;
	
	public Group()
	{
		instances = new ArrayList<Instance>();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}
	
	public void addInstance(Instance instance)
	{
		instances.add(instance);
	}
	
	public ArrayList<Instance> getInstances()
	{
		return instances;
	}
	
}
