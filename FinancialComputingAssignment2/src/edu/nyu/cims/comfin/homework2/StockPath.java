package edu.nyu.cims.comfin.homework2;

import java.util.List;


import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

//The interface for creating StockPath. The returned list should be ordered by date
	public interface StockPath{
	public List <Pair<DateTime, Double>>  getPrices();
	}