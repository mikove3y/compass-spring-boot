package cn.com.compass.camel.router.base;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo feginClient 进行负载均衡远程调用
 * @date 2018年6月16日 上午1:57:50
 *
 */
public abstract class BaseRouter extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {
		routeConfigure();
		handleException();
		handleIntercept();
	}
	
	/**
	 * 路由配置
	 */
	public abstract void routeConfigure();
	/**
	 * 拦截器
	 */
	public abstract void handleIntercept();
	/**
	 * 默认异常处理器
	 */
	public void handleException() {
		// 默认异常处理器
		errorHandler(defaultErrorHandler());
		
		onException(Exception.class)         // 捕获所有异常  
        .handled(true)                       // 路由停止将错误信息返回给最初的消费者  
        .maximumRedeliveries(0)              // 路由尝试返还1次  
        .redeliveryDelay(500)               // 每次返还间隔0.5秒  
        .retryAttemptedLogLevel(LoggingLevel.INFO)   // 返还时日志级别设为INFO  
        .retriesExhaustedLogLevel(LoggingLevel.INFO) // 返还失败时日志级别设为INFO  
        .process(new BaseErrorProcessor()) // 最终异常发生时处理
        .end();  // 处理结束
		
	}
	/**
	 * intercept() .to("log:hello"); from("jms:queue:order") .to("bean:validateOrder") .to("bean:processOrder"); </br>
	 * 	What happens is that the Exchange is intercepted before each processing step, that means that it will be intercepted before: </br>
	 * 	.to("bean:validateOrder") </br>
	 * 	.to("bean:processOrder") </br>
	 * 路由的每个步骤都会进行拦截 </br>
	 */
//	public void handleIntercept() {
//		intercept().to("log:dododo");
//	}
}
