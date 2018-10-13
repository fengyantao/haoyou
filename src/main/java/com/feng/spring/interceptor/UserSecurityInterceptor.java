/**
 *
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/23 下午3:12
 *
 *
 */
package com.feng.spring.interceptor;

import com.feng.spring.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/23 下午3:12
 * @version: V1.0
 * @review: feng[17316085657@163.com]/2018/9/23 下午3:12
 */
@Component
public class UserSecurityInterceptor implements HandlerInterceptor {

    @Autowired
    private IRedisService iRedisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        List<String> urlList = Arrays.asList(new String[]{"/qr/index", "/qr/login"});
//        String requestURI = request.getRequestURI();
//
//        if (requestURI.endsWith(".css")||requestURI.endsWith(".js")||requestURI.endsWith(".gif")||requestURI.endsWith(".jpg")){
//            return true;
//        }
//
//
//        String uuid = iRedisService.get( request.getSession().getId());
//
//        if (StringUtils.isBlank(uuid)) {
//            if (requestURI.endsWith("/qr/index")){
//                return true;
//            }
//            response.sendRedirect(request.getContextPath() + "/qr/index");
//            return false;
//        }
//        String beanStr = iRedisService.get( Constans.REDIS_KEY + uuid);
//        if (StringUtils.isBlank(beanStr)){
//            if (requestURI.endsWith("/qr/login")){
//                return true;
//            }
//            response.sendRedirect(request.getContextPath()+"/qr/login");
//            return false;
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }


}
