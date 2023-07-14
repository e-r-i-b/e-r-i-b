package com.rssl.phizic.web.actions;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.clazz.ClassFilter;
import com.rssl.phizic.utils.clazz.ClassFinder;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author Evgrafov
 * @ created 08.12.2006
 * @ $Author: balovtsev $
 * @ $Revision: 17865 $
 */
public class ActionsTest extends BusinessTestCaseBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final Class   actionClazz = LookupDispatchAction.class;
	private static final Class[] types =
			{
					ActionMapping.class,
					ActionForm.class,
					HttpServletRequest.class,
					HttpServletResponse.class
			};
	private List<Class> actions;

	private static class ActionsFilter implements ClassFilter
	{
		public boolean accept(Class clazz)
		{
			return actionClazz.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers());
		}
	}

	private long errors1;
	private long errors2;
	private long errors3;
	private long totalMappedMethods;

	private Set<String> wrongMethods = new HashSet<String>();

	protected void setUp() throws Exception
	{
		super.setUp();
		actions = findActions();

		wrongMethods.add("saveAndGo");
		wrongMethods.add("cancel");
		wrongMethods.add("add");

		errors1 = 0;
		errors2 = 0;
		errors3 = 0;
		totalMappedMethods = 0;
	}

	/**
	 * <pre>
	 * Проверка struts actions
	 *
	 * error1 - на то что они создаютя и что у них есть методы описанные в action.getKeyMethodMap()
	 *
	 * error2 - на Deprecated методы:
	 * вместо saveAndGo - leftMenuLink
	 * вместо методов add & cancel надо использовать клиентскую кнопку (clientButton)
	 * подробности в документации (ПОЖАЛУЙСТА НЕ ИСПРАВЛЯЙТЕ ПЕРЕИМЕНОВАНИЕМ!!!)
	 *
	 * error3 - на то что все публичные методы с сигнатурой
	 * <code>public ActionForward method(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)</code>
	 * присутствуют в action.getKeyMethodMap()
	 * </pre>
	 */
	public void testMethods() throws Exception
	{
		for (Class actionClazz : actions)
		{
			try
			{
				validateAction(actionClazz);
			}
			catch (Exception e)
			{
				log.error("Ошибка при проверке Action " +
						"\nclass :" + actionClazz.getName() +
						" \ncause " + e +
						"\n");
				errors1++;
			}
		}

		assertEquals("\nПроверено " + actions.size() + " класса(ов) " + totalMappedMethods + " метода(ов) " +
				"\nerror1=" + errors1 +
				"\nerror2=" + errors2 +
				"\nerror3=" + errors3 +
				"\n",
				0,
				errors1 + errors2 + errors3);
	}
	private List<Class> findActions() throws ClassNotFoundException
	{
		ClassFinder classFinder = new ClassFinder("com.rssl.phizic.web", true);

		List<Class> list = classFinder.find(new ActionsFilter());
		Collections.sort(list, new Comparator<Class>()
		{
			public int compare(Class o1, Class o2)
			{
				return o1.getName().compareTo(o2.getName());
			}
		});
		return list;
	}

	private void validateAction(Class actionClazz) throws Exception
	{
		LookupDispatchAction action = (LookupDispatchAction) actionClazz.newInstance();
		Map<String, String> methodMap = action.getKeyMethodMap();
		Set<Map.Entry<String,String>> entries = methodMap.entrySet();
		Set<String> mappedMethods = new HashSet<String>();

		for (Map.Entry<String, String> entry : entries)
		{
			try
			{
				mappedMethods.add(entry.getValue());
				validateMethod(entry, actionClazz);
			}
			catch (Exception e)
			{
				log.error("Ошибка при проверке Action " +
						"\nclass :" + actionClazz.getName() +
						"\nmethod : " + entry.getKey() + " -> " + entry.getValue() +
						"\ncause " + e +
						"\n");
				errors1++;
			}
		}



		Method[] methods = actionClazz.getMethods();
		for (Method method : methods)
		{
			if(hasProperSignature(actionClazz, method))
			{
				if(!mappedMethods.contains(method.getName()))
				{
					log.error("Ошибка при проверке Action " +
							"\nclass :" + actionClazz.getName() +
							"\nmethod : " + method.getName() +
							"\ncause Method absent in action.getKeyMethodMap()" +
							"\n");
					errors3++;
				}

				if (wrongMethods.contains(method.getName()))
				{
					log.error("Ошибка при проверке Action " +
							"\nclass :" + actionClazz.getName() +
							"\nmethod : " + method.getName() +
							"\ncause Deprecated method" +
							"\n");
					errors2++;
				}

			}
		}
	}

	private boolean hasProperSignature(Class actionClazz, Method method)
	{
		//noinspection UnusedCatchParameter
		try
		{
			if (!method.getReturnType().equals(ActionForward.class))
				return false;

			if(method.getName().equals("execute"))
				return false;

			if (method.getName().equals("start"))
				return false;

			actionClazz.getMethod(method.getName(), types);

			return true;
		}
		catch (NoSuchMethodException e)
		{
			return false;
		}
	}

	private void validateMethod(Map.Entry<String, String> entry, Class actionClazz)
			throws Exception
	{
		totalMappedMethods++;

		String methodName = entry.getValue();

		Method method = actionClazz.getMethod(methodName, types);
		if(method == null)
			throw new NullPointerException();
	}
}