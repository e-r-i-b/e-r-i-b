package com.rssl.ikfl.crediting;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Erkin
 * @ created 31.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Конфиг по задаче "Кредитование УКО"
 * + загрузка предодобренных предложений
 */
public class CreditingConfig extends Config
{
	private static final String ERIB_TO_CRM_QUEUE_NAME = "jms/esb/esbQueue";

	private static final String CRM_TO_ERIB_QUEUE_NAME = "jms/esb/esbOutQueue";

	private static final String CONNECTION_FACTORY_NAME = "jms/esb/esbQCF";

	private static final String CRM_SYSTEM_ID = "urn:sbrfsystems:99-crm";

	private static final String WAITING_TIME_SETTING = "com.rssl.iccs.crediting.waitingTime";

	private int waitingTime;

	/**
	 * ctor
	 * @param reader
	 */
	public CreditingConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		waitingTime = getIntProperty(WAITING_TIME_SETTING);
	}

	String getEribToCrmQueueName()
	{
		return ERIB_TO_CRM_QUEUE_NAME;
	}

	String getCrmToEribQueueName()
	{
		return CRM_TO_ERIB_QUEUE_NAME;
	}

	String getConnectionFactoryName()
	{
		return CONNECTION_FACTORY_NAME;
	}

	String getCrmSystemID()
	{
		return CRM_SYSTEM_ID;
	}

	public int getWaitingTime()
	{
		return waitingTime;
	}
}
