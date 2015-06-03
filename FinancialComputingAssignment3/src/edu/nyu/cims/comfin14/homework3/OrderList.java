package edu.nyu.cims.comfin14.homework3;

import java.util.HashMap;
import java.util.LinkedList;


/**
 * This class is to be used for creating separate book for each stock name
 * it has separate books for each BUy and sell order where keys to access the queue / linked list is the asset price 
 * which is fixed for market order 
 * @author Ankur Vij
 *
 */
public class OrderList {
	private HashMap< String, AssetOrder> hashMapForAssetOrder = new HashMap< String, AssetOrder>() ; // String is for OrderID and that specific AssetOrder 
	private HashMap< String, LinkedList<AssetOrder> > hashMapForLLOfAsserOrderBuy = new HashMap< String, LinkedList<AssetOrder> >();
	private HashMap<String, String> commonPair = new HashMap<String, String>();
	// double is for stock price , 
	//and linkedList for that specific price point
	private String stockName; //Stock name for which the object , which has  List of OrderList is being created
	private HashMap< String, LinkedList<AssetOrder> > hashMapForLLOfAsserOrderSell = new HashMap< String, LinkedList<AssetOrder> >();

	protected String getStockName() {
		return stockName;
	}

	OrderList(String tempName){
		this.stockName = tempName;
		
	}

	/**
	 *  This method is used  to run each asset whether it has to be added or to be cancelled and replaced
	 *   this is built in  ReadMessage class . Every asset will be added  to hashMapForAssetOrder
	 *   and either hashMapForLLOfAsserOrderBuy or hashMapForLLOfAsserOrderSell based on type of order
	 * @param tempAssetOrder : parameter to pass the actual asset object 
	 * @param addTrueCRFalse check if the  true is for a new order and false for cancel and replace order
	 * 
	 * @param prinResult this is a boolean which determines if after each new asset the status of the book should be provided or not
	 */
	
