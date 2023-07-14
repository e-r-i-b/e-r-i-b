package com.rssl.phizic.messaging;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * ������ � ����������� ��� ������������� �������
 * @author Rtischeva
 * @ created 04.08.14
 * @ $Author$
 * @ $Revision$
 */
public class MessagingConfig extends Config
{
	private Long numberOfIMSICheckTries = null; //��������� "���������� ������� ��������� ����������� �������� IMSI"
	private Long imsiCheckDelayTime = null; //��������� "�������� ����� ��������� ��������� ����������� �������� IMSI"
	private boolean ermbSmsRequestIdSettingEnabled; //������ "�������� �� �������� �������������� ���-������� � ��������� JMS ���������"
	private boolean ermbTransportUse; //��������� "���������� ��� ��������� � MSS"

	public MessagingConfig(PropertyReader reader)
	{
		super(reader);
	}

	public Long getNumberOfIMSICheckTries()
	{
		return numberOfIMSICheckTries;
	}

	public Long getImsiCheckDelayTime() throws ConfigurationException
	{
		return imsiCheckDelayTime;
	}

	public boolean isErmbSmsRequestIdSettingEnabled()
	{
		return ermbSmsRequestIdSettingEnabled;
	}

	public boolean isErmbTransportUse()
	{
		return ermbTransportUse;
	}

	public void doRefresh() throws ConfigurationException
	{
		numberOfIMSICheckTries = getLongProperty("ermb.transport.tries");
		imsiCheckDelayTime = getLongProperty("ermb.transport.delay.time");
		ermbSmsRequestIdSettingEnabled = getBoolProperty("ermb.smsRequestId.setting.enabled");
		ermbTransportUse = getBoolProperty("ermb.transport.use");
	}
}
