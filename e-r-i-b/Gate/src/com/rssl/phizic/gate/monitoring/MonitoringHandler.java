package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author mihaylov
 * @ created 06.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ������ �� ������� ������� ������ ��� ����� �����������
 */
public abstract class MonitoringHandler
{
	private static final Object LOCKER = new Object();
	private static final String MONITORING_HANDLER_CLASS_NAME = "com.rssl.phizic.gate.monitoring.MonitoringHandler";

	private static volatile MonitoringHandler handler = null;

	/**
	 * ���������� ������ � ���������� ������� �� ������� �������
	 * @param requestTag - ��� �������(�������� ����)
	 * @param time - ����� ���������� ������� �� ������� �������(� �������������)
	 * @param errorCode - ��� ������ ��������� ������� �� ������� �������
	 */
	public abstract void processRequest(String requestTag, long time, String errorCode);

	/**
	 * ��������� ������ �� �����������.
	 * @param requestTag - ��� �������(�������� ����)
	 */
	public abstract void checkRequest(String requestTag) throws GateLogicException;

	/**
	 * ���������� ������ �� ������
	 * @param requestTag - ��� �������(�������� ����)
	 */
	public abstract void processError(String requestTag);

	/**
	 * ���������� ��������� �� ������ �� ������ �� ������� �������
	 * @param responceTag - ��� ������ �� ������� �������
	 * @param errorCode - ��� ������
	 */
	public abstract void processResponce(String responceTag,String errorCode);

	/**
	 * ���������� ��������� �������� ����� �� ������� �������
	 * @param responceTag - ��� ������ �� ������� �������
	 */
	public abstract void processOk(String responceTag);

	/**
	 * ��������� �������� ����������� ��� �������� �����
	 * @return
	 * @throws GateException
	 */
	public static MonitoringHandler getInstance() throws GateException
	{
		if(handler == null)
		{
			synchronized (LOCKER)
			{
				if(handler == null)
				{
					String handlerClassName = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(MONITORING_HANDLER_CLASS_NAME);
					if(StringHelper.isEmpty(handlerClassName))
						handler = new EmptyMonitoringHandler();
					else
					{
						try
						{
							Class<MonitoringHandler> handlerClass = ClassHelper.loadClass(handlerClassName);
							handler = handlerClass.newInstance();
						}
						catch (Exception e)
						{
							throw new GateException(e);
						}
					}
				}
			}
		}
		return handler;
	}
}
