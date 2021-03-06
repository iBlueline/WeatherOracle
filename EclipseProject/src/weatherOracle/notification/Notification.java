package weatherOracle.notification;

import weatherOracle.filter.Filter;
import weatherOracle.forecastData.ForecastData;

import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * A Notification for the user, produced by running a filter on a list of
 * ForecastData
 * 
 */
public class Notification implements Comparable<Notification> {
	private String name;
	private Filter filter;
	private List<ForecastData> dataList;

	/**
	 * 
	 * @param name
	 *            =the name of the notification to be displayed
	 * @param dataList
	 *            =the data that passed the filter and triggered the
	 *            notification
	 * @param filter
	 *            =the filter that produced this notification
	 */
	private Notification(String name, List<ForecastData> dataList, Filter filter) {
		this.name = name;
		this.dataList = dataList;
		this.filter = filter;
	}

	/**
	 * @return The list of weather data that passed the filter
	 */
	public List<ForecastData> getWeatherData() {
		return dataList;
	}

	/**
	 * @return The name of the filter
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return The filter associated with this notification
	 */
	public Filter getFilter() {
		return this.filter;
	}
	
	/**
	 * A constructor, but maybe we want to subclass, so use a static factory
	 * 
	 * @param dataList
	 *            the ForecastData that passed filter. Must contain at least one
	 *            entry
	 * @param filter
	 *            the that this notification is supposed to be about
	 * @return a Notification representing the passing of the contents of
	 *         dataList through filter
	 */
	public static Notification make(List<ForecastData> dataList, Filter filter) {
		String name = filter.getName();
		return new Notification(name, dataList, filter);
	}

	public static Notification makeNoData() {
		return new Notification("No Notification Yet", null, null);
	}
	
	public static Notification makeNoConnection() {
		return new Notification("No Internet Connection",null,null);
	}
	
	public static Notification makeNoLocationData(Filter filter) {
		return new Notification(filter.getName()+": No data at location", null, filter);
	}
	
	public String getDays(){
		boolean[] days=new boolean[7];
		String s="";
		String[] names={"Su","M","Tu","W","Th","F","Sa"};
		if (dataList!=null) {
			for (ForecastData d:dataList){
				int i=d.getDayOfWeek()-1;
				if (!days[i]) s += " - " + names[i];
				days[i]=true;
			}
		}		
		if (s.equals("")) return "None";
		return s.substring(3);
	}
	
	// sort by time of first passing dataList entry
	public int compareTo(Notification other) {
		if (this.dataList==null) return 1;
		if (other.dataList==null) return -1;
		
		return (int) (this.dataList.get(0).getMillisTime() - other.dataList
				.get(0).getMillisTime());
	}

}
