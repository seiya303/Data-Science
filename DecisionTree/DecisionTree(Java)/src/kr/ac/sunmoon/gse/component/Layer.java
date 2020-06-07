package kr.ac.sunmoon.gse.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import kr.ac.sunmoon.gse.util.Util;

public class Layer 
{
	private Feature feature;
	
	private HashMap<String, Group> hmGroup;
	private int instanceCount;
	
	public Layer()
	{
		hmGroup = new HashMap<String, Group>();
		instanceCount = 0;
	}
	
	public double getEntropy()
	{
		double entropy = 0;
		Iterator<Group> iter = hmGroup.values().iterator();
		while(iter.hasNext())
		{
			Group group = iter.next();
			ArrayList<Instance> instances = group.getInstances();
			entropy += Util.getEntropy(instances) * ((double)instances.size()/(double)instanceCount);
		}
		
		return entropy;
	}
	
	public void addInstance(String operator, String operand, Instance instance)
	{
		String key = operand + operator;
		Group group = hmGroup.get(key);
		if(group == null)
		{
			group = new Group();
			group.setOperand(operand);
			group.setOperator(operator);
			hmGroup.put(key, group);
		}
		group.addInstance(instance);
		instanceCount++;
	}
	
	public ArrayList<Group> getGroups()
	{
		ArrayList<Group> groups = new ArrayList<Group>();
		Iterator<Group> iter = hmGroup.values().iterator();
		while(iter.hasNext())
			groups.add(iter.next());
		
		return groups;
	}
	
	public Feature getFeature() {
		return feature;
	}
	public void setFeature(Feature feature) {
		this.feature = feature;
	}
}
