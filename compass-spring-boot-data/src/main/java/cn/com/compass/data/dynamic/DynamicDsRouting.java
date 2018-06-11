package cn.com.compass.data.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 数据源路由
 * @date 2018年6月6日 下午3:51:13
 *
 */
public class DynamicDsRouting extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDsSwitch.getDataSourceType();
	}

}
