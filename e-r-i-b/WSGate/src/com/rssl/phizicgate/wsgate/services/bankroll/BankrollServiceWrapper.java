package com.rssl.phizicgate.wsgate.services.bankroll;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateConfig;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollService_Stub;
import com.rssl.phizicgate.wsgate.services.deposits.generated.DepositService_PortType_Stub;
import com.rssl.phizicgate.wsgate.services.deposits.generated.DepositService_Service_Impl;
import com.rssl.phizicgate.wsgate.services.types.AbstractImpl;
import com.rssl.phizicgate.wsgate.services.types.AccountAbstractImpl;
import com.rssl.phizicgate.wsgate.services.types.CardAbstractImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.rpc.Stub;


/**
 * @author: Pakhomova
 * @created: 20.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class BankrollServiceWrapper extends JAXRPCClientSideServiceBase<BankrollService_Stub> implements BankrollService
{
	private DepositService_PortType_Stub depositStub;  //необходим для выписки по вкладам


	public BankrollServiceWrapper(GateFactory factory) throws GateException
	{
		super(factory);
		String url = factory.config(WSGateConfig.class).getURL() + "/BankrollServiceImpl";
		BankrollServiceImpl_Impl service = new BankrollServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((BankrollService_Stub) service.getBankrollServicePort(), url);

		String depositUrl = getFactory().config(WSGateConfig.class).getURL() + "/DepositService";

		DepositService_Service_Impl depositService = new DepositService_Service_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(depositService);
		depositStub = (DepositService_PortType_Stub) depositService.getDepositServicePort();
		depositStub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, depositUrl);
		GateConfig config = ConfigFactory.getConfig(GateConfig.class);
		updateStub(config.getTimeout());
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Client generatedClient = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Client();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, client, BankrollTypesCorrelation.types);

			String encoded = encodeData(generatedClient.getId());
			generatedClient.setId(encoded);

			List<com.rssl.phizicgate.wsgate.services.bankroll.generated.Account> generatedAccounts = getStub().getClientAccounts(generatedClient);

			List<Account> accounts = new ArrayList<Account>(generatedAccounts.size());

			BeanHelper.copyPropertiesWithDifferentTypes(accounts, generatedAccounts, BankrollTypesCorrelation.types);

			return accounts;
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

	public GroupResult<String, Account> getAccount(String... accountId)
	{
		try
		{
			String[] encoded = accountId;
			for (int i=0; i<accountId.length; i++)
			{
				encoded[i]=encodeData(accountId[i]);
			}
			com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult generatedGroupResult = getStub().getAccount(encoded);
			GroupResult<String, Account> gateGroupResult = new GroupResult<String, Account>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedGroupResult, BankrollTypesCorrelation.types);
 			return gateGroupResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(accountId, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(accountId, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(accountId, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(accountId, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(accountId, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(accountId, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(accountId, new GateException(ex));
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		if (object instanceof Account)
			return getAccountAbstract((Account) object, fromDate, toDate, withCardUseInfo);
		if (object instanceof Card)
			return getCardAbstract((Card) object, fromDate, toDate, withCardUseInfo);
		if (object instanceof Deposit)
		{
			throw new UnsupportedOperationException();
		}
		throw new GateException("Неверный тип объекта");
	}

	private CardAbstract getCardAbstract(Card card, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Card generatedCard = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Card();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedCard, card, BankrollTypesCorrelation.types);

			String encoded = encodeData(generatedCard.getId());
			generatedCard.setId(encoded);

			com.rssl.phizicgate.wsgate.services.bankroll.generated.CardAbstract generatedAbstract = getStub().getCardAbstract2(generatedCard, fromDate, toDate, withCardUseInfo);

			CardAbstractImpl gateAbstarct = new CardAbstractImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateAbstarct, generatedAbstract, BankrollTypesCorrelation.types);

			return gateAbstarct;
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

	private AccountAbstract getAccountAbstract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Account generatedAccount = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Account();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAccount, account, BankrollTypesCorrelation.types);

			String encoded = encodeData(generatedAccount.getId());
			generatedAccount.setId(encoded);

			com.rssl.phizicgate.wsgate.services.bankroll.generated.AccountAbstract generatedAbstract = getStub().getAccountAbstract2(generatedAccount, fromDate, toDate, withCardUseInfo);

			AccountAbstractImpl gateAbstarct = new AccountAbstractImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateAbstarct, generatedAbstract, BankrollTypesCorrelation.types);

			return gateAbstarct;
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

	public GroupResult<Object, AbstractBase> getAbstract(Long number, Object... object)
	{
			if (object[0] instanceof Account)
					return getAccountAbstract(number, object);
			if (object[0] instanceof Card)
					return getCardAbstract(number, object);
			if (object[0] instanceof Deposit)
			{
				throw new UnsupportedOperationException();
			}
			throw new RuntimeException("Неверный тип объекта");

	}

	private GroupResult getCardAbstract(Long number, Object... card)
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Card[] generatedCard = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Card[card.length];
			BeanHelper.copyPropertiesWithDifferentTypes(generatedCard, card, BankrollTypesCorrelation.types);

			for(int i=0; i<generatedCard.length; i++)
			{
				generatedCard[i].setId(encodeData(generatedCard[i].getId()));
			}

			com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult generatedResult = getStub().getCardAbstract(number, generatedCard);

			com.rssl.phizic.common.types.transmiters.GroupResult gateAbstarct = new com.rssl.phizic.common.types.transmiters.GroupResult<Card, AbstractImpl>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateAbstarct, generatedResult, BankrollTypesCorrelation.types);
			com.rssl.phizic.common.types.transmiters.GroupResult<Object, AbstractBase> res = new com.rssl.phizic.common.types.transmiters.GroupResult<Object, AbstractBase>();
			return gateAbstarct;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(card, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(card, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(card, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(card, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(card, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(card, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(card, new GateException(ex));
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private GroupResult getAccountAbstract(Long number, Object... account)
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Account[] generatedAccount = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Account[account.length];
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAccount, account, BankrollTypesCorrelation.types);

			for(int i=0; i<generatedAccount.length; i++)
			{
				generatedAccount[i].setId(encodeData(generatedAccount[i].getId()));
			}

			com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult generatedAbstract = getStub().getAccountAbstract(number, generatedAccount);
			com.rssl.phizic.common.types.transmiters.GroupResult gateAbstarct = new com.rssl.phizic.common.types.transmiters.GroupResult<Account, AbstractImpl>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateAbstarct, generatedAbstract, BankrollTypesCorrelation.types);

			return gateAbstarct;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(account, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(account, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(account, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(account, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(account, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(account, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(account, new GateException(ex));
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}

	}

	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Client generatedClient = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Client();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, client, BankrollTypesCorrelation.types);

			String encoded = encodeData(generatedClient.getId());
			generatedClient.setId(encoded);

			List<com.rssl.phizicgate.wsgate.services.bankroll.generated.Card> generatedCards = getStub().getClientCards(generatedClient);

			List<Card> cards = new ArrayList<Card>(generatedCards.size());

			BeanHelper.copyPropertiesWithDifferentTypes(cards, generatedCards, BankrollTypesCorrelation.types);

			return cards;
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

	public GroupResult<String, Card> getCard(String... cardId)
	{

		try
		{
				String[] encoded = cardId;
				for (int i=0; i<cardId.length; i++)
				{
					encoded[i]=encodeData(cardId[i]);					
				}
				com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult generatedGroupResult = getStub().getCard(encoded);
				GroupResult<String, Card> gateGroupResult = new GroupResult<String, Card>();
				BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedGroupResult, BankrollTypesCorrelation.types);
 				return gateGroupResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(cardId, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(cardId, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(cardId, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardId, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardId, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardId, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(cardId, new GateException(ex));
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}

	}

	public GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCard)
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Card[] generatedCard = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Card[mainCard.length];
			BeanHelper.copyPropertiesWithDifferentTypes(generatedCard, mainCard, BankrollTypesCorrelation.types);

			for(int i=0; i<generatedCard.length; i++)
			{
				generatedCard[i].setId(encodeData(generatedCard[i].getId()));
			}

			com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult generatedGroupResult = getStub().getAdditionalCards(generatedCard);
			GroupResult<Card, List<Card>> gateGroupResult = new GroupResult<Card, List<Card>>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedGroupResult, BankrollTypesCorrelation.types);
			return gateGroupResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(mainCard, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(mainCard, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(mainCard, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(mainCard, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(mainCard, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(mainCard, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(mainCard, new GateException(ex));
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}

	}

	public GroupResult<Card, Account> getCardPrimaryAccount(Card... cards)
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Card[] generatedCard = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Card[cards.length];
			BeanHelper.copyPropertiesWithDifferentTypes(generatedCard, cards, BankrollTypesCorrelation.types);

			for(int i=0; i<generatedCard.length; i++)
			{
				generatedCard[i].setId(encodeData(generatedCard[i].getId()));
			}

			com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult generatedGroupResult = getStub().getCardPrimaryAccount(generatedCard);

			GroupResult<Card, Account> gateGroupResult = new GroupResult<Card, Account>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedGroupResult, BankrollTypesCorrelation.types);
			return gateGroupResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(cards, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(cards, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(cards, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cards, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cards, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cards, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(cards, new GateException(ex));
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}

	}

	public GroupResult<Card, Client> getOwnerInfo(Card... cards)
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Card[] generatedCard = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Card[cards.length];
			BeanHelper.copyPropertiesWithDifferentTypes(generatedCard, cards, BankrollTypesCorrelation.types);

			for(int i=0; i<generatedCard.length; i++)
			{
				generatedCard[i].setId(encodeData(generatedCard[i].getId()));
			}

			com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult generatedGroupResult = getStub().getOwnerInfo2(generatedCard);

			GroupResult<Card, Client> gateGroupResult = new GroupResult<Card, Client>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedGroupResult, BankrollTypesCorrelation.types);

			return gateGroupResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(cards, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(cards, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(cards, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cards, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cards, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cards, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(cards, new GateException(ex));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office> ... cardInfo)
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Pair[] generatedInfo = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Pair[cardInfo.length];
			BeanHelper.copyPropertiesWithDifferentTypes(generatedInfo, cardInfo, BankrollTypesCorrelation.types);

			com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult generatedGroupResult = getStub().getOwnerInfoByCardNumber(generatedInfo);

			GroupResult<Pair<String,Office>, Client> gateGroupResult = new GroupResult<Pair<String,Office>, Client>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedGroupResult, BankrollTypesCorrelation.types);

			return gateGroupResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(cardInfo, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(cardInfo, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(cardInfo, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardInfo, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardInfo, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardInfo, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(cardInfo, new GateException(ex));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public GroupResult<Account, Client> getOwnerInfo(Account... accounts)
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Account[] generatedAccount = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Account[accounts.length];
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAccount, accounts, BankrollTypesCorrelation.types);

			for(int i=0; i<generatedAccount.length; i++)
			{
				generatedAccount[i].setId(encodeData(generatedAccount[i].getId()));
			}

			com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult generatedGroupResult = getStub().getOwnerInfo(generatedAccount);

			GroupResult<Account, Client> gateGroupResult = new GroupResult<Account, Client>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedGroupResult, BankrollTypesCorrelation.types);

			return gateGroupResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(accounts, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(accounts, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(accounts, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(accounts, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(accounts, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(accounts, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(accounts, new GateException(ex));
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Account generatedAccount = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Account();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAccount, account, BankrollTypesCorrelation.types);

			String encoded = encodeData(generatedAccount.getId());
			generatedAccount.setId(encoded);

			com.rssl.phizicgate.wsgate.services.bankroll.generated.AccountAbstract generatedAbstract = getStub().getAccountExtendedAbstract(generatedAccount, fromDate, toDate);

			AccountAbstractImpl gateAbstarct = new AccountAbstractImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateAbstarct, generatedAbstract, BankrollTypesCorrelation.types);

			return gateAbstarct;
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

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Pair[] generatedInfo = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Pair[accountInfo.length];
			BeanHelper.copyPropertiesWithDifferentTypes(generatedInfo, accountInfo, BankrollTypesCorrelation.types);

			com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult generatedGroupResult = getStub().getAccountByNumber(generatedInfo);

			GroupResult<Pair<String,Office>, Account> gateGroupResult = new GroupResult<Pair<String,Office>,Account>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedGroupResult, BankrollTypesCorrelation.types);
			return gateGroupResult;
		}
			catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(accountInfo, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(accountInfo, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(accountInfo, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(accountInfo, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(accountInfo, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(accountInfo, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(accountInfo, new GateException(ex));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public GroupResult<Pair<String,Office>,Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		try{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Client generatedClient = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Client();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, client, BankrollTypesCorrelation.types);

			com.rssl.phizicgate.wsgate.services.bankroll.generated.Pair[] generatedInfo = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Pair[cardInfo.length];
			BeanHelper.copyPropertiesWithDifferentTypes(generatedInfo, cardInfo, BankrollTypesCorrelation.types);

			com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult generatedGroupResult = getStub().getCardByNumber(generatedClient, generatedInfo);

			GroupResult<Pair<String,Office>,Card> gateGroupResult = new GroupResult<Pair<String,Office>,Card>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedGroupResult, BankrollTypesCorrelation.types);
			return gateGroupResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(cardInfo, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(cardInfo, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(cardInfo, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardInfo, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardInfo, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardInfo, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(cardInfo, new GateException(ex));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.bankroll.generated.Account generatedAccount = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Account();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAccount, account, BankrollTypesCorrelation.types);

			String encoded = encodeData(generatedAccount.getId());
			generatedAccount.setId(encoded);

			com.rssl.phizicgate.wsgate.services.bankroll.generated.AccountAbstract generatedAbstract = getStub().getAccHistoryFullExtract(generatedAccount, fromDate, toDate, withCardUseInfo);

			AccountAbstractImpl gateAbstarct = new AccountAbstractImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateAbstarct, generatedAbstract, BankrollTypesCorrelation.types);

			return gateAbstarct;
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

	public void updateStub(int timeout)
	{
		super.updateStub(timeout);
		rwl.writeLock().lock();
		try
		{
			depositStub._setTransportFactory(getClientTimeoutTransportFactory(timeout));
		}
		finally
		{
			rwl.writeLock().unlock();
		}
	}

	protected DepositService_PortType_Stub getDepositStub()
	{
		rwl.readLock().lock();
		try
		{
			return depositStub;
		}
		finally
		{
			rwl.readLock().unlock();
		}
	}
}
