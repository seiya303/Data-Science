package kr.ac.sunmoon.gse.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import kr.ac.sunmoon.gse.util.Util;

public class DecisionTree 
{
	public static final String LTE = "¡Â";
	public static final String GT = ">";
	public static final String EQ = "=";
	
	private Dataset dataset;
	private Node root;
	
	public void setDataset(Dataset dataset) 
	{
		this.dataset = dataset;
		
		ArrayList<Instance> instances = dataset.getInstances();
		root = new Node();
		for(int i=0; i<instances.size(); i++)
			root.addInstance(instances.get(i));
	}
	
	public void fit()
	{
		extendNode(root);
		dataset = null;
	}
	
	private void extendNode(Node node)
	{
		if(Util.getEntropy(node.getInstances()) > 0)
		{
			Layer bestLayer = null;
			ArrayList<Instance> instances = node.getInstances();
			for(int i=0; i<dataset.getDimension(); i++)
			{
				Feature feature = dataset.getFeatures().get(i);
				ArrayList<String> operands = getOperands(instances, feature.getIndex());
				int featureType = feature.getType();
				if(featureType == Feature.NUMERIC)
				{
					for(int j=0; j<operands.size(); j++)
					{
						Layer layer = new Layer();
						layer.setFeature(feature);
						String operand = operands.get(j);
						
						double numOperand = Double.parseDouble(operand);
						for(int k=0; k<instances.size(); k++)
						{
							Instance instance = instances.get(k);
							double value = Double.parseDouble(instance.getValueAt(i));
							if(value <= numOperand)
								layer.addInstance(DecisionTree.LTE, operand, instance);
							else
								layer.addInstance(DecisionTree.GT, operand, instance);
						}
						
						if(bestLayer == null)
							bestLayer = layer;
						else
						{
							if(bestLayer.getEntropy() > layer.getEntropy())
								bestLayer = layer;
						}
					}
				} else {
					Layer layer = new Layer();
					for(int j=0; j<operands.size(); j++) {
						layer.setFeature(feature);
						String operand = operands.get(j);
						
						for(int k=0; k<instances.size(); k++) {
							Instance instance = instances.get(k);
							String value = instance.getValueAt(i);
							if(value.equals(operand)) {
								layer.addInstance(DecisionTree.EQ, operand, instance);
							}
						}
						
						if(bestLayer == null)
							bestLayer = layer;
						else {
							if(bestLayer.getEntropy() > layer.getEntropy())
								bestLayer = layer;
						}
					}
				}
			}
			
			if(bestLayer.getEntropy() < Util.getEntropy(node.getInstances()))
			{
				Feature feature = bestLayer.getFeature();
				
				ArrayList<Group> groups = bestLayer.getGroups();
				for(int i=0; i<groups.size(); i++)
				{
					Group group = groups.get(i);
					Node child = new Node();
					child.setFeature(feature);
					child.setOperand(group.getOperand());
					child.setOperator(group.getOperator());
					child.setInstances(group.getInstances());
					node.addChild(child);
					extendNode(child);
				}
			}
		}
	}
	
	private ArrayList<String> getOperands(ArrayList<Instance> instances, int featureIndex)
	{
		HashMap<String, String> hmValues = new HashMap<String, String>();
		for(int i=0; i<instances.size(); i++)
		{
			Instance instance = instances.get(i);
			String value = instance.getValueAt(featureIndex);
			hmValues.put(value, value);
		}

		Iterator<String> iter = hmValues.values().iterator();
		ArrayList<String> operands = new ArrayList<String>();
		while(iter.hasNext())
			operands.add(iter.next());
		
		return operands;
	}
	
	public String predict(Instance instance)
	{
		ArrayList<Node> children = root.getChildren();
		for(int i=0; i<children.size(); i++)
		{
			Node child = children.get(i);
			String answer = getAnswer(child, instance);
			if(answer != null)
				return answer;
		}
		
		return "Prediction fail";
	}
	
	private String getAnswer(Node node, Instance instance)
	{
		Feature feature = node.getFeature();
		int featureType = feature.getType();
		int featureIndex = feature.getIndex();
		String featureValue = instance.getValueAt(featureIndex);
		String operand = node.getOperand();
		String operator = node.getOperator();
		
		String answer = null;
		if(isSatisfy(featureType, featureValue, operator, operand))
		{
			answer = node.getAnswer();
			if(answer != null)
				return answer;
			else
			{
				ArrayList<Node> children = node.getChildren();
				for(int i=0; i<children.size(); i++)
				{
					Node child = children.get(i);
					answer = getAnswer(child, instance);
					if(answer != null)
						return answer;
				}
			}
		}
		
		return answer;
	}
	
	private boolean isSatisfy(int featureType, String featureValue, String operator, String operand)
	{
		if(featureType == Feature.NUMERIC)
		{
			double dFeatureValue = Double.parseDouble(featureValue);
			double dOperand = Double.parseDouble(operand);
			if(operator.equals(DecisionTree.LTE))
			{
				if(dFeatureValue <= dOperand)
					return true;
				else 
					return false;
			}
			else if(operator.equals(DecisionTree.GT))
			{
				if(dFeatureValue > dOperand)
					return true;
				else 
					return false;
			}
		} else {
			if(operator.equals(DecisionTree.EQ)) {
				if(featureValue.equals(operand))
					return true;
				else
					return false;
			}
		}
		
		return false;
	}
	
	public Node getRoot()
	{
		return root;
	}
}
