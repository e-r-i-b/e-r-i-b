package com.rssl.phizicgate.wsgate.services.offices;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_Impl;
import com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_Stub;
import com.rssl.phizicgate.wsgate.services.types.OfficeImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 05.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class OfficeGateServiceWrapper extends JAXRPCClientSideServiceBase<OfficeGateService_PortType_Stub> implements OfficeGateService
{
	public OfficeGateServiceWrapper(GateFactory factory)
	{
		super(factory);
		String url = getFactory().config(WSGateConfig.class).getURL() + "/OfficeGateService";

		OfficeGateService_Impl service = new OfficeGateService_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((OfficeGateService_PortType_Stub) service.getOfficeGateService_PortTypePort(), url);
	}

	public Office getOfficeById(String id) throws GateException, GateLogicException
	{
		try
		{
			String encoded = encodeData(id);

			com.rssl.phizicgate.wsgate.services.offices.generated.Office office = getStub().getOfficeById(encoded);
			Office result = new OfficeImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(result, office, TypesCorrelation.types);
			return result;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<Office> getAll(Office template, int firstResult, int listLimit) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.offices.generated.Office office = new com.rssl.phizicgate.wsgate.services.offices.generated.Office();
			BeanHelper.copyPropertiesWithDifferentTypes(office, template, TypesCorrelation.types);
			List offices = getStub().getAll(office, firstResult, listLimit);
			List<Office> result = new ArrayList<Office>();
			for (Object o : offices)
			{
				Office tmp = new OfficeImpl();
				BeanHelper.copyPropertiesWithDifferentTypes(tmp, o, TypesCorrelation.types);
				result.add(tmp);
			}
			return result;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<Office> getAll(int firstResult, int maxResult) throws GateException, GateLogicException
	{
		try
		{
			List generatedOffices = getStub().getAll2(firstResult, maxResult);
			List<Office> offices = new ArrayList<Office>();

			for (Object o : generatedOffices)
			{
				Office tmp = new OfficeImpl();
				BeanHelper.copyPropertiesWithDifferentTypes(tmp, o, TypesCorrelation.types);
				offices.add(tmp);
			}
			return offices;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);
						
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
