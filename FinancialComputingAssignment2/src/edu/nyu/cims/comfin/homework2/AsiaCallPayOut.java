package edu.nyu.cims.comfin.homework2;

import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class AsiaCallPayOut implements PayOut {


	private double  strikePrice; 
	
	protected double getStrikePrice() {
		return strikePrice;
	}

	protected void setStrikePrice(double strikePrice) {
		this.strikePrice = strikePrice;
	}
	
	protected AsiaCallPayOut(double strikePrice) {
		super();
		this.strikePrice = strikePrice;
	}

	@Override
	public double getPayout(StockPath path) {
		// TODO Auto-generated method stub
		double addPrices = 0.0;
		List<Pair<DateTime, Double>> tempPriceList = path.getPrices(); //getting all path values
		int tempSize = tempPriceList.size(); //Determining  size of the list
		double tempPrice = 0.0;
		double returnValue = 0.0;
		for(int i = 0 ; i < tempSize; i++ ){
			Pair<DateTime, Double> tempPair = tempPriceList.get(i); // Getting pair of each element
			tempPrice = tempPair.getValue();  // Getting value of each pair
			addPrices = addPrices + tempPrice; //Adding all value
		}
		double averagePrice = (addPrices * 1.0)/(tempSize * 1.0); //Calculating average
		double tempStrikePrice = this.getStrikePrice();
		if((averagePrice - tempStrikePrice) > 0.0 ){ // determining highest value
			returnValue = averagePrice - tempStrikePrice;
		}
		else{
			returnValue = 0.0;
		}
		return returnValue;
	}

}
