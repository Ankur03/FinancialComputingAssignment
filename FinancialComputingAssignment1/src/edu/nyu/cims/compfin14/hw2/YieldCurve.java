package edu.nyu.cims.compfin14.hw2;

import java.util.List;
import java.util.Map;

public class YieldCurve {
	private Bond yCurveBond;
	private List<Bond> yCBondList;
	//Below are constructor with different types of bonds & with Bond List too
	protected YieldCurve(double faceValue, double maturity){
		yCurveBond = new Bond(faceValue,  maturity); 
	}
	
	protected YieldCurve(double faceValue, double maturity,int numberOfPayementPerYear, double coupon ){
		yCurveBond = new Bond(faceValue,  maturity, numberOfPayementPerYear, coupon);
	}
	
	protected YieldCurve(List<Bond> bondList){
		yCBondList = bondList;
	}
	
	protected Bond getTheBond(){
		if(this.yCurveBond != null){ 
		return this.yCurveBond;}
		else{
			System.out.println("BOND List is filled and Bond is empty ");
			return this.yCurveBond;
		}
	}
	
	protected void yieldCurveSetPrice(){
		if(this.yCurveBond !=null){ //Setting price of bond according to default yield curve
			Bond bond= this.yCurveBond;
			this.yCurveBond.setPrice(getPrice(this, bond));
		}
		else{
			for(int i=0; i< this.yCBondList.size(); i++ ){ //Setting price for all bonds in bond list according to default bond curve 
				Bond tempBond = this.yCBondList.get(i);
				tempBond.setPrice(getPrice(this, tempBond)); 
				yCBondList.set(i, tempBond);
			}
		}
	}
	
	@Override public String toString(){//Overriding toString to output the object Content
		StringBuilder result = new StringBuilder();
	    String NEW_LINE = System.getProperty("line.separator");
		if(this.yCurveBond != null)  {
			result.append( " Yield Curve Bond Details {" + NEW_LINE);
		    result.append(" Face Value: " + this.yCurveBond.getFaceValue() + NEW_LINE);
		    result.append(" Maturity of the bond " + this.yCurveBond.getMaturity() + NEW_LINE);
		    if(this.yCurveBond.getPrice()>0.0){
		    	 result.append(" Bond Price with YCM: " + this.yCurveBond.getPrice()+ NEW_LINE );
		    }
		    if(this.yCurveBond.getCoupon() > 0.0){
		    	result.append(" Year Coupon value: " + this.yCurveBond.getCoupon()+ NEW_LINE );
		    	result.append(" Number of payment per year: " + this.yCurveBond.getNumberOfPaymentsPerYear()+ NEW_LINE );
		    }	
		    result.append("\n}\n");
		}
		else{
			for(int i=0; i< this.yCBondList.size(); i++ ){
				Bond tempToStringBond = this.yCBondList.get(i);
				result.append(this.getClass().getName() + " Object {" + NEW_LINE);
			    result.append(" Face Value: " + tempToStringBond.getFaceValue()  + NEW_LINE);
			    result.append(" Maturity of the bond " + tempToStringBond.getMaturity() + NEW_LINE);
			    result.append(" Bond Price with YCM: " + tempToStringBond.getPrice()+ NEW_LINE );
			    if(tempToStringBond.getCoupon() > 0.0){
			    	result.append(" Year Coupon value: " + tempToStringBond.getCoupon() + NEW_LINE );
			    	result.append(" Number of payment per year: " + tempToStringBond.getNumberOfPaymentsPerYear()+ NEW_LINE );
			    }	
			    result.append("\n}\n");
			}			
		}
		return result.toString();
	}
	
