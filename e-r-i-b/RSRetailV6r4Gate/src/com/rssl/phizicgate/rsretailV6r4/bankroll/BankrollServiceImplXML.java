package com.rssl.phizicgate.rsretailV6r4.bankroll;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountInfo;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.rsretailV6r4.messaging.RetailXMLHelper;
import com.rssl.phizicgate.rsretailV6r4.money.CurrencyHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author Omeliyanchuk
 * @ created 20.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class BankrollServiceImplXML extends BankrollServiceImpl
{
    public BankrollServiceImplXML(GateFactory factory)
    {
        super(factory);
    }

	public List<Account> getClientAccounts(final Client client) throws GateException, GateLogicException
	{
		Document document = null;

		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getClientAccounts_q");
		message.addParameter("clientId", client.getId());

		document = service.sendOnlineMessage(message, null);

		NodeList nodes =  null;
		try
		{
			nodes = XmlHelper.selectNodeList(document.getDocumentElement(), "accounts/account");
		}
		catch (TransformerException ex)
		{
			throw new GateException(ex);
		}

		int count = nodes.getLength();

		List<Account> clients = new ArrayList<Account>(count);

		for (int i = 0; i < count; i++)
		{
			Element element = (Element) nodes.item(i);
			AccountImpl account = parseAccount(element);
			fillOffice(account);
			clients.add(account);
		}

		return clients;
	}

	public GroupResult<String, Account> getAccount(String... accountIds)
	{
		GroupResult<String, Account> res = new GroupResult<String, Account>();
		for (String accountId: accountIds)
		{
			try
			{
				res.putResult(accountId, getAccount(accountId.trim(), false));
			}catch (IKFLException e)
			{
				res.putException(accountId, e);
			}
		}
		return res;
	}

	/**
	 * Заполнение информации по счету
	 * @param id идентификатор счета во внешней системе
	 * @param isCardAccount признак карточного счета
	 * @return AccountImpl
	 */
	public AccountImpl getAccount(String id, boolean isCardAccount) throws GateException, GateLogicException
	{
		Document document = null;

		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getAccount_q");
		message.addParameter("accountReferenc", id);

		document = service.sendOnlineMessage(message, null);

		Element root = document.getDocumentElement();
		AccountImpl account = parseAccount(root);
		fillOffice(account);
		if (!isCardAccount)
			fillRate(account);
		return account;
	}

	public static AccountImpl parseAccount(Element accountRoot) throws GateException, GateLogicException
	{
		AccountImpl account = new AccountImpl();
		account.setLongId(Long.parseLong( RetailXMLHelper.getElementValue(accountRoot, "id") ));
		account.setNumber( RetailXMLHelper.getElementValue(accountRoot, "number"));
		account.setDescription( RetailXMLHelper.getElementValue(accountRoot, "description") );

		CurrencyHelper currencyHelper = new CurrencyHelper();
		Long currencyId = RetailXMLHelper.getElementLong(accountRoot, "currency");
		if(currencyId!=null)
			account.setCurrency(currencyHelper.getGeneralCurrencyById(currencyId));

		account.setOpenDate( RetailXMLHelper.getElementDate(accountRoot, "openDate") );
		account.setBranch(RetailXMLHelper.getElementLong(accountRoot, "branch"));

		account.setCreditAllowed( "1".equals(RetailXMLHelper.getElementValue(accountRoot, "creditAllowed")));
		account.setDebitAllowed( "1".equals(RetailXMLHelper.getElementValue(accountRoot, "debitAllowed")));

		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getAccountInfo_q");
		message.addParameter("accountReferenc", account.getId());
		Document document = service.sendOnlineMessage(message, null);
		Element balanceInfo = document.getDocumentElement();
		BigDecimal decimal = new BigDecimal(RetailXMLHelper.getElementValue(balanceInfo, "decimalBalance"));
		Currency currency = currencyHelper.getGeneralCurrencyById(currencyId);
		Money balance = new Money(decimal,currency);
		account.setBalance(balance);

		if ("1".equals( RetailXMLHelper.getElementValue(balanceInfo, "isOpen")))
			account.setAccountState(AccountState.OPENED);
		if ("1".equals( RetailXMLHelper.getElementValue(balanceInfo, "isLock")))
			account.setAccountState(AccountState.LOST_PASSBOOK);
		return account;
	}

	public GroupResult<Account, AccountInfo> getAccountInfo(Account... accounts)
	{
		GroupResult<Account, AccountInfo> res = new GroupResult<Account, AccountInfo>();

		for (Account account: accounts)
		{
			try
			{
				Document document = null;
				WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
				GateMessage message = service.createRequest("getAccountInfo_q");
				message.addParameter("accountReferenc", account.getId());

				document = service.sendOnlineMessage(message, null);
				Element root = document.getDocumentElement();
				res.putResult(account, parseAccountInfo(root));
			}
			catch (IKFLException e)
			{
				res.putException(account, e);
			}
		}
		return res;
	}

	private AccountInfo parseAccountInfo(Element accountInfoRoot) throws GateException, GateLogicException
	{
		AccountInfoImpl accountInfo = new AccountInfoImpl();

		accountInfo.setAccountId(Long.parseLong( RetailXMLHelper.getElementValue(accountInfoRoot, "accountId") ));
		accountInfo.setLastTransactionDate( RetailXMLHelper.getElementDate(accountInfoRoot, "lastTransactionDate") );

		Long currencyId = RetailXMLHelper.getElementLong(accountInfoRoot, "currencyId");
		CurrencyHelper currencyHelper = new CurrencyHelper();
		BigDecimal decimal = new BigDecimal(RetailXMLHelper.getElementValue(accountInfoRoot, "decimalBalance"));
		Currency currency = currencyHelper.getGeneralCurrencyById(currencyId);
		Money balance = new Money(decimal,currency);
		accountInfo.setCloseDate( RetailXMLHelper.getElementDate(accountInfoRoot, "closeDate") );
		accountInfo.setAgreementNumber( RetailXMLHelper.getElementValue(accountInfoRoot, "agreementNumber") );
		accountInfo.setPassbook(("X").equals(RetailXMLHelper.getElementValue(accountInfoRoot, "passbook")));
		return accountInfo;
	}

	public GroupResult<String, Card> getCard(String... externalIds)
	{
		GroupResult<String, Card> res = new GroupResult<String, Card>();

		for (String externalId: externalIds)
		{
			try
			{
				Document document = null;

				final Long cardId   = CardImpl.parseCardId(externalId);
				final Long clientId = CardImpl.parseOwnerId(externalId);


				WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
				GateMessage message = service.createRequest("getCard_q");
				message.addParameter("cardId", cardId);
				message.addParameter("clientId", clientId);

				document = service.sendOnlineMessage(message, null);

				Element root = document.getDocumentElement();

				res.putResult(externalId, parseCard(root, clientId));
			}
			catch (IKFLException e)
			{
				res.putException(externalId, e);
			}
		}
		return res;
	}

	private CardImpl parseCard(Element cardRoot, long clientId) throws GateException, GateLogicException
	{
		CardImpl card = new CardImpl();

		card.setLongId(Long.parseLong( RetailXMLHelper.getElementValue(cardRoot, "id") ));
		card.setDescription( RetailXMLHelper.getElementValue(cardRoot, "description") );
		card.setNumber( RetailXMLHelper.getElementValue(cardRoot, "number") );
		card.setIssueDate( RetailXMLHelper.getElementDate(cardRoot, "issueDate") );
		card.setExpireDate( RetailXMLHelper.getElementDate(cardRoot, "expireDate") );
		card.setDisplayedExpireDate(DateHelper.toDisplayedExpiredate(card.getExpireDate()));
		card.setOwnerId(clientId);
		card.setMain("1".equals(RetailXMLHelper.getElementValue(cardRoot, "isMain")));

		String cardType = RetailXMLHelper.getElementValue(cardRoot, "cardType");
		card.setCardType( parseCardType(cardType) );

		//Заполнение СКС карты
		fillPrimaryAccount(card);
		//Account a = GroupResultHelper.getOneResult(getCardPrimaryAccount(card)).getResult(card);
		return card;
	}

	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		Document document = null;

		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getClientCards_q");
		message.addParameter("clientId", client.getId());

		document = service.sendOnlineMessage(message, null);

		NodeList nodes =  null;
		try
		{
			nodes = XmlHelper.selectNodeList(document.getDocumentElement(), "cards/card");
		}
		catch (TransformerException ex)
		{
			throw new GateException(ex);
		}

		int count = nodes.getLength();

		List<Card> cards = new ArrayList<Card>(count);
		long clientId=Long.parseLong(client.getId());

		for (int i = 0; i < count; i++)
		{
			Element element = (Element) nodes.item(i);
			cards.add(parseCard(element, clientId));
		}

		return cards;
	}

	public GroupResult<Card, Account> getCardPrimaryAccount(Card... cards)
	{
		GroupResult<Card, Account> result = new GroupResult<Card, Account>();

		if(cards==null || cards.length==0)
			return result;

		Map<Card,String> cardsAccountIds = null;
		try
		{
			RequestHelper helper = new RequestHelper(getFactory());
			cardsAccountIds = helper.getCardsAccountIds(cards);
		}
		catch (GateException ex)
		{
			return GroupResultHelper.getOneErrorResult(cards,new GateException(ex));
		}
		catch (GateLogicException ex)
		{
			return GroupResultHelper.getOneErrorResult(cards, new GateException(ex));
		}

		for (Card card : cards)
		{
			try
			{
				String accountReference = cardsAccountIds.get(card);
				if (StringHelper.isEmpty(accountReference))
				{
					result.putResult(card, null);
				}
				else
				{
					result.putResult(card, getAccount(accountReference.trim(), true));
				}
			}
			catch (IKFLException ex)
			{
				result.putException(card, ex);
			}
		}
		return result;
	}
}