package kr.ac.sunmoon.gse.component;

import java.util.ArrayList;

public class Instance 
{
	private String id;
	private ArrayList<String> values;
	private String answer;
	
	public Instance()
	{
		values = new ArrayList<String>();
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public void addValue(String value)
	{
		values.add(value);
	}
	
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
	
	public String getId()
	{
		return id;
	}
	
	public ArrayList<String> getValues()
	{
		return values;
	}
	
	public String getValueAt(int index)
	{
		return values.get(index);
	}
	
	public String getAnswer()
	{
		return answer;
	}
	
	public void setValues(ArrayList<String> values)
	{
		this.values = values;
	}
	
	public String toString()
	{
		String str = id + ",";
		
		for(int i=0; i<values.size(); i++)
			str += values.get(i) + ",";
		
		str += answer;
		
		return str;
	}
}
