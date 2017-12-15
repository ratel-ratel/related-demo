package org.spring.springboot.response;

import lombok.Data;

/**
 * Response
 * Created by johnd on 7/17/17.
 */
@Data
public class Response {
    /**
     * 请求结果
     */
    protected Integer returnCode;
    /**
     * 错误信息
     */
    protected String message;
}