	protected double getInterestRate(double time){// This function return the interest rate according to predefined Yield curve table exception is
		// If Yield curve object has provided list of bond with face and price value then custom yield curve is used
		double interestRate = 0.0;
		double timeOne = 0.00;
		double timeTwo = 0.00;
		double rateOne = 0.00;
		double rateTwo = 0.00;
		
		if (this.yCBondList != null){
			double tempTime=0.0;
			double tempTimeOne =0.0;
			double tempTimeTwo =10000.0;
			for(int i=0; i< this.yCBondList.size(); i++ ){
				Bond tempInterestcalBond = this.yCBondList.get(i);
				if(tempInterestcalBond.getPrice() ==0.0){
					this.yCurveBond = new Bond(); //Default bond  As we do not have price we cannot 
					//get YTM to create a yield curve  and so have to use default  Yield curve values
					System.out.println("\n INFORMATION FYI: We are switching to  default yield curve values\n ");
					break;
				}
				tempTime =   tempInterestcalBond.getMaturity();
				if(tempTime < time && tempTime >=tempTimeOne ){
					tempTimeOne = tempTime;
					timeOne =tempTime;
					rateOne =this.getYTM(tempInterestcalBond, tempInterestcalBond.getPrice()) ;
				}
				if(tempTime > time &&tempTime <=tempTimeTwo ){
					timeTwo =tempTime;
					rateTwo =this.getYTM(tempInterestcalBond, tempInterestcalBond.getPrice()) ;
				}	
			}
			if(timeTwo==0.0){
				this.yCurveBond = new Bond(); // if according to given values of bonds table
				//there is no bond with maturity greater than time given then go to default yield Curve table
			}
		}
		if(this.yCurveBond != null){
			if(time ==1){
				interestRate = 2.0;
				return (interestRate/100); 
			}
			else if(time ==2){
				interestRate = 2.3;
				return  (interestRate/100);
			}
			else if(time  ==3 ){
				interestRate = 3.0;
				return (interestRate/100);
			}
			else if(time < 1.0){
				timeOne = 0.00;
				timeTwo = 1.00;
				rateOne = 0.00;
				rateTwo = 2.00;
			}
			else if (time < 2.0){
				timeOne = 1.00;
				timeTwo = 2.00;
				rateOne = 2.00;
				rateTwo = 2.30;
			}
			else{
				timeOne = 2.00;
				timeTwo = 3.00;
				rateOne = 2.30;
				rateTwo = 3.00;
			}
		}
		interestRate = ( (( timeTwo - time )*( rateOne ))+ ((time - timeOne)*(rateTwo))) / (timeTwo - timeOne); 
		return  (interestRate/100);
	}
	
	protected double getForwardRate(double t0, double t1){ //Function to get forward rate 
		double forwardRate     = 0.0;
		double interestRateOne = this.getInterestRate(t0);
		double interestRateTwo = this.getInterestRate(t1);

		forwardRate = (( interestRateTwo * t1 )-( interestRateOne * t0))/(t1 -t0) ;		
		return forwardRate;
	}
	
	protected double getDiscountFactor(double t){
		double discountFactor=0.0;
		double tempInterestRate = this.getInterestRate(t) ;
		if((t* tempInterestRate) == 1.0 || (t* tempInterestRate)==3.0 ){
			
			for(int i= 1; i<=(tempInterestRate * t ) ;i++ ){
				if(i==1){
					 discountFactor = Math.E;	
				}
				else{
					discountFactor  = discountFactor * Math.E;
				}	
			}
		}
		else{
		discountFactor = Math.pow(Math.E, (tempInterestRate * t ));
		}
		return (1/discountFactor);
	}
	
