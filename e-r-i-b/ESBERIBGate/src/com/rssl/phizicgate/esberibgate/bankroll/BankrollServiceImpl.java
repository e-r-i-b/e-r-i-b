package com.rssl.phizicgate.esberibgate.bankroll;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.cache.CacheKeyConstants;
import com.rssl.phizicgate.esberibgate.cache.ESBCacheKeyGenerator;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;
import com.rssl.phizicgate.esberibgate.messaging.AccountResponseSerializer;
import com.rssl.phizicgate.esberibgate.messaging.CardResponseSerializer;
import com.rssl.phizicgate.esberibgate.types.AccountImpl;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author: Pakhomova
 * @created: 15.04.2010
 * @ $Author$
 */
public class BankrollServiceImpl extends AbstractService implements BankrollService
{
	private CardResponseSerializer cardResponseSerializer = new CardResponseSerializer();
	private AccountResponseSerializer accountResponseSerializer = new AccountResponseSerializer();
	private BankrollRequestHelper bankrollRequestHelper = new BankrollRequestHelper(getFactory());

	private static final String SRB_RB_TB_BRANCH_ID = "40400001";

	private static final String SHORT_ACCOUNT_ABSTRACT = "Short10";
	private static final String FULL_ACCOUNT_ABSTRACT = "Full";

	// Кеш сообщений детальной информации по карте. Ключом служит связка "номер карты"-"LOGIN_ID"
	private static final Cache cardDetailsCache;
	private static final Cache accountDetailsCache;


	static
	{
		cardDetailsCache = CacheProvider.getCache(CacheKeyConstants.CARD_DETAILS_CACHE);
		accountDetailsCache = CacheProvider.getCache(CacheKeyConstants.ACCOUNT_DETAILS_CACHE);
	}

	public BankrollServiceImpl(GateFactory factory) throws GateException
	{
		super(factory);
	}

	/**
	 * Проверяем пришедший ответ из шины. Если в ответе -102 ошибка, т.е. на подразделении не поддерживается БП
	 * Тогда необходимо запросить информацию по картам только из WAY
	 * @param ifxRs - пришедший ответ из шины
	 * @param client - клиент
	 * @param document - документ
	 * @return Новый запрос в шину либо null, если нет необходимости в повторном запросе
	 * @throws GateLogicException, GateException
	 */
	private IFXRq_Type onlyWayRequest(IFXRs_Type ifxRs, Client client, ClientDocument document) throws GateLogicException, GateException
	{
		//Если вернулось -102, то повторяем запрос только с картами из WAY от имени Среднерусского банка.		
		if (!cardResponseSerializer.isESBReallySupported(ifxRs))
		{
			ProductContainer cardWayContainer = bankrollRequestHelper.createBankAcctInqRq(client, document, SRB_RB_TB_BRANCH_ID, BankProductType.CardWay);
			return cardWayContainer.getIfxRq_type();
		}
		return null;
	}

	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		List<? extends ClientDocument> documents = client.getDocuments();
		if (documents.isEmpty())
			throw new GateException("Не заполнены документы для клиента с id=" + client.getId());

		Collections.sort(documents, new DocumentTypeComparator());

