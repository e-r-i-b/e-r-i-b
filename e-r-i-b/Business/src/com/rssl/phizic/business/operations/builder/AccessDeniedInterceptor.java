package com.rssl.phizic.business.operations.builder;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.security.AccessControlException;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 14.05.2007
 * @ $Author: krenev $
 * @ $Revision: 12322 $
 */

public class AccessDeniedInterceptor implements MethodInterceptor, Serializable
{
	public static final MethodInterceptor INSTANCE = new AccessDeniedInterceptor();

	private AccessDeniedInterceptor()
	{
	}

	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable
	{
		throw new AccessControlException("Вызов метода запрещен " + method);
	}
}