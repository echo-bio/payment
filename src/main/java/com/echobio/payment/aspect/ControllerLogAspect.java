package com.echobio.payment.aspect;

import cn.hutool.core.bean.BeanUtil;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class ControllerLogAspect {

    @Autowired
    private Gson gson;

    @Pointcut("execution(public * com.echobio.payment.controller.*.*(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        //print log
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        if (BeanUtil.isEmpty(request) || StringUtils.isBlank(request.getRequestURL())) {
            return;
        }

        //start print
        log.info("=======================================  Start  =====================================");
        //print url
        log.info("URL         : {}", request.getRequestURL().toString());
        //print http method
        log.info("HTTP Method : {}", request.getMethod());
        //print class
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // print source ip
        log.info("IP             : {}", request.getRemoteAddr());
        // pprint request params
        log.info("Request Args   : {}", new Gson().toJson(joinPoint.getArgs()));

        ApiOperation apiOperation = null;
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        apiOperation = ms.getMethod().getDeclaredAnnotation(ApiOperation.class);
        if (apiOperation != null) { ;
            log.info("operation   : {}", apiOperation.value());
        }
    }

    /**
     * weaver after pointcut
     *
     * @throws Throwable
     */
    @After("log()")
    public void doAfter() throws Throwable {
        log.info("=========================================== End ===========================================");
        log.info("");
    }

    /**
     * arount
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("log()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        // print response
        log.info("Response Args  : {}", new Gson().toJson(result));
        // cost time
        log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);

        Object[] paramValues = pjp.getArgs();//获取参数数组
        Signature sig = pjp.getSignature();//获取方法对象

        log.info("请求参数： {}", gson.toJson(paramValues));
        log.info("返回值：{}", gson.toJson(result));
        return result;
    }

}
