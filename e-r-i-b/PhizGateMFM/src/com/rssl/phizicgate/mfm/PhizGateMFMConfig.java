package com.rssl.phizicgate.mfm;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nady
 * @ created 22.09.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Конфиг для щлюза MFM
 */
public class PhizGateMFMConfig extends Config
{
	private static final String MFM_URL = "com.rssl.iccs.phiz.gate.mfm.url";

	private static final String MFM_LOGIN = "com.rssl.iccs.phiz.gate.mfm.login";

	private static final String MFM_PASSWORD = "com.rssl.iccs.phiz.gate.mfm.password";

	private static final String MFM_USE_LOGGING = "com.rssl.iccs.phiz.gate.mfm.logging";

	private String url;
	private String login;
	private String password;
	private Map<Long, String> accPriority;
	private boolean logging;
	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	public PhizGateMFMConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		url = getProperty(MFM_URL);
		login = getProperty(MFM_LOGIN);
		password = getProperty(MFM_PASSWORD);
		accPriority = new HashMap<Long, String>();
		for (long i=0; i<10; i++)
		{
			accPriority.put(i, getProperty("com.rssl.iccs.phiz.gate.mfm.sms.priority."+i));
		}
		logging = getBoolProperty(MFM_USE_LOGGING);
	}

	public String getUrl()
	{
		return url;
	}

	public String getLogin()
	{
		return login;
	}

	public String getPassword()
	{
		return password;
	}

	public String getPriorityByCode(Long code)
	{
		return accPriority.get(code);
	}

	public boolean isLogging()
	{
		return logging;
	}
}
