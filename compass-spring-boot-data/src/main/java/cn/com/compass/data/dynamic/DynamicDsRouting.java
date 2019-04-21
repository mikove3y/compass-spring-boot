package cn.com.compass.data.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 数据源路由
 * @date 2018年6月6日 下午3:51:13
 * @since v1.1.2 sharding-jdbc自动实现了读写分离 master-slave分离，不在使用自定义方式切换数据源
 */
public class DynamicDsRouting extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDsHodler.getDataSourceType();
	}

}
