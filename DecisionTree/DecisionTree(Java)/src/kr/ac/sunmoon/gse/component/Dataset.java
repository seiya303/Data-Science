package kr.ac.sunmoon.gse.component;

import java.util.ArrayList;

public class Dataset 
{
	private ArrayList<Feature> features;
	private ArrayList<Instance> instances;
	
	public Dataset()
	{
		features = new ArrayList<Feature>();
		instances = new ArrayList<Instance>();
	}
	
	public void addFeature(Feature feature)
	{
		features.add(feature);
	}
	
	public void addInstance(Instance instance)
	{
		instances.add(instance);
	}
	
	public ArrayList<Feature> getFeatures()
	{
		return features;
	}
	
	public ArrayList<Instance> getInstances()
	{
		return instances;
	}
	
	public int getDimension()
	{
		return features.size();
	}
}