		for (ClientDocument document : documents)
		{
			IFXRs_Type ifxRs;

			ProductContainer cardConteiner = bankrollRequestHelper.createBankAcctInqRq(client, document, BankProductType.Card);
			if (cardConteiner.getIfxRq_type() != null)
			{
				//если внешняя система активна
				ifxRs = getRequest(cardConteiner.getIfxRq_type());
				IFXRq_Type wayRequest = onlyWayRequest(ifxRs, client, document);
				if (wayRequest != null)
					ifxRs = getRequest(wayRequest);
			}
			//если внешняя система неактивна и документ - паспорт way
			else if (ClientDocumentType.PASSPORT_WAY ==	document.getDocumentType())
			{
				//внешняя система не активна, получаем карты из way
				ProductContainer cardWayConteiner = bankrollRequestHelper.createBankAcctInqRq(client, document, SRB_RB_TB_BRANCH_ID, BankProductType.CardWay);
				if (cardWayConteiner.getIfxRq_type() == null)
				{
					//way неактивен, цод неактивен
					throw new InactiveExternalSystemException(cardConteiner.getProductError(BankProductType.Card));
				}

				ifxRs = getRequest(cardWayConteiner.getIfxRq_type());
			}
			else
			{
				continue;
			}

			String[] cardIds = cardResponseSerializer.getCardIds(cardConteiner.getIfxRq_type(), ifxRs, client.getInternalOwnerId());
			if (ArrayUtils.isNotEmpty(cardIds))
				return GroupResultHelper.getResults(getCard(cardIds));
		 }
		return null;
	}

	public GroupResult<Object, AbstractBase> getAbstract(Long number, Object... object)
	{
		if (object[0] instanceof Card)
			return getAbstract(number, toEntity(object, new Card[object.length]));
		if (object[0] instanceof Account)
			return getAbstract(number, toEntity(object, new Account[object.length]));
		return GroupResultHelper.getOneErrorResult(object,
				new GateException("Выписка по объекту " + object.getClass().getName() + " не поддерживается"));
	}

	private GroupResult getAbstract(Long number, Account... accounts)
	{
		try
		{
			GroupResult<Account, AbstractBase> result = new GroupResult<Account, AbstractBase>();
			for (Account account : accounts)
			{
				try
				{
					IFXRq_Type ifxRq = bankrollRequestHelper.createDepAcctStmtInqRq(SHORT_ACCOUNT_ABSTRACT, null, null, account);
					IFXRs_Type ifxRs = getRequest(ifxRq);
					result.add(accountResponseSerializer.fillGroupAccountAbstract(ifxRs, null, null, account));
				}
				catch (GateLogicException e)
				{
					//Локальная ошибка (шина вернула по данному вкладу/счету ответ с ошибкой при получении выписки)
					result.putException(account, e);
				}
			}
			return result;
		}
		catch (GateException e)
		{
			//Глобальная ошибка (шина вернула выписку не по тому вкладу/счету или ничего не вернула)
			return  GroupResultHelper.getOneErrorResult(accounts,e);
		}
	}

	private GroupResult getAbstract(Long number, Card... object)
	{
		try
		{
			IFXRq_Type ifxRq = bankrollRequestHelper.createCCAcctExtStmtInqRq(number, (Card[]) object);
			IFXRs_Type ifxRs = getRequest(ifxRq);
			return cardResponseSerializer.fillGroupCardAbstract(ifxRs, object);
		}
		catch (GateException e)
		{
			return GroupResultHelper.getOneErrorResult(object, e);
		}
		catch (GateLogicException e)
		{
			return GroupResultHelper.getOneErrorResult(object, e);
		}
	}

	public GroupResult<String, Card> getCard(String... cardIds)
	{
		GroupResult<String, Card> res = new GroupResult<String, Card>();
		if (cardIds.length == 0)
			return res;
		for (String cardId : cardIds)
		{
			try
			{
				IFXRs_Type ifxRs = getCardDetails(cardId);
				GroupResult<String, Card> tmpRes = cardResponseSerializer.fillCardsByIds(ifxRs, cardId);
				res.add(tmpRes);
			}
			catch (GateException e)
			{
				res.putException(cardId, e);
			}
			catch (GateLogicException e)
			{
				res.putException(cardId, e);
			}
		}
		return res;
	}

	//	Получить карту по ее номеру. в заданном офисе, если офиса нет, то везде
	public GroupResult<Pair<String, Office>, Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		GroupResult<Pair<String, Office>, Card> result = new GroupResult<Pair<String, Office>, Card>();

		if (cardInfo.length == 0)
			return result;

		//Получаем карты
		for (Pair<String, Office> pair : cardInfo)
		{
			try
			{
				IFXRs_Type ifxRs = getCardDetails(bankrollRequestHelper.getRbTbBrch(client.getInternalOwnerId()), pair);
				GroupResult<Pair<String, Office>, Card> tmpRes = cardResponseSerializer.fillCardsByNum(ifxRs, client, pair);
				result.add(tmpRes);
			}
			catch (GateException e)
			{
				result.putException(pair, e);
			}
			catch (GateLogicException e)
			{
				result.putException(pair, e);
			}
		}
		return result;
	}

	public GroupResult<Card, Client> getOwnerInfo(Card... cards)
	{
		if (cards.length == 0)
			return new GroupResult<Card, Client>();

		GroupResult<Card, Client> res = new GroupResult<Card, Client>();
		for (Card card : cards)
		{
			try
			{
				IFXRs_Type ifxRs = getCardDetails(card.getId());
				GroupResult<Card, Client> tmpRes = cardResponseSerializer.fillClient(ifxRs, card);
				res.add(tmpRes);
			}
			catch (GateException e)
			{
				res.putException(card, e);
			}
			catch (GateLogicException e)
			{
				res.putException(card, e);
			}
		}
		return res;
	}

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office>... cardInfo)
	{
		if (ArrayUtils.isEmpty(cardInfo))
		{
			return new GroupResult<Pair<String, Office>, Client>();
		}

		GroupResult<Pair<String, Office>, Client> responce = new GroupResult<Pair<String, Office>, Client>();
		for (Pair<String, Office> pair : cardInfo)
		{
			try
			{
				IFXRs_Type ifxRs = getCardDetails(bankrollRequestHelper.generateRbTbBrchId(pair.getSecond()), pair);
				responce.add(cardResponseSerializer.fillClient(ifxRs, pair));
			}
			catch (GateException e)
			{
				responce.putException(pair, e);
			}
			catch (GateLogicException e)
			{
				responce.putException(pair, e);
			}
		}
		return responce;
	}

	public GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCard)
	{
		GroupResult<Card, List<Card>> result = new GroupResult<Card, List<Card>>();

		if (ArrayUtils.isEmpty(mainCard))
			return result;

		try
		{
			IFXRq_Type ifxRq = bankrollRequestHelper.createCardAdditionalInfoRq(mainCard);
			IFXRs_Type ifxRs = getRequest(ifxRq);
			result.add(cardResponseSerializer.fillAdditionalCards(ifxRs, mainCard));
		}
		catch (GateException e)
		{
			GroupResultHelper.getOneErrorResult(mainCard, e);
		}
		catch (GateLogicException e)
		{
			GroupResultHelper.getOneErrorResult(mainCard, e);
		}

		return result;
	}

	//Получаем счет из GFL
	public GroupResult<Card, Account> getCardPrimaryAccount(Card... card)
	{

		BackRefBankrollService backRefBankrollService = GateSingleton.getFactory().service(BackRefBankrollService.class);
		GroupResult<Card, Account> res = new GroupResult<Card, Account>();
	    for (Card oneCard: card)
		{
			try
			{
				EntityCompositeId compositeId = EntityIdHelper.getCardCompositeId(oneCard);
				Account account = backRefBankrollService.getCardAccount(compositeId.getLoginId(), oneCard.getNumber());
				if (account == null)
				{
					res.putResult(oneCard, null);
					continue;
				}
				AccountImpl accountImpl = new AccountImpl(account);
				CardOrAccountCompositeId cardGFLCompositeId = EntityIdHelper.getCardOrAccountCompositeId(account.getId());
				accountImpl.setOffice(accountResponseSerializer.getCorrectedOffice(cardGFLCompositeId.getRegionId(),
						cardGFLCompositeId.getAgencyId(), cardGFLCompositeId.getBranchId()));
				accountImpl.setCurrency(oneCard.getCurrency());
				res.putResult(oneCard, accountImpl);
			}
			catch (GateLogicException e)
			{
				return GroupResultHelper.getOneErrorResult(card, e);
			}
			catch (GateException e)
			{
				return GroupResultHelper.getOneErrorResult(card, e);
			}
		}
		return res;
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		if (object instanceof Account)
		{
			Account account = (Account) object;
			IFXRq_Type ifxRq = bankrollRequestHelper.createDepAcctStmtInqRq(FULL_ACCOUNT_ABSTRACT, fromDate, toDate, account);

			try
			{
				return GroupResultHelper.getOneResult(accountResponseSerializer.fillGroupAccountAbstract(getRequest(ifxRq), fromDate, toDate, (Account) object));
			}
			catch (SystemException e)
			{
				throw new GateException(e);
			}
			catch (LogicException e)
			{
				throw new GateLogicException(e);
			}
		}
		throw new UnsupportedOperationException();
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		return (AccountAbstract) getAbstract(account, fromDate, toDate, Boolean.FALSE);
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		List<? extends ClientDocument> documents = client.getDocuments();
		if (documents.isEmpty())
			throw new GateException("Не заполнены документы для клиента с id=" + client.getId());
		Collections.sort(documents, new DocumentTypeComparator());
		List<Account> accounts = new ArrayList<Account>();
		for (ClientDocument document : documents)
		{
			ProductContainer container = bankrollRequestHelper.createBankAcctInqRq(client, document, BankProductType.Deposit);
			if (container.getIfxRq_type() == null)
				throw new InactiveExternalSystemException(container.getProductError(BankProductType.Deposit));

			// Посылаем GFL-запрос
			IFXRs_Type ifxRs = getRequest(container.getIfxRq_type());
			// Из вернувшегося ответа получаем только номера счетов
			DepAcctRec_Type[] accountRecs = ifxRs.getBankAcctInqRs().getDepAcctRec();
			if ((accountRecs == null) || (accountRecs.length < 0))
				return null;

			String[] accountIds = new String[accountRecs.length];
			for (int i = 0; i < accountRecs.length; i++)
			{
				// Формируем внешний идентификатор сущности
				accountIds[i] = EntityIdHelper.createAccountCompositeId(accountRecs[i], client.getInternalOwnerId());
			}

			// Сами счета получаем по запросу детальной информации по каждому счету.
			accounts = GroupResultHelper.getResults(getAccount(accountIds));
			if (!accounts.isEmpty())
				break;
		}
		return accounts;
	}

	private IFXRs_Type getRsByAcctInfoRq(String accountExternalId) throws GateException, GateLogicException
	{
		String cacheKey = ESBCacheKeyGenerator.getCardOrAccountDetailsKey(accountExternalId);
		Element element = accountDetailsCache.get(cacheKey);
		if (element == null)
		{
			IFXRs_Type ifxRs = getRsByAcctInfoRq(accountExternalId, null);
			if (checkAccountDetails(ifxRs))
				accountDetailsCache.put(new Element(cacheKey, ifxRs));
			return ifxRs;
		}
		else
			return (IFXRs_Type) element.getObjectValue();
	}

	private IFXRs_Type getRsByAcctInfoRq(String accountId, Office office) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = bankrollRequestHelper.createAcctInfoRq(accountId, office);
		return getRequest(ifxRq);
	}

	public GroupResult<String, Account> getAccount(String... accountIds)
	{
		GroupResult<String, Account> res = new GroupResult<String, Account>();
		if (accountIds.length == 0)
			return res;
		for (String accountId : accountIds)
		{
			try
			{
				IFXRs_Type ifxRs = getRsByAcctInfoRq(accountId);
				res.add(accountResponseSerializer.fillAccountByAccountInfo(ifxRs, accountId, accountIds));
			}
			catch (GateException e)
			{
				res.putException(accountId, e);
			}
			catch (GateLogicException e)
			{
				res.putException(accountId, e);
			}
		}
		return res;
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountsInfo)
	{
		GroupResult<Pair<String, Office>, Account> res = new GroupResult<Pair<String, Office>, Account>();
		BackRefBankrollService backRefBankrollService = GateSingleton.getFactory().service(BackRefBankrollService.class);

		if (ArrayUtils.isEmpty(accountsInfo))
		{
			return res;
		}
		for (Pair<String, Office> accountInfo : accountsInfo)
		{
			try
			{
				String accountId = backRefBankrollService.findAccountExternalId(accountInfo.getFirst());
				IFXRs_Type ifxRs = getRsByAcctInfoRq(accountId, accountInfo.getSecond());
				res.add(accountResponseSerializer.fillAccountByAccountInfo(ifxRs, new Pair<Pair<String, Office>, String>(accountInfo, accountId)));
			}
			catch (GateException e)
			{
				res.putException(accountInfo, e);
			}
			catch (GateLogicException e)
			{
				res.putException(accountInfo, e);
			}
		}
		return res;
	}

	public GroupResult<Account, Client> getOwnerInfo(Account... accounts)
	{
		GroupResult<Account, Client> res = new GroupResult<Account, Client>();

		for (Account account : accounts)
		{
			try
			{
				IFXRs_Type ifxRs = getRsByAcctInfoRq(account.getId());
				res.add(accountResponseSerializer.fillOwnerInfo(ifxRs, accounts));
			}
			catch (IKFLException e)
			{
				res.putException(account, e);
			}
		}
		return res;
	}

	private IFXRs_Type getCardDetails(String cardId) throws GateLogicException, GateException
	{
		String cacheKey = ESBCacheKeyGenerator.getProductDetailsKey(Card.class, cardId);
		Element element = cardDetailsCache.get(cacheKey);

		if (element == null)
		{
			IFXRq_Type ifxRq = bankrollRequestHelper.createCardAcctDInqRq(new String[]{cardId});
			IFXRs_Type ifxRs = getRequest(ifxRq);

			// Кладем сообщение в кеш только в случае ответа без ошибок
			if (checkCardDetails(ifxRs))
				cardDetailsCache.put(new Element(cacheKey, ifxRs));

			return ifxRs;
		}

		return (IFXRs_Type) element.getObjectValue();
	}

	private IFXRs_Type getCardDetails(String rbTbBrunch, Pair<String, Office> pair) throws GateLogicException, GateException
	{
		String cacheKey = ESBCacheKeyGenerator.getProductDetailsKey(Card.class, pair.getFirst(), rbTbBrunch);
		Element element = cardDetailsCache.get(cacheKey);

		if (element == null)
		{
			IFXRq_Type ifxRq = bankrollRequestHelper.createCardAcctDInqRq(rbTbBrunch, pair);
			IFXRs_Type ifxRs = getRequest(ifxRq);

			// Кладем сообщение в кеш только в случае ответа без ошибок
			if (checkCardDetails(ifxRs))
				cardDetailsCache.put(new Element(cacheKey, ifxRs));

			return ifxRs;
		}

		return (IFXRs_Type) element.getObjectValue();
	}

	private boolean checkCardDetails(IFXRs_Type ifxRs)
	{
		CardAcctDInqRs_Type cardAcctDInqRs = ifxRs.getCardAcctDInqRs();
		if (cardAcctDInqRs == null)
			return false;

		for (CardAcctRec_Type cardAcctRec : cardAcctDInqRs.getBankAcctRec())
		{
			if (cardAcctRec.getStatus().getStatusCode() != 0L)
				return false;
		}

		return true;
	}

	private boolean checkAccountDetails(IFXRs_Type ifxRs)
	{
		AcctInfoRs_Type acctInfoRs = ifxRs.getAcctInfoRs();
		if (acctInfoRs == null)
			return false;

		for (DetailAcctInfo_Type acctRec : acctInfoRs.getDetailAcctInfo())
		{
			if (acctRec.getStatus().getStatusCode() != 0L)
				return false;
		}

		return true;
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
