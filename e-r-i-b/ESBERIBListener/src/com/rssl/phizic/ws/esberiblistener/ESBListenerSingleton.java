package com.rssl.phizic.ws.esberiblistener;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.ws.esberiblistener.depo.EsbEribDepoBackService;
import com.rssl.phizic.ws.esberiblistener.esberib.EsbEribBackService;
import com.rssl.phizic.ws.esberiblistener.pfr.EsbEribPFRBackService;
import com.rssl.phizic.gate.config.ESBEribConfig;

/**
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ESBListenerSingleton
{
	private static final EsbEribBackService esbEribBackService = createEsbEribBackService();
	private static final EsbEribDepoBackService esbEribDepoBackService = createEsbEribDepoBackService();
	private static final EsbEribPFRBackService esbEribPFRBackService = createEsbEribPFRBackService();

	private static EsbEribBackService createEsbEribBackService()
	{
		ESBEribConfig eribConfig = ConfigFactory.getConfig(ESBEribConfig.class);
		return (EsbEribBackService) newInstance(eribConfig.getBackServiceListener());
	}

	private static EsbEribDepoBackService createEsbEribDepoBackService()
	{
		ESBEribConfig eribConfig = ConfigFactory.getConfig(ESBEribConfig.class);
		return (EsbEribDepoBackService) newInstance(eribConfig.getBackServiceDepoListener());
	}

	private static EsbEribPFRBackService createEsbEribPFRBackService()
	{
		ESBEribConfig eribConfig = ConfigFactory.getConfig(ESBEribConfig.class);
		return (EsbEribPFRBackService) newInstance(eribConfig.getBackServicePFRListener());
	}

	private static Object newInstance(String backServiceListenerClass)
	{
		try
		{
			Class backServiceListener = ClassHelper.loadClass(backServiceListenerClass);
			return backServiceListener.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return Инстанс обработчика входящих сообщений от шины
	 */
	public static EsbEribBackService getEsbEribBackServiceImpl()
	{
		return esbEribBackService;
	}

	/**
	 * @return Инстанс обработчика входящих запросов по депозитарию от шины
	 */
	public static EsbEribDepoBackService getEsbEribDepoBackServiceImpl()
	{
		return esbEribDepoBackService;
	}

	/**
	 * @return Инстанс обработчика входящих запросов по ПФР от шины
	 */
	public static EsbEribPFRBackService getEsbEribPFRBackServiceImpl()
	{
		return esbEribPFRBackService;
	}
}
