package kr.ac.sunmoon.gse;

import java.util.ArrayList;

import kr.ac.sunmoon.gse.component.Dataset;
import kr.ac.sunmoon.gse.component.DecisionTree;
import kr.ac.sunmoon.gse.component.Instance;
import kr.ac.sunmoon.gse.util.Util;

public class Main4Simsons {

	public static void main(String[] args) 
	{
		// read dataset
		Dataset dataset = Util.readDataset("data/simpsons.csv");
		Util.printDataset(dataset);
		
		// train decision tree
		DecisionTree tree = new DecisionTree();
		tree.setDataset(dataset);
		tree.fit();
		Util.printTree(tree);
		
		// predict Bart
		Instance testInstance = new Instance();
		ArrayList<String> values = new ArrayList<String>();
		values.add("2");
		values.add("90");
		values.add("10");
		testInstance.setValues(values);
		String result = tree.predict(testInstance);
		Util.printPredictionResult(dataset.getFeatures(), testInstance, result);
		
		// predict Maggie
		values.clear();
		values.add("4");
		values.add("20");
		values.add("1");
		testInstance.setValues(values);
		result = tree.predict(testInstance);
		Util.printPredictionResult(dataset.getFeatures(), testInstance, result);
	}
}
