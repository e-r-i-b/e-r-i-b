package test.cglib;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Evgrafov
 * @ created 15.02.2006
 * @ $Author: Roshka $
 * @ $Revision: 911 $
 */
public class TestProxy implements MethodInterceptor
{
	private static final Class[] CALLBACK_TYPES = new Class[]{MethodInterceptor.class, NoOp.class};

	private static final CallbackFilter FILTER = new CallbackFilter()
	{
		public int accept(Method method)
		{
			if(method.getParameterTypes().length != 0)
			{
				//System.out.println("not accept " + method.getName());
				return 1;
			}
			else
			{
				//System.out.println("accept " + method.getName());
				return 0;
			}
		}
	};
	public static final Callback[] CALLBACKS = new Callback[]{new TestProxy(), NoOp.INSTANCE};

	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable
	{
		Object result = null;
		String name = method.getName();
		Class clazz = obj.getClass().getSuperclass();
		String clazzName = clazz.getName();

		try
		{
			if (name.equals("doSomething") && clazzName.equals("test.cglib.TestOperation") )
				System.out.println("Before intercept: " + method.getName());

			//System.out.println("before intercept " + method.getName());
			if( !Modifier.isAbstract(method.getModifiers()) )
				result = proxy.invokeSuper(obj, args);
		}
		finally
		{
			if(name.equals("doSomething") && clazzName.equals("test.cglib.TestOperation") )
			   System.out.println("After intercept: " + method.getName() );
			//System.out.println("after  intercept " + method.getName());
		}
		return result;
	}

	public static Object newInstance(Class clazz)
	{
		Enhancer en = new Enhancer();
		en.setUseCache(false);
		en.setInterceptDuringConstruction(false);

		en.setCallbackTypes(CALLBACK_TYPES);
		en.setCallbackFilter(FILTER);
		en.setSuperclass(clazz);
		en.setCallbacks(CALLBACKS);

		return en.create();
	}

	public static Class createFactory(Class clazz)
	{
		Enhancer en = new Enhancer();
		en.setUseCache(false);
		en.setInterceptDuringConstruction(false);

		en.setCallbackTypes(CALLBACK_TYPES);
		en.setCallbackFilter(FILTER);
		en.setSuperclass(clazz);

		return en.createClass();
	}

}