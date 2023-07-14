package com.rssl.phizicgate.wsgate.services.registration;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.registration.generated.CancelationCallBackImpl;
import com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_Stub;
import com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_Server_Impl_Impl;
import com.sun.xml.rpc.client.ClientTransportException;
import org.w3c.dom.Document;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Calendar;

/**
 * @author hudyakov
 * @ created 12.07.2009
 * @ $Author$
 * @ $Revision$
 */

public class RegistartionClientServiceWrapper extends JAXRPCClientSideServiceBase<RegistartionClientService_PortType_Stub> implements RegistartionClientService
{
	public RegistartionClientServiceWrapper(GateFactory factory)
	{
		super(factory);
		String url = factory.config(WSGateConfig.class).getURL() + "/RegistartionClientService_Server_Impl";
		RegistartionClientService_Server_Impl_Impl service = new RegistartionClientService_Server_Impl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((RegistartionClientService_PortType_Stub) service.getRegistartionClientService_PortTypePort(), url);
	}

	public void register(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		com.rssl.phizicgate.wsgate.services.registration.generated.Office generated = new com.rssl.phizicgate.wsgate.services.registration.generated.Office();

		try
		{
			BeanHelper.copyPropertiesWithDifferentTypes(generated, office, RegistartionClientServiceTypeCorrelation.toGeneratedTypes);
			String encoded = encodeData(generated.getSynchKey());
			generated.setSynchKey(encoded);
			getStub().register(generated, convert2String(registerRequest));
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

	public void update(Client client, Calendar lastUpdateDate, boolean isNew, User user) throws GateLogicException, GateException
	{
		com.rssl.phizicgate.wsgate.services.registration.generated.Client generatedClient = new com.rssl.phizicgate.wsgate.services.registration.generated.Client();
		com.rssl.phizicgate.wsgate.services.registration.generated.User generatedUser = new com.rssl.phizicgate.wsgate.services.registration.generated.User();
		try
		{
			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, client, RegistartionClientServiceTypeCorrelation.toGeneratedTypes);
			BeanHelper.copyPropertiesWithDifferentTypes(generatedUser, user, RegistartionClientServiceTypeCorrelation.toGeneratedTypes);
			String encoded = encodeData(generatedClient.getId());
			generatedClient.setId(encoded);
			getStub().update(generatedClient, lastUpdateDate, isNew, generatedUser);

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

	public void update(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		com.rssl.phizicgate.wsgate.services.registration.generated.Office generated = new com.rssl.phizicgate.wsgate.services.registration.generated.Office();

		try
		{
			BeanHelper.copyPropertiesWithDifferentTypes(generated, office, RegistartionClientServiceTypeCorrelation.toGeneratedTypes);
			String encoded = encodeData(generated.getSynchKey());
			generated.setSynchKey(encoded);
			getStub().update2(generated, convert2String(registerRequest));
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

	public CancelationCallBack cancellation(Client client, String trustingClientId, Calendar date, CancelationType type, String reason) throws GateLogicException, GateException
	{
		com.rssl.phizicgate.wsgate.services.registration.generated.Client generated = new  com.rssl.phizicgate.wsgate.services.registration.generated.Client();

		try
		{
			BeanHelper.copyPropertiesWithDifferentTypes(generated, client, RegistartionClientServiceTypeCorrelation.toGeneratedTypes);
			String encoded = encodeData(generated.getId());
			generated.setId(encoded);
			CancelationCallBackImpl generatedCallback = getStub().cancellation(generated, trustingClientId, date, type.toString(), reason);

			CancelationCallBack callback = new com.rssl.phizicgate.wsgate.services.types.CancelationCallBackImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(callback, generatedCallback, RegistartionClientServiceTypeCorrelation.toGateTypes);
			return callback;

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

	private String convert2String(Document document) throws GateException
	{
		try
		{
			return XmlHelper.convertDomToText(document);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
