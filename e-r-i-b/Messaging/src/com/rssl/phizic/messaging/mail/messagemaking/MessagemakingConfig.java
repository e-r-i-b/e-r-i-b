package com.rssl.phizic.messaging.mail.messagemaking;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 16.03.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public abstract class MessagemakingConfig extends Config
{
	protected MessagemakingConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * �������� ����� ���������������� MessageBuilder ��� ��������� ������ ��������
	 * @param channel ����� ��������
	 * @return ��� ������
	 */
	public abstract String getMessageBuilderClassName(String channel);

	/**
	 * �������� ����� ���������������� AddressBuilder ��� ��������� ������ ��������
	 * @param channel ����� ��������
	 * @return ��� ������
	 */
	public abstract String getAddressBuilderClassName(String channel);

	/**
	 * �������� ����� ���������������� TemplateLoader ��� ��������� ������ ��������
	 * @param channel ����� ��������
	 * @return ��� ������
	 */
	public abstract String getResourceLoaderClassName(String channel);

	/**
	 * @return ������ ���� ������� ��������
	 */
	public abstract List<String> getAllChannels();
}
