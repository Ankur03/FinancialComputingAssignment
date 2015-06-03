package edu.nyu.cims.comfin.homework2;
import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;



public class NormallyDistributedNumbers implements RandomVectorGenerator {
	private long seed; //Seed is a standard input value, 
	//if given then the generator always creates same array of random series 
	private int length; // variable used to specify how long should the randomly generated array should be
	 RandomGenerator randomGenerator = new JDKRandomGenerator() ; // variable used to store objects of  newly created random vector
	private GaussianRandomGenerator gaussianRandomGenerator; //variable used to store object for gaussian variable
	//Standard setter and getter methods
	
	protected long getSeed() {
		return seed;
	}
	
	protected void setSeed(int seed) { //set the value of seed of the object and of the random vector 
		this.seed = seed;
		randomGenerator.setSeed(seed);
	}
	
	protected NormallyDistributedNumbers( int lengthCont, long seedCont ){ // Constructor method that 
		seed = seedCont;											//takes parameter to specify seed and length					
		length= lengthCont;
		randomGenerator.setSeed(seed);
	}
	
	protected NormallyDistributedNumbers( int lengthCont ){ //Constructor method that takes in parameter
		seed = System.currentTimeMillis();								// and specifies the length
		length= lengthCont;
		randomGenerator.setSeed(seed);
	}
	
	
	@Override
	public double[] getVector() {
		// TODO Auto-generated method stub
				gaussianRandomGenerator = new  GaussianRandomGenerator(randomGenerator);
				double[] vector = new double[this.length];
		         //get list of gaussian vectors
				for(int i =0 ; i< this.length ;i++ ){
		            vector[i] = gaussianRandomGenerator.nextNormalizedDouble();
				}
				return vector;
	}

}
