package com.rssl.phizic.web.gate.services.loyalty;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.*;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.gate.services.loyalty.generated.*;
import com.rssl.phizic.web.gate.services.types.CardImpl;
import com.rssl.phizic.web.gate.services.types.LoyaltyProgramImpl;
import com.rssl.phizic.web.security.Constants;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramServiceImpl implements LoyaltyProgramService
{
	public LoyaltyProgram getClientLoyaltyProgram(String string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.loyalty.LoyaltyProgramService service =
						GateSingleton.getFactory().service(com.rssl.phizic.gate.loyalty.LoyaltyProgramService.class);

			com.rssl.phizic.gate.loyalty.LoyaltyProgram program = service.getClientLoyaltyProgram(string_1);
			if (program == null)
				return null;

			LoyaltyProgram generatedProgram = new LoyaltyProgram();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedProgram, program, TypesCorrelation.getTypes());
			return generatedProgram;
		}
		catch(GateTimeOutException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TIMEOUT_MESSAGE_PREFIX + e.getMessage() + Constants.TIMEOUT_MESSAGE_SUFFIX, e);
		}
		catch (InactiveExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.INACTIVE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.INACTIVE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch (OfflineExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.OFFLINE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.OFFLINE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List<LoyaltyProgramOperation> getLoyaltyOperationInfo(LoyaltyProgram loyaltyProgram) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.loyalty.LoyaltyProgramService service =
						GateSingleton.getFactory().service(com.rssl.phizic.gate.loyalty.LoyaltyProgramService.class);

			LoyaltyProgramImpl loyaltyProgramImpl = new LoyaltyProgramImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(loyaltyProgramImpl, loyaltyProgram, TypesCorrelation.getTypes());

			List<com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation> result = service.getLoyaltyOperationInfo(loyaltyProgramImpl);
			List<LoyaltyProgramOperation> generatedResult = new ArrayList<LoyaltyProgramOperation>();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedResult, result, TypesCorrelation.getTypes());
			return generatedResult;
		}
		catch(GateTimeOutException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TIMEOUT_MESSAGE_PREFIX + e.getMessage() + Constants.TIMEOUT_MESSAGE_SUFFIX, e);
		}
		catch (InactiveExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.INACTIVE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.INACTIVE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch (OfflineExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.OFFLINE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.OFFLINE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List<LoyaltyOffer> getLoyaltyOffers(LoyaltyProgram loyaltyProgram) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.loyalty.LoyaltyProgramService service =
						GateSingleton.getFactory().service(com.rssl.phizic.gate.loyalty.LoyaltyProgramService.class);

			LoyaltyProgramImpl loyaltyProgramImpl = new LoyaltyProgramImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(loyaltyProgramImpl, loyaltyProgram, TypesCorrelation.getTypes());

			List<com.rssl.phizic.gate.loyalty.LoyaltyOffer> result = service.getLoyaltyOffers(loyaltyProgramImpl);
			List<LoyaltyOffer> generatedResult = new ArrayList<LoyaltyOffer>();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedResult, result, TypesCorrelation.getTypes());
			return generatedResult;
		}
		catch(GateTimeOutException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TIMEOUT_MESSAGE_PREFIX + e.getMessage() + Constants.TIMEOUT_MESSAGE_SUFFIX, e);
		}
		catch (InactiveExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.INACTIVE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.INACTIVE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch (OfflineExternalServiceException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).info(e);
			throw new RemoteException(Constants.OFFLINE_SERVICE_MESSAGE_PREFIX + e.getMessage() + Constants.OFFLINE_SERVICE_MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public LoyaltyProgramOperation __forGenerateLoyaltyProgramOperation() throws RemoteException
	{
		return null;
	}

	public LoyaltyOffer __forGenerateLoyaltyOffer() throws RemoteException
	{
		return null;
	}

}
