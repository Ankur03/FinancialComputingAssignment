package edu.nyu.cims.comfin14.homework3;

import java.util.HashMap;
import java.util.Iterator;
/**
 * The sole purpose of this class is to initialize the OrdersIterator  class object
 * read the messages and build separate book for each asset
 * @author Ankur Vij
 *
 */

public class ReadMessage extends OrdersIterator {
	 private Iterator<Message> messageIterator; 
	 private boolean resultDisplay;
	 private HashMap<String, String> tempGetSymbolMap = new HashMap<String, String>();
	 private HashMap<String, OrderList> tempGetOrderList = new HashMap<String, OrderList>();
	 
	/**
	 * 
	 * @param messages It contain the message Iterator from OrdersIterator
	 * @param showResults The parameter defines if after each asset addition should we print the current status 
	 * of the buy and sell book
	 */
	ReadMessage( Iterator<Message> messages, boolean showResults){
		this.messageIterator = messages;
		this.resultDisplay = showResults;
	}
	/**
	 * Once this method is called it reads through all the messages from the iterator it determines,
	 *  whether it a new order or cancel and replace order  based on the class the object is instanceOf .
	 *  Then using the tempGetOrderList HashMap it extract the order list object of the particular stock name  and calls the 
	 *  addOrUpdate function
	 *  The tempGetSymbolMap HashMap store stock name related with each orderId so that it can be retrieved in case of 
	 *   cancel and replace
	 */
	protected void readEachAsset(){
		boolean readResult = this.resultDisplay;
		Iterator<Message> tempIterator = this.messageIterator;
		Message tempMessage;
		while(tempIterator.hasNext()){
			tempMessage = 	tempIterator.next();
		//Here we are checking if the order is a new order or cancel and replace type order
		//Then accordingly will create AssetOrder object and send to respective Order list object	
			if(tempMessage instanceof  OrdersIterator.NewOrderImpl && !(tempMessage instanceof OrdersIterator.OrderCxRImpl)){
				OrdersIterator.NewOrderImpl tempNewOrderImpl =(NewOrderImpl) tempMessage;
				String tempSymbol = tempNewOrderImpl.getSymbol();
				this.tempGetSymbolMap.put(tempNewOrderImpl.getOrderId(), tempSymbol);
				AssetOrder tempAssetOrder = new AssetOrder(tempSymbol, tempNewOrderImpl.getOrderId(), 
						tempNewOrderImpl.getSize(), tempNewOrderImpl.getLimitPrice(), true);
				if(!this.tempGetOrderList.containsKey(tempSymbol)){
					OrderList tempOrder = new OrderList(tempSymbol);
					this.tempGetOrderList.put(tempSymbol, tempOrder ) ;
				}
				OrderList tempOrderList = this.tempGetOrderList.get(tempSymbol);
				tempOrderList.addOrUpdate(tempAssetOrder, true, readResult);
				
			}
			else{
				OrdersIterator.OrderCxRImpl tempOrderCxRImpl = (OrderCxRImpl) tempMessage;
				String tempSymbol = this.tempGetSymbolMap.get(tempOrderCxRImpl.getOrderId());
				AssetOrder tempAssetOrder = new AssetOrder(tempSymbol, tempOrderCxRImpl.getOrderId(), 
						tempOrderCxRImpl.getSize(), tempOrderCxRImpl.getLimitPrice(), true);
				OrderList tempOrderList = this.tempGetOrderList.get(tempSymbol);
				tempOrderList.addOrUpdate(tempAssetOrder, false, readResult);	
			}
		}
	}
		
}
