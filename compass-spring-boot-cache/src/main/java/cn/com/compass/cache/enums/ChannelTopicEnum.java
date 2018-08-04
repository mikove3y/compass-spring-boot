package cn.com.compass.cache.enums;

import org.springframework.data.redis.listener.ChannelTopic;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 
 * @date 2018年8月4日 下午11:12:16
 *
 */
public enum ChannelTopicEnum {
	
	REDIS_CACHE_DELETE_TOPIC("redis:cache:delete:topic", "删除redis缓存消息频道"),
    REDIS_CACHE_CLEAR_TOPIC("redis:cache:clear:topic", "清空redis缓存消息频道");

    String channelTopic;
    String label;

    ChannelTopicEnum(String channelTopic, String label) {
        this.channelTopic = channelTopic;
        this.label = label;
    }

    public ChannelTopic getChannelTopic() {
        return new ChannelTopic(channelTopic);
    }

    public static ChannelTopicEnum getChannelTopicEnum(String channelTopic) {
        for (ChannelTopicEnum awardTypeEnum : ChannelTopicEnum.values()) {
            if (awardTypeEnum.getChannelTopicStr().equals(channelTopic)) {
                return awardTypeEnum;
            }
        }
        return null;
    }

    public String getChannelTopicStr() {
        return channelTopic;
    }

}
