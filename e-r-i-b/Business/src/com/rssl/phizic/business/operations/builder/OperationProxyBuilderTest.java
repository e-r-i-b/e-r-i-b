package com.rssl.phizic.business.operations.builder;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.business.operations.config.OperationsConfig;
import com.rssl.phizic.business.operations.config.XmlOperationsConfig;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.MethodMatcher;
import com.rssl.phizic.business.operations.NameMatcher;
import com.rssl.phizic.config.ConfigFactory;
import net.sf.cglib.proxy.Factory;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ArrayList;
import java.security.AccessControlException;

/**
 * @author Evgrafov
 * @ created 16.03.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57925 $
 */

public class OperationProxyBuilderTest extends BusinessTestCaseBase
{
	public void testOperationLogCallbackFilter() throws Exception
	{
		OperationsConfig config = XmlOperationsConfig.get();
		OperationDescriptor od = config.findOperationByKey("CreateFormPaymentOperation");
		List<MethodMatcher> matchers = new ArrayList<MethodMatcher>();
		matchers.add(new NameMatcher("save"));

		OperationProxyBuilder operationProxyBuilder = new OperationProxyBuilder(od, matchers);

		Class proxyFactory = operationProxyBuilder.createProxyFactory();

		Object operation = proxyFactory.newInstance();

		Class clazz = ClassHelper.loadClass(od.getOperationClassName());

		assertTrue(Factory.class.isAssignableFrom(operation.getClass()));
		assertFalse(Factory.class.isAssignableFrom(operation.getClass().getSuperclass()));

		assertEquals(clazz, ClassHelper.getActualClass(operation));

		try
		{
			Method method = clazz.getMethod("saveAsTemplate", String.class);
			method.invoke(operation, "");
		}
		catch (Throwable e)
		{
			// так и надо, тут не важно сможет ли выполниться метод, главное чтоб запись об этом появилась
			e.toString();
		}

		try
		{
			Method method = clazz.getMethod("save");
			method.invoke(operation);
			fail("хачу ашипку");
		}
		catch (InvocationTargetException e)
		{
			assertTrue(e.getCause().getClass().equals(AccessControlException.class));
		}
	}
}
