package edu.nyu.cims.comfin14.homework3;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JunitOrderList {

	@Test
	public void test01() {
		AssetOrder assetOrder1 = new AssetOrder("IBM","ABC1",1000,100.00, true);
		OrderList orderList = new OrderList("IBM");
		orderList.addOrUpdate(assetOrder1, true, true);
		AssetOrder assetOrder2 = new AssetOrder("MSFT","XYZ1",1000,100.00, true);
		OrderList orderList1 = new OrderList("MSFT");
		orderList1.addOrUpdate(assetOrder2, true, true);
		AssetOrder assetOrder3 = new AssetOrder("IBM","IBM2",-1000, 99.00, true);
		orderList.addOrUpdate(assetOrder3, true, true);
		AssetOrder assetOrder4 = new AssetOrder("IBM" ,"ABC1", 1200, 101.01,true );
		orderList.addOrUpdate(assetOrder4, false, true);
		AssetOrder assetOrderFive = new AssetOrder("MSFT","XYZ1",1200,101.10, true);
		orderList1.addOrUpdate(assetOrderFive, false, true);
		AssetOrder assetOrder6 = new AssetOrder("IBM","IBM3", 100, Double.NaN, true);
		orderList.addOrUpdate(assetOrder6, true, true);
		assertTrue(true);
	}

}
