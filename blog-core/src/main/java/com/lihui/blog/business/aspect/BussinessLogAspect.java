/**
 * MIT License
 * Copyright (c) 2018 yadong.zhang
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.lihui.blog.business.aspect;

import com.lihui.blog.business.annotation.BussinessLog;
import com.lihui.blog.util.RegexUtils;
import com.lihui.blog.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * AOP切面记录日志
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Slf4j
@Aspect
@Component
public class BussinessLogAspect {

    @Pointcut(value = "@annotation(com.lihui.blog.business.annotation.BussinessLog)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object writeLog(ProceedingJoinPoint point) throws Throwable {

        //先执行业务
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) throws Throwable {
        log.error("捕获到了异常...", ex);
    }

    private void handle(ProceedingJoinPoint point) throws Exception {
        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();
        //获取操作名称
        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        String bussinessName = parseContent(point.getArgs(), annotation.value());
        String ua = RequestUtil.getUa();

        log.info("{}-{}.{}", bussinessName, className, methodName);
        log.info("IP: {}, Method: {}, Request URL: {}", RequestUtil.getIp(), RequestUtil.getMethod(), RequestUtil.getRequestUrl());
        log.info("User-Agent: " + ua);
    }

    private String parseContent(Object[] params, String bussinessName) {
        if (bussinessName.contains("{") && bussinessName.contains("}")) {
            List<String> result = RegexUtils.match(bussinessName, "(?<=\\{)(\\d+)");
            for (String s : result) {
                int index = Integer.parseInt(s);
                bussinessName = bussinessName.replaceAll("\\{" + index + "\\}", String.valueOf(params[index - 1]));
            }
        }
        return bussinessName;
    }
}
