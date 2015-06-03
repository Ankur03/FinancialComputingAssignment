package edu.nyu.cims.compfin14.hw2;

import java.util.HashMap;
import java.util.Map;

public class Bond {
	
	private double price;
	private double coupon;
	private double maturity;
	private double faceValue;
	private int numberOfPaymentsPerYear; // Annual =1 , semi-Annual = 2
	
	protected Bond(){
		
	}
	
	protected Bond(double faceVal , double mature  ){
		this(faceVal, mature, 0.0 , 1 , 0.0);	
		}	
	
	protected Bond(double faceVal , double mature , double pric ){
	this(faceVal, mature,pric, 1 , 0.0);
	}

	protected Bond(double faceVal , double mature, double pric, int nOPPY , double copon ){//numberOfPaymentsPerYear =nOPPY
		this.faceValue = faceVal;
		this.coupon = copon;
		this.maturity = mature;
		this.price = pric;
		this.numberOfPaymentsPerYear = nOPPY;
	}
	
	Bond(double faceVal , double mature, int nOPPY , double copon ){
		this.faceValue = faceVal;
		this.coupon = copon;
		this.maturity = mature;
		this.numberOfPaymentsPerYear = nOPPY;
	}
	
	protected int getNumberOfPaymentsPerYear(){
		return this.numberOfPaymentsPerYear;
	}
	
	protected double getPrice(){
		return this.price;
	}
	
	protected void setPrice(double value){
		 this.price =value ;
	}
	
	protected double getCoupon(){
		double couponPercentage =this.coupon;
		double tempFaceVal = this.faceValue;
		double tempCoupon = (couponPercentage/100) * tempFaceVal;	 	
		return tempCoupon;
	}
	
	protected double getMaturity(){
		return this.maturity;
	}
	
	protected double getFaceValue(){
		return this.faceValue;
	}
		
	protected double getValueOfBond(double rate){ //This function is created for refactoring 
		//some part of calculation required for getYTM
		double bondValue = 0.0;
		Map<Double,Double> tempMap = this.getCashFlow();
		double tempCoupon        = this.getCoupon();
		double tempMaturity      = this.getMaturity();
		double realRate 		 = 0.0;
		double tempNumberOfPaymentsPerYear = this.getNumberOfPaymentsPerYear();
		if(tempNumberOfPaymentsPerYear != 1.0){
			realRate = rate /tempNumberOfPaymentsPerYear;
		}
		else {
			realRate = rate;
		}
		
		realRate = realRate * 0.01;
		
		if(tempNumberOfPaymentsPerYear != 1.0 && tempCoupon != 0.0  )  {
			if(tempCoupon > 0.0 &&  tempNumberOfPaymentsPerYear != 1){
				for (double  i = (1 / tempNumberOfPaymentsPerYear)  ; i <= tempMaturity ;  i = i + (1 / tempNumberOfPaymentsPerYear)  ){
					double tempDiscountFactor = Math.pow(Math.E, (realRate * i ));
					double valuePayed =  tempMap.get(i);
					bondValue = bondValue + (valuePayed / tempDiscountFactor) ;
					
				}
			}
		}
		else if (tempCoupon >0.0){
				for (double i =1 ; i <= tempMaturity ;i++){
					double tempDiscountFactor = Math.pow(Math.E, (realRate * i ));
					double valuePayed =  tempMap.get(i);
					bondValue = bondValue + (valuePayed / tempDiscountFactor) ;
				}
		}			
		return bondValue; 
	}
		
	protected Map<Double,Double> getCashFlow(){//Function to get all individual cash flow in a Map
		Map <Double, Double> hashMap = new HashMap<Double, Double>();
		double tempCoupon = this.getCoupon();
		double tempMaturity = this.getMaturity();
		double tempFaceValue = this.getFaceValue();
		double increment = 1.0;
		int tempNumberOfPaymentsPerYear = this.getNumberOfPaymentsPerYear(); 
		
		if(tempNumberOfPaymentsPerYear != 1.0){
			tempCoupon= tempCoupon / tempNumberOfPaymentsPerYear ;	
			increment = increment / tempNumberOfPaymentsPerYear ;
		}
		
		if(tempCoupon > 0.0){
			for( double i = increment ; i< tempMaturity;i = i + increment ) {
				hashMap.put( i , tempCoupon  );
			}		
			hashMap.put(tempMaturity, (tempFaceValue + tempCoupon));
		}
		else{
			hashMap.put(tempMaturity, tempFaceValue);
		}
		return hashMap;		
	}
	
}
