package org.spring.springboot.cache;

/**
 * 数据处理操作标识常量
 * <p>
 * Created by admin on 2016/12/13.
 */
public class OperateFlagConstant {
    private OperateFlagConstant() {

    }

    /**
     * 数字类型[integer]的处理失败标识: 0
     */
    public static final int INTEGER_FALSE = 0;

    /**
     * 数字类型[integer]的处理成功标识: 1
     */
    public static final int INTEGER_TRUE = 1;

    /**
     * 数字类型[integer]的处理错误(异常)标识: 2
     */
    public static final int INTEGER_WRONG = 2;
}
