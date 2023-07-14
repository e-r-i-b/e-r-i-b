package com.rssl.phizicgate.wsgateclient.services.person.update;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.services.StubUrlBackServiceWrapper;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizgate.common.ws.exceptions.WSGateInvalidTargerException;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.CancelationCallBack;
import com.rssl.phizic.gate.clients.ClientState;
import com.rssl.phizic.gate.clients.UpdatePersonService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.wsgateclient.WSGateClientException;
import com.rssl.phizicgate.wsgateclient.WSGateLogicClientException;
import com.rssl.phizicgate.wsgateclient.WSTemporalGateClientException;
import com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType;
import com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_Server_Impl_Impl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * @author hudyakov
 * @ created 09.07.2009
 * @ $Author$
 * @ $Revision$
 */

public class UpdatePersonServiceWrapper extends AbstractService implements UpdatePersonService
{
	private StubUrlBackServiceWrapper<UpdatePersonService_PortType> wrapper;

	public UpdatePersonServiceWrapper(GateFactory factory)
	{
		super(factory);
		wrapper = new StubUrlBackServiceWrapper<UpdatePersonService_PortType>("UpdatePersonService_Server_Impl"){
			protected void createStub()
			{
				UpdatePersonService_Server_Impl_Impl service = new UpdatePersonService_Server_Impl_Impl();
				WSRequestHandlerUtil.addWSRequestHandlerToService(service);
				setStub(service.getUpdatePersonService_PortTypePort());
			}
		};
	}

	public void updateState(String clientId, ClientState newState) throws GateException, GateLogicException
	{

		com.rssl.phizicgate.wsgateclient.services.person.update.generated.ClientState generatedClientState =
				new com.rssl.phizicgate.wsgateclient.services.person.update.generated.ClientState();

		try
		{
			BeanHelper.copyPropertiesWithDifferentTypes(newState, generatedClientState, UpdatePersonTypeCorrelation.toGeneratedTypes);
			wrapper.getStub().updateState2(clientId, generatedClientState);
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
			if (ex.getMessage().contains(WSGateInvalidTargerException.MESSAGE_PREFIX))
			{
				throw new WSGateInvalidTargerException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void lockOrUnlock(String clientId, Date lockDate, Boolean islock, Money liability) throws GateException, GateLogicException
	{

		com.rssl.phizicgate.wsgateclient.services.person.update.generated.Money generatedMoney = null;
		if(liability !=null)
		{
			generatedMoney = new com.rssl.phizicgate.wsgateclient.services.person.update.generated.Money();
		}


		try
		{
			BeanHelper.copyPropertiesWithDifferentTypes(generatedMoney, liability, UpdatePersonTypeCorrelation.toGeneratedTypes);
			wrapper.getStub().lockOnUnlock(clientId, DateHelper.toCalendar(lockDate), islock, generatedMoney);
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
			if (ex.getMessage().contains(WSGateInvalidTargerException.MESSAGE_PREFIX))
			{
				throw new WSGateInvalidTargerException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void updateState(CancelationCallBack callback, ClientState newState) throws GateException, GateLogicException
	{
		com.rssl.phizicgate.wsgateclient.services.person.update.generated.ClientState generatedClientState =
				new com.rssl.phizicgate.wsgateclient.services.person.update.generated.ClientState();

		try
		{
			BeanHelper.copyPropertiesWithDifferentTypes(generatedClientState, newState, UpdatePersonTypeCorrelation.toGeneratedTypes);
			com.rssl.phizicgate.wsgateclient.services.person.update.generated.CancelationCallBackImpl generatedCancelationCallBack =
					new com.rssl.phizicgate.wsgateclient.services.person.update.generated.CancelationCallBackImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedCancelationCallBack, callback, UpdatePersonTypeCorrelation.toGeneratedTypes);
			wrapper.getStub().updateState(generatedCancelationCallBack, generatedClientState);
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
			if (ex.getMessage().contains(WSGateInvalidTargerException.MESSAGE_PREFIX))
			{
				throw new WSGateInvalidTargerException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
