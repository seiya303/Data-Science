package kr.ac.sunmoon.gse.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import kr.ac.sunmoon.gse.component.Dataset;
import kr.ac.sunmoon.gse.component.DecisionTree;
import kr.ac.sunmoon.gse.component.Feature;
import kr.ac.sunmoon.gse.component.Instance;
import kr.ac.sunmoon.gse.component.Node;

public class Util
{
	public static Dataset readDataset(String dataPath)
	{
		Dataset dataset = new Dataset();
		
		try
		{
			FileInputStream fstream = new FileInputStream(dataPath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			CSVReader reader = new CSVReader(br);
			
			boolean isFirstLine = true;
			String[] nextLine;
			while((nextLine = reader.readNext()) != null)
			{
				if(isFirstLine)
				{
					for(int i=1; i<nextLine.length-1; i++)
					{
						Feature feature = new Feature();
						feature.setIndex(i-1);
						feature.setType(Feature.NUMERIC);
						String name = nextLine[i].trim();
						feature.setName(name);
						dataset.addFeature(feature);
					}
					isFirstLine = false;
				}
				else
				{
					Instance instance = new Instance();
					String id = nextLine[0];
					instance.setId(id);
					for(int i=1; i<dataset.getDimension()+1; i++)
					{
						String value = nextLine[i];
						instance.addValue(value);
						if(!isNumeric(value))
							dataset.getFeatures().get(i-1).setType(Feature.STRING);
					}
					instance.setAnswer(nextLine[dataset.getDimension()+1]);
					dataset.addInstance(instance);
				}
			}
			
			reader.close();
			br.close();
			in.close();
			fstream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return dataset;
	}
	
	private static boolean isNumeric(String value) 
	{
	    return value.matches("-?\\d+(\\.\\d+)?");
	}
	
	public static void printDataset(Dataset dataset)
	{
		System.out.println("================[Dataset]=================");
		System.out.println("[Features]");
		ArrayList<Feature> features = dataset.getFeatures();
		for(int i=0; i<features.size(); i++)
		{
			Feature feature = features.get(i);
			System.out.print(feature.getName() + ": ");
			if(feature.getType() == Feature.NUMERIC)
				System.out.println("numeric");
			else
				System.out.println("string");
		}
		System.out.println("----------------------------------------");
		
		System.out.println("[Instances]");
		ArrayList<Instance> instances = dataset.getInstances();
		for(int i=0; i<instances.size(); i++)
		{
			Instance instance = instances.get(i);
			System.out.print(instance.getId() + ": ");
			ArrayList<String> values = instance.getValues();
			for(int j=0; j<values.size(); j++)
			{
				String value = values.get(j);
				if(features.get(j).getType() == Feature.NUMERIC)
				{
					double number = Double.parseDouble(value);
					System.out.print(number + ", ");
				}
				else
					System.out.print(value + ", ");
			}
			System.out.println(instance.getAnswer());
		}
		System.out.println("===============================================");
	}
	
	public static void printTree(DecisionTree tree)
	{
		System.out.println("================[Decision tree]================");
		ArrayList<Node> nodes = tree.getRoot().getChildren();
		for(int i=0; i<nodes.size(); i++)
			printNode(nodes.get(i), 0);
		System.out.println("===============================================");
	}
	
	private static void printNode(Node node, int depth)
	{
		String msg = "";
		Feature feature = node.getFeature();
		String operator = node.getOperator();
		String operand = node.getOperand();
		for(int i=0; i<depth; i++)
			msg += "\t";
		msg += feature.getName() + " " + operator + " " + operand;
		if(node.getChildrenCount() == 0)
			msg += " --> [" + node.getAnswer() + "]";
		
		System.out.println(msg);
		
		ArrayList<Node> children = node.getChildren();
		for(int i=0; i<children.size(); i++)
			printNode(children.get(i), depth+1);
	}
	
	public static void printPredictionResult(ArrayList<Feature> features, Instance instance, String result)
	{
		System.out.println("==============[Prediction result]==============");
		String msg = "";
		int dimension = features.size();
		for(int i=0; i<dimension; i++)
		{
			msg += features.get(i).getName() + ": " + instance.getValueAt(i);
			if(i < dimension-1)
				msg += ", ";
			else
				msg += " --> ";
		}
		msg += "[" + result + "]";
		System.out.println(msg);
		System.out.println("===============================================");
		
	}
	
	public static double getEntropy(ArrayList<Instance> instances)
	{
		HashMap<String, Integer> hmAnswer = new HashMap<String, Integer>();
		for(int i=0; i<instances.size(); i++)
		{
			Instance data = instances.get(i);
			String answer = data.getAnswer();
			if(hmAnswer.get(answer) == null)
				hmAnswer.put(answer, 1);
			else
				hmAnswer.put(answer, hmAnswer.get(answer)+1);
		}
		
		double entropy = 0;
		
		Iterator<Integer> iter = hmAnswer.values().iterator();
		while(iter.hasNext())
		{
			int count = iter.next();
			double prob = (double)count / instances.size();
			entropy += prob*(-1*Math.log(prob)/Math.log(2));
		}
		
		return entropy;
	}
}
