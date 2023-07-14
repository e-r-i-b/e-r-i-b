package com.rssl.phizic.business.operations.builder;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.hibernate.Session;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author Roshka
 * @ created 10.03.2006
 * @ $Author$
 * @ $Revision$
 * Интерсептор для выполнения метода в транзакции.
 */
class OperationTransactionalMethodInterceptor implements MethodInterceptor, Serializable
{
	private class InvokeException extends Exception
	{
		InvokeException(Throwable throwable)
		{
			super(throwable);
		}
	}

	OperationTransactionalMethodInterceptor()
	{
	}

	public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					try
					{
						return proxy.invokeSuper(obj, args);
					}
					catch (Throwable throwable)
					{
						throw new InvokeException(throwable);
					}
				}
			});
		}
		catch (InvokeException e)
		{
			// кидаем исключение из-за которого была неудача
			throw e.getCause();
		}
	}
}