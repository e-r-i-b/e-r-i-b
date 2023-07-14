package com.rssl.phizic.web.gate.services.bankroll;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.services.types.OfficeImpl;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.AbstractBase;
import com.rssl.phizic.gate.dictionaries.officies.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.EncodeHelper;
import com.rssl.phizic.web.gate.services.bankroll.generated.*;
import com.rssl.phizic.web.gate.services.bankroll.generated.Office;
import com.rssl.phizic.web.gate.services.types.AccountImpl;
import com.rssl.phizic.web.gate.services.types.CardImpl;
import com.rssl.phizic.web.gate.services.types.ClientImpl;
import com.rssl.phizic.web.security.Constants;
import org.apache.commons.codec.binary.Base64;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author: Pakhomova
 * @created: 20.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class BankrollService_Server_Impl implements BankrollService
{
	public GroupResult getAccount(String[] string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);
			String[] decoded = decodeData(string_1);
			com.rssl.phizic.common.types.transmiters.GroupResult<String, com.rssl.phizic.gate.bankroll.Account> gateGroupResult = service.getAccount(decoded);
			com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult generatedGroupResult = new com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedGroupResult, gateGroupResult, BankrollTypesCorrelation.types);
			return generatedGroupResult;
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

	public GroupResult getAccountAbstract(Long long_2, Account... account_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);

			for (int i=0; i<account_1.length; i++)
			{
				account_1[i].setId(decodeData(account_1[i].getId()));
			}

			com.rssl.phizic.gate.bankroll.Account[] gateAccount = new AccountImpl[account_1.length];
			BeanHelper.copyPropertiesWithDifferentTypes(gateAccount, account_1, BankrollTypesCorrelation.types);

			com.rssl.phizic.common.types.transmiters.GroupResult anAbstract = service.getAbstract(long_2, gateAccount);

			GroupResult generatedAbstract = new GroupResult();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAbstract, anAbstract, BankrollTypesCorrelation.types);

			return generatedAbstract;
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

	public AccountAbstract getAccountAbstract2(Account account_1, Calendar calendar_2, Calendar calendar_3, Boolean withCardUseInfo) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);

			String decoded = decodeData(account_1.getId());
			account_1.setId(decoded);

			com.rssl.phizic.gate.bankroll.Account gateAccount = new AccountImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateAccount, account_1, BankrollTypesCorrelation.types);

			AbstractBase anAbstract = service.getAbstract(gateAccount, calendar_2, calendar_3, withCardUseInfo);

			AccountAbstract generatedAbstract = new AccountAbstract();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAbstract, anAbstract, BankrollTypesCorrelation.types);

			return generatedAbstract;
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

	public GroupResult getAccountByNumber(Pair... accountInfo) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);
			com.rssl.phizic.common.types.transmiters.Pair[] gatePair = new com.rssl.phizic.common.types.transmiters.Pair[accountInfo.length];
			BeanHelper.copyPropertiesWithDifferentTypes(gatePair, accountInfo, BankrollTypesCorrelation.types);

			com.rssl.phizic.common.types.transmiters.GroupResult  gateGroupResult = service.getAccountByNumber(gatePair);

			com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult generatedGroupResult = new com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedGroupResult, gateGroupResult, BankrollTypesCorrelation.types);
			return generatedGroupResult;
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

	public AccountAbstract getAccountExtendedAbstract(Account account_1, Calendar calendar_2, Calendar calendar_3) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);

			String decoded = decodeData(account_1.getId());
			account_1.setId(decoded);

			com.rssl.phizic.gate.bankroll.Account gateAccount = new AccountImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateAccount, account_1, BankrollTypesCorrelation.types);

			AbstractBase anAbstract = service.getAccountExtendedAbstract(gateAccount, calendar_2, calendar_3);

			AccountAbstract generatedAbstract = new AccountAbstract();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAbstract, anAbstract, BankrollTypesCorrelation.types);

			return generatedAbstract;
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

	public GroupResult getAdditionalCards(Card... card_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);

			for (int i=0; i<card_1.length; i++)
			{
				card_1[i].setId(decodeData(card_1[i].getId()));
			}

			com.rssl.phizic.gate.bankroll.Card[] gateCard = new CardImpl[card_1.length];
			BeanHelper.copyPropertiesWithDifferentTypes(gateCard, card_1, BankrollTypesCorrelation.types);
			com.rssl.phizic.common.types.transmiters.GroupResult<com.rssl.phizic.gate.bankroll.Card, List<com.rssl.phizic.gate.bankroll.Card>>  cards = service.getAdditionalCards(gateCard);
			com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult generatedCards = new com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedCards, cards, BankrollTypesCorrelation.types);
			return generatedCards;
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

	public GroupResult getCard(String[] string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);
			String[] decoded = decodeData(string_1);
			com.rssl.phizic.common.types.transmiters.GroupResult<String, com.rssl.phizic.gate.bankroll.Card> gateGroupResult = service.getCard(decoded);
			com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult generatedGroupResult = new com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedGroupResult, gateGroupResult, BankrollTypesCorrelation.types);
			return generatedGroupResult;
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

	public GroupResult getCardAbstract(Long long_2, Card... card_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);

			for (int i=0; i<card_1.length; i++)
			{
				card_1[i].setId(decodeData(card_1[i].getId()));
			}

			com.rssl.phizic.gate.bankroll.Card[] gateCard = new CardImpl[card_1.length];
			BeanHelper.copyPropertiesWithDifferentTypes(gateCard, card_1, BankrollTypesCorrelation.types);

			com.rssl.phizic.common.types.transmiters.GroupResult anAbstract = service.getAbstract(long_2, gateCard);

			GroupResult generatedResult = new GroupResult();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedResult, anAbstract, BankrollTypesCorrelation.types);

			return generatedResult;
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

	public CardAbstract getCardAbstract2(Card card_1, Calendar calendar_2, Calendar calendar_3, Boolean withCardUseInfo) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);

			String decoded = decodeData(card_1.getId());
			card_1.setId(decoded);

			com.rssl.phizic.gate.bankroll.Card gateCard = new CardImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateCard, card_1, BankrollTypesCorrelation.types);

			AbstractBase anAbstract = service.getAbstract(gateCard, calendar_2, calendar_3, withCardUseInfo);

			CardAbstract generatedAbstract = new CardAbstract();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAbstract, anAbstract, BankrollTypesCorrelation.types);

			return generatedAbstract;
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

	public GroupResult getCardPrimaryAccount(Card... card_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);

			for (int i=0; i<card_1.length; i++)
			{
				card_1[i].setId(decodeData(card_1[i].getId()));
			}

			com.rssl.phizic.gate.bankroll.Card[] gateCard = new CardImpl[card_1.length];
			BeanHelper.copyPropertiesWithDifferentTypes(gateCard, card_1, BankrollTypesCorrelation.types);
			com.rssl.phizic.common.types.transmiters.GroupResult<com.rssl.phizic.gate.bankroll.Card, com.rssl.phizic.gate.bankroll.Account> gateAccount = service.getCardPrimaryAccount(gateCard);
			com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult generatedAccount = new com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAccount, gateAccount, BankrollTypesCorrelation.types);

			return generatedAccount;
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

	public List getClientAccounts(Client client_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);

			String decoded = decodeData(client_1.getId());
			client_1.setId(decoded);

			com.rssl.phizic.gate.clients.Client client = new ClientImpl();

			BeanHelper.copyPropertiesWithDifferentTypes(client, client_1, BankrollTypesCorrelation.types);

			List<com.rssl.phizic.gate.bankroll.Account>  accounts = service.getClientAccounts(client);

			List<Account> generatedAccounts = new ArrayList<Account>();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAccounts, accounts, BankrollTypesCorrelation.types);

			return generatedAccounts;

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

	public List getClientCards(Client client_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);

			String decoded = decodeData(client_1.getId());
			client_1.setId(decoded);

			com.rssl.phizic.gate.clients.Client client = new ClientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(client, client_1, BankrollTypesCorrelation.types);

			List<com.rssl.phizic.gate.bankroll.Card>  cards = service.getClientCards(client);

			List<Card> generatedCards = new ArrayList<Card>();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedCards, cards, BankrollTypesCorrelation.types);

			return generatedCards;

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

	public GroupResult getOwnerInfo(Account... account_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);

			for (int i=0; i<account_1.length; i++)
			{
				account_1[i].setId(decodeData(account_1[i].getId()));
			}

			com.rssl.phizic.gate.bankroll.Account[] gateAccount = new AccountImpl[account_1.length];
			BeanHelper.copyPropertiesWithDifferentTypes(gateAccount, account_1, BankrollTypesCorrelation.types);

			com.rssl.phizic.common.types.transmiters.GroupResult gateClient = service.getOwnerInfo(gateAccount);

			com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult generatedClient = new com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, gateClient, BankrollTypesCorrelation.types);

			return generatedClient;
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

	public GroupResult getOwnerInfo2(Card... card_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);

			for (int i=0; i<card_1.length; i++)
			{
				card_1[i].setId(decodeData(card_1[i].getId()));
			}

			com.rssl.phizic.gate.bankroll.Card[] gateCard = new CardImpl[card_1.length];
			BeanHelper.copyPropertiesWithDifferentTypes(gateCard, card_1, BankrollTypesCorrelation.types);

			com.rssl.phizic.common.types.transmiters.GroupResult gateClient = service.getOwnerInfo(gateCard);

			com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult generatedClient = new com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, gateClient, BankrollTypesCorrelation.types);

			return generatedClient;
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

	public GroupResult getOwnerInfoByCardNumber(Pair ... cardInfo) throws RemoteException
	{
		return null;
	}

	public GroupResult getCardByNumber(com.rssl.phizic.web.gate.services.bankroll.generated.Client client_1, Pair... cardInfo) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.clients.Client client = new ClientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(client, client_1, BankrollTypesCorrelation.types);

			com.rssl.phizic.common.types.transmiters.Pair[] gatePair = new com.rssl.phizic.common.types.transmiters.Pair[cardInfo.length];
			BeanHelper.copyPropertiesWithDifferentTypes(gatePair, cardInfo, BankrollTypesCorrelation.types);

			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);
			com.rssl.phizic.common.types.transmiters.GroupResult<com.rssl.phizic.common.types.transmiters.Pair<String, com.rssl.phizic.gate.dictionaries.officies.Office>, com.rssl.phizic.gate.bankroll.Card>  gateGroupResult = service.getCardByNumber(client, gatePair);

			com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult generatedGroupResult = new com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedGroupResult, gateGroupResult, BankrollTypesCorrelation.types);
			return generatedGroupResult;
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

	public ClientDocument __forGenerateClientDocument() throws RemoteException
	{
		return null;
	}

	public IKFLException __forGenerateIKFLException() throws RemoteException
	{
		return null;
	}

	public Trustee __forGenerateTrustee() throws RemoteException
	{
		return null;
	}

	public AccountTransaction __forGenerateAccountTransaction() throws RemoteException
	{
		return null;
	}

	public CardOperation __forGenerateCardOperation() throws RemoteException
	{
		return null;
	}

	public CardInfo __forGenerateCardInfo() throws RemoteException
	{
		return null;
	}

	public OverdraftInfo __forGenerateOverdraftInfo() throws RemoteException
	{
		return null;
	}

	public AccountInfo __forGenerateAccountInfo() throws RemoteException
	{
		return null;
	}

	private static String[] decodeData(String[] data)
	{
		String[] newData = data;
		for (int i=0; i<data.length; i++)
		{
			newData[i] = decodeData(data[i]);
		}
		return newData;
	}

	private static String decodeData(String data)
	{
		//вырезаем конечные "=="
		String end = "";
		int i = data.indexOf('=');
		if (i >= 0)
		{
			end = data.substring(i);
			data = data.substring(0, i);
		}
		//декодируем
		byte[] bytes = EncodeHelper.decode(data.getBytes());

		//вырезаем контрольную информацию
		byte contralInfo = bytes[0];
		//получаем строку дл€ раскодировани€ из base64
		byte[] base64bytes = new byte[bytes.length - 1 + end.length()];
		System.arraycopy(bytes, 1, base64bytes, 0, bytes.length - 1);
		System.arraycopy(end.getBytes(), 0, base64bytes, bytes.length - 1, end.length());

		//провер€ем контрольную информацию
		checkContralInfo(base64bytes, contralInfo);
		byte[] decodedBase64 = Base64.decodeBase64(base64bytes);

		return new String(decodedBase64);
	}

	private static final byte[] info = new byte[64];

	static
	{
		info[0] = 'A';info[1] = 'B';info[2] = 'C';
		info[3] = 'D';info[4] = 'E';info[5] = 'F';
		info[6] = 'G';info[7] = 'H';info[8] = 'I';
		info[9] = 'J';info[10] = 'K';info[11] = 'L';
		info[12] = 'M';info[13] = 'N';info[14] = 'O';
		info[15] = 'P';info[16] = 'Q';info[17] = 'R';
		info[18] = 'S';info[19] = 'T';info[20] = 'U';
		info[21] = 'V';info[22] = 'W';info[23] = 'X';
		info[24] = 'Y';info[25] = 'Z';info[26] = 'a';
		info[27] = 'b';info[28] = 'c';info[29] = 'd';
		info[30] = 'e';info[31] = 'f';info[32] = 'g';
		info[33] = 'h';info[34] = 'i';info[35] = 'j';
		info[36] = 'k';info[37] = 'l';info[38] = 'm';
		info[39] = 'n';info[40] = 'o';info[41] = 'p';
		info[42] = 'q';info[43] = 'r';info[44] = 's';
		info[45] = 't';info[46] = 'u';info[47] = 'v';
		info[48] = 'w';info[49] = 'x';info[50] = 'y';
		info[51] = 'z';info[52] = '0';info[53] = '1';
		info[54] = '2';info[55] = '3';info[56] = '4';
		info[57] = '5';info[58] = '6';info[59] = '7';
		info[60] = '8';info[61] = '9';info[62] = '+';
		info[63] = '/';
	}

	private static void checkContralInfo(byte[] data, byte contralInfo)
	{
		int sum = 0;
		for (byte b : data)
		{
			sum += b;
		}
		if (info[sum % info.length] != contralInfo)
		{
			throw new IllegalArgumentException("Ќарушены лицензионные ограничени€");
		}
	}

	public AccountAbstract getAccHistoryFullExtract(Account account_1, Calendar calendar_2, Calendar calendar_3, Boolean c) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.bankroll.BankrollService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.bankroll.BankrollService.class);

			String decoded = decodeData(account_1.getId());
			account_1.setId(decoded);

			com.rssl.phizic.gate.bankroll.Account gateAccount = new AccountImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateAccount, account_1, BankrollTypesCorrelation.types);

			AbstractBase anAbstract = service.getAccHistoryFullExtract(gateAccount, calendar_2, calendar_3, c);

			AccountAbstract generatedAbstract = new AccountAbstract();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedAbstract, anAbstract, BankrollTypesCorrelation.types);

			return generatedAbstract;
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

}
