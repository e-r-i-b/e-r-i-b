package com.rssl.phizicgate.esberibgate.clients;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.externalsystem.AutoTechnoBreakConfig;
import com.rssl.phizic.gate.*;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankProductTypeWrapper;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientProductsService;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.gate.utils.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.bankroll.BankrollRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.*;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 *
 * Сейчас ClientProductsService используется при входе клиента только для обновления линков,
 * а также потому, что был сделан дабы не нагружать слишком сильно шину,
 * решено не перегружать этот метод огромным количеством запросов и делать ТОЛЬКО GFL.
 * Т.е. получается, что некоторые сущности будут НЕ до конца заполнены.
 * todo. После реализации групповых зарпосов шиной сделать как GFL,
 * todo. так и получение доп инфы по всем продуктам и полное заполнение сущностей
 *
 * @ author: filimonova
 * @ created: 10.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class ClientProductsServiceImpl extends AbstractService implements ClientProductsService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final Long CORRECT_MESSAGE_STATUS = 0L;
	private static final String CA_TB_CODE = "99";
	private static final Set<Long> OFFLINE_SYSTEM_STATUSES;
	private static final Set<BankProductType> POSSIBLE_AUTO_STOP_PRODUCTS_SYSTEM;

	static {
		OFFLINE_SYSTEM_STATUSES = new HashSet<Long>(3);
		OFFLINE_SYSTEM_STATUSES.add(-100L);
		OFFLINE_SYSTEM_STATUSES.add(-105L);
		OFFLINE_SYSTEM_STATUSES.add(-400L);
		POSSIBLE_AUTO_STOP_PRODUCTS_SYSTEM = new HashSet<BankProductType>(5);
		POSSIBLE_AUTO_STOP_PRODUCTS_SYSTEM.add(BankProductType.Card);
		POSSIBLE_AUTO_STOP_PRODUCTS_SYSTEM.add(BankProductType.Deposit);
		POSSIBLE_AUTO_STOP_PRODUCTS_SYSTEM.add(BankProductType.IMA);
		POSSIBLE_AUTO_STOP_PRODUCTS_SYSTEM.add(BankProductType.CardWay);
		POSSIBLE_AUTO_STOP_PRODUCTS_SYSTEM.add(BankProductType.Credit);
	}

	private static final String SRB_RB_TB_BRANCH_ID = "40400001";

	public ClientProductsServiceImpl(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public List<Pair<Card, AdditionalProductData>> getLightClientCards(Client client) throws GateLogicException, GateException
	{
		List<Pair<Card, AdditionalProductData>> result = new ArrayList<Pair<Card, AdditionalProductData>>();
		try
		{
			ESBEribConfig eribConfig = ConfigFactory.getConfig(ESBEribConfig.class);
			ExternalSystemHelper.check(eribConfig.getEsbERIBCardSystemId99Way());
		}
		catch (InactiveExternalSystemException ignored)
		{
			return result;
		}

		BankrollRequestHelper bankrollRequestHelper = new BankrollRequestHelper(getFactory());
		CardResponseSerializer cardResponseSerializer = new CardResponseSerializer();

		List<? extends ClientDocument> documents = client.getDocuments();
		for (ClientDocument document : documents)
		{
			ProductContainer productContainer = bankrollRequestHelper.createBankAcctInqRq(client, document, BankProductType.Card);
			IFXRs_Type ifxRs = getRequest(productContainer.getIfxRq_type());
			result.addAll(cardResponseSerializer.fillCards(ifxRs, null));
		}
		return result;
	}

	@SuppressWarnings({"ThrowableInstanceNeverThrown"})
	public GroupResult<Class, List<Pair<Object, AdditionalProductData>>> getClientProducts(Client client, Class... clazz)
	{
		BankrollRequestHelper bankrollRequestHelper = new BankrollRequestHelper(getFactory());
		LoanResponseSerializer loanSerializer = new LoanResponseSerializer();
		LongOfferResponseSerializer longOfferSerializer = new LongOfferResponseSerializer();
		CardResponseSerializer cardResponseSerializer = new CardResponseSerializer();
		AccountResponseSerializer accountResponseSerializer = new AccountResponseSerializer();
		DepoAccountsResponseSerializer depoAccountsResponseSerializer = new DepoAccountsResponseSerializer();
		IMAResponseSerializer imaResponseSerializer = new IMAResponseSerializer();
		SecurityAccountResponseSerializer securityAccountResponseSerializer = new SecurityAccountResponseSerializer();

		// Создаём копию списка документов, т.к. дальше потребуется его отсортировать (исходный список в client менять не хорошо)
		List<? extends ClientDocument> documents = new ArrayList<ClientDocument>(client.getDocuments());
		GroupResult<Class, List<Pair<Object, AdditionalProductData>>> result = new GroupResult<Class, List<Pair<Object, AdditionalProductData>>>();

		if (documents.isEmpty())
			return GroupResultHelper.getOneErrorResult(clazz, new GateException("Не заполнены документы для клиента с id=" + client.getId()));

		Collections.sort(documents, new DocumentTypeComparator());

		int badResponseCounter = 0;
		boolean badResponse = false;

		List<Class> usedProducts = collectDocumentProducts(clazz);
		Map<BankProductType, String> inactiveSystemErrors = new HashMap<BankProductType, String>();
		Set<BankProductType> offlineProductSystemSet = new HashSet<BankProductType>();

		for (ClientDocument document : documents)
		{
			//Если закончился список продуктов по которым мы ничего не получили, но хотели получить, выходим.
			if (CollectionUtils.isEmpty(usedProducts))
				break;

			Map<BankProductType, IFXRs_Type> ifXRsByProductMap = new HashMap<BankProductType, IFXRs_Type>();

			// Для некоторых видов документов (например, для Паспорта WAY)
			// диапазон продуктов, которые можно получить, существенно ограничен (BUG026747)
			BankProductType[] documentProducts = collectDocumentProducts(usedProducts, document);
			if (ArrayUtils.isEmpty(documentProducts))
				continue;

			try
			{
				ProductContainer productContainer = bankrollRequestHelper.createBankAcctInqRq(client, document, documentProducts);

				if (MapUtils.isNotEmpty(productContainer.getErrors()))
				{
					//записываем ошибки неактивности внешних систем
					productContainer.joinErrors(inactiveSystemErrors, productContainer.getErrors());
				}

				if (productContainer.getIfxRq_type() == null)
					continue;

				boolean requestTimeOut = false;
				//Посылаем запрос по каждому документу, пока не вернутся продукты, по каждому из документов.
				//Разные продукты могут быть зарегистрированы под разными документами в разных системах,
				//поэтому ищем по всем, запрашиваемым продуктам.
				IFXRs_Type ifxRs = null;
				try
				{
					ifxRs = getRequest(productContainer.getIfxRq_type());
				}
				catch (GateTimeOutException ignored)
				{
					requestTimeOut = true;
				}

				//Если вернулось -102, то повторяем запрос только с картами из WAY. Если карты вообще нужны были.
				// А также получение карт делаем от имени Среднерусского банка.
				if (!requestTimeOut && !cardResponseSerializer.isESBReallySupported(ifxRs) && usedProducts.contains(Card.class)
						&& ClientDocumentType.PASSPORT_WAY == document.getDocumentType())
				{
					logBadIFXResponse(ifxRs, client.getInternalOwnerId());

					productContainer = bankrollRequestHelper.createBankAcctInqRq(client, document, SRB_RB_TB_BRANCH_ID, BankProductType.CardWay);
					if (MapUtils.isNotEmpty(productContainer.getErrors()))
					{
						//записываем ошибки неактивности внешних систем
						productContainer.joinErrors(inactiveSystemErrors, productContainer.getErrors());
					}

					if (productContainer.getIfxRq_type() != null)
					{
						try
						{
							ifxRs = getRequest(productContainer.getIfxRq_type());
						}
						catch (GateTimeOutException ignored)
						{
							requestTimeOut = true;
						}
					}
				}

				if (logBadIFXResponse(ifxRs, client.getInternalOwnerId()))
				{
					if (!requestTimeOut)
					{
						// Ошибка -100 для 99 ТБ сигнализирует о недоступности шины в целом
						long statusCode = ifxRs.getBankAcctInqRs().getStatus().getStatusCode();
						ExtendedCodeGateImpl clientOfficeCode = new ExtendedCodeGateImpl(client.getOffice().getCode());
						if (statusCode == -100L && clientOfficeCode.getRegion().equals(CA_TB_CODE))
						{
							return GroupResultHelper.getOneErrorResult(clazz, new OfflineESBException());
						}
					}

					badResponse = true;
					if (productContainer.getProducts().size() == 1)
					{
						// Для систем, поддерживающих автоматическое выставление технологического перерыва,
						// фиксируем ошибки недоступности и таймаут шины
						BankProductType bankProductType = productContainer.getProducts().get(0);
						if (POSSIBLE_AUTO_STOP_PRODUCTS_SYSTEM.contains(bankProductType) && (requestTimeOut ||
								(ifxRs != null && OFFLINE_SYSTEM_STATUSES.contains(ifxRs.getBankAcctInqRs().getStatus().getStatusCode()))))
						{
							offlineProductSystemSet.add(bankProductType);
						}
						else
						{
							// Если получили ошибку, отличную от статусов недоступности и таймаута, фиксируем её
							badResponseCounter++;
							continue;
						}
					}
					else if (!ConfigFactory.getConfig(AutoTechnoBreakConfig.class).isAllowGFLForEachProduct() || (!requestTimeOut && (ifxRs == null ||
							!OFFLINE_SYSTEM_STATUSES.contains(ifxRs.getBankAcctInqRs().getStatus().getStatusCode()))))
					{
						// Если получили ошибку, отличную от статусов недоступности и таймаута, фиксируем её
						badResponseCounter++;
						continue;
					}
					else
					{
						// Делаем запросы по каждому из требуемых продуктов
						for (BankProductType bankProductType : productContainer.getProducts())
						{
							ProductContainer partlyProductContainer = bankrollRequestHelper.createBankAcctInqRq(client, document, bankProductType);
							boolean partlyRequestTimeout = false;
							IFXRs_Type partlyIfxRs = null;
							try
							{
								partlyIfxRs = getRequest(partlyProductContainer.getIfxRq_type());
							}
							catch (GateTimeOutException ignored)
							{
								partlyRequestTimeout = true;
							}

							if (partlyRequestTimeout || logBadIFXResponse(partlyIfxRs, client.getInternalOwnerId()))
							{
								// Для систем, поддерживающих автоматическое выставление технологического перерыва,
								// фиксируем ошибки недоступности и таймаут шины
								if ((partlyRequestTimeout || OFFLINE_SYSTEM_STATUSES.contains(partlyIfxRs.getBankAcctInqRs().getStatus().getStatusCode()))
										&& POSSIBLE_AUTO_STOP_PRODUCTS_SYSTEM.contains(bankProductType))
								{
									offlineProductSystemSet.add(bankProductType);
								}
							}
							else
							{
								ifXRsByProductMap.put(bankProductType, partlyIfxRs);
							}
						}
					}
				}

				//Заполнять нужно только те, что выбрали, что заполнили исключать из запросов
				Long internalOwnerId = client.getInternalOwnerId();

				//Если запрашивали карты, заполняем их и удаляем из списка типов продуктов для запроса
				updateResult(usedProducts, result, Card.class, cardResponseSerializer.fillCards(
						ifXRsByProductMap.get(BankProductType.Card) != null ? ifXRsByProductMap.get(BankProductType.Card) : ifxRs, internalOwnerId));

				//Если запрашивали счета\вклады, заполняем их и удаляем из списка типов продуктов для запроса
				updateResult(usedProducts, result, Account.class, accountResponseSerializer.fillAccounts(
						ifXRsByProductMap.get(BankProductType.Deposit) != null ? ifXRsByProductMap.get(BankProductType.Deposit) : ifxRs, internalOwnerId));

				//Если запрашивали кредиты, заполняем их и удаляем из списка типов продуктов для запроса
				updateResult(usedProducts, result, Loan.class, convertToPairList(loanSerializer.fillLoans(
						ifXRsByProductMap.get(BankProductType.Credit) != null ? ifXRsByProductMap.get(BankProductType.Credit) : ifxRs, internalOwnerId)));

				//Если запрашивали счета ДЕПО, заполняем их и удаляем из списка типов продуктов для запроса
				updateResult(usedProducts, result, DepoAccount.class, convertToPairList(depoAccountsResponseSerializer.fillDepoAccounts(
						ifXRsByProductMap.get(BankProductType.DepoAcc) != null ? ifXRsByProductMap.get(BankProductType.DepoAcc) : ifxRs, internalOwnerId)));

				//Если запрашивали ОМС, заполняем их и удаляем из списка типов продуктов для запроса
				updateResult(usedProducts, result, IMAccount.class, convertToPairList(imaResponseSerializer.extractIMAccountsFormResponse(
						ifXRsByProductMap.get(BankProductType.IMA) != null ? ifXRsByProductMap.get(BankProductType.IMA) : ifxRs, internalOwnerId)));

				//Если запрашивали сберегательные сертификаты, заполняем их и удаляем из списка типов продуктов для запроса
				if (usedProducts.contains(SecurityAccount.class))
				{
					updateResult(usedProducts, result, SecurityAccount.class, convertToPairList(securityAccountResponseSerializer.fillSecurityAccounts(productContainer.getIfxRq_type(),
							ifXRsByProductMap.get(BankProductType.Securities) != null ? ifXRsByProductMap.get(BankProductType.Securities) : ifxRs, internalOwnerId)));
				}

				//Если запрашивали Длительные поручения, заполняем их и удаляем из списка типов продуктов для запроса
				if (usedProducts.contains(LongOffer.class))
				{
					updateResult(usedProducts, result, LongOffer.class, convertToPairList(longOfferSerializer.fillLongOffers(productContainer.getIfxRq_type(),
							client, ifXRsByProductMap.get(BankProductType.LongOrd) != null ? ifXRsByProductMap.get(BankProductType.LongOrd) : ifxRs)));
				}
			}
			catch (GateException e)
			{
				return GroupResultHelper.getOneErrorResult(clazz, new OfflineESBException(e));
			}
			catch (GateLogicException e)
			{
				return GroupResultHelper.getOneErrorResult(clazz, e);
			}
		}

		//по всем продуктам получили ответ
		if (usedProducts.isEmpty())
			return result;

		if (badResponseCounter == documents.size())
			return GroupResultHelper.getOneErrorResult(clazz, new GateException("Ни по одному из документов клиента не получена информация о продуктах."));

		if (MapUtils.isNotEmpty(inactiveSystemErrors) && CollectionUtils.isNotEmpty(usedProducts))
		{
			//если по продукту ответ не получен, но есть исключение неактивности системы, должны положить сообщение об ошибке
			Iterator iterator = usedProducts.iterator();
			while (iterator.hasNext())
			{
				Class product = (Class) iterator.next();
				BankProductType productType = BankProductTypeWrapper.getBankProductType(product);
				if (inactiveSystemErrors.containsKey(productType))
				{
					result.putException(product, new GateLogicException(inactiveSystemErrors.get(productType)));
					iterator.remove();
				}
			}
		}

		if (badResponse && CollectionUtils.isNotEmpty(usedProducts))
		{
			//если по продукту ответ не получен, должны положить сообщение об ошибке
			Iterator iterator = usedProducts.iterator();
			while (iterator.hasNext())
			{
				Class product = (Class) iterator.next();
				if (offlineProductSystemSet.contains(BankProductTypeWrapper.getBankProductType(product)))
				{
					ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
					try
					{
						List<? extends ExternalSystem> externalSystems = externalSystemGateService.findByProduct(client.getOffice(), BankProductTypeWrapper.getBankProductType(product));
						result.putException(product, new OfflineExternalSystemException(externalSystems));
					}
					catch (GateException ignored)
					{
						result.putException(product, new GateLogicException(ProductContainer.DEFAULT_ERROR_MESSAGE));
					}
				}
				else if (product.equals(Card.class) && offlineProductSystemSet.contains(BankProductType.CardWay))
				{
					ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
					try
					{
						List<? extends ExternalSystem> externalSystems = externalSystemGateService.findByProduct(client.getOffice(), BankProductType.CardWay);
						result.putException(product, new OfflineExternalSystemException(externalSystems));
					}
					catch (GateException ignored)
					{
						result.putException(product, new GateLogicException(ProductContainer.DEFAULT_ERROR_MESSAGE));
					}
				}
				else
					result.putException(product, new GateLogicException(ProductContainer.DEFAULT_ERROR_MESSAGE));
				iterator.remove();
			}
		}

		//складываем продукты по которым ничего не получили
		for (Class usedProduct : usedProducts)
			result.putResult(usedProduct, Collections.<Pair<Object, AdditionalProductData>>emptyList());

		return result;
	}

	public GroupResult<Object, Boolean> updateProductPermission(Client client, List<Pair<Object, ProductPermission>> pairs) throws GateException, GateLogicException
	{
		if (pairs == null || pairs.isEmpty())
			throw new GateException("Не заполнен список счетов для изменения видимости. Id клиента - " + client.getId());

		GroupResult<Object, Boolean> result = new GroupResult<Object, Boolean>();
		BankrollRequestHelper bankrollRequestHelper = new BankrollRequestHelper(getFactory());

		// Создаём копию списка документов, т.к. дальше потребуется его отсортировать (исходный список в client менять не хорошо)
		List<? extends ClientDocument> documents = new ArrayList<ClientDocument>(client.getDocuments());
		if (documents.isEmpty())
			throw new GateException("Не заполнены документы для клиента с id=" + client.getId());
		Collections.sort(documents, new DocumentTypeComparator());
		Map<String, Account> accountsMap = getAccountsMap(pairs); //список счетов, по которым получаем ответ

		for (ClientDocument document : documents)
		{
			if (pairs.isEmpty()) //если список пуст - выходим
				break;

			IFXRq_Type ifxRq = bankrollRequestHelper.createBankAcctPermissModRq(client, document, pairs);
			IFXRs_Type ifxRs = getRequest(ifxRq);

			BankAcctPermissModRs_Type bankAcctPermissRs = ifxRs.getBankAcctPermissModRs();
			Status_Type statusType = bankAcctPermissRs.getStatus();
			if (statusType.getStatusCode() != 0)
			{
				ESBERIBExceptionStatisticHelper.throwErrorResponse(statusType, BankAcctPermissModRs_Type.class, ifxRq);
			}

			for (DepAcctRec_Type depAcctRec : bankAcctPermissRs.getBankAcctResult().getDepAcctRec())
			{
				depAcctRec.setBankInfo(depAcctRec.getDepAcctId().getBankInfo()); //для получения RbBrchId в EntityIdHelper

				Account account = findAccountByNumber(depAcctRec,accountsMap);
				if (account == null)
					throw new GateException("При изменении отображения продуктов вернулся счет с некорректным номером =" + depAcctRec.getDepAcctId().getAcctId());

				if (depAcctRec.getStatus().getStatusCode() == 0)
					result.putResult(account, Boolean.TRUE);
				else
					result.putResult(account, Boolean.FALSE);

				accountsMap.remove(account.getId());
			}
		}


		//добавляем отрицательный результат для счетов по которым не пришел ответ
		for (Account ac : accountsMap.values())
			result.putResult(ac, Boolean.FALSE);

		return result;

	}

	public GroupResult<Object, Boolean> updateOTPRestriction(Client client, List<Pair<Object, OTPRestriction>> pairs) throws GateException, GateLogicException
	{
		if (pairs == null || pairs.isEmpty())
			throw new GateException("Не заполнен список карт для изменения ограничения на печать и использование одноразовых паролей. Id клиента - " + client.getId());

		GroupResult<Object, Boolean> result = new GroupResult<Object, Boolean>();
		BankrollRequestHelper bankrollRequestHelper = new BankrollRequestHelper(getFactory());

		Map<String, Card> cardsMap = getCardsMap(pairs); //список карт, по которым получаем ответ

		IFXRq_Type ifxRq = bankrollRequestHelper.createOTPRestrictionModRq(client, pairs);
		IFXRs_Type ifxRs = getRequest(ifxRq);

		OTPRestrictionModRs_Type otpRestrictionModRs = ifxRs.getOTPRestrictionModRs();
		Status_Type statusType = otpRestrictionModRs.getStatus();
		if (statusType.getStatusCode() != 0)
		{
			ESBERIBExceptionStatisticHelper.throwErrorResponse(statusType, OTPRestrictionModRs_Type.class, ifxRq);
		}

		for (CardAcctRec_Type cardAcctRec : otpRestrictionModRs.getBankAcctResult().getCardAcctRec())
		{
			Card card = findCardByNumber(cardAcctRec, cardsMap);
			if (card == null)
				throw new GateException("При изменении ограничений на печать и использование одноразовых паролей вернулась карта с некорректным номером = " + cardAcctRec.getCardAcctId().getAcctId());

			if (cardAcctRec.getStatus().getStatusCode() == 0)
				result.putResult(card, Boolean.TRUE);
			else
				result.putResult(card, Boolean.FALSE);

			cardsMap.remove(card.getId());
		}

 		//добавляем отрицательный результат для карт по которым не пришел ответ
		for (Card ac : cardsMap.values())
			result.putResult(ac, Boolean.FALSE);

		return result;

	}

	/** поиск счета по вернувшимся из шины данным **/
	private Account findAccountByNumber(DepAcctRec_Type depAcctRec, Map<String, Account> accountsMap)
	{
		for(Account account : accountsMap.values())
		{
			String accountId = account.getId();
			CardOrAccountCompositeId compositeId = EntityIdHelper.getCardOrAccountCompositeId(accountId);
			DepAcctId_Type depAcctId = depAcctRec.getDepAcctId();
			if(compositeId.getEntityId().equals(depAcctId.getAcctId())
					&& compositeId.getSystemId().equals(depAcctId.getSystemId())
					&& compositeId.getRbBrchId().equals(depAcctId.getBankInfo().getRbBrchId()))
				return account;
		}
		return null;
	}

	/** поиск карты по вернувшимся из шины данным **/
	private Card findCardByNumber(CardAcctRec_Type cardAcctRec, Map<String, Card> cardMap)
	{
		for(Card card : cardMap.values())
		{
			String cardID = card.getId();
			CardOrAccountCompositeId compositeId = EntityIdHelper.getCardOrAccountCompositeId(cardID);
			CardAcctId_Type cardAcctId = cardAcctRec.getCardAcctId();
			if(compositeId.getEntityId().equals(cardAcctId.getCardNum()))
				return card;
		}
		return null;
	}

	private <T> void updateResult(List<Class> userProducts, GroupResult<Class, List<Pair<Object, AdditionalProductData>>> result, Class<T> clazz, List<Pair<T, AdditionalProductData>> products)
	{
		//Если мы вообще запрашивали такой продукт и если в ответе пришел список продуктов
		if (!userProducts.contains(clazz) || CollectionUtils.isEmpty(products))
			return;

		result.putResult(clazz, fillProductsList(products));
		userProducts.remove(clazz);
	}

	private Map<String, Account> getAccountsMap(List<Pair<Object, ProductPermission>> pairs) throws GateException
	{
		Map<String, Account> result = new HashMap<String, Account>();
		for (Pair<Object, ProductPermission> pair : pairs)
		{
			if (pair.getFirst() instanceof Account)
			{
				Account account = (Account) pair.getFirst();
				result.put(account.getId(), account);
			}
			else
			{
				throw new GateException("Некорректный тип объекта. Должен быть Account");
			}
		}

		return result;
	}

	private Map<String, Card> getCardsMap(List<Pair<Object, OTPRestriction>> pairs) throws GateException
	{
		Map<String, Card> result = new HashMap<String, Card>();
		for (Pair<Object, OTPRestriction> pair : pairs)
		{
			if (pair.getFirst() instanceof Card)
			{
				Card card = (Card) pair.getFirst();
				result.put(card.getId(), card);
			}
			else
			{
				throw new GateException("Некорректный тип объекта. Должен быть Card");
			}
		}

		return result;
	}

	private <T> List fillProductsList(List<Pair<T, AdditionalProductData>> products)
	{
		List<Pair<T, AdditionalProductData>> productsList = new ArrayList<Pair<T, AdditionalProductData>>();
		productsList.addAll(products);
		return productsList;
	}

	private <T extends Object> List<Pair<T, AdditionalProductData>> convertToPairList(List<T> products)
	{
		List<Pair<T, AdditionalProductData>> result = new ArrayList<Pair<T, AdditionalProductData>>();
		for (T obj : products)
			result.add(new Pair<T, AdditionalProductData>(obj, null));

		return result;
	}

	/**
	 * Выбирает список видов продуктов, которые можно получить по указанному документу
	 * @param products - выборка классов (видов) продуктов
	 * @return список видов продуктов (never null)
	 */
	private static List<Class> collectDocumentProducts(Class ... products)
	{
		//Если ничего не передали, значит нужно получать данные по всем банковским продуктам.
		if (ArrayUtils.isEmpty(products))
		{
			//Т.е. надо реально весь список сюда прописать. Т.к. потом убираются продукты, по которым найдены данные
			//Сделано из-за множества и разнообразия документов
			return BankProductTypeWrapper.getProductTypeList();
		}
		return ListUtil.fromArray(products);
	}

	/**
	 * Выбирает список видов продуктов, которые можно получить по указанному документу
	 * @param products - выборка классов (видов) продуктов
	 * @param document - документ
	 * @return список видов продуктов (never null)
	 */
	private static List<Class> collectDocumentProducts(Collection<Class> products, ClientDocument document)
	{
		if (CollectionUtils.isEmpty(products))
			return Collections.emptyList();

		switch (document.getDocumentType())
		{
			// По паспорту way можно получить только список карт
			case PASSPORT_WAY:
				//noinspection unchecked
				return ListUtils.retainAll(products, Collections.singleton(Card.class));
			default:
				return new ArrayList<Class>(products);
		}
	}

	/**
	 * Выбирает список видов продуктов, которые можно получить по указанному документу
	 * @param products - выборка классов (видов) продуктов
	 * @param document - документ
	 * @return список видов продуктов (never null)
	 */
	private static BankProductType[] collectDocumentProducts(List<Class> products, ClientDocument document)
	{
		if (CollectionUtils.isEmpty(products))
			return new BankProductType[]{};

		//по паспорту way запрашиваем только карты
		if (ClientDocumentType.PASSPORT_WAY == document.getDocumentType())
		{
			if (products.contains(Card.class))
				return new BankProductType[]{BankProductType.CardWay};
			return new BankProductType[]{}; 
		}

		return BankProductTypeWrapper.getBankProductTypes(products);
	}

	private static boolean logBadIFXResponse(IFXRs_Type ifxRs, Long clientId)
	{
		if (ifxRs == null)
			return true;

		BankAcctInqRs_Type bankAcctInqRs = ifxRs.getBankAcctInqRs();
		Status_Type status = bankAcctInqRs.getStatus();
		long statusCode = status.getStatusCode();
		if (statusCode != CORRECT_MESSAGE_STATUS)
		{
			log.error("Вернулось сообщение с ошибкой. Ошибка номер " + statusCode + ". "
					+ "ID клиента " + clientId + ". "
					+ status.getStatusDesc());
			return true;
		}
		return false;
	}
}
