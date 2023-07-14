package com.rssl.phizic.gate.impl;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.logging.messaging.System;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: Moshenko
 * Date: 27.07.2012
 * Time: 14:30:36
 */
public abstract class AbstractDataSourceServiceGate extends AbstractService implements Service
{
	protected static final Log log = LogFactory.getLog(AbstractDataSourceServiceGate.class);
	private volatile JDBCActionExecutor executor = null;
	private final Object lock = new Object();

	protected AbstractDataSourceServiceGate(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Получить имя датасорца.
	 * @return имя датасорца
	 */
	protected abstract String getDataSourceName();

	/**
	 * Получить систему.
	 * @return система
	 */
	protected abstract System getSystem();

	protected JDBCActionExecutor getJDBCActionExecutor()
	{
		if (executor != null)
		{
			return executor;
		}
		synchronized (lock)
		{
			if (executor != null)
			{
				return executor;
			}
			executor = createExecutor(getDataSourceName());
		}
		return executor;
	}

	protected <T> T executeJDBCAction(JDBCAction<T> action) throws SystemException
	{
		return getJDBCActionExecutor().execute(action);
	}

	protected JDBCActionExecutor createExecutor(String dataSourceName)
	{
		return new JDBCActionExecutor(dataSourceName, getSystem());
	}
}
