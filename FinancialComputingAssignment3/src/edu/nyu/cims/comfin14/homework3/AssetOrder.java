package edu.nyu.cims.comfin14.homework3;
/**
 * Asset Order Class to to create object containing data of new orders 
 * the basic characteristic are stored in following 
 * String symbol;
 * String orderId;
 * int size;
 * double limitPrice;
 * boolean active;
 * the main aim to the class to to set and retrieve characteristics of the asset   
 *   
 * @author Ankur
 *
 */

public class AssetOrder implements NewOrder {
	  private String symbol;
	  private String orderId;
      private int size;
      private double limitPrice;
      private boolean active;
      public String getSymbol() {
		return symbol;
      }
      protected void setSymbol(String symbol) {
		this.symbol = symbol;
      }
      public String getOrderId() {
		return orderId;
      }
      protected void setOrderId(String orderId) {
		this.orderId = orderId;
      }
      public int getSize() {
		return size;
      }
      protected void setSize(int size) {
		this.size = size;
      }
      public double getLimitPrice() {
		return limitPrice;
      }
      protected void setLimitPrice(double limitPrice) {
		this.limitPrice = limitPrice;
      }
      protected boolean isActive() {
		return active;
      }
      protected void setActive(boolean active) {
		this.active = active;
      }
/**
 * This is the constructor class which specifies all the basic values required for an asset
 * @param symbol -->StockName
 * @param orderId --> Order Id
 * @param size   --> Size of the order
 * @param limitPrice -->  What is the limit price if market order specify  Double.NAN
 * @param active     --> Shows whether order is active or not
 */
	AssetOrder( String symbol, String orderId, int size, double limitPrice,boolean active){
		this.symbol =symbol;
		this.orderId = orderId;
		this.size = size;
		this.limitPrice = limitPrice;
		this.active =active;
	}
}
