package com.rssl.phizicgate.wsgateclient.services.statistics.exception;

import com.rssl.phizgate.common.services.StubUrlBackServiceWrapper;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.statistics.exception.ExceptionStatisticService;
import com.rssl.phizic.gate.statistics.exception.ExternalExceptionInfo;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgateclient.WSGateClientException;
import com.rssl.phizicgate.wsgateclient.WSTemporalGateClientException;
import com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExceptionStatisticServiceImpl_Impl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.rmi.RemoteException;

/**
 * @author akrenev
 * @ created 16.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * враппер сервиса сбора статистики по исключениям
 */

public class ExceptionStatisticServiceWrapper extends AbstractService implements ExceptionStatisticService
{
	private StubUrlBackServiceWrapper<com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExceptionStatisticService> wrapper;

	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public ExceptionStatisticServiceWrapper(GateFactory factory)
	{
		super(factory);
		wrapper = new StubUrlBackServiceWrapper<com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExceptionStatisticService>("ExceptionStatisticServiceImpl"){
			protected void createStub()
			{
				ExceptionStatisticServiceImpl_Impl service = new ExceptionStatisticServiceImpl_Impl();
				WSRequestHandlerUtil.addWSRequestHandlerToService(service);
				setStub(service.getExceptionStatisticServicePort());
			}
		};
	}

	public String addException(ExternalExceptionInfo exceptionInfo) throws GateLogicException, GateException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo gateInstance =
					new com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo();
			BeanHelper.copyPropertiesWithDifferentTypes(gateInstance, exceptionInfo, TypesCorrelation.getTypes());
			return wrapper.getStub().addException(gateInstance);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
