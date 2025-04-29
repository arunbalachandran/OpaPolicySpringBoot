package com.arunbalachandran.opapolicyspringboot.config;

import com.arunbalachandran.opapolicyspringboot.service.OpaService;
import com.arunbalachandran.opapolicyspringboot.service.UserService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component("customAuthorizationManager")
public class CustomAuthorizationManager implements AuthorizationManager<MethodInvocation> {

    private final UserService userService;

    private final OpaService opaService;

    public CustomAuthorizationManager(UserService userService, OpaService opaService) {
        this.userService = userService;
        this.opaService = opaService;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, MethodInvocation methodInvocation) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(methodInvocation.getMethod().getAnnotation(PreAuthorize.class).value());
        CustomMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler(userService, opaService);
        EvaluationContext evaluationContext = expressionHandler.createEvaluationContext(authenticationSupplier, methodInvocation);
        boolean granted = Boolean.TRUE.equals(expression.getValue(evaluationContext, Boolean.class));
        return new AuthorizationDecision(granted);
    }
}
