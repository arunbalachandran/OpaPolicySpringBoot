package com.arunbalachandran.opapolicyspringboot.config;

import com.arunbalachandran.opapolicyspringboot.service.OpaService;
import com.arunbalachandran.opapolicyspringboot.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    @Getter
    @Setter
    public Object filterObject;

    @Getter
    @Setter
    public Object returnObject;

    @Setter
    private UserService userService;

    @Setter
    private OpaService opaService;

    @Override
    public Object getThis() {
        return this;
    }

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    /*
     * Call the OPA Policy engine to check whether the user has permissions.
     */
    public boolean hasAuthorization(String authToken) {
        return opaService.checkPermission(authToken);
    }
}
