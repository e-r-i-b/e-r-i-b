package com.rssl.phizic;

import com.rssl.phizic.common.types.annotation.Singleton;
import com.rssl.phizic.utils.ClassHelper;

/**
 * @author Erkin
 * @ created 02.08.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ������� � ������������� ��������
 */
@Singleton
public final class RequestCoordinatorSingleton
{
	private static final RequestCoordinatorSingleton instance = new RequestCoordinatorSingleton();

	private final PhizMBRequestCoordinator phizMBRequestCoordinator;

	private RequestCoordinatorSingleton()
	{
		phizMBRequestCoordinator = (PhizMBRequestCoordinator) loadRequestCoordinator("com.rssl.phizic.PhizMBRequestCoordinatorImpl");
	}

	private RequestCoordinator loadRequestCoordinator(String coordinatorClassName)
	{
		try
		{
			Class<? extends RequestCoordinator> coordinatorClass = ClassHelper.loadClass(coordinatorClassName);
			return coordinatorClass.newInstance();
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return ����������� �������� �� ����
	 */
	public static PhizMBRequestCoordinator getPhizMBRequestCoordinator()
	{
		return instance.phizMBRequestCoordinator;
	}
}
