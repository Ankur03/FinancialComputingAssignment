package edu.nyu.cims.comfin.homework2;
import org.apache.commons.math3.distribution.NormalDistribution;



public class SimulationManager {
	
	private Option option;
	private double confIntervalProb ;
	private double confidenceMultiplier;
	private double error;	
	
	//Standard getter and setter methods
	protected Option getOption() {
		return option;
	}
	
	protected void setOption(Option option) {
		this.option = option;
	}
	
	protected double getConfIntervalProb() {
		return confIntervalProb;
	}
	
	protected void setConfIntervalProb(double confIntervalProb) {
		this.confIntervalProb = confIntervalProb;
	}
	
	protected double getError() {
		return error;
	}
	
	protected void setError(double error) {
		this.error = error;
	}

	SimulationManager(Option tempOption, double tempError, double tempConfidenceInterval ){ //Constructor class
		this.option = tempOption;
		this.error = tempError;
		this.confIntervalProb = tempConfidenceInterval; //this is the confidence interval eg 0.96 0.68 etc 
		this.confidenceMultiplier = -1 * new NormalDistribution().inverseCumulativeProbability(( 1- this.confIntervalProb)/2);
		//this helps us calculate confidence multiplier or t - multiplier
	}
	
	protected double runSimulation(double strikePrice){	
		BrownianStockPrices brownStockPrices = new BrownianStockPrices(this.getOption());
		PayOut normalPayout;
		if(option.isAsian()){ //determine if stock option wants to follow Asian payout or last day delivery payout
			normalPayout   = new AsiaCallPayOut(strikePrice);
		}else{
			normalPayout   = new DeliveryDayCallPayOut(strikePrice);
		}
		double deliveryPayOut = normalPayout.getPayout(brownStockPrices);
		StatisticalCollector statisticalCollector = new StatisticalCollector(); //creating an object to calculate basic statistics
		statisticalCollector.addValue(deliveryPayOut);
		double numberOfIterations = 30; //This number of iterations will be re calculated this is to just start the process
		double tempStandardDeviation = Math.sqrt(statisticalCollector.getVariance());
		double tempMean              = statisticalCollector.getMean();
		long j;
		for( j = 2 ; j < numberOfIterations; j++ ){
			deliveryPayOut = normalPayout.getPayout(brownStockPrices);
			statisticalCollector.addValue(deliveryPayOut);
			tempStandardDeviation = Math.sqrt(statisticalCollector.getVariance());
			tempMean              = statisticalCollector.getMean();
			numberOfIterations = (  ( confidenceMultiplier * Math.sqrt(tempStandardDeviation)) / (this.error * tempMean ) 
					) * ( ( confidenceMultiplier * Math.sqrt(tempStandardDeviation)) / (this.error * tempMean ) ); 
			//Multiplied error with tempMean to accommodate  standard of error of mean according to the mean
			//this reduces number of runs
//			if(Double.isNaN(numberOfIterations) ){ //Used for testing
//				System.out.println("\n Mean is :"+tempMean+"\n Number of iterations:" +j );
//			}
			if( numberOfIterations == 0 || Double.isNaN(numberOfIterations) ){
				numberOfIterations = 1000; // if multiple pay out initially is 0 then it can terminate early
			}
			if(j > 1000000000){
				break;//force stop after hundred thousand iteration
			}
		}
		System.out.println("Number of Runs:"+j);
//		System.out.println("\nArray in statistic collector");
//		for(int t =0; t<j; t++){
//		System.out.println(""+statisticalCollector.getArrayList().get(t).doubleValue() );}
		double raisedTo        = Math.exp(-1 * option.getRate() * option.getPeriodInDays()); //-1 to get its todays price
//		System.out.println("\nRaised to : "+ raisedTo);
		double callOptionPrice = statisticalCollector.getMean() * raisedTo;  //based on slide	
		return callOptionPrice;
	}
	
	
}
