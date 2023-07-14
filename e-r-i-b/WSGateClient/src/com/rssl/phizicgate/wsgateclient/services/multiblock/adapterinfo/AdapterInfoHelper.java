package com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.*;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * @author gladishev
 * @ created 08.11.13
 * @ $Author$
 * @ $Revision$
 */
public class AdapterInfoHelper
{
	/**
	 * Получение адреса обработчика сообщений от ВС
	 * @param url - адрес серверной части веб-сервиса
	 * @param adapterUUID - uuid адаптера
	 * @return адрес обработчика
	 */
	public static String getAdapterListenerUrl(String url, String adapterUUID) throws GateException
	{
		try
		{
			AdapterInfoRq rq = new AdapterInfoRq();
			rq.setUuid(adapterUUID);
			AdapterInfoRs adapterInfoRs = getStub(url).adapterInfo(rq);

			StatusType status = adapterInfoRs.getStatus();
			if (status.getStatusCode() != 0L)
				throw new GateException(status.getStatusDescription());

			return adapterInfoRs.getAddress();
		}
		catch (ServiceException e)
		{
			throw new GateException(e);
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
	}

	private static AdapterInfoServiceSoapBindingStub getStub(String url) throws javax.xml.rpc.ServiceException
	{
		AdapterInfoServiceImplLocator locator = new AdapterInfoServiceImplLocator();
		locator.setAdapterInfoServicePortEndpointAddress(url);
		return (AdapterInfoServiceSoapBindingStub) locator.getAdapterInfoServicePort();
	}
}
