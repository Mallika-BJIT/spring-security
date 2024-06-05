package com.example.security.security;

import com.example.security.model.RequiresPermission;
import com.example.security.service.RbacService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

@Component
public class RbacFilter implements HandlerInterceptor {

    @Autowired
    private RbacService rbacService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            if (method.isAnnotationPresent(RequiresPermission.class)) {
                RequiresPermission permission = method.getAnnotation(RequiresPermission.class);
                String resource = permission.resource();
                String action = permission.action();

                if (!rbacService.hasPermission(resource, action)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                    return false;
                }
            }
        }

        return true;
    }
}
