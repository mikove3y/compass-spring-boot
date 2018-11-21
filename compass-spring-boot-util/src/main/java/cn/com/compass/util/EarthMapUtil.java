/**
 * 
 */
package cn.com.compass.util;

/**
 * @author hexiaomei
 *
 */
public class EarthMapUtil {
	/**
	 * 高德转百度
	 * @param gd_lon
	 * @param gd_lat
	 * @return
	 */
	public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
	    double[] bd_lat_lon = new double[2];
	    double PI = Math.PI * 3000.0 / 180.0;
	    double x = gd_lon, y = gd_lat;
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
	    bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
	    bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
	    return bd_lat_lon;
	}
	/**
	 * 百度转高德
	 * @param bd_lat
	 * @param bd_lon
	 * @return
	 */
	public static double[] baiduToGaoDe(double bd_lat, double bd_lon) {
	    double[] gd_lat_lon = new double[2];
	    double PI = Math.PI * 3000.0 / 180.0;
	    double x = bd_lon - 0.0065, y = bd_lat - 0.006;
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
	    gd_lat_lon[0] = z * Math.cos(theta);
	    gd_lat_lon[1] = z * Math.sin(theta);
	    return gd_lat_lon;
	 }

    //地区半径 千米
    private static double EARTH_RADIUS=6378.137;

    /**
     * 计算弧度
     * @param d
     * @return
     */
    public static double rad(double d){
        return d * Math.PI / 180.0;
    }

    /**
     *  返回当前坐标与目标坐标的距离(米)
     * @param targetLat
     * @param targetLng
     * @param localLat
     * @param localLng
     * @return
     */
    public static double isInCircle(double targetLat,double targetLng,double localLat,double localLng){
        //计算两点间的差距
        double lat = rad(targetLat)-rad(localLat);
        double lng = rad(targetLng)-rad(localLng);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(lat / 2), 2) +
                Math.cos(rad(targetLat)) * Math.cos(rad(localLat)) * Math.pow(Math.sin(lng / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance*10000d)/10000d;
        distance = distance*1000;//km->m
        return distance;
    }
}
