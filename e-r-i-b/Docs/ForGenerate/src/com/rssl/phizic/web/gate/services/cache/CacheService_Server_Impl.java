package com.rssl.phizic.web.gate.services.cache;

import com.rssl.phizic.web.gate.services.cache.generated.*;
import com.rssl.phizic.web.gate.services.cache.generated.Client;
import com.rssl.phizic.web.gate.services.cache.generated.CacheService;
import com.rssl.phizic.web.gate.services.cache.generated.CurrencyRate;
import com.rssl.phizic.web.gate.services.cache.*;
import com.rssl.phizic.web.gate.services.cache.CacheServiceTypesCorrelation;
import com.rssl.phizic.web.gate.services.types.*;
import com.rssl.phizic.web.security.Constants;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.BeanHelper;

import java.rmi.RemoteException;

import org.apache.commons.logging.LogFactory;

/**
 * @author osminin
 * @ created 04.09.2009
 * @ $Author$
 * @ $Revision$
 */

public class CacheService_Server_Impl implements CacheService
{
	public void clearClientCache(Client client_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			ClientImpl client = new ClientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(client, client_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearClientCache(client);
		}
		catch(GateException e)
		{
			LogFactory.getLog(CacheService_Server_Impl.class).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			LogFactory.getLog(CacheService_Server_Impl.class).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
	}

	public void clearAccountCache(Account account_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			AccountImpl account = new AccountImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(account, account_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearAccountCache(account);
		}
		catch(GateException e)
		{
			LogFactory.getLog(CacheService_Server_Impl.class).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			LogFactory.getLog(CacheService_Server_Impl.class).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
	}

	public void clearCardCache(Card card_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			CardImpl card = new CardImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(card, card_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearCardCache(card);
		}
		catch(GateException e)
		{
		    LogFactory.getLog(CacheService_Server_Impl.class).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			LogFactory.getLog(CacheService_Server_Impl.class).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
	}

	public void clearDepositCache(Deposit deposit_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			DepositImpl deposit = new DepositImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(deposit, deposit_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearDepositCache(deposit);
		}
		catch(GateException e)
		{
		    LogFactory.getLog(CacheService_Server_Impl.class).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			LogFactory.getLog(CacheService_Server_Impl.class).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
	}

	public void clearCurrencyRateCache(CurrencyRate currencyRate_1, Office office_2) throws RemoteException
	{
		try
		{
	 	    com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);

			com.rssl.phizic.common.types.CurrencyRate currencyRate = new com.rssl.phizic.common.types.CurrencyRate();
			OfficeImpl office = new OfficeImpl();

			BeanHelper.copyPropertiesWithDifferentTypes(currencyRate, currencyRate_1, CacheServiceTypesCorrelation.toGateTypes);
			BeanHelper.copyPropertiesWithDifferentTypes(office, office_2, CacheServiceTypesCorrelation.toGateTypes);

			service.clearCurrencyRateCache(currencyRate, office);
		}
		catch(GateException e)
		{
		    LogFactory.getLog(CacheService_Server_Impl.class).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			LogFactory.getLog(CacheService_Server_Impl.class).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
	}
}