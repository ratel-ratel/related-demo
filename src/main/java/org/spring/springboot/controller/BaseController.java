package org.spring.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.spring.springboot.enums.ReturnCodeEnum;
import org.spring.springboot.response.BackResponseUtil;
import org.spring.springboot.response.BaseResponse;
import org.spring.springboot.util.JsonUtil;
import org.spring.springboot.util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 服务基础服务Controller抽象类
 * <p>
 * Created by yangqizhang on 2017/5/12.
 */
@ControllerAdvice
@Slf4j
public abstract class BaseController {

    /**
     * 异常拦截处理
     *
     * @param exp
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)  //自定义异常处理类
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception exp, HttpServletRequest request, HttpServletResponse response) {
        BaseResponse respInfo = new BaseResponse();
        Long timeStamp = System.currentTimeMillis();
        if (exp instanceof Exception) {
            respInfo = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1004.getCode());
            respInfo.setMessage("grpc exception, timeStamp:" + timeStamp);
            log.error("timeStamp:" + timeStamp, exp.getMessage(), exp);
        } else {
            respInfo.setReturnCode(ReturnCodeEnum.CODE_1004.getCode());
            respInfo.setMessage("exception, timStamp:" + timeStamp);
            log.error("timeStamp:" + timeStamp + exp.getMessage(), exp);
        }
        this.setResponseBody(response, JsonUtil.objectToJson(respInfo), null, null);
    }

    /**
     * 设置通讯返回信息
     *
     * @param resp
     * @param respBodyStr
     * @param contentType  媒体类型
     * @param encodingType 字符编码类型
     */
    public static void setResponseBody(HttpServletResponse resp,
                                       String respBodyStr,
                                       String contentType,
                                       String encodingType) {
        if (StringUtil.isEmpty(contentType)) {
            contentType = "application/json;charset=UTF-8";
        }
        if (StringUtil.isEmpty(contentType)) {
            contentType = "application/json;charset=UTF-8";
        }
        resp.setCharacterEncoding(encodingType);
        resp.setContentType(contentType);
        PrintWriter printWriter = null;
        try {
            printWriter = resp.getWriter();
            printWriter.write(respBodyStr);
            printWriter.flush();
        } catch (IOException e) {
            log.error("HttpServletResponse写入异常" + e.getMessage(), e);
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
