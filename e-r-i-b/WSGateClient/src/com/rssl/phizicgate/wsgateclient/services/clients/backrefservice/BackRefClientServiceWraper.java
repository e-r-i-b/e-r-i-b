package com.rssl.phizicgate.wsgateclient.services.clients.backrefservice;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.services.StubUrlBackServiceWrapper;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgateclient.WSGateClientException;
import com.rssl.phizicgate.wsgateclient.WSGateLogicClientException;
import com.rssl.phizicgate.wsgateclient.WSTemporalGateClientException;
import com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService;
import com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientServiceImpl_Impl;
import com.rssl.phizgate.common.services.types.ClientImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Calendar;

/**
 * @author akrenev
 * @ created 04.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class BackRefClientServiceWraper extends AbstractService implements com.rssl.phizic.gate.clients.BackRefClientService
{
	private StubUrlBackServiceWrapper<BackRefClientService> wrapper;

	public BackRefClientServiceWraper(GateFactory factory)
	{
		super(factory);

		wrapper = new StubUrlBackServiceWrapper<BackRefClientService>("BackRefClientServiceImpl"){
			protected void createStub()
			{
				BackRefClientServiceImpl_Impl backRefClientService = new BackRefClientServiceImpl_Impl();
				WSRequestHandlerUtil.addWSRequestHandlerToService(backRefClientService);
				setStub(backRefClientService.getBackRefClientServicePort());
			}
		};
	}

	public Client getClientById(Long id) throws GateLogicException, GateException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.Client generated = wrapper.getStub().getClientById(id);
			com.rssl.phizic.gate.clients.Client client = new ClientImpl();

			BeanHelper.copyPropertiesWithDifferentTypes(client, generated, BackRefClientServiceTypesCorrelation.types);
			return client;
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
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicClientException(ex);
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

	public Client getClientByFIOAndDoc(String firstName, String lastName, String middleName, String docSeries, String docNumber, Calendar birthDate, String tb) throws GateLogicException, GateException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.Client generated = wrapper.getStub().getClientByFIOAndDoc(firstName, lastName, middleName, docSeries, docNumber, birthDate, tb);
			com.rssl.phizic.gate.clients.Client client = new ClientImpl();

			BeanHelper.copyPropertiesWithDifferentTypes(client, generated, BackRefClientServiceTypesCorrelation.types);
			return client;
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
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicClientException(ex);
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

	public String getClientDepartmentCode(Long loginId) throws GateException, GateLogicException
	{
		try
		{
			return wrapper.getStub().getClientDepartmentCode(loginId);
		}
		catch(RemoteException e)
		{
			if (e.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicClientException(e);
			}
			if (e.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(e);
			}
			throw new GateException(e);
		}
	}

	public String getClientCreationType(String clientId) throws GateLogicException, GateException
	{
		try
		{
			return wrapper.getStub().getClientCreationType(clientId);
		}
		catch(RemoteException e)
		{
			if (e.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicClientException(e);
			}
			if (e.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(e);
			}
			throw new GateException(e);
		}
	}
}
