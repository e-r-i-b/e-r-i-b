/**
 * EribRatesImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.currency.sbrf;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.gate.config.ESBEribConfig;

import java.rmi.RemoteException;

/**
 * Веб служба по приему, анализу и обновлению информации о валюте
 */
public class EribRatesImpl implements com.rssl.phizic.ws.currency.sbrf.EribRates_PortType
{
	private static final EsbEribRatesBackService esbEribBackService = createEsbEribRatesBackService();

	public String doIFX(String req) throws RemoteException
	{
		return esbEribBackService.doIFX(req);
	}

	private static EsbEribRatesBackService createEsbEribRatesBackService()
	{
		try
		{
			ESBEribConfig eribConfig = ConfigFactory.getConfig(ESBEribConfig.class);
			Class backServiceListener = ClassHelper.loadClass(eribConfig.getRatesBackServiceListener());
			return (EsbEribRatesBackService) backServiceListener.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
