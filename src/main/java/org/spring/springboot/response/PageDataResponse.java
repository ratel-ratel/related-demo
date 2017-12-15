package org.spring.springboot.response;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.List;

/**
 * 分页返回基类
 *
 * Created by admin on 2016/3/9.
 */
@MappedSuperclass
@Data
public class PageDataResponse<T> extends Response implements Serializable {
    /**
     * 每页查询记录数
     */
    protected Integer pageSize;

    /**
     * 页码
     */
    protected Integer pageNumber;

    /**
     * 总记录数
     */
    protected Integer totalRecord = 0;

    /**
     * 操作数据信息(请求结果成功返回)
     */
    protected List<T> pageData;


}
