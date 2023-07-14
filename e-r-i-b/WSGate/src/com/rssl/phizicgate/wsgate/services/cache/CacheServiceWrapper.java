package com.rssl.phizicgate.wsgate.services.cache;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.cache.generated.CacheServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_Stub;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author osminin
 * @ created 03.09.2009
 * @ $Author$
 * @ $Revision$
 */

public class CacheServiceWrapper extends JAXRPCClientSideServiceBase<CacheService_Stub> implements CacheService
{
	public CacheServiceWrapper(GateFactory factory)
	{
		super(factory);
		String url = factory.config(WSGateConfig.class).getURL() + "/CacheServiceImpl";
		CacheServiceImpl_Impl service = new CacheServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((CacheService_Stub) service.getCacheServicePort(), url);
	}

	public void clearClientCache(Client client) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.Client generatedClient= new com.rssl.phizicgate.wsgate.services.cache.generated.Client();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, client, CacheServiceTypesCorrelation.toGeneratedTypes);
			getStub().clearClientCache(generatedClient);
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


	public void clearClientProductsCache(Client client, Class... clazz) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.Client generatedClient = new com.rssl.phizicgate.wsgate.services.cache.generated.Client();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, client, CacheServiceTypesCorrelation.toGeneratedTypes);
			String[] className = new String[clazz.length];
			int i=0;
			for (Class  c : clazz)
			{
				className[i] = c.getName();
				i++;
			}
			getStub().clearClientProductsCache(generatedClient, className);
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

	public void clearAccountCache(Account account) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.Account generatedAccount = new com.rssl.phizicgate.wsgate.services.cache.generated.Account();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAccount, account, CacheServiceTypesCorrelation.toGeneratedTypes);
			getStub().clearAccountCache(generatedAccount);
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

	public void clearCardCache(Card card) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.Card generatedCard = new com.rssl.phizicgate.wsgate.services.cache.generated.Card();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedCard, card, CacheServiceTypesCorrelation.toGeneratedTypes);
			getStub().clearCardCache(generatedCard);
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

	public void clearDepositCache(Deposit deposit) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.Deposit generatedDeposit = new com.rssl.phizicgate.wsgate.services.cache.generated.Deposit();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedDeposit, deposit, CacheServiceTypesCorrelation.toGeneratedTypes);
			getStub().clearDepositCache(generatedDeposit);
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

	public void clearCurrencyRateCache(CurrencyRate rate, Office office) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.CurrencyRate generatedRate = new com.rssl.phizicgate.wsgate.services.cache.generated.CurrencyRate();
			com.rssl.phizicgate.wsgate.services.cache.generated.Office generatedOffice = new com.rssl.phizicgate.wsgate.services.cache.generated.Office();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedOffice, office, CacheServiceTypesCorrelation.toGeneratedTypes);
			generatedRate = getGeneratedRate(rate);

			getStub().clearCurrencyRateCache(generatedRate, generatedOffice);
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

	public void clearIMACache(IMAccount imAccount) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.IMAccount generatedIMAccount = new com.rssl.phizicgate.wsgate.services.cache.generated.IMAccount();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedIMAccount, imAccount, CacheServiceTypesCorrelation.toGeneratedTypes);
			getStub().clearIMACache(generatedIMAccount);
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

	public void clearLoanCache(Loan loan) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.Loan generatedLoan = new com.rssl.phizicgate.wsgate.services.cache.generated.Loan();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedLoan, loan, CacheServiceTypesCorrelation.toGeneratedTypes);
			getStub().clearLoanCache(generatedLoan);
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

	public void clearDepoAccountCache(DepoAccount depoAccount) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.DepoAccount generatedDepoAccount = new com.rssl.phizicgate.wsgate.services.cache.generated.DepoAccount();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedDepoAccount, depoAccount, CacheServiceTypesCorrelation.toGeneratedTypes);
			getStub().clearDepoAccountCache(generatedDepoAccount);
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

	public void clearLongOfferCache(LongOffer longOffer) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.LongOffer generatedLongOffer = new com.rssl.phizicgate.wsgate.services.cache.generated.LongOffer();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedLongOffer, longOffer, CacheServiceTypesCorrelation.toGeneratedTypes);
			getStub().clearLongOfferCache(generatedLongOffer);
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

	public void clearAutoPaymentCache(AutoPayment autoPayment, Card ... cards) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.AutoPayment generatedAutoPayment = new com.rssl.phizicgate.wsgate.services.cache.generated.AutoPayment();
			com.rssl.phizicgate.wsgate.services.cache.generated.Card generatedCards[] = new com.rssl.phizicgate.wsgate.services.cache.generated.Card[cards.length];
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAutoPayment, autoPayment, CacheServiceTypesCorrelation.toGeneratedTypes);
			BeanHelper.copyPropertiesWithDifferentTypes(generatedCards, cards, CacheServiceTypesCorrelation.toGeneratedTypes);
			getStub().clearAutoPaymentCache(generatedAutoPayment, generatedCards);
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

	public void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws GateException, GateLogicException
	{
		// автоподписки не идут через пр€мую интеграцию
	}

	public void clearLoyaltyProgramCache(com.rssl.phizic.gate.loyalty.LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.LoyaltyProgram generatedLoyaltyProgram = new com.rssl.phizicgate.wsgate.services.cache.generated.LoyaltyProgram();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedLoyaltyProgram, loyaltyProgram, CacheServiceTypesCorrelation.toGeneratedTypes);
			getStub().clearLoyaltyProgramCache(generatedLoyaltyProgram);
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

	public void clearInsuranceAppCache(InsuranceApp insuranceApp) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.InsuranceApp generatedInsuranceApp = new com.rssl.phizicgate.wsgate.services.cache.generated.InsuranceApp();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedInsuranceApp, insuranceApp, CacheServiceTypesCorrelation.toGeneratedTypes);
			getStub().clearInsuranceAppCache(generatedInsuranceApp);
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

	public void clearSecurityAccountCache(SecurityAccount securityAccount) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.cache.generated.SecurityAccount generatedSecurityAccount = new com.rssl.phizicgate.wsgate.services.cache.generated.SecurityAccount();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedSecurityAccount, securityAccount, CacheServiceTypesCorrelation.toGeneratedTypes);
			getStub().clearSecurityAccountCache(generatedSecurityAccount);
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

	public void clearShopOrderCache(String orderUuid) throws GateException, GateLogicException
	{
	}

	private com.rssl.phizicgate.wsgate.services.cache.generated.CurrencyRate getGeneratedRate(CurrencyRate rate) throws Exception
	{
		com.rssl.phizicgate.wsgate.services.cache.generated.Currency generatedCurrrencyFrom = new com.rssl.phizicgate.wsgate.services.cache.generated.Currency();
		com.rssl.phizicgate.wsgate.services.cache.generated.Currency generatedCurrrencyTo = new com.rssl.phizicgate.wsgate.services.cache.generated.Currency();

		BeanHelper.copyPropertiesWithDifferentTypes(generatedCurrrencyFrom, rate.getFromCurrency(), CacheServiceTypesCorrelation.toGeneratedTypes);
		BeanHelper.copyPropertiesWithDifferentTypes(generatedCurrrencyTo, rate.getToCurrency(), CacheServiceTypesCorrelation.toGeneratedTypes);

		com.rssl.phizicgate.wsgate.services.cache.generated.CurrencyRate generatedRate = new com.rssl.phizicgate.wsgate.services.cache.generated.CurrencyRate();

		generatedRate.setFromCurrency(generatedCurrrencyFrom);
		generatedRate.setFromValue(rate.getFromValue());
		generatedRate.setToCurrency(generatedCurrrencyTo);
		generatedRate.setToValue(rate.getToValue());
		generatedRate.setType(rate.getType().toString());

		return generatedRate;
	}
}
