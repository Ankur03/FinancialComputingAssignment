package edu.nyu.cims.comfin.homework2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.Ignore;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestYourCode {

	@Test
	public void test01() {
		NormallyDistributedNumbers normallyDistributedNumbers  = new NormallyDistributedNumbers(6, 10);
		double testArray[] = {0,0,0,0,0,0};
		testArray = normallyDistributedNumbers.getVector();
		System.out.println(Arrays.toString(testArray));
		assertTrue(testArray[4] > 0);
	}
	
	@Test
	public void test02GetSameResult() {
		NormallyDistributedNumbers normallyDistributedNumbers  = new NormallyDistributedNumbers(6, 10);
		double testArray[] = {0,0,0,0,0,0};
		testArray = normallyDistributedNumbers.getVector();
		System.out.println("Testing effective ness of setting seed"+Arrays.toString(testArray));
		assertTrue(testArray[4] > 0);
	}
	
	@Test
	public void test03NoSeed() {
		NormallyDistributedNumbers normallyDistributedNumbers  = new NormallyDistributedNumbers(6);
		double testArray[] = {0,0,0,0,0,0};
		testArray = normallyDistributedNumbers.getVector();
		System.out.println("Testing effectiveness of not setting seed"+Arrays.toString(testArray));
		
	}
	
	@Test
	public void test04Antithetic() {
		AntiTheticDecorator antiTheticDecorator  = new AntiTheticDecorator(6);
		double testArray[] = {0,0,0,0,0,0};
		testArray = antiTheticDecorator.getVector();
		System.out.println("Testing effectiveness of antithetic call 1"+Arrays.toString(testArray));
		testArray = antiTheticDecorator.getVector();
		System.out.println("Testing effectiveness of antithetic call 2"+Arrays.toString(testArray));
		double testArray1[] = antiTheticDecorator.getVector();
		double temp1 = testArray1[4];
		System.out.println("Testing effectiveness of antithetic call 3"+Arrays.toString(testArray1));
		testArray = antiTheticDecorator.getVector();
		System.out.println("Testing effectiveness of antithetic call 4"+Arrays.toString(testArray));
		double temp2 = testArray[4];
		assertTrue((temp2 + temp1  ) < 0.0001 ); //Testing the Antithetic capabilities
	}
	 
	@Test
	public void test05OptionsObjects(){
		 Option option = new  Option("IBM",  152.35,  0.01,
					 0.0001,  252,  165.0 ,  false );
		 
		 System.out.println("\n Current date: "+ option.getCurrentDate());
		 System.out.println("\n Current Date + 252 days: "+option.getEndDate());
		 assertTrue((option.getCurrentPrice() - 152.35 ) <  0.0000001);
		 assertTrue((option.getVolatility() - 0.01)<0.00001);
	     assertTrue((option.getRate()- 0.0001)<  0.00001);
		 assertTrue((option.getPeriodInDays() -252) < 0.00001);
		 assertTrue((option.getStockName().equals("IBM")));
		 assertFalse(option.isAsian());
	 }
	 
	 @Test
	 public void test06BrownianStockPrice(){
		 Option option = new  Option("IBM",  152.35,  0.01,
				 0.0001,  252,  165.0 ,  false );
		 BrownianStockPrices brownianStockPrices = new BrownianStockPrices(option);
		 List<Pair<DateTime, Double>> tempList = brownianStockPrices.getPrices();
		 System.out.println(tempList.toString());
	 }
	 
	 @Test
	 public void test07StatisticalCollector(){
		 List<Double> tempArrayList = new  ArrayList<Double>();
		 for (int i=1; i<11 ;i++){
		 tempArrayList.add((double) i);
		 }
		 
		 StatisticalCollector statisticalCollector = new StatisticalCollector(tempArrayList);
		 System.out.println("\nStatisticalCollector testing : Mean of number 1 to 10: \t "+statisticalCollector.getMean());
		 System.out.println("StatisticalCollector testing : Variance of number 1 to 10: \t"+statisticalCollector.getVariance());
	 }
	 
	 @Test
	 public void test08DeliveryDayCallPayout(){
		 Option option = new  Option("IBM",  152.35,  0.01,
				 0.0001,  252,  165.0 ,  false );
		 BrownianStockPrices brownianStockPrices = new BrownianStockPrices(option);
		 DeliveryDayCallPayOut deliveryDayCallPayOut =new DeliveryDayCallPayOut(option.getStrikePrice());
		 System.out.println("\nDelivery Day payout is :"+deliveryDayCallPayOut.getPayout(brownianStockPrices));
		 System.out.println("\nPlease Note for delivery day payout calculation another call \n is made to get prices"
		 		+ " so last day value will be different from previous call ");
	 }

	 @Test
	 public void test09SimulationTesting(){
		 Option option = new  Option("IBM",  152.35,  0.01,
				 0.0001,  252,  165.0 ,  false );
		SimulationManager simultationManager = new SimulationManager(option, 0.01, 0.96 ) ;
		System.out.println("Payout for Option is :" +simultationManager.runSimulation(165));
	 }
}
