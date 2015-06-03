package edu.nyu.cims.comfin.homework2;

import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class DeliveryDayCallPayOut implements PayOut {
	
	private double strikePrice; 

	protected double getStrikePrice() {
		return strikePrice;
	}
	
	protected void setStrikePrice(double strikePrice) {
		this.strikePrice = strikePrice;
	}
	
	protected DeliveryDayCallPayOut(double strikePrice) {
		super();
		this.strikePrice = strikePrice;
	}

	@Override
	public double getPayout(StockPath path) {
		// TODO Auto-generated method stub
		List<Pair<DateTime, Double>> tempPriceList = path.getPrices();
		int tempSize = tempPriceList.size();
		Pair<DateTime, Double> tempPair = tempPriceList.get(tempSize - 1 );
		double tempPrice = tempPair.getValue();
		double returnValue = 0.0;
		if((tempPrice - this.strikePrice) > 0){
			returnValue = tempPrice - this.strikePrice;
		}
		else{
			returnValue = 0.0;
		}
		return returnValue;
	}

}
