package com.rssl.phizic.web.gate.services.cache;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.services.types.OfficeImpl;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.web.gate.services.cache.generated.*;
import com.rssl.phizic.web.gate.services.types.*;
import com.rssl.phizic.web.security.Constants;
import com.sun.org.apache.xalan.internal.xsltc.dom.SAXImpl;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

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
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
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

	public void clearClientProductsCache(Client client_1, String... className) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			ClientImpl client = new ClientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(client, client_1, CacheServiceTypesCorrelation.toGateTypes);
			Class[] clazz = new Class[className.length];
			int i=0;
			for (String name : className)
			{
				clazz[i] = ClassHelper.loadClass(name);
				i++;
			}
			service.clearClientProductsCache(client, clazz);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
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

	public void clearAccountCache(Account account_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			AccountImpl account = new AccountImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(account, account_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearAccountCache(account);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalDocumentException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
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

	public void clearCardCache(Card card_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			CardImpl card = new CardImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(card, card_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearCardCache(card);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalDocumentException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
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

	public void clearDepositCache(Deposit deposit_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			DepositImpl deposit = new DepositImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(deposit, deposit_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearDepositCache(deposit);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalDocumentException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
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

	public void clearIMACache(IMAccount imAccount_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			IMAccountImpl imAccount = new IMAccountImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(imAccount, imAccount_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearIMACache(imAccount);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalDocumentException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
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

	public void clearLoanCache(Loan loan_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			LoanImpl loan = new LoanImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(loan, loan_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearLoanCache(loan);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalDocumentException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
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

	public void clearLongOfferCache(LongOffer longOffer_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			LongOfferImpl longOffer = new LongOfferImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(longOffer, longOffer_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearLongOfferCache(longOffer);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalDocumentException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
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

	public void clearLoyaltyProgramCache(LoyaltyProgram loyaltyProgram) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			LoyaltyProgramImpl loyaltyProgramImpl = new LoyaltyProgramImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(loyaltyProgramImpl, loyaltyProgram, CacheServiceTypesCorrelation.toGateTypes);
			service.clearLoyaltyProgramCache(loyaltyProgramImpl);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalDocumentException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
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

	public void clearSecurityAccountCache(com.rssl.phizic.web.gate.services.cache.generated.SecurityAccount securityAccount_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			SecurityAccountImpl securityAccount = new SecurityAccountImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(securityAccount, securityAccount_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearSecurityAccountCache(securityAccount);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalDocumentException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
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


	public void clearInsuranceAppCache(InsuranceApp insuranceApp_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			InsuranceAppImpl insuranceApp = new InsuranceAppImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(insuranceApp, insuranceApp_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearInsuranceAppCache(insuranceApp);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalDocumentException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
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

	public void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws RemoteException
	{
		// автоподписки не идут по прямой интеграции
	}

	public void clearAutoPaymentCache(AutoPayment autoPayment_1, Card ... card_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			AutoPaymentImpl autoPayment = new AutoPaymentImpl();
			com.rssl.phizic.gate.bankroll.Card[] cards = new com.rssl.phizic.gate.bankroll.Card[card_1.length];
			BeanHelper.copyPropertiesWithDifferentTypes(autoPayment, autoPayment_1, CacheServiceTypesCorrelation.toGateTypes);
			BeanHelper.copyPropertiesWithDifferentTypes(cards, card_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearAutoPaymentCache(autoPayment, cards);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalDocumentException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
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

	public void clearDepoAccountCache(DepoAccount depoAccount_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.cache.CacheService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.cache.CacheService.class);
			DepoAccountImpl depoAccount = new DepoAccountImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(depoAccount, depoAccount_1, CacheServiceTypesCorrelation.toGateTypes);
			service.clearDepoAccountCache(depoAccount);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalDocumentException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
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
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalDocumentException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
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
}