	protected void addOrUpdate(AssetOrder tempAssetOrder, boolean addTrueCRFalse, boolean prinResult ){
		double getTempLimitPrice =tempAssetOrder.getLimitPrice();
		if(!Double.isNaN(getTempLimitPrice)) { 
			double tempAssetPrice = tempAssetOrder.getLimitPrice();
			String tempStringAssetPrice = null ;
			if(Double.isNaN(tempAssetPrice) ){
				tempStringAssetPrice ="MO";// formating the key based on the asset limit price which is fixed for market order
			}
			else{
				tempStringAssetPrice = String.format("%.6g%n",tempAssetPrice); // formating the key based on the asset limit price
			}
			
			String tempOrderId =   tempAssetOrder.getOrderId();
			LinkedList<AssetOrder> value;
			if(tempAssetOrder.getSize()>0){
				value =hashMapForLLOfAsserOrderBuy.get(tempStringAssetPrice);
			}else{
				value =hashMapForLLOfAsserOrderSell.get(tempStringAssetPrice);
			}
			if(addTrueCRFalse) {	
				hashMapForAssetOrder.put(tempOrderId, tempAssetOrder);
			}else{// Cancel and Replace
				AssetOrder tempAO = hashMapForAssetOrder.get(tempOrderId);
				tempAO.setActive(false);
				hashMapForAssetOrder.remove(tempOrderId);
				hashMapForAssetOrder.put(tempOrderId, tempAssetOrder);
			}
			//add the asset order in the correct linkedList according to the asset price
			if (value != null) {
				value.addLast(tempAssetOrder);
			} 
			else {
				    // Key might be present...
				    if (tempAssetOrder.getSize()>0) {
				       // Okay, there's a key but the value is null
				    	LinkedList<AssetOrder> tempOrder =new LinkedList<AssetOrder>();
				    	tempOrder.add(tempAssetOrder);
				    	hashMapForLLOfAsserOrderBuy.put(tempStringAssetPrice, tempOrder);
				    } else {
				       // Definitely no such key
				    	LinkedList<AssetOrder> tempOrder =new LinkedList<AssetOrder>();
				    	tempOrder.add(tempAssetOrder);
				    	hashMapForLLOfAsserOrderSell.put(tempStringAssetPrice, tempOrder);
				    }
				}
//******* This below code line is to be used  if are are operating as an exchange after each order 			
			//this.checkMatchingOrders();
		}
		else{ // this is run if order is a Market Order
			String tempOrderId =   tempAssetOrder.getOrderId();
			hashMapForAssetOrder.put(tempOrderId, tempAssetOrder);
			if(tempAssetOrder.getSize()>0){ // this if true mean it as a call/buy order
				
					this.callBuyMarketReval(tempAssetOrder);
			}
			else{ // else ask/sell order
				this.callSellMarketReval(tempAssetOrder);
			}
		}
		if(prinResult){
			this.printBestBidAndAsk();
		}
		
	}
	
	
	// This method is to be used if we even operate as an exchange and then the is a buy market value order
	/**
	 * This method is to be called every time the is a market order to Buy this asset this look for all sell order
	 *  which has lowest price  
	 * @param tempAO this parameter is the actual asset which is a market order
	 */
	protected void callBuyMarketReval(AssetOrder tempAO){
		String tempKey =null;
		int assetAskSize = tempAO.getSize();
		while(assetAskSize > 0){//1
			tempKey = this.getMinKeyOfAssetOrderLL(false);
			if(tempKey.contains("NA")){
				System.out.println("No available Sell order, so considering it as market or cancel order, dropping the order");
				break;
			}
			LinkedList<AssetOrder> tempLL =  this.hashMapForLLOfAsserOrderSell.get(tempKey);
			while(tempLL.size()>0 && assetAskSize > 0){
				AssetOrder tempFirstAO = tempLL.getFirst();
				if((tempFirstAO.getSize() * -1) <=assetAskSize){
					assetAskSize = assetAskSize + tempFirstAO.getSize();
					tempLL.removeFirst();
				}else{
					int tempSize =tempFirstAO.getSize();
					tempFirstAO.setSize(tempSize + assetAskSize);
					assetAskSize=0;
				}
			}							
		}//1
	}
	// This method is to be used if we even operate as an exchange and then the is a Sell market value order
	/**
	 * This method is to be called every time the is a market order to Sell this asset this look for all Buy order
	 *  which has highest price
	 * @param tempAO
	 */
	protected void callSellMarketReval(AssetOrder tempAO){
		String tempKey =null;
		int assetAskSize = tempAO.getSize();
		while(assetAskSize < 0){//1
			tempKey = this.getMaxKeyOfAssetOrderLL(false);
			if(tempKey.contains("NA")){
				System.out.println("No available buy order so considering it as market or cancel order dropping the order");
				break;
			}
			LinkedList<AssetOrder> tempLL =  this.hashMapForLLOfAsserOrderBuy.get(tempKey);
			while(tempLL.size()>0 && assetAskSize<0){
				AssetOrder tempFirstAO = tempLL.getFirst();
				if(tempFirstAO.getSize()>=assetAskSize && tempFirstAO.getSize() < 0){
					assetAskSize = assetAskSize + tempFirstAO.getSize();
					tempLL.removeFirst();
				}else{
					int tempSize =tempFirstAO.getSize();
					tempFirstAO.setSize(tempSize + assetAskSize);
					assetAskSize=0;
				}
			}							
		}//1
	}
	
	// No Market value call or ask however new order has matching order check if we also use this program as an exchange
	/**
	 * This function is to be called for each new asset order if it is not a market value order. 
	 *It will help to find all matching orders 
	 *this Function is not used as Professor instructed not to match order normally only to look
	 * for matching order in case of market Order
	 */
	
