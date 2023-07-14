package com.rssl.phizgate.common.messaging.retail;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Omeliyanchuk
 * @ created 25.11.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class RetailMessagingConfig extends Config
{
	public static final String CONFIG_FILE_PATH = "com.rssl.gate.retail.configFilePath";
	public static final String SYSTEM_NAME = "com.rssl.gate.retail.systemName";
	public static final String RESPONCE_HANDLER = "com.rssl.gate.retail.responceHandlerClass";

	protected RetailMessagingConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ���� � retail-message-config.xml
	 * @return
	 */
	public abstract String getConfigFilePath();

	/**
	 * ������������� ������� ������� ��� ����
	 * @return
	 */
	public abstract String getSystemName();

	/**
	 * ����� ��� ������� �������
	 * @return ������ ��� ������.
	 */
	public abstract String getResponceHandlerClass();

}
