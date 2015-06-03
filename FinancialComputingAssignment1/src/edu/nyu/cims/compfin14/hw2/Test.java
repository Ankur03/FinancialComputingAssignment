package edu.nyu.cims.compfin14.hw2;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub	
	System.out.println("\n\n\n***************Execution of Question 1****************** \n\n\n");
	YieldCurve firstYieldCurve= new YieldCurve(500, 3, 2, 5);
	System.out.println("\nFirst Bond Print of Yield Curve Object \n");
	System.out.println(firstYieldCurve.toString());
	System.out.println("\nNow price is being set\n");
	firstYieldCurve.yieldCurveSetPrice();
	System.out.println("\n"+firstYieldCurve.toString());
	System.out.println("\n\n\n***************  End of Question 1    ****************** \n\n\n");	
	System.out.println("\n\n\n***************Execution of Question 2****************** \n\n\n");
	Bond bondOne = new Bond(100 , 0.5, 95 );
	Bond bondTwo = new Bond(1000,1, 895 );
	List<Bond> bondListObject = new ArrayList<Bond>() ; 
	bondListObject.add(bondOne);
	bondListObject.add(bondTwo);
	System.out.println("\nBond List"+bondListObject +" \n");
	YieldCurve secondYieldCurveObject = new YieldCurve(bondListObject); 
	System.out.println(secondYieldCurveObject.toString());
	System.out.println("The value for get interest at time perios 0.75 is "+secondYieldCurveObject.getInterestRate(.75)+"\n");
	System.out.println("\n\n\n***************  End of Question 2    ****************** \n\n\n");
	System.out.println("\n\n\n***************Execution of Question 3****************** \n\n\n");
	double YieldToMaturity = firstYieldCurve.getYTM(firstYieldCurve.getTheBond(),firstYieldCurve.getTheBond().getPrice());	
	System.out.println("Yield Curve Object after setting price\n"+firstYieldCurve.toString());
	System.out.println("The Yield to maturity is :" + YieldToMaturity);
	System.out.println("\n\n\n***************  End of Question 3    ****************** \n\n\n");	
	}
}
