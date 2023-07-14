package com.rssl.phizgate.messaging.internalws.client;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.PropertyConfig;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author vagin
 * @ created 07.08.14
 * @ $Author$
 * @ $Revision$
 * ������ ������ ���������� ���������� ��� ������� ������� ��������� �������� �������. �� ������ �������� ������� �������� getServiceTimeOut()
 */
public class InternalServiceForMainPagesSender extends InternalServiceSender
{
	/**
	 * ctor
	 * @param destinationSystemName - �������, ���������� ���������
	 */
	public InternalServiceForMainPagesSender(String destinationSystemName)
	{
		super(destinationSystemName);
	}

	protected int getServiceTimeOut()
	{
		String timeout = ConfigFactory.getConfig(PropertyConfig.class).getProperty("com.rssl.phizgate.messaging.internalws.client.internalService.additional.timeout");
		return StringHelper.isEmpty(timeout) ? 0 : Integer.parseInt(timeout);
	}
}
