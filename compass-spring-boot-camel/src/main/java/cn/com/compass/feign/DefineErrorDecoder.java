package cn.com.compass.feign;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;

import cn.com.compass.util.JacksonObjectMapperWrapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefineErrorDecoder implements ErrorDecoder{

	@Override
	public Exception decode(String methodKey, Response response) {
		ObjectMapper om = JacksonObjectMapperWrapper.getInstance();
		Exception exception = null;
        try {
			Object resEntity = om.readValue(Util.toString(response.body().asReader()), Object.class);
			//为了说明我使用的 WebApplicationException 基类，去掉了封装
            exception = new WebApplicationException(javax.ws.rs.core.Response.status(response.status()).entity(resEntity).type(javax.ws.rs.core.MediaType.APPLICATION_JSON).build());
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		}
		// 这里只封装4开头的请求异常
		if (400 <= response.status() || response.status() < 500){
			exception = new HystrixBadRequestException("request exception wrapper", exception);
		}else{
			log.error(exception.getMessage(), exception);
		}
		return exception;
	}

}
