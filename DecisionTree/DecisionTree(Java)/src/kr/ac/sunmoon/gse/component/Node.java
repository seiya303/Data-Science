package kr.ac.sunmoon.gse.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Node 
{
	private ArrayList<Instance> instances;
	
	private Feature feature;
	private String operator;
	private String operand;
	
	private ArrayList<Node> children;
	
	public Node()
	{
		instances = new ArrayList<Instance>();
		children = new ArrayList<Node>();
	}
	
	public void addInstance(Instance instance)
	{
		instances.add(instance);
	}
	
	public ArrayList<Instance> getInstances()
	{
		return instances;
	}
	
	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
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
	
	public void setInstances(ArrayList<Instance> instances)
	{
		this.instances = instances;
	}
	
	public void addChild(Node child)
	{
		children.add(child);
	}
	
	public ArrayList<Node> getChildren()
	{
		return children;
	}
	
	public String getAnswer()
	{
		if(children.size() > 0)
			return null;
		
		HashMap<String, InstanceDistribution> hmDist = new HashMap<String, InstanceDistribution>();
		for(int i=0; i<instances.size(); i++)
		{
			Instance instance = instances.get(i);
			InstanceDistribution dist = hmDist.get(instance.getAnswer());
			if(dist == null)
			{
				dist = new InstanceDistribution();
				dist.setAnswer(instance.getAnswer());
				hmDist.put(instance.getAnswer(), dist);
			}
			dist.increaseCount();
		}
		
		ArrayList<InstanceDistribution> dists = new ArrayList<InstanceDistribution>();
		Iterator<InstanceDistribution> iter = hmDist.values().iterator();
		while(iter.hasNext())
			dists.add(iter.next());
		
		Collections.sort(dists);
		
		return dists.get(0).getAnswer();
	}
	
	public int getChildrenCount()
	{
		return children.size();
	}
	
	class InstanceDistribution implements Comparable<InstanceDistribution>
	{
		private String answer;
		private int count;
		
		public InstanceDistribution()
		{
			count = 0;
		}
		
		public void setAnswer(String answer)
		{
			this.answer = answer;
		}
		
		public String getAnswer()
		{
			return answer;
		}
		
		public void increaseCount()
		{
			count++;
		}
		
		public int getCount()
		{
			return count;
		}

		@Override
		public int compareTo(InstanceDistribution o) {
			// TODO Auto-generated method stub
			return o.getCount() - count;
		}
	}
}
