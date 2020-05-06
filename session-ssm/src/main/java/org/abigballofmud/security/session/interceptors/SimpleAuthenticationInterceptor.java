package org.abigballofmud.security.session.interceptors;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.abigballofmud.security.session.constants.SecurityConstant;
import org.abigballofmud.security.session.model.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/12 1:14
 * @since 1.0
 */
@Component
public class SimpleAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 校验用户请求的url是否在用户的权限范围内
        // 取出用户身份信息
        Object object = request.getSession().getAttribute(SecurityConstant.SESSION_USER_KEY);
        if (Objects.isNull(object)) {
            writeContent(response, "请先登录");
        }
        // 开始校验
        UserDTO userDTO = Optional.ofNullable((UserDTO) object).orElseThrow(IllegalAccessError::new);
        String requestUri = request.getRequestURI();
        if (userDTO.getAuthorities().contains("p1") && requestUri.contains("test1")) {
            return true;
        } else if (userDTO.getAuthorities().contains("p2") && requestUri.contains("test2")) {
            return true;
        } else {
            writeContent(response, "没有权限访问该接口");
        }
        return false;
    }

    private void writeContent(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.println(message);
        }
        response.resetBuffer();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // ignore
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // ignore
    }

}
