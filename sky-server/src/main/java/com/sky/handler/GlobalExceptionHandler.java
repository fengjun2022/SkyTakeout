package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.exception.BaseException;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.swing.*;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result<T> exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result<T> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String msg = ex.getMessage();
        if (msg.contains("Duplicate entry")){

            String[] split = msg.split(" ");
            String userName = split[2];
            String errMessage = userName + "已存在";
            return Result.error(errMessage);

        }else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }




}
