package com.blogpress.common.aspect;

import com.blogpress.common.util.AssertUtils;
import com.blogpress.common.util.ContextHolder;
import com.blogpress.user.bean.dto.UserDTO;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 登录检查切面
 * @author JY
 */
@Aspect
@Component
public class LoginCheckAspect {

    @Pointcut("@annotation(com.blogpress.common.annotation.RequireLogin)")
    public void pointCut() {}

    /**
     * 在方法执行前检查是否已经登录
     */
    @Before("pointCut()")
    public void loginCheck() {
        UserDTO user = ContextHolder.currentLoginUser();
        AssertUtils.isNotNull(user, "user.not.login");
    }

}
