package org.spring.springboot.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * CacheQueue 数值操作工具类
 * <p>
 * Created by admin on 2016/12/13.
 */
public class HCacheQueueUtil {
    private HCacheQueueUtil() {

    }

    /**
     * 把对象数据信息推入cache队列
     *
     * @param hInstance      HazelcastInstance实例对象
     * @param queueNamespace cache队列命名空间
     * @param data           对象数据信息
     * @param <T>
     * @return
     */
    public static <T> int pushQueueData(HazelcastInstance hInstance,
                                        String queueNamespace,
                                        T data) {
        if (null == hInstance || StringUtils.isEmpty(queueNamespace) || null == data) {
            return OperateFlagConstant.INTEGER_FALSE;
        }

        // 获取队列
        BlockingQueue<T> queue = hInstance.getQueue(queueNamespace);
        // 判断数据是否队列中存在
        if (queue.contains(data)) {
            // 已存在无需添加
            return OperateFlagConstant.INTEGER_TRUE;
        } else {
            if (queue.offer(data)) {
                // 推入成功
                return OperateFlagConstant.INTEGER_TRUE;
            } else {
                // 推入失败
                return OperateFlagConstant.INTEGER_WRONG;
            }
        }
    }

    /**
     * 把对象数据信息列表推入cache队列
     *
     * @param hInstance      HazelcastInstance实例对象
     * @param queueNamespace cache队列命名空间
     * @param dataSet        对象数据信息Set列表
     * @param <T>
     * @return
     */
    public static <T> int pushQueueSet(HazelcastInstance hInstance,
                                       String queueNamespace,
                                       Set<T> dataSet) {
        return pushQueueCollection(hInstance, queueNamespace, dataSet);
    }

    /**
     * 把对象数据信息列表推入cache队列
     *
     * @param hInstance      HazelcastInstance实例对象
     * @param queueNamespace cache队列命名空间
     * @param dataList       对象数据信息List列表
     * @param <T>
     * @return
     */
    public static <T> int pushQueueList(HazelcastInstance hInstance,
                                        String queueNamespace,
                                        List<T> dataList) {
        return pushQueueCollection(hInstance, queueNamespace, dataList);
    }

    /**
     * 把对象数据信息列表推入cache队列
     *
     * @param hInstance      HazelcastInstance实例对象
     * @param queueNamespace cache队列命名空间
     * @param datas          对象数据信息Collection列表
     * @param <T>
     * @return
     */
    public static <T> int pushQueueCollection(HazelcastInstance hInstance,
                                              String queueNamespace,
                                              Collection<T> datas) {
        if (null == hInstance || StringUtils.isEmpty(queueNamespace)
                || null == datas || datas.isEmpty()) {
            return OperateFlagConstant.INTEGER_FALSE;
        }

        // 获取队列
        BlockingQueue<T> queue = hInstance.getQueue(queueNamespace);
        for (T data : datas) {
            if (queue.contains(data)) {
                continue;
            } else {
                if (!queue.offer(data)) {
                    // Queue full
                    return OperateFlagConstant.INTEGER_WRONG;
                }
            }
        }
        return OperateFlagConstant.INTEGER_TRUE;
    }

    /**
     * 获取cache队列中的对象数据信息
     *
     * @param hInstance      HazelcastInstance实例对象
     * @param queueNamespace cache队列命名空间
     * @param cls            对象类
     * @param <T>
     * @return
     */
    public static <T> T getQueueData(HazelcastInstance hInstance,
                                     String queueNamespace,
                                     Class<T> cls) {
        if (null == hInstance || StringUtils.isEmpty(queueNamespace)
                || null == cls) {
            return null;
        }

        // 获取队列
        BlockingQueue<T> queue = hInstance.getQueue(queueNamespace);
        return queue.poll();
    }

    /**
     * 获取cache队列中的对象数据信息列表
     *
     * @param hInstance      HazelcastInstance实例对象
     * @param queueNamespace cache队列命名空间
     * @param maxElements    最大取出量
     * @param cls            对象类
     * @param <T>
     * @return
     */
    public static <T> List<T> getQueueDataList(HazelcastInstance hInstance,
                                               String queueNamespace,
                                               Integer maxElements,
                                               Class<T> cls) {
        List<T> dataList = null;
        if (null == hInstance || StringUtils.isEmpty(queueNamespace)
                || null == cls) {
            return dataList;
        }
        // 获取队列
        BlockingQueue<T> queue = hInstance.getQueue(queueNamespace);
        if (queue.isEmpty()) {
            return dataList;
        } else {
            dataList = new ArrayList<T>();
            if (null == maxElements || maxElements < 1) {
                queue.drainTo(dataList);
            } else {
                queue.drainTo(dataList, maxElements);
            }
            return dataList;
        }
    }

    /**
     * 获取数据cache队列数据量
     *
     * @param hInstance
     * @param queueNamespace
     * @return
     */
    public static int getQueueDataSize(HazelcastInstance hInstance,
                                       String queueNamespace) {
        if (null == hInstance || StringUtils.isEmpty(queueNamespace)) {
            return 0;
        }

        // 获取队列
        BlockingQueue queue = hInstance.getQueue(queueNamespace);
        return queue.size();
    }
}
