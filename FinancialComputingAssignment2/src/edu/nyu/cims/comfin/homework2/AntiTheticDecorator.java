package edu.nyu.cims.comfin.homework2;



public class AntiTheticDecorator implements edu.nyu.cims.comfin.homework2.RandomVectorGenerator {
	private int length;
	private int count = 0;
	public double[] vector;
	NormallyDistributedNumbers normallyDistributedNumbers ;
	protected AntiTheticDecorator( int lengthCont, int seedCont) {
		normallyDistributedNumbers = new NormallyDistributedNumbers(lengthCont, seedCont);
		// TODO Auto-generated constructor stub
		length= lengthCont;
		 vector = new double[this.length];
	}
	
	protected AntiTheticDecorator( int lengthCont) {
		// TODO Auto-generated constructor stub
		normallyDistributedNumbers = new NormallyDistributedNumbers(lengthCont);
		length= lengthCont;
		 vector = new double[this.length];
	}

	@Override
	public double[] getVector() {
		
		if(count > 1){ // this will happen one the same object of AntiThetic is called third time
			this.count = 0; // this method will help to generate a new series of random numbers
			long tempSeed = System.currentTimeMillis();
			normallyDistributedNumbers = new NormallyDistributedNumbers(this.length, tempSeed);
			}
		if(count == 0){ //this is executed when a new seed value based series is called the first time
			count++ ;
			vector = normallyDistributedNumbers.getVector();		
		}
		else{ //this is executed when a new seed value based series is called the second time
			count++;
			for ( int i = 0; i < vector.length; ++i){
	            vector[i] = (-1) * vector[i]; 
	        }
		}
		return vector;	
	}

}
