package com.rssl.phizic.gate.payments;

import com.rssl.phizic.config.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PropertyConfig;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

import java.util.Calendar;

/**
 * @author Novikov_A
 * @ created 04.06.2007
 * @ $Author$
 * @ $Revision$
* @deprecated Не удовлетворяет модели гейта.
 */

@Deprecated
public class StatusChanger
{
    private String propertyName;
	private String className;

	/**
	 * Установить имя проперти для хранения времени предыдущего запуска
	 * @param propertyName имя проперти
	 */
	public void setLastRunPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}

	/**
	 * Установить имя класса, реализующего интерфейс StatusChangerTask
	 * @param className имя класса
	 */
	public void setTaskClassName(String className)
	{
		this.className = className;
	}

	/**
	 * обновление статусов платежей
	 * @throws GateException
	 */
	public void update() throws GateException, GateLogicException
	{
		Calendar          currentDate = Calendar.getInstance();
		Calendar          startDate   = loadLastTime ();
		StatusChangerTask task        = createTask();

		task.updateStatus(startDate, currentDate);

		DbPropertyService.updateProperty(propertyName, XMLDatatypeHelper.formatDateTime(currentDate));
	}

	private StatusChangerTask createTask() throws GateException
	{
		StatusChangerTask task;
		try
		{
			Class<StatusChangerTask> object = ClassHelper.loadClass(className);
			task = object.newInstance();
		}
		catch (ClassNotFoundException e)
		{
			throw new GateException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new GateException(e);
		}
		catch (InstantiationException e)
		{
			throw new GateException(e);
		}
		return task;
	}

	private Calendar loadLastTime ()
	{
		Calendar startDate;

		String property = ConfigFactory.getConfig(PropertyConfig.class).getProperty(propertyName);

		if (property == null)
		   startDate = DateHelper.getPreviousMonth();
		else
		   startDate = XMLDatatypeHelper.parseDateTime(property);

		return startDate;
	}
}
