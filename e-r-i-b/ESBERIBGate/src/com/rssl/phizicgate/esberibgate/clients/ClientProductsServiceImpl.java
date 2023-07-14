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
 * ������ ClientProductsService ������������ ��� ����� ������� ������ ��� ���������� ������,
 * � ����� ������, ��� ��� ������ ���� �� ��������� ������� ������ ����,
 * ������ �� ����������� ���� ����� �������� ����������� �������� � ������ ������ GFL.
 * �.�. ����������, ��� ��������� �������� ����� �� �� ����� ���������.
 * todo. ����� ���������� ��������� �������� ����� ������� ��� GFL,
 * todo. ��� � ��������� ��� ���� �� ���� ��������� � ������ ���������� ���������
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

		// ������ ����� ������ ����������, �.�. ������ ����������� ��� ������������� (�������� ������ � client ������ �� ������)
		List<? extends ClientDocument> documents = new ArrayList<ClientDocument>(client.getDocuments());
		GroupResult<Class, List<Pair<Object, AdditionalProductData>>> result = new GroupResult<Class, List<Pair<Object, AdditionalProductData>>>();

		if (documents.isEmpty())
			return GroupResultHelper.getOneErrorResult(clazz, new GateException("�� ��������� ��������� ��� ������� � id=" + client.getId()));

		Collections.sort(documents, new DocumentTypeComparator());

		int badResponseCounter = 0;
		boolean badResponse = false;

		List<Class> usedProducts = collectDocumentProducts(clazz);
		Map<BankProductType, String> inactiveSystemErrors = new HashMap<BankProductType, String>();
		Set<BankProductType> offlineProductSystemSet = new HashSet<BankProductType>();

		for (ClientDocument document : documents)
		{
			//���� ���������� ������ ��������� �� ������� �� ������ �� ��������, �� ������ ��������, �������.
			if (CollectionUtils.isEmpty(usedProducts))
				break;

			Map<BankProductType, IFXRs_Type> ifXRsByProductMap = new HashMap<BankProductType, IFXRs_Type>();

			// ��� ��������� ����� ���������� (��������, ��� �������� WAY)
			// �������� ���������, ������� ����� ��������, ����������� ��������� (BUG026747)
			BankProductType[] documentProducts = collectDocumentProducts(usedProducts, document);
			if (ArrayUtils.isEmpty(documentProducts))
				continue;

			try
			{
				ProductContainer productContainer = bankrollRequestHelper.createBankAcctInqRq(client, document, documentProducts);

				if (MapUtils.isNotEmpty(productContainer.getErrors()))
				{
					//���������� ������ ������������ ������� ������
					productContainer.joinErrors(inactiveSystemErrors, productContainer.getErrors());
				}

				if (productContainer.getIfxRq_type() == null)
					continue;

				boolean requestTimeOut = false;
				//�������� ������ �� ������� ���������, ���� �� �������� ��������, �� ������� �� ����������.
				//������ �������� ����� ���� ���������������� ��� ������� ����������� � ������ ��������,
				//������� ���� �� ����, ������������� ���������.
				IFXRs_Type ifxRs = null;
				try
				{
					ifxRs = getRequest(productContainer.getIfxRq_type());
				}
				catch (GateTimeOutException ignored)
				{
					requestTimeOut = true;
				}

				//���� ��������� -102, �� ��������� ������ ������ � ������� �� WAY. ���� ����� ������ ����� ����.
				// � ����� ��������� ���� ������ �� ����� �������������� �����.
				if (!requestTimeOut && !cardResponseSerializer.isESBReallySupported(ifxRs) && usedProducts.contains(Card.class)
						&& ClientDocumentType.PASSPORT_WAY == document.getDocumentType())
				{
					logBadIFXResponse(ifxRs, client.getInternalOwnerId());

					productContainer = bankrollRequestHelper.createBankAcctInqRq(client, document, SRB_RB_TB_BRANCH_ID, BankProductType.CardWay);
					if (MapUtils.isNotEmpty(productContainer.getErrors()))
					{
						//���������� ������ ������������ ������� ������
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
						// ������ -100 ��� 99 �� ������������� � ������������� ���� � �����
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
						// ��� ������, �������������� �������������� ����������� ���������������� ��������,
						// ��������� ������ ������������� � ������� ����
						BankProductType bankProductType = productContainer.getProducts().get(0);
						if (POSSIBLE_AUTO_STOP_PRODUCTS_SYSTEM.contains(bankProductType) && (requestTimeOut ||
								(ifxRs != null && OFFLINE_SYSTEM_STATUSES.contains(ifxRs.getBankAcctInqRs().getStatus().getStatusCode()))))
						{
							offlineProductSystemSet.add(bankProductType);
						}
						else
						{
							// ���� �������� ������, �������� �� �������� ������������� � ��������, ��������� �
							badResponseCounter++;
							continue;
						}
					}
					else if (!ConfigFactory.getConfig(AutoTechnoBreakConfig.class).isAllowGFLForEachProduct() || (!requestTimeOut && (ifxRs == null ||
							!OFFLINE_SYSTEM_STATUSES.contains(ifxRs.getBankAcctInqRs().getStatus().getStatusCode()))))
					{
						// ���� �������� ������, �������� �� �������� ������������� � ��������, ��������� �
						badResponseCounter++;
						continue;
					}
					else
					{
						// ������ ������� �� ������� �� ��������� ���������
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
								// ��� ������, �������������� �������������� ����������� ���������������� ��������,
								// ��������� ������ ������������� � ������� ����
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

				//��������� ����� ������ ��, ��� �������, ��� ��������� ��������� �� ��������
				Long internalOwnerId = client.getInternalOwnerId();

				//���� ����������� �����, ��������� �� � ������� �� ������ ����� ��������� ��� �������
				updateResult(usedProducts, result, Card.class, cardResponseSerializer.fillCards(
						ifXRsByProductMap.get(BankProductType.Card) != null ? ifXRsByProductMap.get(BankProductType.Card) : ifxRs, internalOwnerId));

				//���� ����������� �����\������, ��������� �� � ������� �� ������ ����� ��������� ��� �������
				updateResult(usedProducts, result, Account.class, accountResponseSerializer.fillAccounts(
						ifXRsByProductMap.get(BankProductType.Deposit) != null ? ifXRsByProductMap.get(BankProductType.Deposit) : ifxRs, internalOwnerId));

				//���� ����������� �������, ��������� �� � ������� �� ������ ����� ��������� ��� �������
				updateResult(usedProducts, result, Loan.class, convertToPairList(loanSerializer.fillLoans(
						ifXRsByProductMap.get(BankProductType.Credit) != null ? ifXRsByProductMap.get(BankProductType.Credit) : ifxRs, internalOwnerId)));

				//���� ����������� ����� ����, ��������� �� � ������� �� ������ ����� ��������� ��� �������
				updateResult(usedProducts, result, DepoAccount.class, convertToPairList(depoAccountsResponseSerializer.fillDepoAccounts(
						ifXRsByProductMap.get(BankProductType.DepoAcc) != null ? ifXRsByProductMap.get(BankProductType.DepoAcc) : ifxRs, internalOwnerId)));

				//���� ����������� ���, ��������� �� � ������� �� ������ ����� ��������� ��� �������
				updateResult(usedProducts, result, IMAccount.class, convertToPairList(imaResponseSerializer.extractIMAccountsFormResponse(
						ifXRsByProductMap.get(BankProductType.IMA) != null ? ifXRsByProductMap.get(BankProductType.IMA) : ifxRs, internalOwnerId)));

				//���� ����������� �������������� �����������, ��������� �� � ������� �� ������ ����� ��������� ��� �������
				if (usedProducts.contains(SecurityAccount.class))
				{
					updateResult(usedProducts, result, SecurityAccount.class, convertToPairList(securityAccountResponseSerializer.fillSecurityAccounts(productContainer.getIfxRq_type(),
							ifXRsByProductMap.get(BankProductType.Securities) != null ? ifXRsByProductMap.get(BankProductType.Securities) : ifxRs, internalOwnerId)));
				}

				//���� ����������� ���������� ���������, ��������� �� � ������� �� ������ ����� ��������� ��� �������
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

		//�� ���� ��������� �������� �����
		if (usedProducts.isEmpty())
			return result;

		if (badResponseCounter == documents.size())
			return GroupResultHelper.getOneErrorResult(clazz, new GateException("�� �� ������ �� ���������� ������� �� �������� ���������� � ���������."));

		if (MapUtils.isNotEmpty(inactiveSystemErrors) && CollectionUtils.isNotEmpty(usedProducts))
		{
			//���� �� �������� ����� �� �������, �� ���� ���������� ������������ �������, ������ �������� ��������� �� ������
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
			//���� �� �������� ����� �� �������, ������ �������� ��������� �� ������
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

		//���������� �������� �� ������� ������ �� ��������
		for (Class usedProduct : usedProducts)
			result.putResult(usedProduct, Collections.<Pair<Object, AdditionalProductData>>emptyList());

		return result;
	}

	public GroupResult<Object, Boolean> updateProductPermission(Client client, List<Pair<Object, ProductPermission>> pairs) throws GateException, GateLogicException
	{
		if (pairs == null || pairs.isEmpty())
			throw new GateException("�� �������� ������ ������ ��� ��������� ���������. Id ������� - " + client.getId());

		GroupResult<Object, Boolean> result = new GroupResult<Object, Boolean>();
		BankrollRequestHelper bankrollRequestHelper = new BankrollRequestHelper(getFactory());

		// ������ ����� ������ ����������, �.�. ������ ����������� ��� ������������� (�������� ������ � client ������ �� ������)
		List<? extends ClientDocument> documents = new ArrayList<ClientDocument>(client.getDocuments());
		if (documents.isEmpty())
			throw new GateException("�� ��������� ��������� ��� ������� � id=" + client.getId());
		Collections.sort(documents, new DocumentTypeComparator());
		Map<String, Account> accountsMap = getAccountsMap(pairs); //������ ������, �� ������� �������� �����

		for (ClientDocument document : documents)
		{
			if (pairs.isEmpty()) //���� ������ ���� - �������
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
				depAcctRec.setBankInfo(depAcctRec.getDepAcctId().getBankInfo()); //��� ��������� RbBrchId � EntityIdHelper

				Account account = findAccountByNumber(depAcctRec,accountsMap);
				if (account == null)
					throw new GateException("��� ��������� ����������� ��������� �������� ���� � ������������ ������� =" + depAcctRec.getDepAcctId().getAcctId());

				if (depAcctRec.getStatus().getStatusCode() == 0)
					result.putResult(account, Boolean.TRUE);
				else
					result.putResult(account, Boolean.FALSE);

				accountsMap.remove(account.getId());
			}
		}


		//��������� ������������� ��������� ��� ������ �� ������� �� ������ �����
		for (Account ac : accountsMap.values())
			result.putResult(ac, Boolean.FALSE);

		return result;

	}

	public GroupResult<Object, Boolean> updateOTPRestriction(Client client, List<Pair<Object, OTPRestriction>> pairs) throws GateException, GateLogicException
	{
		if (pairs == null || pairs.isEmpty())
			throw new GateException("�� �������� ������ ���� ��� ��������� ����������� �� ������ � ������������� ����������� �������. Id ������� - " + client.getId());

		GroupResult<Object, Boolean> result = new GroupResult<Object, Boolean>();
		BankrollRequestHelper bankrollRequestHelper = new BankrollRequestHelper(getFactory());

		Map<String, Card> cardsMap = getCardsMap(pairs); //������ ����, �� ������� �������� �����

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
				throw new GateException("��� ��������� ����������� �� ������ � ������������� ����������� ������� ��������� ����� � ������������ ������� = " + cardAcctRec.getCardAcctId().getAcctId());

			if (cardAcctRec.getStatus().getStatusCode() == 0)
				result.putResult(card, Boolean.TRUE);
			else
				result.putResult(card, Boolean.FALSE);

			cardsMap.remove(card.getId());
		}

 		//��������� ������������� ��������� ��� ���� �� ������� �� ������ �����
		for (Card ac : cardsMap.values())
			result.putResult(ac, Boolean.FALSE);

		return result;

	}

	/** ����� ����� �� ����������� �� ���� ������ **/
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

	/** ����� ����� �� ����������� �� ���� ������ **/
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
		//���� �� ������ ����������� ����� ������� � ���� � ������ ������ ������ ���������
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
				throw new GateException("������������ ��� �������. ������ ���� Account");
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
				throw new GateException("������������ ��� �������. ������ ���� Card");
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
	 * �������� ������ ����� ���������, ������� ����� �������� �� ���������� ���������
	 * @param products - ������� ������� (�����) ���������
	 * @return ������ ����� ��������� (never null)
	 */
	private static List<Class> collectDocumentProducts(Class ... products)
	{
		//���� ������ �� ��������, ������ ����� �������� ������ �� ���� ���������� ���������.
		if (ArrayUtils.isEmpty(products))
		{
			//�.�. ���� ������� ���� ������ ���� ���������. �.�. ����� ��������� ��������, �� ������� ������� ������
			//������� ��-�� ��������� � ������������ ����������
			return BankProductTypeWrapper.getProductTypeList();
		}
		return ListUtil.fromArray(products);
	}

	/**
	 * �������� ������ ����� ���������, ������� ����� �������� �� ���������� ���������
	 * @param products - ������� ������� (�����) ���������
	 * @param document - ��������
	 * @return ������ ����� ��������� (never null)
	 */
	private static List<Class> collectDocumentProducts(Collection<Class> products, ClientDocument document)
	{
		if (CollectionUtils.isEmpty(products))
			return Collections.emptyList();

		switch (document.getDocumentType())
		{
			// �� �������� way ����� �������� ������ ������ ����
			case PASSPORT_WAY:
				//noinspection unchecked
				return ListUtils.retainAll(products, Collections.singleton(Card.class));
			default:
				return new ArrayList<Class>(products);
		}
	}

	/**
	 * �������� ������ ����� ���������, ������� ����� �������� �� ���������� ���������
	 * @param products - ������� ������� (�����) ���������
	 * @param document - ��������
	 * @return ������ ����� ��������� (never null)
	 */
	private static BankProductType[] collectDocumentProducts(List<Class> products, ClientDocument document)
	{
		if (CollectionUtils.isEmpty(products))
			return new BankProductType[]{};

		//�� �������� way ����������� ������ �����
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
			log.error("��������� ��������� � �������. ������ ����� " + statusCode + ". "
					+ "ID ������� " + clientId + ". "
					+ status.getStatusDesc());
			return true;
		}
		return false;
	}
}
