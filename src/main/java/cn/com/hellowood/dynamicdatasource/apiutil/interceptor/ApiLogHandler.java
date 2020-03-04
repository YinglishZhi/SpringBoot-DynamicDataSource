package cn.com.hellowood.dynamicdatasource.apiutil.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Enumeration;

/**
 * @author LDZ
 * @date 2020-03-04 11:53
 */

public class ApiLogHandler implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ApiLogHandler.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        request.setAttribute("startTime", LocalDateTime.now());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("-------------------->> Request START <<--------------------");
        // request

        log.info("ip: {}", request.getRemoteAddr());
        log.info("URL: {}", request.getRequestURL());
        log.info("Method: {}", request.getMethod());
        log.info("Protocol: {}", request.getProtocol());

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headName = headerNames.nextElement();
            log.info("Request Header --> {}:{}", headName, request.getHeader(headName));
        }

        if (request.getContentType() != null) {
            log.info("Content-Type --> {}", request.getContentType());

        }

        request.getContentType();

        log.info("==================================================");

        // response
        log.info("Response Code: {}", response.getStatus());
        Collection<String> responseHeaderNames = response.getHeaderNames();
        for (String headerName : responseHeaderNames) {
            log.info("Response Header --> {}:{}", headerName, response.getHeader(headerName));
        }

        LocalDateTime startTime = (LocalDateTime) request.getAttribute("startTime");
        log.info("-------------------->> Request end cost: {} ms, size: {} byte body <<--------------------", Duration.between(startTime, LocalDateTime.now()).toMillis(), response.getBufferSize());
    }
}
