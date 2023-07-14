package com.rssl.phizic.business.operations.builder;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.MethodMatcher;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.utils.ClassHelper;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Evgrafov
 * @ created 16.03.2006
 * @ $Author: krenev $
 * @ $Revision: 12322 $
 */
public class OperationProxyBuilder
{
	private static final int NOOP_INDEX  = 0; // ������ � ������� ������������� ��� ������� ������������
	private static final int INDEX_SHIFT = 1; // ���������� ����������� �������������

	private class InternalFilter implements CallbackFilter
	{
		public int accept(Method method)
		{
			Integer index = methodIndexMap.get(method);
			return index == null ? NOOP_INDEX : index;
		}
	}

	private OperationDescriptor  descriptor;
	private List<MethodMatcher>  removeMatchers;
	private Callback[]           callbacks;
	private Class[]              callbackTypes;
	private Map<Method, Integer> methodIndexMap = new HashMap<Method, Integer>();

	/**
	 * ctor
	 * @param descriptor     �������� ��������
	 * @param removeMatchers ������ MethodMatcher's ������������ ����� ������ ������ ���� ��������� �� ��������
	 */
	public OperationProxyBuilder(OperationDescriptor descriptor, List<MethodMatcher> removeMatchers)
	{
		this.descriptor = descriptor;
		this.removeMatchers = removeMatchers;
	}

	private void createInterceptors(Class operationClass) throws BusinessException
	{
		Method[] methods = operationClass.getMethods();
		callbacks = new Callback[methods.length + INDEX_SHIFT];
		callbackTypes = new Class[methods.length + INDEX_SHIFT];

		// ������� ������� ��� ������������� ���� ��������� �������
		callbacks[NOOP_INDEX] = NoOpWraper.INSTANCE;
		callbackTypes[NOOP_INDEX] = NoOpWraper.class;

		for (int i = 0; i < methods.length; i++)
		{
			int callbackIndex = i + INDEX_SHIFT;
			Method method = methods[i];
			methodIndexMap.put(method, callbackIndex);

			boolean accessDenied = isAccessDenied(method);

			if (accessDenied)
			{
				callbacks[callbackIndex]     = AccessDeniedInterceptor.INSTANCE;
				callbackTypes[callbackIndex] = AccessDeniedInterceptor.class;
				continue;
			}

			boolean trancactional = method.getAnnotation(Transactional.class) != null;

			if (trancactional)
			{
				callbacks[callbackIndex] = new OperationTransactionalMethodInterceptor();
				callbackTypes[callbackIndex] = OperationTransactionalMethodInterceptor.class;
			}
			else
			{
				callbacks[callbackIndex] = NoOpWraper.INSTANCE;
				callbackTypes[callbackIndex] = NoOpWraper.class;
			}
		}
	}

	private boolean isAccessDenied(Method method)
	{
		for (MethodMatcher matcher : removeMatchers)
		{
			if (matcher.match(method))
				return true;
		}

		return false;
	}


	/**
	 * ������� ��������� ������� (����� � ���������� ������ �������)
	 * @return ��������� ��������
	 * @throws BusinessException
	 */
	public <T extends Operation> Class<T> createProxyFactory() throws BusinessException
	{
		Class<T> operationClass = loadOperationClass();

		createInterceptors(operationClass);

		Enhancer en = new Enhancer();
		en.setUseCache(true);
		en.setInterceptDuringConstruction(false);

		en.setCallbackTypes(callbackTypes);
		en.setCallbackFilter(new InternalFilter());
		en.setSuperclass(operationClass);

		Class generatedClass = en.createClass();
		Enhancer.registerStaticCallbacks(generatedClass, callbacks);
		//noinspection unchecked
		return generatedClass;
	}

	private <T extends Operation> Class<T> loadOperationClass() throws BusinessException
	{
		Class<T> operationClass = null;
		try
		{
			operationClass = ClassHelper.loadClass(descriptor.getOperationClassName());
		}
		catch (ClassNotFoundException e)
		{
			throw new BusinessException(e);
		}
		return operationClass;
	}
}