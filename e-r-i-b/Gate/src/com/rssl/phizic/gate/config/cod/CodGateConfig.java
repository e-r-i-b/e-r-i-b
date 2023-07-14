package com.rssl.phizic.gate.config.cod;

import com.rssl.phizic.config.*;

/**
 * @author Omeliyanchuk
 * @ created 17.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class CodGateConfig extends Config
{
	// URL webservice ЦОД
	public static final String WS_URL_COD_KEY = "com.rssl.phisicgate.sbrf.ws.cod.url";
	//URL webservice собственной разработки сбера для передачи необработанных нами (не принадлежищих нам ) сообшений
	public static final String SBOL_URL_KEY = "com.rssl.phisicgate.sbrf.ws.sbol.url";
	/**
	 * Префикс для параметра в настройках для URl перехода.
	 */
	private static final String URL_PREFIX_PARAMETER = "com.rssl.phisicgate.sbrf.ws.cod.url.";

	public CodGateConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
	}

	public String getCodUrl()
	{
		return getProperty(WS_URL_COD_KEY);
	}

	public String getSBOLUrl()
	{
		return getProperty(SBOL_URL_KEY);
	}

	public String getUrlToRoute(String routeCode)
	{
		return getProperty(URL_PREFIX_PARAMETER + routeCode);
	}
}
