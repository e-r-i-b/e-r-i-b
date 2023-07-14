package com.rssl.phizic.logging.operations;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import com.rssl.phizic.logging.Constants;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 26.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class ChangeUserTypeLogDataWrapper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public static final String KEY_PREFIX = "com.rssl.phizic.persons.";

	public static final String ADD_KEY    = "Add";
	public static final String REMOVE_KEY = "Remove";
	public static final String CLIENT_KEY = "Client";
	public static final String IN_DEPARTMENT_KEY = "InDepartment";

	private static final String ADD_DESCRIPTION    = "ƒобавление договора клиента.";
	private static final String REMOVE_DESCRIPTION = "”даление договора клиента.";

	private static LogDataReader getAddingClientLogDataReader(String creationType, Long departmentId)
	{
		String key = KEY_PREFIX + ADD_KEY + creationType + CLIENT_KEY + IN_DEPARTMENT_KEY + departmentId;
		return new DefaultLogDataReader(ADD_DESCRIPTION, key, key);
	}

	private static LogDataReader getRemoveClientLogDataReader(String creationType, Long departmentId)
	{
		String key = KEY_PREFIX + REMOVE_KEY + creationType + CLIENT_KEY + IN_DEPARTMENT_KEY + departmentId;
		return new DefaultLogDataReader(REMOVE_DESCRIPTION, key, key);
	}

	/**
	 * «апись в лог добавлени€ договора клиента
	 * @param creationType тип договора клиента
	 */
	public static void writeAddingClientLogData(String creationType, Long departmentId)
	{
		writeLogData(getAddingClientLogDataReader(creationType, departmentId));
	}

	/**
	 * «апись в лог обновлени€ договора клиента
	 * @param fromCreationType старый тип договора клиента
	 * @param toCreationType новый тип договора клиента
	 */
	public static void writeUpdateClientLogData(String fromCreationType, String toCreationType, Long departmentId)
	{
		writeLogData(getRemoveClientLogDataReader(fromCreationType, departmentId));
		writeLogData(getAddingClientLogDataReader(toCreationType, departmentId));
	}

	/**
	 * «апись в лог удалени€ договора клиента 
	 * @param creationType тип договора клиента
	 */
	public static void writeRemoveClientLogData(String creationType, Long departmentId)
	{
		writeLogData(getRemoveClientLogDataReader(creationType, departmentId));
	}

	private static void writeLogData(LogDataReader logDataReader)
	{
		try
		{
			OperationLogFactory.getLogWriter().writeActiveOperation(logDataReader, Calendar.getInstance(), Calendar.getInstance());
		}
		catch (Exception e)
		{
			log.error("ќшибка при записи в лог изменени€ договора клиента.", e);
		}
	}
}