	protected void checkMatchingOrders(){
		
		String tempBuyKey =  this.getMaxKeyOfAssetOrderLL(false);
		String tempSellKey = this.getMinKeyOfAssetOrderLL(false);
		if(tempBuyKey.contains("NA") || tempSellKey.contains("NA") ){
			return;
		}
		while(Math.abs(Double.parseDouble(tempSellKey) - Double.parseDouble(tempBuyKey)) <0.00001 ||(Double.parseDouble(tempSellKey) < Double.parseDouble(tempBuyKey)) ){
			LinkedList<AssetOrder> tempBuyLL =  this.hashMapForLLOfAsserOrderBuy.get(tempBuyKey);
			LinkedList<AssetOrder> tempSellLL =  this.hashMapForLLOfAsserOrderSell.get(tempSellKey);
				AssetOrder assetBuyOrder = tempBuyLL.getFirst();
				AssetOrder assetSellOrder = tempSellLL.getFirst();
				if(!assetBuyOrder.isActive()){
					tempBuyLL.removeFirst();
				}
				if(!assetSellOrder.isActive()){
					tempSellLL.removeFirst();
				}
				int aggregateSize = assetBuyOrder.getSize() + assetSellOrder.getSize();
				if(Math.abs(aggregateSize) < 0.0001){
					assetBuyOrder.setActive(false);
					assetSellOrder.setActive(false);
					tempBuyLL.removeFirst();
					tempSellLL.removeFirst();
				}else if(aggregateSize >0){
					assetSellOrder.setActive(false);
					tempSellLL.removeFirst();
					assetBuyOrder.setSize(aggregateSize);
				}else{
					assetBuyOrder.setActive(false);
					tempBuyLL.removeFirst();
					assetSellOrder.setSize(aggregateSize);
				}
				
			tempBuyKey  =  this.getMaxKeyOfAssetOrderLL(false);
			tempSellKey = this.getMinKeyOfAssetOrderLL(false);	
			if(tempBuyKey.contains("NA") || tempSellKey.contains("NA") ){
				break;
			}
		}
		
	}
	/**
	 * this function will return the string which is a for Sell order HashMap  
	 * key which has lowest asset limit price value among  the asset sell orders 
	 * @return
	 */
	protected String getMinKeyOfAssetOrderLL(boolean sizePrint){
		double minValue  = 10000000;
		String tempUPKey = "NA";
		if(this.hashMapForLLOfAsserOrderSell.get("MO")!=null && this.hashMapForLLOfAsserOrderSell.get("MO").size()>0){
			tempUPKey="MO";
			return tempUPKey;
		}
		//Check with size of key set  then then check minimum order value
		if(this.hashMapForLLOfAsserOrderSell.keySet().size()>0){
			for(String tempKey :this.hashMapForLLOfAsserOrderSell.keySet()){//2
				double value = Double.parseDouble(tempKey);
				
				if(value < minValue && this.hashMapForLLOfAsserOrderSell.get(tempKey) !=null &&this.hashMapForLLOfAsserOrderSell.get(tempKey).size() > 0 ){//3
					minValue= value;
					tempUPKey = tempKey;
				}		
			}	
		}
		//Print the size of order with minimum value
		if(!tempUPKey.contains("NA")&& sizePrint)
		{System.out.println("\nFor stock : "+this.getStockName()+" \nSize of min value sell order is :"+ this.hashMapForLLOfAsserOrderSell.get(tempUPKey).getFirst().getSize());//TODO REMOVE THIS
		}
		else if(tempUPKey.contains("NA") && sizePrint){
			System.out.println("No Stock is available to Sell");
		}
		return tempUPKey;
	}
	/**
	 * this function will return the string which is a for Buy order HashMap  
	 * key which has highest asset limit price value among  the asset buy orders 
	 * @return
	 */
	protected String getMaxKeyOfAssetOrderLL(boolean sizePrint){
		double maxValue  = -1;
		String tempUPKey = "NA";
		if(this.hashMapForLLOfAsserOrderBuy.get("MO")!=null && this.hashMapForLLOfAsserOrderBuy.get("MO").size()>0){
			tempUPKey="MO";
			return tempUPKey;
		}
		//Check with size of key set  then then check max order value
		if(this.hashMapForLLOfAsserOrderBuy.keySet().size()>0){
			for(String tempKey :this.hashMapForLLOfAsserOrderBuy.keySet()){//2
				double value = Double.parseDouble(tempKey);
				if(value > maxValue && this.hashMapForLLOfAsserOrderBuy.get(tempKey) != null && this.hashMapForLLOfAsserOrderBuy.get(tempKey).size() > 0 ){//3
					maxValue= value;
					tempUPKey = tempKey;
				}			
			}
		}	
		//Print the size of order with max value
		if((!tempUPKey.contains("NA")) && sizePrint){
		System.out.println("\nFor stock : "+this.getStockName()+"\nSize of Max value buy order is : "+ this.hashMapForLLOfAsserOrderBuy.get(tempUPKey).getFirst().getSize());//TODO REMOVE THIS
		}
		else if(tempUPKey.contains("NA") && sizePrint){
			System.out.println("No Stock is available to Buy");
		}
		return tempUPKey;
	}
	/**
	 * this function print the highest active bid price  and lowest active ask price for the asset
	 */
	protected void printBestBidAndAsk(){
		System.out.println("\n\n********************New Order Added******************************");
		String tempSellKey = this.getMinKeyOfAssetOrderLL(true);
		System.out.print("Best ask price for "+this.stockName+" is "+tempSellKey);
		String tempBuyKey =  this.getMaxKeyOfAssetOrderLL(true);
		System.out.print("Best Bid price for "+this.stockName+" is "+tempBuyKey);
	}
}
