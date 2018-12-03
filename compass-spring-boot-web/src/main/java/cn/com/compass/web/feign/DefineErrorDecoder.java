package cn.com.compass.web.feign;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.WebApplicationException;
import java.io.IOException;

@Slf4j
public class DefineErrorDecoder extends ErrorDecoder.Default{

	@Override
	public Exception decode(String methodKey, Response response) {
		Exception exception = null;
		String resString = "";
        try {
        	resString = Util.toString(response.body().asReader());
			//为了说明我使用的 WebApplicationException 基类，去掉了封装
            exception = new WebApplicationException(javax.ws.rs.core.Response.status(response.status()).entity(resString).type(javax.ws.rs.core.MediaType.APPLICATION_JSON).build());
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		}
		// 这里只封装4开头的请求异常
		if (400 <= response.status() || response.status() < 500){
			return new HystrixBadRequestException(resString, exception);
		}else{
			log.error(exception.getMessage(), exception);
		}
		return feign.FeignException.errorStatus(methodKey, response);
	}

}
