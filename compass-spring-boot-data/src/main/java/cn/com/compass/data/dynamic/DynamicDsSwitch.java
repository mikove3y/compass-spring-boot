package cn.com.compass.data.dynamic;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 数据源切换
 * @date 2018年6月6日 下午3:51:25
 *
 */
public class DynamicDsSwitch {
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

	public static void setDataSourceType(String customerType) {
		contextHolder.set(customerType);
	}

	public static String getDataSourceType() {
		return contextHolder.get();
	}

	public static void clearDataSourceType() {
		contextHolder.set(null);
		contextHolder.remove();
	}

}
