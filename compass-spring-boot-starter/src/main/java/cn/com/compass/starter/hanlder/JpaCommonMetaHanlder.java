package cn.com.compass.starter.hanlder;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.vo.BaseSubject;
import cn.com.compass.web.context.GlobalContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo jpa 通用sql拦截器，配置jpa hibernate 属性 hibernate.ejb.interceptor
 * @date 2018年6月6日 下午3:56:13
 *
 */
@Component
@Slf4j
public class JpaCommonMetaHanlder extends EmptyInterceptor {

	private static final long serialVersionUID = -5464568289140494842L;
	
	@Autowired
	private GlobalContext globalContext;

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if(log.isDebugEnabled()) {
			log.debug("delete..............");
		}
		if(log.isInfoEnabled()) {
			log.info("delete..............");
		}
		super.onDelete(entity, id, state, propertyNames, types);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		if(log.isDebugEnabled()) {
			log.debug("flushDirty..............");
		}
		if(log.isInfoEnabled()) {
			log.info("flushDirty..............");
		}
		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if(log.isDebugEnabled()) {
			log.debug("save..............");
		}
		if(log.isInfoEnabled()) {
			log.info("save..............");
		}
		if (entity instanceof BaseEntity) {
			try {
				BaseSubject sub = globalContext.getGlobalSubject();
				for (int i = 0; i < propertyNames.length; i++) {
					if (sub != null) {
						if (propertyNames[i].equals(BaseEntity.CREATERID)) {
							state[i] = sub.getUserId();
							return true;
						}
					}
					if (propertyNames[i].equals(BaseEntity.CREATETIME)) {
						state[i] = new Date();
					}
				}
			} catch (Exception e) {
				log.error("保存插入创建人Id错误", e);
			}
		}
		return false;
	}

	@Override
	public void onCollectionRecreate(Object collection, Serializable key) throws CallbackException {
		if(log.isDebugEnabled()) {
			log.debug("recreate..............");
		}
		if(log.isInfoEnabled()) {
			log.info("recreate..............");
		}
		super.onCollectionRecreate(collection, key);
	}

	@Override
	public void onCollectionRemove(Object collection, Serializable key) throws CallbackException {
		if(log.isDebugEnabled()) {
			log.debug("remove..............");
		}
		if(log.isInfoEnabled()) {
			log.info("remove..............");
		}
		super.onCollectionRemove(collection, key);
	}

	@Override
	public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException {
		if(log.isDebugEnabled()) {
			log.debug("collectionUpdate..............");
		}
		if(log.isInfoEnabled()) {
			log.info("collectionUpdate..............");
		}
		super.onCollectionUpdate(collection, key);
	}

	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if(log.isDebugEnabled()) {
			log.debug("load..............");
		}
		if(log.isInfoEnabled()) {
			log.info("load..............");
		}
		return super.onLoad(entity, id, state, propertyNames, types);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void postFlush(Iterator entities) {
		if(log.isDebugEnabled()) {
			log.debug("flush..............");
		}
		if(log.isInfoEnabled()) {
			log.info("flush..............");
		}
		super.postFlush(entities);
	}

	@Override
	public String onPrepareStatement(String sql) {
		if(log.isDebugEnabled()) {
			log.debug("statement..............");
		}
		if(log.isInfoEnabled()) {
			log.info("statement..............");
		}
		return super.onPrepareStatement(sql);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void preFlush(Iterator entities) {
		if(log.isDebugEnabled()) {
			log.debug("preflush..............");
		}
		if(log.isInfoEnabled()) {
			log.info("preflush..............");
		}
		super.preFlush(entities);
	}

}
