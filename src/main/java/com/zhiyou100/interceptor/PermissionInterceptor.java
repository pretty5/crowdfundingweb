package com.zhiyou100.interceptor;

import com.zhiyou100.annotation.Permission;
import com.zhiyou100.entity.AdminEntity;
import com.zhiyou100.service.AdminService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/*
*@ClassName:PermissionInterceptor
 @Description:TODO
 @Author:
 @Date:2018/9/19 9:21 
 @Version:v1.0
*/
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AdminEntity admin = (AdminEntity) request.getSession().getAttribute("admin");
        if (admin==null){
            return  false;
        }
        HandlerMethod method = (HandlerMethod) handler;
        boolean present = method.getMethod().isAnnotationPresent(Permission.class);
        //如果方法上有此注解   则需要进行权限控制  如果没有 则认为所有人都可以访问
        if (!present){
            return true;
        }else{
            if (admin.getRoles()==null){
                admin.setRoles(adminService.findRolesById(admin.getId()));
            }
            List<String> roleList = admin.getRoles();
            //获取方法上的   权限注解
            String[] roles = method.getMethod().getAnnotation(Permission.class).role();

            Collection intersection = CollectionUtils.intersection(roleList, Arrays.asList(roles));

            if (intersection.size()>0){
                return  true;
            }else{
                return false;
            }

        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
