package edu.nyu.cims.comfin.homework2;

import java.util.ArrayList;
import java.util.List;

public class StatisticalCollector {
	
	protected StatisticalCollector() {
		//Empty Constructor for normal objects
	}
	protected StatisticalCollector(List<Double> tempArray){//Constructor if list of double array is already provided 
		this.arrayList = tempArray;
		this.getMean();   //Updating mean value
		this.getVariance(); //Updating Variance
	}
	
	//Declaration of variables
	private List<Double> arrayList = new ArrayList<Double>(); 
	protected List<Double> getArrayList() {
		return arrayList;
	}
	protected void setArrayList(List<Double> arrayList) {
		this.arrayList = arrayList;
	}

	private double mean = 0.0;
	private double variance = 0.0;
	
	
	protected double getMean(){  //Method to calculate Mean
		int tempSize     = arrayList.size();
		double tempMean  = 0.0;
		double tempTotal = 0.0;
		for(int i = 0; i < tempSize; i++ ){
			tempTotal = tempTotal + arrayList.get(i);
		}
		tempMean = tempTotal / tempSize;
		this.mean = tempMean;
		return tempMean;
	}

	protected void addValue(double addValue){ //Method to add new values to the array 
		arrayList.add(addValue);
		this.getMean();  //calling methods to calculate and update mean and variance values
		this.getVariance();
	}
	
	protected double getVariance(){ //method to calculate variance for standard deviation take square root
		double tempVariance = 0.0;
		double tempMean     = this.getMean();
		int    tempSize     = arrayList.size();
		for(int i = 0; i < tempSize; i++){
			tempVariance = tempVariance + ((arrayList.get(i) - tempMean) * (arrayList.get(i) - tempMean));
		}
		tempVariance  = tempVariance / tempSize;
		this.variance = tempVariance;
		return tempVariance;
	}

	
}
