package com.camelot.kuka.common.config;

import com.camelot.kuka.model.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 *
 * @author liubin3
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

	/**
	 * 请求参数校验异常处理
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Result<?> result;
		BindingResult bindingResult = ex.getBindingResult();
		if (bindingResult.hasErrors()) {
			result = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).findFirst()
					.map(Result::error).get();
		} else {
			result = Result.error("参数错误");
		}
		return result;
	}

	/**
	 * 请求入参必填验证
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Result<?> missingRequestParameterException(MissingServletRequestParameterException ex) {
		return Result.error(ex.getMessage());
	}

	/**
	 * 服务器内部处理失败异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	public Result<?> badRequestException(Exception e) {
		log.error("服务器处理错误", e);
		return Result.error("网络不稳定，请稍后再试!");
	}

}
