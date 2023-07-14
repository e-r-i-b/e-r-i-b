package com.rssl.phizic.mbk;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.messaging.ermb.ErmbJndiConstants;

/**
 * @author Gulov
 * @ created 24.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Конфиг для джоба P2PProxyJob
 */
public class P2PConfig extends Config
{
	/**
	 * конструктор
	 */
	public P2PConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
	}

	public String getProxyQCFName()
	{
		return ErmbJndiConstants.MBK_P2P_QCF;
	}

	public String getProxyQueueName()
	{
		return ErmbJndiConstants.MBK_P2P_QUEUE;
	}
}