	protected double getYTM(Bond bond, double price){
		double yTM = 0.0;
		double tempPrice         = price ;
		double tempCoupon        = bond.getCoupon();
		double tempMaturity      = bond.getMaturity();
		double tempFaceValue     = bond.getFaceValue(); 
		double tempYtm=0.0;
		 
		if( tempCoupon != 0.0  )  {
			double highValue = 0; 
			double lowValue  = 0.99;
			double highValBond = bond.getValueOfBond(highValue);
			double lowValBond  = bond.getValueOfBond(lowValue);
			while(true){
				highValBond = bond.getValueOfBond(highValue);
				lowValBond  = bond.getValueOfBond(lowValue);
				if((highValBond-price) < 0.0001 && (highValBond-price) > -0.0001 ){
					return highValue;
				}
				if ((lowValBond -price) < 0.0001 && (lowValBond -price) > -0.0001){
					return lowValue; 
				}
				if(highValue ==lowValue){
					System.out.println("ERROR ytm range  both are same ");
				}
				if((highValBond-price)>0 && (lowValBond -price)>0 ){
					if((lowValBond -price)> (highValBond-price)) {
						highValue = highValue +   ((highValue +lowValue)/4  ) ;//using a factor of 4 and not 2 
						lowValue = lowValue   +   ((highValue +lowValue)/4  ) ;//so that highValue & low value can never be equal
					}
					else if((lowValBond -price)< (highValBond-price)) {
						lowValue = lowValue     +  ((highValue +lowValue)/4  ) ;
						highValue = highValue   +  ((highValue +lowValue)/4  ) ;
					}	
				}
				if((highValBond-price)>0 && (lowValBond -price)<0 ){
					highValue = highValue + ((highValue +lowValue)/4  ) ;
					lowValue = lowValue - ((highValue +lowValue)/4  ) ;
					
				}
				if((highValBond-price)<0 && (lowValBond -price)>0 ){
					highValue = highValue - ((highValue +lowValue)/4  ) ;
					lowValue = lowValue + ((highValue +lowValue)/4  ) ;
				}
				
				if((highValBond-price)<0 && (lowValBond -price)<0 ){
					if ((lowValBond -price)> (highValBond-price)){
						lowValue =lowValue - ((highValue +lowValue)/4);
						highValue =highValue - ((highValue +lowValue)/4);
					}
					else if ((lowValBond -price) < (highValBond-price)){
						highValue = highValue - ((highValue +lowValue)/4);
						lowValue = lowValue - ((highValue +lowValue)/4);
					}	
				}			
			}			
		}
		else if(tempCoupon == 0.0) {
				double interimTemp = ( tempFaceValue/ tempPrice ) 	   ;
				tempYtm =Math.pow(interimTemp, (1/tempMaturity));
		}
		yTM= (tempYtm -1 ) * 100 ;
		return yTM;
	}
	
	protected double getPrice(Bond bond, double ytm){//Calculating value of bond using Bond attributes & YTM rate
		double tempCoupon    = bond.getCoupon();
		double tempMaturity  = bond.getMaturity();
		double tempFaceValue = bond.getFaceValue();
		double tempNumberOfPaymentsPerYear = bond.getNumberOfPaymentsPerYear(); 
		
		if(tempNumberOfPaymentsPerYear != 1.0){
			tempMaturity = tempNumberOfPaymentsPerYear * tempMaturity ; 
			tempCoupon   = tempCoupon / tempNumberOfPaymentsPerYear ;
			ytm          = ytm / tempNumberOfPaymentsPerYear ; 
		}
		
		double actualYtm = ytm/100;
		actualYtm        = 1 + actualYtm;
		double currentPrice = 0.0;
		double tempActualYtm = actualYtm;
		for(int i=1; i<= tempMaturity; i++ ){
			if(tempMaturity != i){
				currentPrice = currentPrice + ( tempFaceValue / tempActualYtm );	
			}
			else{
				currentPrice = currentPrice + ( tempCoupon/ tempActualYtm ) ;
			}
			tempActualYtm = tempActualYtm * actualYtm;
		}
		return currentPrice;
	}
	
	protected double getPrice(YieldCurve ycm, Bond bond){ 
		double ycmPrice = 0.0;	
		double tempCoupon        = bond.getCoupon();
		double tempMaturity      = bond.getMaturity();
		double tempFaceValue     = bond.getFaceValue();
		double tempNumberOfPaymentsPerYear = bond.getNumberOfPaymentsPerYear(); 
		Map<Double,Double> tempMap = bond.getCashFlow();
		double valuePayed;
		double tempDiscountFactor;
		
		if(tempCoupon > 0.0 &&  tempNumberOfPaymentsPerYear != 1.0){
			double increment = (1 / tempNumberOfPaymentsPerYear);
			for (double  i = increment  ; i <= tempMaturity ;  i = i + increment  ){
				valuePayed =  tempMap.get(i);
				tempDiscountFactor = ycm.getDiscountFactor(i);
				ycmPrice =  ycmPrice + valuePayed * tempDiscountFactor;
			}
		}
		else if (tempCoupon >0.0){
			for (double i =1 ; i <= tempMaturity ;i++){
				valuePayed =  tempMap.get(i);
				tempDiscountFactor = ycm.getDiscountFactor(i);
				ycmPrice =  ycmPrice + valuePayed * tempDiscountFactor;
			}
		}
		else {
			tempDiscountFactor = ycm.getDiscountFactor(tempMaturity);
			ycmPrice =  tempFaceValue * tempDiscountFactor;
		}
		return ycmPrice;
		}	
}
