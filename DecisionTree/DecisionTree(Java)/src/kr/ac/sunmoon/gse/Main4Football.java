package kr.ac.sunmoon.gse;

import java.util.ArrayList;

import kr.ac.sunmoon.gse.component.Dataset;
import kr.ac.sunmoon.gse.component.DecisionTree;
import kr.ac.sunmoon.gse.component.Instance;
import kr.ac.sunmoon.gse.util.Util;

public class Main4Football 
{
	public static void main(String[] args) 
	{
		// read dataset
		Dataset dataset = Util.readDataset("data/football.csv");
		Util.printDataset(dataset);
		
		// train decision tree
		DecisionTree tree = new DecisionTree();
		tree.setDataset(dataset);
		tree.fit();
		Util.printTree(tree);
		
		// predict Day05
		Instance testInstance = new Instance();
		ArrayList<String> values = new ArrayList<String>();
		values.add("Sunny");
		values.add("Mild");
		values.add("Normal");
		values.add("Strong");
		testInstance.setValues(values);
		String result = tree.predict(testInstance);
		Util.printPredictionResult(dataset.getFeatures(), testInstance, result);
		
		// predict Day12
		values.clear();
		values.add("Rain");
		values.add("Cool");
		values.add("Normal");
		values.add("Strong");
		testInstance.setValues(values);
		result = tree.predict(testInstance);
		Util.printPredictionResult(dataset.getFeatures(), testInstance, result);
	}
}
