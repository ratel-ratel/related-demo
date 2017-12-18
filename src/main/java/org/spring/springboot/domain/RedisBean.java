package org.spring.springboot.domain;

/**
 *redis String 类型实体类
 */
public class RedisBean {

    /**
     *有效时间
     */
    private Long expireTime;

    /**
     * key值
     */
    private String key;
    /**
     * 对key 做增量,小于0 做减法
     */
    private String delta;

    public String getDelta() {
        return delta;
    }

    public void setDelta(String delta) {
        this.delta = delta;
    }

    /**
     * value
     */
    private String value;

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
