package com.rssl.phizicgate.wsgate.services.loyalty;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.exceptions.*;
import com.rssl.phizic.gate.loyalty.LoyaltyOffer;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramService;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramService_Stub;
import com.rssl.phizicgate.wsgate.services.loyalty.types.LoyaltyProgramImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramServiceWrapper extends JAXRPCClientSideServiceBase<LoyaltyProgramService_Stub> implements LoyaltyProgramService
{
	public LoyaltyProgramServiceWrapper(GateFactory factory)
	{
		super(factory);
		String url = getFactory().config(WSGateConfig.class).getURL() + "/LoyaltyProgramServiceImpl";
		LoyaltyProgramServiceImpl_Impl service = new LoyaltyProgramServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((LoyaltyProgramService_Stub) service.getLoyaltyProgramServicePort(), url);
	}

	public LoyaltyProgram getClientLoyaltyProgram(String externalId) throws GateException, GateLogicException
	{
		try
		{
			LoyaltyProgram loyaltyProgram = new LoyaltyProgramImpl();
			com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgram clientLoyaltyProgram = getStub().getClientLoyaltyProgram(externalId);
			if (clientLoyaltyProgram == null)
				return null;
			BeanHelper.copyPropertiesWithDifferentTypes(loyaltyProgram, clientLoyaltyProgram, TypesCorrelation.getTypes());
			return loyaltyProgram;
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
			if(ex.getMessage().contains(GateTimeOutException.MESSAGE_PREFIX))
			{
				throw new GateTimeOutException(ex);
			}
			if(ex.getMessage().contains(OfflineExternalServiceException.MESSAGE_PREFIX))
			{
				throw new OfflineExternalServiceException(ex);
			}
			if(ex.getMessage().contains(InactiveExternalServiceException.MESSAGE_PREFIX))
			{
				throw new InactiveExternalServiceException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<LoyaltyProgramOperation> getLoyaltyOperationInfo(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgram generatedLoyaltyProgram =
					new com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgram();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedLoyaltyProgram, loyaltyProgram, TypesCorrelation.getTypes());

			List<LoyaltyProgramOperation> result = new ArrayList<LoyaltyProgramOperation>();
			BeanHelper.copyPropertiesWithDifferentTypes(result, getStub().getLoyaltyOperationInfo(generatedLoyaltyProgram), TypesCorrelation.getTypes());
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
			if(ex.getMessage().contains(GateTimeOutException.MESSAGE_PREFIX))
			{
				throw new GateTimeOutException(ex);
			}
			if(ex.getMessage().contains(OfflineExternalServiceException.MESSAGE_PREFIX))
			{
				throw new OfflineExternalServiceException(ex);
			}
			if(ex.getMessage().contains(InactiveExternalServiceException.MESSAGE_PREFIX))
			{
				throw new InactiveExternalServiceException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<LoyaltyOffer> getLoyaltyOffers(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgram generatedLoyaltyProgram =
					new com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgram();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedLoyaltyProgram, loyaltyProgram, TypesCorrelation.getTypes());

			List<LoyaltyOffer> result = new ArrayList<LoyaltyOffer>();
			BeanHelper.copyPropertiesWithDifferentTypes(result, getStub().getLoyaltyOffers(generatedLoyaltyProgram), TypesCorrelation.getTypes());
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
			if(ex.getMessage().contains(GateTimeOutException.MESSAGE_PREFIX))
			{
				throw new GateTimeOutException(ex);
			}
			if(ex.getMessage().contains(OfflineExternalServiceException.MESSAGE_PREFIX))
			{
				throw new OfflineExternalServiceException(ex);
			}
			if(ex.getMessage().contains(InactiveExternalServiceException.MESSAGE_PREFIX))
			{
				throw new InactiveExternalServiceException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
