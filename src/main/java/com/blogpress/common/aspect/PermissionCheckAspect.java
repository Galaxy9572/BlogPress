package com.blogpress.common.aspect;

import com.blogpress.common.annotation.Permission;
import com.blogpress.common.enums.role.RoleEnum;
import com.blogpress.common.util.AssertUtils;
import com.blogpress.common.util.ContextHolder;
import com.blogpress.user.bean.dto.UserDTO;
import com.blogpress.user.bean.dto.UserRoleDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限检查切面
 * @author JY
 */
@Aspect
@Component
public class PermissionCheckAspect {

    @Pointcut("@annotation(com.blogpress.common.annotation.Permission)")
    public void pointCut() {}

    /**
     * 在方法执行前检查权限
     */
    @Before(value = "pointCut()")
    public void permissionCheck(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Permission permission = method.getAnnotation(Permission.class);
        boolean needLogin = permission.needLogin();
        RoleEnum[] roles = permission.roles();

        if (!needLogin) {
            return;
        }

        UserDTO user = ContextHolder.currentLoginUser();
        AssertUtils.isNotNull(user, "user.not.login");

        List<UserRoleDTO> userRoles = user.getRoles();
        boolean isRoleValid = isRoleValid(roles, userRoles);
        AssertUtils.isTrue(isRoleValid, HttpStatus.FORBIDDEN, "permission.denied");
    }

    private static boolean isRoleValid(RoleEnum[] roles, List<UserRoleDTO> userRoles){
        if (roles == null || roles.length == 0){
            return true;
        }
        if (CollectionUtils.isEmpty(userRoles)) {
            return false;
        }
        List<String> needRoleCode = Arrays.stream(roles).map(RoleEnum::getCode).collect(Collectors.toList());
        List<String> actualRoleCode = userRoles.stream().map(UserRoleDTO::getCode).collect(Collectors.toList());
        actualRoleCode.retainAll(needRoleCode);
        return !CollectionUtils.isEmpty(actualRoleCode);
    }

}
