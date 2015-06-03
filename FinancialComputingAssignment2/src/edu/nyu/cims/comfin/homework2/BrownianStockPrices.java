package edu.nyu.cims.comfin.homework2;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class BrownianStockPrices implements StockPath { //Declaring variables
	NormallyDistributedNumbers normallyDistributedNumbers ;
	private Option option;
	AntiTheticDecorator antiTheticDecorator;
	
	//Declaring Setter and getters
	protected Option getOption() {
		return option;
	}

	protected void setOption(Option option) {
		this.option = option;
	}

	protected AntiTheticDecorator getAntiTheticDecorator() {
		return antiTheticDecorator;
	}

	protected void setAntiTheticDecorator(AntiTheticDecorator antiTheticDecorator) {
		this.antiTheticDecorator = antiTheticDecorator;
	}
	
	protected BrownianStockPrices( Option option, int seed) {
		super();
		this.option = option;
		antiTheticDecorator = new AntiTheticDecorator(option.getPeriodInDays(), seed);
	}

	protected BrownianStockPrices( Option option) {
		super();
		this.option = option;
		antiTheticDecorator = new AntiTheticDecorator(option.getPeriodInDays());
	}	
	
	@Override
	public List<Pair<DateTime, Double>> getPrices() {  
		// TODO Auto-generated method stub
			LinkedList<Pair<DateTime, Double>> priceList = new LinkedList<Pair<DateTime, Double>>(); //Declaring and creating list pair
		    double initialPrice = option.getCurrentPrice();
		    DateTime optionDate = option.getCurrentDate();
		    double volatility = option.getVolatility() ;
		    double drift      = option.getRate();
		    double fixedPart = drift - volatility * volatility / 2; //Based on brownian formula
		    double[] vectorSample = antiTheticDecorator.getVector();
		    int length = vectorSample.length;
		    double brownianValue = initialPrice;
		    for(int i = 0; i < length ; i++){
		    	optionDate = optionDate.plusDays(1); //Applying geometric Brownian stock prices valuation in next line 
		    	brownianValue = brownianValue  * Math.exp((fixedPart ) + (volatility *  vectorSample[i] )); //variable part is 
		    	// vector sample  Used formula from   one on 4th last slide under title Model for Stock prices given by professor
		    	//As prices build on the value of the stock the previous day initial value is brownian value
		    	Pair<DateTime, Double> tempPair = new Pair<DateTime, Double>(optionDate, brownianValue);
		    	priceList.add(tempPair); // inserting pair one day at time	
		    	
		    }
		    return priceList;
	}

}
