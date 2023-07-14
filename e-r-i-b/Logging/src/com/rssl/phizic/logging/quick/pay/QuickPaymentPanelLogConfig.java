package com.rssl.phizic.logging.quick.pay;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.writers.QuickPaymentPanelLogWriter;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author lukina
 * @ created 04.08.2014
 * @ $Author$
 * @ $Revision$
 */
public class QuickPaymentPanelLogConfig   extends Config
{
	public static final String WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.QuickPaymentPanelLogWriter";
	public static final String LOGGING_ON_KEY = "com.rssl.phizic.logging.quick.pay.QuickPaymentPanelLog.on";

	private QuickPaymentPanelLogWriter writer;
	private boolean loggingOn;

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 * @param reader �����.
	 */
	public QuickPaymentPanelLogConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return writer
	 */
	public QuickPaymentPanelLogWriter getWriter()
	{
		return writer;
	}

	/**
	 * @return true - ������������ ��������
	 */
	public boolean isLoggingOn()
	{
		return loggingOn;
	}

	protected void doRefresh() throws ConfigurationException
	{
		String className = getProperty(WRITER_CLASS_KEY);
		if (writer == null || !writer.getClass().getName().equals(className))
		{
			writer = loadWriter(className);
		}
		loggingOn = getBoolProperty(LOGGING_ON_KEY);
	}

	private QuickPaymentPanelLogWriter loadWriter(String className)
	{
		if (StringHelper.isEmpty(className))
		{
			return null;
		}

		try
		{
			return (QuickPaymentPanelLogWriter) ClassHelper.loadClass(className).newInstance();
		}
		catch (Exception e)
		{
			throw new ConfigurationException("������ �������� " + className, e);
		}
	}
}

