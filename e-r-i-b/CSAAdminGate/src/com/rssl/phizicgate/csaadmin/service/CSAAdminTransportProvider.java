package com.rssl.phizicgate.csaadmin.service;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.csaadmin.service.generated.CSAAdminServiceSoapBindingStub;
import com.rssl.phizicgate.csaadmin.service.generated.CSAAdminService_ServiceLocator;
import com.rssl.phizicgate.csaadmin.service.generated.RequestType;
import com.rssl.phizicgate.csaadmin.service.generated.ResponseType;
import com.rssl.phizicgate.csaadmin.service.log.CSAAdminLogHelper;

import java.rmi.RemoteException;
import java.util.Calendar;
import javax.xml.rpc.ServiceException;

/**
 * @author akrenev
 * @ created 12.12.2013
 * @ $Author$
 * @ $Revision$
 *
 * Класс реализующий взаимодействие с ЦСА Админ
 */

class CSAAdminTransportProvider
{
	private static CSAAdminServiceSoapBindingStub getStub() throws GateException
	{
		try
		{
			CSAAdminGateConfig config = ConfigFactory.getConfig(CSAAdminGateConfig.class);
			String url = config.getListenerUrl() + "/CSAAdminService";
			CSAAdminService_ServiceLocator locator = new CSAAdminService_ServiceLocator();
			CSAAdminServiceSoapBindingStub stub = (CSAAdminServiceSoapBindingStub) locator.getCSAAdminService();
			stub._setProperty(org.apache.axis.client.Stub.ENDPOINT_ADDRESS_PROPERTY, url);
			stub.setTimeout(config.getListenerTimeout());
			return stub;
		}
		catch (ServiceException e)
		{
			throw new GateException(e);
		}
	}

	final static ResponseType process(RequestType data) throws GateException, GateLogicException
	{
		CSAAdminServiceSoapBindingStub stub = getStub();
		Calendar start = Calendar.getInstance();
		Calendar end = null;
		try
		{
			ResponseType response = stub.exec(data);
			end = Calendar.getInstance();
			return response;
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
		finally
		{
			if (end == null)
				end = Calendar.getInstance();

			CSAAdminLogHelper.writeToLog(stub._getCall().getMessageContext(), DateHelper.diff(end, start));
		}
	}
}
