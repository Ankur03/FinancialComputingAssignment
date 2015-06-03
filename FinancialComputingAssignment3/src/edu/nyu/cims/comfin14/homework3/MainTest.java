package edu.nyu.cims.comfin14.homework3;



public class MainTest {
	/**
	 * initiator of the program also create an object of OrdersIterator to start reading 
	 * then ReadMessage handles the rest
	 * @param args
	 */
	public static void main(String[] args) {
		OrdersIterator  inititateOrdersIterator=  new OrdersIterator();
		ReadMessage readMessage = new ReadMessage(inititateOrdersIterator.getIterator() , true);
		readMessage.readEachAsset();
	}
}
