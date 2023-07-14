package com.rssl.phizicgate.wsgate.services.deposits;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.deposits.generated.DepositService_PortType_Stub;
import com.rssl.phizicgate.wsgate.services.deposits.generated.DepositService_Service_Impl;
import com.rssl.phizicgate.wsgate.services.types.DepositImpl;
import com.rssl.phizicgate.wsgate.services.types.DepositInfoImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 30.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class DepositServiceWrapper extends JAXRPCClientSideServiceBase<DepositService_PortType_Stub> implements DepositService
{
	public DepositServiceWrapper(GateFactory factory)
	{
		super(factory);
		String url = getFactory().config(WSGateConfig.class).getURL() + "/DepositService";

		DepositService_Service_Impl service = new DepositService_Service_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((DepositService_PortType_Stub) service.getDepositServicePort(), url);
	}

	public List<? extends Deposit> getClientDeposits(Client client) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.deposits.generated.Client generated = new  com.rssl.phizicgate.wsgate.services.deposits.generated.Client();
			BeanHelper.copyPropertiesWithDifferentTypes(generated, client, TypesCorrelation.types);
			String encoded = encodeData(generated.getId());
			generated.setId(encoded);
			List deposits = getStub().getClientDeposits(generated);
			List<DepositImpl> rezult = new ArrayList<DepositImpl>();
			BeanHelper.copyPropertiesWithDifferentTypes(rezult, deposits, TypesCorrelation.types);
			return rezult;
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

	public Deposit getDepositById(String depositId) throws GateException, GateLogicException
	{
		try
		{
			String encoded = encodeData(depositId);
			com.rssl.phizicgate.wsgate.services.deposits.generated.Deposit deposit = getStub().getDepositById(encoded);
			Deposit rezult = new DepositImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(rezult, deposit, TypesCorrelation.types);
			return rezult;
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

	public DepositInfo getDepositInfo(Deposit deposit) throws GateException, GateLogicException
	{
		com.rssl.phizicgate.wsgate.services.deposits.generated.Deposit generated = new com.rssl.phizicgate.wsgate.services.deposits.generated.Deposit();
		try
		{
			BeanHelper.copyPropertiesWithDifferentTypes(generated, deposit, TypesCorrelation.types);
			String encoded = encodeData(generated.getId());
			generated.setId(encoded);
			com.rssl.phizicgate.wsgate.services.deposits.generated.DepositInfo depositInfo = getStub().getDepositInfo(generated);
			DepositInfo rezult = new DepositInfoImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(rezult, depositInfo, TypesCorrelation.types);
			return rezult;
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
