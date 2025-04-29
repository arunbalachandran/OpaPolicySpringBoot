package com.arunbalachandran.opapolicyspringboot.config;

import com.arunbalachandran.opapolicyspringboot.service.OpaService;
import com.arunbalachandran.opapolicyspringboot.service.UserService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private final UserService userService;
    private final OpaService opaService;

    public CustomMethodSecurityExpressionHandler(UserService userService, OpaService opaService) {
        this.userService = userService;
        this.opaService = opaService;
    }

    @Override
    public EvaluationContext createEvaluationContext(Supplier<Authentication> authenticationSupplier, MethodInvocation methodInvocation) {
        StandardEvaluationContext evaluationContext = (StandardEvaluationContext) super.createEvaluationContext(authenticationSupplier, methodInvocation);
        MethodSecurityExpressionOperations delegate = (MethodSecurityExpressionOperations) evaluationContext.getRootObject().getValue();
        CustomMethodSecurityExpressionRoot expressionRoot = new CustomMethodSecurityExpressionRoot(delegate.getAuthentication());
        expressionRoot.setUserService(userService);
        expressionRoot.setOpaService(opaService);
        evaluationContext.setRootObject(expressionRoot);
        return evaluationContext;
    }
}
