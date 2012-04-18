package weatherOracle.filter;

import java.io.Serializable;

import weatherOracle.forecastData.ForecastData;

abstract interface Rule extends Serializable{
	public abstract Boolean apply(ForecastData data);
	public abstract void showUI(); // TODO : Placeholder for showing UI, needs some paramaters
}
