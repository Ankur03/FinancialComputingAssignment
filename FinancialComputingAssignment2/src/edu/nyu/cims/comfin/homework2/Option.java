package edu.nyu.cims.comfin.homework2;

import org.joda.time.DateTime;

public class Option {

	private boolean asian; //specify type of stock so that we can determine calculation
	private double   currentPrice;
	private int   periodInDays;
	private double  rate;
	private String  stockName;
	private double   strikePrice;
	private double   volatility ;
	private DateTime currentDate;
	private DateTime endDate;
	
	
	protected Option(String stockNameTemp, double currentPriceTemp, double volatilityTemp,
			double rateTemp, int periodInDaysTemp, double strikePriceTemp, boolean asianTrueOrFalse ) {
		// constructor stub
		stockName    = stockNameTemp;
		currentPrice = currentPriceTemp;
		volatility   = volatilityTemp;
		rate         = rateTemp;
		periodInDays = periodInDaysTemp;
		strikePrice  = strikePriceTemp;
		asian        = asianTrueOrFalse; 
		currentDate  = new DateTime();
		endDate      = currentDate.plusDays(periodInDays);		
	}


	protected boolean isAsian() {
		return asian;
	}

	protected void setAsian(boolean asian) {
		this.asian = asian;
	}

	protected double getCurrentPrice() {
		return currentPrice;
	}

	protected void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	protected int getPeriodInDays() {
		return periodInDays;
	}

	protected void setPeriodInDays(int periodInDays) {
		this.periodInDays = periodInDays;
	}

	protected double getRate() {
		return rate;
	}

	protected void setRate(double rate) {
		this.rate = rate;
	}

	protected String getStockName() {
		return stockName;
	}

	protected void setStockName(String stockName) {
		this.stockName = stockName;
	}

	protected double getStrikePrice() {
		return strikePrice;
	}

	protected void setStrikePrice(double strikePrice) {
		this.strikePrice = strikePrice;
	}

	protected double getVolatility() {
		return volatility;
	}

	protected void setVolatility(double volatility) {
		this.volatility = volatility;
	}

	protected DateTime getCurrentDate() {
		return currentDate;
	}

	protected void setCurrentDate(DateTime currentDate) {
		this.currentDate = currentDate;
	}

	protected DateTime getEndDate() {
		return endDate;
	}

	protected void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

}
