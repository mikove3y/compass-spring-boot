package cn.com.compass.starter.hanlder;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import cn.com.compass.base.constant.BaseBizeStatusEnum.YesOrNo;
import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.vo.BaseSubject;
import cn.com.compass.web.context.AppContext;
import cn.com.compass.web.context.GlobalContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo jpa 通用sql拦截器，配置jpa hibernate 属性 hibernate.ejb.interceptor
 * @date 2018年6月6日 下午3:56:13
 * @tip onSave – 保存数据的时候调用，数据还没有保存到数据库.
 * @tip onFlushDirty – 更新数据时调用，但数据还没有更新到数据库
 * @tip onDelete – 删除时调用.
 * @tip preFlush – 保存，删除，更新 在提交之前调用 (通常在 postFlush 之前).
 * @tip postFlush – 提交之后调用(commit之后)
 * 
 *
 */
@Component
@Slf4j
public class JpaCommonMetaHanlder extends EmptyInterceptor {

	private static final long serialVersionUID = -5464568289140494842L;
	
	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		super.onDelete(entity, id, state, propertyNames, types);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		try {
			if (entity instanceof BaseEntity) {
				ApplicationContext appContext = AppContext.getInstance();
				BaseSubject sub = null;
				if(appContext!=null) {
					GlobalContext context = appContext.getBean(GlobalContext.class);
					HttpServletRequest request = context.getRequest();
					if(request!=null)
						sub = context.getGlobalSubject();
				}
				for (int i = 0; i < propertyNames.length; i++) {
					if (sub != null) {
						// 创建人
						if (propertyNames[i].equals(BaseEntity.LASTUPDATERID)) {
							currentState[i] = sub.getUserId();
						}
					}
					// 更新时间
					if (propertyNames[i].equals(BaseEntity.LASTUPDATETIME)) {
						currentState[i] = new Date();
					}
				}
			}
		} catch (Exception e) {
			log.error("onFlushDirty entity of " + entity.getClass().getName() + ", id is " + id + ",error {}",e);
			return false;
		}
		return true;
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		try {
			if (entity instanceof BaseEntity) {
				ApplicationContext appContext = AppContext.getInstance();
				BaseSubject sub = null;
				if(appContext!=null) {
					GlobalContext context = appContext.getBean(GlobalContext.class);
					HttpServletRequest request = context.getRequest();
					if(request!=null)
						sub = context.getGlobalSubject();
				}
				for (int i = 0; i < propertyNames.length; i++) {
					if (sub != null) {
						// 创建人
						if (propertyNames[i].equals(BaseEntity.CREATERID)) {
							state[i] = sub.getUserId();
						}
					}
					// 创建时间
					if (propertyNames[i].equals(BaseEntity.CREATETIME)) {
						state[i] = new Date();
					}
					// 逻辑删除字段
					if (propertyNames[i].equals(BaseEntity.ENABLED)) {
						state[i] = YesOrNo.NO.getCode();
					}
				}
			}
		} catch (Exception e) {
			log.error("onSave entity of " + entity.getClass().getName() + ", id is " + id + ",error {}",e);
			return false;
		}
		return true;
	}

	@Override
	public void onCollectionRecreate(Object collection, Serializable key) throws CallbackException {
		super.onCollectionRecreate(collection, key);
	}

	@Override
	public void onCollectionRemove(Object collection, Serializable key) throws CallbackException {
		super.onCollectionRemove(collection, key);
	}

	@Override
	public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException {
		super.onCollectionUpdate(collection, key);
	}

	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		return super.onLoad(entity, id, state, propertyNames, types);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void postFlush(Iterator entities) {
		super.postFlush(entities);
	}

	@Override
	public String onPrepareStatement(String sql) {
		return super.onPrepareStatement(sql);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void preFlush(Iterator entities) {
		super.preFlush(entities);
	}

}
