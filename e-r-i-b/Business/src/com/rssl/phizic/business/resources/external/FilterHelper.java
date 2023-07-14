package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import net.sf.cglib.reflect.FastClass;
import org.apache.commons.collections.Predicate;

import java.lang.reflect.InvocationTargetException;

/**
 * ������ ��� ��������, �������� �� ������ ��� ��������� ������� ��� ���.
 *
 * @author bogdanov
 * @ created 13.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class FilterHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * �������� �� ������ ����� �������� ������.
	 *
	 * @param filterClassName ��� ������-�������.
	 * @param object ������ ��� ��������.
	 * @return ���������� ������ ������ ��� ���.
	 */
	public static boolean acceptObject(String filterClassName, Object object)
	{
		try
		{
			FastClass fastClass =  FastClass.create(Thread.currentThread().getContextClassLoader().loadClass(filterClassName));
			Object filterObject = fastClass.newInstance();
			if (!(filterObject instanceof Predicate))
				throw new RuntimeException("���������� ������� ������ ����������� " + Predicate.class.getName());

			Predicate filter = (Predicate) filterObject;
			return filter.evaluate(object);
		}
		catch (ClassNotFoundException ex)
		{
			log.error("����� " + filterClassName + " �� ������ ��� ������������� ����� " + FilterHelper.class.getName(), ex);
			throw new RuntimeException(ex);
		}
		catch (InvocationTargetException ex)
		{
			log.error("����� " + filterClassName + " �� �������������� ��� ������������� ����� " + FilterHelper.class.getName(), ex);
			throw new RuntimeException(ex);
		}
	}
}
