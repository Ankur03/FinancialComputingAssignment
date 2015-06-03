package edu.nyu.cims.comfin.homework2;

public class MonteCarloMain {

	public static void main(String[] args) {//TODO
		// TODO Auto-generated method stub
		Option option = new  Option("IBM",  152.35,  0.01,
				 0.0001,  252,  165.0 ,  false );
		SimulationManager simultationManager = new SimulationManager(option, 0.01, 0.96 ) ;
		System.out.println("Payout for first  Option is : " +simultationManager.
				runSimulation(option.getStrikePrice())+"\n");
		Option optionTwo = new  Option("IBM",  152.35,  0.01,
				 0.0001,  252,  165.0 ,  true );
		SimulationManager simultationManagerTwo = new SimulationManager(optionTwo, 0.01, 0.96 ) ;
		System.out.println("Payout for second  Option with type Asian is : " +simultationManagerTwo.runSimulation(164));
	}

}
